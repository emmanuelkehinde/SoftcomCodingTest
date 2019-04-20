package com.emmanuelkehinde.softcomcodingtest.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.emmanuelkehinde.softcomcodingtest.App
import com.emmanuelkehinde.softcomcodingtest.data.repo.FormRepository
import com.emmanuelkehinde.softcomcodingtest.ui.form.FormInteractor
import com.emmanuelkehinde.softcomcodingtest.util.ImageUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var app: App) {

    @Provides
    @Singleton
    internal fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideFormRepository(context: Context, gson: Gson): FormRepository {
        return FormRepository(context, gson)
    }

}