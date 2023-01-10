package pers.beans.smpmvvm.application

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/**
 * @ProjectName:    Simple Mvvm Demo
 * @Package:        pers.beans.smpmvvm.application
 * @ClassName:      MyApplication
 * @Description:    java类作用描述
 * @Author:         Beans mac
 * @CreateDate:     2023/1/9 11:06 上午
 * @Version:        1.0
 */
@HiltAndroidApp
class MyApplication : Application() {

    companion object {
        lateinit var context : Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}