package com.emmanuelkehinde.softcomcodingtest

import com.emmanuelkehinde.softcomcodingtest.data.model.Form
import com.emmanuelkehinde.softcomcodingtest.data.repo.FormRepository
import com.emmanuelkehinde.softcomcodingtest.di.module.AppModule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class FormRepositoryTest {

    @JvmField
    var app = App()

    @Inject
    lateinit var formRepository: FormRepository

    @Before
    fun setup() {
        setupDI()
    }

    private fun setupDI() {
        val testAppComponent = DaggerTestAppComponent.builder()
            .appModule(AppModule(app))
            .build()
        testAppComponent.inject(this)
    }

//    @Test
//    fun formIsNotNull() {
//        val form = formRepository.getForm()
//        assertNotNull(form)
//    }
//
//    @Test
//    fun formNameIsSet() {
//        val form = formRepository.getForm()
//        assertEquals("Pet Adoption Application Form",form?.name)
//    }

    @Test
    fun formHasPages() {

    }
}