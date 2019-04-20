package com.emmanuelkehinde.softcomcodingtest.data.repo

import android.content.Context
import com.emmanuelkehinde.softcomcodingtest.data.model.Form
import com.google.gson.Gson
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
            val jsonFile = context.assets.open("form/pet_adoption-1.json.json")
            val size = jsonFile.available()
            val buffer = ByteArray(size)
            jsonFile.read(buffer)
            jsonFile.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        return json
    }
}