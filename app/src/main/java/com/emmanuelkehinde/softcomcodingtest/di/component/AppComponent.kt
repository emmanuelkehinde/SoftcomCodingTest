package com.emmanuelkehinde.softcomcodingtest.di.component

import com.emmanuelkehinde.softcomcodingtest.di.module.AppModule
import com.emmanuelkehinde.softcomcodingtest.di.module.ViewModelModule
import com.emmanuelkehinde.softcomcodingtest.ui.form.FormActivity
import com.emmanuelkehinde.softcomcodingtest.ui.form.FormInteractor
import com.emmanuelkehinde.softcomcodingtest.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (ViewModelModule::class)])
interface AppComponent {
    fun inject(view: MainActivity)
    fun inject(view: FormActivity)
}