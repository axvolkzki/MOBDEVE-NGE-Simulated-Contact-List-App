package com.mobdeve.tighee.contactsapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.tighee.contactsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MAIN_ACTIVITY"
        private const val CONTACTS_STRING_KEY = "CONTACTS_STRING_KEY"
        private lateinit var sp: SharedPreferences
    }

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // SharedPreferences
        sp = getSharedPreferences("mySharedPrefContacts", Context.MODE_PRIVATE)

        this.viewBinding.addBtn.setOnClickListener(View.OnClickListener {
            val name = this.viewBinding.nameEtv.text.toString()
            val number = this.viewBinding.numberEtv.text.toString()

            if (name != "" || number != "") {   // If at least one field is filled, add to the list
                Log.d(TAG, "onClick: added properly")
                val temp = "${this.viewBinding.contactsStringHolderTv.text}$name : $number\n"
                this.viewBinding.contactsStringHolderTv.setText(temp)    // This will be saved to SharedPreferences later

                // Remove this for spamming
                this.viewBinding.nameEtv.setText("")        // Clear the EditText fields
                this.viewBinding.numberEtv.setText("")      // Clear the EditText fields
            } else {                    // If both fields are empty, do nothing
                Log.d(TAG, "onClick: not added properly")
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val displayString = sp.getString(CONTACTS_STRING_KEY, "")!!.trim() + "\n"

        this.viewBinding.contactsStringHolderTv.text = displayString
        Log.d(TAG, "onStart: \nread data:\\n|" + this.viewBinding.contactsStringHolderTv.text + "|")
    }

    override fun onStop() {
        super.onStop()

        val editor = sp.edit()
        editor.putString(CONTACTS_STRING_KEY, this.viewBinding.contactsStringHolderTv.text.toString())
        editor.apply()

        Log.d(TAG, "onStop: \nwrite data:\\n|" + this.viewBinding.contactsStringHolderTv.text + "|")
    }
}