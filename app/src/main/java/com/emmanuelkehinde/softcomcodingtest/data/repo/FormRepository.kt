package com.emmanuelkehinde.softcomcodingtest.data.repo

import android.content.Context
import com.emmanuelkehinde.softcomcodingtest.data.FORM_FILEPATH
import com.emmanuelkehinde.softcomcodingtest.data.model.Form
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.IOException

class FormRepository(var context: Context, var gson: Gson) {

    fun getForm(): Form? {
        var form: Form?
        val jsonString = loadJsonAsStringFromAsset()

        jsonString.let {
            form = gson.fromJson(jsonString, Form::class.java)
        }

        return form
    }

    private fun loadJsonAsStringFromAsset(): String? {
        var json: String? = null

        try {
            runBlocking {
                launch {
                    val jsonFile = withContext(Dispatchers.Default) {
                        context.assets.open(FORM_FILEPATH)
                    }
                    val size = jsonFile.available()
                    val buffer = ByteArray(size)
                    jsonFile.read(buffer)
                    jsonFile.close()
                    json = String(buffer, Charsets.UTF_8)
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        return json
    }
}