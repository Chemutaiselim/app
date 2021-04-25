package com.pamela.projo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_2.*


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInInputArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        auth = Firebase.auth

        signInInputArray = arrayOf(email_login_page, password_login_page)

        signup_login_page.setOnClickListener {
            val i = Intent(this, acoount_set_up::class.java)
            startActivity(i)
            finish()
        }
        login_btn_login_page.setOnClickListener {
            signInUser()
        }

    }
    private fun notEmpty(): Boolean =signInEmail.isNotEmpty() && signInPassword.isNotEmpty()
    private fun signInUser(){
        signInEmail=email_login_page.text.toString().trim()
        signInPassword=password_login_page.text.toString().trim()

        if (notEmpty()){
            auth.signInWithEmailAndPassword(signInEmail,signInPassword).addOnCompleteListener { signIn->
                if(signIn.isSuccessful){
                    val i = Intent(this, fist_page::class.java)
                    startActivity(i)
                    Toast.makeText(this,"Log In Successful :)",Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(this,"LogIn Failed :(",Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            signInInputArray.forEach { input->
                if(input.text.toString().trim().isEmpty()){
                    input.error="${input.hint}is required :|"
                }
            }
        }
    }

}