package pers.beans.smpmvvm.page.main

import pers.beans.smpmvvm.bean.GitEvents
import pers.beans.smpmvvm.mvvm.m.BaseRepository
import pers.beans.smpmvvm.page.main.net.MainApiService
import javax.inject.Inject

class MainRepository @Inject constructor() : BaseRepository()  {

    @Inject
    lateinit var mApi: MainApiService

    suspend fun getGitEvents()  = flowRequest<List<GitEvents>> {
        mApi.getGitEvents().apply {
            emit(this)
        }
    }

}
