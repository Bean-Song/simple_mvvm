package pers.beans.smpmvvm.page.main.net

import pers.beans.smpmvvm.bean.GitEvents
import retrofit2.http.GET

interface MainApiService {

    @GET("/events")
    suspend fun getGitEvents() : List<GitEvents>

}
