package pers.beans.smpmvvm.page.main

import dagger.hilt.android.AndroidEntryPoint
import pers.beans.smpmvvm.databinding.ActivityMainBinding
import pers.beans.smpmvvm.ktx.gone
import pers.beans.smpmvvm.ktx.visible
import pers.beans.smpmvvm.mvvm.v.BaseFrameActivity

@AndroidEntryPoint
class MainActivity : BaseFrameActivity<ActivityMainBinding, MainViewModel>() {

    override fun ActivityMainBinding.initView() {

        mBinding.info.setOnClickListener() {
            mViewModel.requestGitEvent()
        }
    }

    override fun initLiveDataObserve() {

        mViewModel.gitEventList.observe(this) {
            var names = ""
            it.forEach { event ->
                names += "${event.actor?.login}\n"
            }
            mBinding.info.text = names
        }

        mViewModel.isLoading.observe(this) { loading ->
            if (loading) {
                mBinding.progressBar.visible()
                mBinding.info.gone()
            } else {
                mBinding.progressBar.gone()
                mBinding.info.visible()

            }
        }

    }

    override fun initRequestData() {
        mViewModel.requestGitEvent()
    }
}