package pers.beans.smpmvvm.page.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import pers.beans.smpmvvm.bean.GitEvents
import pers.beans.smpmvvm.mvvm.vm.BaseViewModel
import javax.inject.Inject

/**
 * @ProjectName:    Simple Mvvm Demo
 * @Package:        pers.beans.smpmvvm.page.main
 * @ClassName:      MainViewModel
 * @Description:    java类作用描述
 * @Author:         Beans mac
 * @CreateDate:     2023/1/9 3:05 下午
 * @Version:        1.0
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val mRepo : MainRepository) : BaseViewModel<MainRepository>() {
    override fun initRepository() = MainRepository()

    private val _gitEventLiveData = MutableLiveData<List<GitEvents>>()

    val gitEventList : LiveData<List<GitEvents>> = _gitEventLiveData

    var eventsMutableList = mutableListOf<GitEvents>()

    fun requestGitEvent () {
        viewModelScope.launch { 
            mRepo.getGitEvents()
                // 开始请求的操作
                .onStart {
                    isLoading.postValue(true)
                }
                //请求异常时，捕获异常
                .catch {
                    isLoading.postValue(false)
                }
                .onCompletion {
                    isLoading.postValue(false)
                }
                .collect {
                    eventsMutableList.addAll(it)
                    _gitEventLiveData.postValue(it)
                }
        }
    }

}