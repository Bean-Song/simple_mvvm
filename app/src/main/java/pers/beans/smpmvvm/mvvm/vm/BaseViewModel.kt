package pers.beans.smpmvvm.mvvm.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pers.beans.smpmvvm.mvvm.m.BaseRepository

/**
 * @Class: BaseViewModel
 * @Remark: ViewModel 基类
 */
abstract class BaseViewModel<R : BaseRepository> : ViewModel() {

    // Loading 状态
    val isLoading = MutableLiveData(false)

    // 请求异常
    val requestError = MutableLiveData<Throwable?>()

    protected val mRepository: R by lazy { initRepository() }

    protected abstract fun initRepository(): R
}