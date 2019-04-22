package com.emmanuelkehinde.softcomcodingtest

import com.emmanuelkehinde.softcomcodingtest.di.module.AppModule
import com.emmanuelkehinde.softcomcodingtest.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (ViewModelModule::class)])
interface TestAppComponent {
    fun inject(formRepositoryTest: FormRepositoryTest)
}