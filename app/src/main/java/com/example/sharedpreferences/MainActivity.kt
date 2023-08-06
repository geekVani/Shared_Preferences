package com.example.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var userName : EditText
    lateinit var userPassword : EditText
    lateinit var userMessage : EditText
    lateinit var counter : Button
    lateinit var remember : CheckBox

    // integer container
    var cnt : Int = 0

    // containers for shared preferences
    var name: String? = null
    var message: String? = null
    var password: String? = null
    var isChecked: Boolean? = null

    // object of Shared Preferences class
    lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userName = findViewById(R.id.etName)
        userPassword = findViewById(R.id.etPwd)
        userMessage = findViewById(R.id.etMsg)
        counter = findViewById(R.id.btnCounter)
        remember = findViewById(R.id.cbRemember)

        // counter on click event
        counter.setOnClickListener {
            cnt++
            counter.text = "Increase Counter: $cnt"
        }
    }

    override fun onPause() {
        super.onPause()

        // call fun to save data
        saveData()
    }

    /*
    * Function to save data in local memory
    * */
    fun saveData(){

        // private for only accessible to this application
        sharedPref = this.getSharedPreferences("SaveData", Context.MODE_PRIVATE)

        // save data into the containers
        name = userName.text.toString()
        password = userPassword.text.toString()
        message = userMessage.text.toString()
        isChecked = remember.isChecked

        // assign object to editor class function called, edit() from SharedPreferences
        val editor = sharedPref.edit()

        // use diff key names to avoid confusion
        editor.putString("Key name", name)
        editor.putString("Key password", password)
        editor.putString("Key message", message)
        editor.putInt("Key count", cnt)
        editor.putBoolean("Key remember", isChecked!!)

        // to store data
        editor.apply()
        // show toast
        Toast.makeText(this, "Your data is saved successfully.", Toast.LENGTH_LONG).show()
    }

    /*
    * Function to retrieve data on screen
    * */
    fun retrieveData(){

        // private for only accessible to this application
        sharedPref = this.getSharedPreferences("SaveData", Context.MODE_PRIVATE)

        name = sharedPref.getString("Key name", null) // keyword with default value
        password = sharedPref.getString("Key password", null)
        message = sharedPref.getString("Key message", "This \nis \nmy \nmessage")
        isChecked = sharedPref.getBoolean("Key remember", false)
        cnt = sharedPref.getInt("Key count", 0)

        userName.setText(name)
        userPassword.setText(password)
        userMessage.setText(message)
        counter.setText("Increase Counter: $cnt")
        remember.isChecked = isChecked!!
    }

    override fun onResume() {
        super.onResume()

        // function call to retrieve data
        retrieveData()
    }

}