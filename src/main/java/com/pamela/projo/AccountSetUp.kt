package com.pamela.projo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity4.*

class AccountSetUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var createAccountInputArray: Array<EditText>
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseUser: FirebaseUser? = firebaseAuth.currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity4)

        auth = Firebase.auth

        createAccountInputArray = arrayOf(
            first_name,
            last_name,
            email_create_account,
            password_create_account,
            confirm_password_create_account
        )
        back_create_account.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }

        submit_create_account.setOnClickListener {
            signIn()
        }

        login_create_account.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "Please LogIn to your Account :|", Toast.LENGTH_SHORT).show()
            finish()
        }
    }


    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, FirstPage::class.java))
            Toast.makeText(this, "Welcome Back $last_name :),", Toast.LENGTH_LONG).show()

        }
    }


    private fun notEmpty(): Boolean = email_create_account.text.toString().trim()
        .isNotEmpty() && password_create_account.text.toString().trim().isNotEmpty() &&
            confirm_password_create_account.text.toString().trim()
                .isNotEmpty() && first_name.text.toString().trim()
        .isNotEmpty() && last_name.text.toString().trim().isNotEmpty()


    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
            password_create_account.text.toString().trim().isEmpty() ==
            confirm_password_create_account.text.toString().trim().isEmpty()
        ) {
            identical = true}
        else if (!notEmpty()) {
            createAccountInputArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required :|"
                }

            }
        }else{
            Toast.makeText(this,"Passwords are not matching:|",Toast.LENGTH_SHORT).show()
        }
        return identical
    }


    private fun signIn(){
        if (identicalPassword()){
            userEmail=email_create_account.text.toString().trim()
            userPassword=password_create_account.text.toString().trim()

            firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener { task->
                if (task.isSuccessful){
                    Toast.makeText(this,"Created Account Successfully :)!",Toast.LENGTH_SHORT).show()
                    sendEmailVerification()
                    startActivity(Intent(this,FirstPage::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this,"Failed To Authenticate :(",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun sendEmailVerification() {
        firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task->
                if(task.isSuccessful){
                    Toast.makeText(this,"Email Verification sent to $userEmail",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


}