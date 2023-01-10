package pers.beans.smpmvvm.net.di

import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.hilt.InstallIn
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pers.beans.smpmvvm.BuildConfig
import pers.beans.smpmvvm.application.MyApplication
import pers.beans.smpmvvm.net.NetUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @ProjectName:    AndroidBaseMVVM
 * @Package:        com.beans.common.net.di
 * @ClassName:      DINetworkModule
 * @Description:    全局网络依赖注入
 * @Author:         Beans mac
 * @CreateDate:     2022/12/26 10:45 上午
 * @Version:        1.0
 */
@Module
@InstallIn(SingletonComponent::class)
class DINetworkProvider {


    private val BODY by lazy(mode = LazyThreadSafetyMode.NONE) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val NONE by lazy(mode = LazyThreadSafetyMode.NONE) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    private infix fun HttpLoggingInterceptor.or(none : HttpLoggingInterceptor) : HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) this else none
    }

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val CONNECT_TIME_OUT = 15L
        val READ_TIME_OUT = 20L

        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(BODY or NONE)
            .addInterceptor(ChuckInterceptor(MyApplication.context))
            .retryOnConnectionFailure(true)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetUrl.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}