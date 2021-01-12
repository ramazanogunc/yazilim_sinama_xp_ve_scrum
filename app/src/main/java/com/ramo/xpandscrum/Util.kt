package com.ramo.xpandscrum

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import com.ramo.xpandscrum.model.User
import java.text.SimpleDateFormat
import java.util.*

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}


fun predictDateFromDescription(description: String): Int {
    val space = description.split(" ").size
    return space * 3
}


fun getFakeUsers(): List<User> {
    return listOf<User>(User("Ramo"), User("Yusuf"))
}

@SuppressLint("SimpleDateFormat")
fun Date.getCurrentDate(): String {
    val date = SimpleDateFormat("dd/M/yyyy HH:mm")
    return date.format(this)
}

@SuppressLint("SimpleDateFormat")
fun dateConvert(): Date {
    val date = SimpleDateFormat("dd/M/yyyy HH:mm").parse(Date().getCurrentDate())
    return date!!
}

fun View.hide() {
    this.visibility = View.GONE
}
