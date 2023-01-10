package pers.beans.smpmvvm.page.main.net.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pers.beans.smpmvvm.page.main.net.MainApiService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

/**
 * @ProjectName:    Simple Mvvm Demo
 * @Package:        pers.beans.smpmvvm.page.main.net.di
 * @ClassName:      Git
 * @Description:    java类作用描述
 * @Author:         Beans mac
 * @CreateDate:     2023/1/9 5:25 下午
 * @Version:        1.0
 */
@Module
@InstallIn(SingletonComponent::class)
class GitEventsApiServiceModel {

    @Singleton
    @Provides
    fun provideGitEventsApiService (retrofit: Retrofit) : MainApiService {
        return retrofit.create(MainApiService::class.java)
    }
}