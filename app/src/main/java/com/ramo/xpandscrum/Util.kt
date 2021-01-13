package com.ramo.xpandscrum

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ramo.xpandscrum.model.User
import java.text.SimpleDateFormat
import java.util.*

// Toast Extension
fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}


// calculation predict date
fun predictDateFromDescription(description: String): Int {
    val space = description.split(" ").size
    return space * 3
}

// getting fake users
fun getFakeUsers(): List<User> {
    return listOf<User>(User("Ramo"), User("Yusuf"))
}

// date convert
@SuppressLint("SimpleDateFormat")
fun Date.getCurrentDate(): String {
    val date = SimpleDateFormat("dd/M/yyyy HH:mm")
    return date.format(this)
}

// String to date format
@SuppressLint("SimpleDateFormat")
fun dateConvert(): Date {
    val date = SimpleDateFormat("dd/M/yyyy HH:mm").parse(Date().getCurrentDate())
    return date!!
}

// View extension on hide
fun View.hide() {
    this.visibility = View.GONE
}

// return Show real time
fun calculateRealMinute(date: Date): Int {
    val today = Date()
    val diff = today.time - date.time
    return (diff / (1000 * 60)).toInt()
}

// check of textinput
fun TextInputEditText.isNotNullOrEmpty(errorString: String): Boolean {
    val textInputLayout = this.parent.parent as TextInputLayout
    textInputLayout.errorIconDrawable = null
    this.doOnTextChanged { _, _, _, _ ->
        textInputLayout.error = null
    }

    return if (this.text.toString().trim().isEmpty()) {
        textInputLayout.error = errorString
        false
    } else {
        true
    }
}

// çokul viewlerin validasyonunu sağlamak
fun validateAndDo(textInputEditText: List<TextInputEditText>, block: () -> Unit) {

    var boolean = false
    for (text in textInputEditText) {
        if (!text.isNotNullOrEmpty("Zorunlu"))
            boolean = true
    }

    if (!boolean)
        block()
}


