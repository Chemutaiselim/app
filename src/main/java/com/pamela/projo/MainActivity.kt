package com.pamela.projo

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

private val signup:TextView= findViewById(R.id.signup_login_page)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)


signup.setOnClickListener{
    val i=Intent(this,sign_up::class.java)
    startActivity(i)
}

    }
}