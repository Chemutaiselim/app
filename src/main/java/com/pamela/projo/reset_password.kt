package com.pamela.projo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity3.*

class reset_password : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var InputArray: Array<EditText>
    lateinit var Email: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity3)

        InputArray = arrayOf(EmailAddress_forgot_Password)


        Submit_forgot_password.setOnClickListener {
            sendEmail()
        }
    }
    private fun notEmpty(): Boolean =Email.isNotEmpty()

        private fun sendEmail() {
            Email = EmailAddress_forgot_Password.text.toString().trim()
            if (notEmpty()) {
                auth.sendPasswordResetEmail(Email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                        Toast.makeText(this, "Check your email, $Email to reset Password:)", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Failed:( Kindly Input your email correctly and retry", Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                InputArray.forEach { input->
                    if(input.text.toString().trim().isEmpty()){
                        input.error="${input.hint}is required :)"
                    }
                }
        }
    }
}





