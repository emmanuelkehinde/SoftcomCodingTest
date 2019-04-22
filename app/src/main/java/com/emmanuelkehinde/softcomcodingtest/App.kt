package com.emmanuelkehinde.softcomcodingtest

import android.app.Application
import com.emmanuelkehinde.softcomcodingtest.di.component.AppComponent
import com.emmanuelkehinde.softcomcodingtest.di.component.DaggerAppComponent
import com.emmanuelkehinde.softcomcodingtest.di.module.AppModule
import com.squareup.leakcanary.LeakCanary

class App: Application() {

    private lateinit var appComponent: AppComponent

    companion object {
        private lateinit var mInstance: App

        fun getInstance(): App = mInstance
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
//        initLeakCanary()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    fun getAppComponent(): AppComponent = appComponent
}