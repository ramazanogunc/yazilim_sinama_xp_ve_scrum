package com.ramo.xpandscrum

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/*
Tüm her şeyi kapsayan ve konteks içeren ana activitymiz
 */
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}