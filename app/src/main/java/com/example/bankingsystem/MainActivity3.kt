package com.example.bankingsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity3 : AppCompatActivity() {
    // RecyclerView
    var recyclerView: RecyclerView? = null
    var myAdapter: RecyclerView.Adapter<*>? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var userArrayList: ArrayList<User>? = null

    // Database
    private var dbHelper: UserHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        // Create ArrayList of Users
        userArrayList = ArrayList()

        // Create Table in the Database
        dbHelper = UserHelper(this)

        // Read Data from DataBase
        displayDatabaseInfo()

        // Show list of items
        recyclerView = findViewById(R.id.all_users_list)
        recyclerView?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)
        myAdapter = UserListAdapter(this, userArrayList!!)
        recyclerView?.setAdapter(myAdapter)
    }

    private fun displayDatabaseInfo() {
        userArrayList!!.clear()
        val cursor = UserHelper(this).readAllData()
        val phoneNoColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_PHONE_NO)
        val emailColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_EMAIL)
        val ifscCodeColumnIndex =
            cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_IFSC_CODE)
        val accountNumberColumnIndex =
            cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_ACCOUNT_NUMBER)
        val nameColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_NAME)
        val accountBalanceColumnIndex =
            cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_ACCOUNT_BALANCE)
        while (cursor.moveToNext()) {
            val currentName = cursor.getString(nameColumnIndex)
            val accountNumber = cursor.getInt(accountNumberColumnIndex)
            val email = cursor.getString(emailColumnIndex)
            val phoneNumber = cursor.getString(phoneNoColumnIndex)
            val ifscCode = cursor.getString(ifscCodeColumnIndex)
            val accountBalance = cursor.getInt(accountBalanceColumnIndex)

            // Display the values from each column of the current row in the cursor in the TextView
            userArrayList!!.add(
                User(
                    currentName,
                    accountNumber,
                    phoneNumber,
                    ifscCode,
                    accountBalance,
                    email
                )
            )
        }
    }
}