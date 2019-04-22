package com.emmanuelkehinde.softcomcodingtest.extension

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showConfirmDialog(posText: String, posFunc: () -> Unit, negText: String, negFunc: () -> Unit = {}, msg: String) {
    val builder = AlertDialog.Builder(this)
        .setPositiveButton(posText) { _, _ ->
            posFunc.invoke()
        }.setNegativeButton(negText) { dialog, _ ->
            negFunc.invoke()
            dialog.dismiss()
        }
    val ad = builder.create()
    ad.setMessage(msg)
    ad.show()
}