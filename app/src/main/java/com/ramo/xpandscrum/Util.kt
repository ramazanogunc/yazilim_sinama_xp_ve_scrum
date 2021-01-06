package com.ramo.xpandscrum

import android.content.Context
import android.widget.Toast
import com.ramo.xpandscrum.model.User

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