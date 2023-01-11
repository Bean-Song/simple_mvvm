package pers.beans.smpmvvm.page.main

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pers.beans.smpmvvm.bean.GitEvents
import pers.beans.smpmvvm.databinding.ActivityMainBinding
import pers.beans.smpmvvm.ktx.gone
import pers.beans.smpmvvm.ktx.observeLiveData
import pers.beans.smpmvvm.ktx.visible
import pers.beans.smpmvvm.mvvm.v.BaseFrameActivity
import pers.beans.smpmvvm.page.main.adapter.EventsAdapter
import pers.beans.smpmvvm.utils.toastShow
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseFrameActivity<ActivityMainBinding, MainViewModel>() {

    // adapter
    @Inject
    lateinit var eventsAdapter: EventsAdapter

    override fun ActivityMainBinding.initView() {

        mBinding.info.setOnClickListener() {
            mViewModel.requestGitEvent()
        }

        eventsAdapter.apply {
            setOnItemClickListener{ adapter, view , position ->
                toastShow("${(adapter.data[position] as GitEvents).id}")
            }
        }

        mBinding.rvEventMain.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = eventsAdapter
        }



    }

    override fun initLiveDataObserve() {

        observeLiveData(mViewModel.gitEventList, ::loadList)

        mViewModel.gitEventList.observe(this) { eventList ->
            var names = ""
            eventList.forEach { event ->
                names += "${event.actor?.login}\n"
            }
            mBinding.info.text = names
        }

        mViewModel.isLoading.observe(this) { loading ->


            if (loading) {
                mBinding.progressBar.visible()
                //mBinding.info.gone()
            } else {
                mBinding.progressBar.gone()
                //mBinding.info.visible()

            }
        }

    }

    private fun loadList(list: List<GitEvents>) {
        eventsAdapter.setNewInstance(mViewModel.eventsMutableList)
    }

    override fun initRequestData() {
        mViewModel.requestGitEvent()
    }
}