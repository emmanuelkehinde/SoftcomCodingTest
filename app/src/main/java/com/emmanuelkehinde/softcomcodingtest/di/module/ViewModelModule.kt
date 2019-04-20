package com.emmanuelkehinde.softcomcodingtest.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emmanuelkehinde.softcomcodingtest.ui.main.MainViewModel
import com.emmanuelkehinde.softcomcodingtest.viewmodel.ViewModelFactory
import com.emmanuelkehinde.softcomcodingtest.di.annotation.ViewModelKey
import com.emmanuelkehinde.softcomcodingtest.ui.form.FormViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FormViewModel::class)
    internal abstract fun formViewModel(viewModel: FormViewModel): ViewModel

}