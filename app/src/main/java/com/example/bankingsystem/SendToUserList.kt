package com.example.bankingsystem

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class SendToUserList : AppCompatActivity(), SendToUserAdapter.OnUserListener {
    // RecyclerView
    var recyclerView: RecyclerView? = null
    var myAdapter: RecyclerView.Adapter<*>? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var userArrayList: ArrayList<User>? = null

    // Database
    private var dbHelper: UserHelper? = null
    var date: String? = null
    var time: String? = null
    var fromUserAccountNo = 0
    var toUserAccountNo = 0
    var toUserAccountBalance = 0
    var fromUserAccountName: String? = null
    var fromUserAccountBalance: String? = null
    var transferAmount: String? = null
    var toUserAccountName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_to_user_list)

        // Get time instance
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy, hh:mm a")
        val date_and_time = simpleDateFormat.format(calendar.time)

        // Get Intent
        val bundle = intent.extras
        if (bundle != null) {
            fromUserAccountName = bundle.getString("FROM_USER_NAME")
            fromUserAccountNo = bundle.getInt("FROM_USER_ACCOUNT_NO")
            fromUserAccountBalance = bundle.getString("FROM_USER_ACCOUNT_BALANCE")
            transferAmount = bundle.getString("TRANSFER_AMOUNT")
        }

        // Create ArrayList of Users
        userArrayList = ArrayList()

        // Create Table in the Database
        dbHelper = UserHelper(this)

        // Show list of items
        recyclerView = findViewById(R.id.all_users_list)
        recyclerView?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)
        myAdapter = SendToUserAdapter(userArrayList!!, this)
        recyclerView?.setAdapter(myAdapter)
    }

    override fun onStart() {
        super.onStart()
        displayDatabaseInfo()
    }

    override fun onUserClick(position: Int) {
        // Insert data into transactions table
        toUserAccountNo = userArrayList!![position].accountNumber
        toUserAccountName = userArrayList!![position].name
        toUserAccountBalance = userArrayList!![position].balance
        calculateAmount()
        TransactionHelper(this).insertTransferData(
            fromUserAccountName,
            toUserAccountName,
            transferAmount,
            1
        )
        Toast.makeText(this, "Transaction Successful!!", Toast.LENGTH_LONG).show()
        startActivity(Intent(this@SendToUserList, MainActivity::class.java))
        finish()
    }

    private fun calculateAmount() {
        val currentAmount = fromUserAccountBalance!!.toInt()
        val transferAmountInt = transferAmount!!.toInt()
        val remainingAmount = currentAmount - transferAmountInt
        val increasedAmount = transferAmountInt + toUserAccountBalance

        // Update amount in the dataBase
        UserHelper(this).updateAmount(fromUserAccountNo, remainingAmount)
        UserHelper(this).updateAmount(toUserAccountNo, increasedAmount)
    }

    override fun onBackPressed() {
        val builder_exitButton = AlertDialog.Builder(this@SendToUserList)
        builder_exitButton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
            .setPositiveButton("yes") { dialogInterface, i -> // Transactions Cancelled
                val dbHelper = TransactionHelper(applicationContext)
                val db = dbHelper.writableDatabase
                val values = ContentValues()
                values.put(TransactionContract.TransactionEntry.COLUMN_FROM_NAME, fromUserAccountName)
                values.put(TransactionContract.TransactionEntry.COLUMN_TO_NAME, toUserAccountName)
                values.put(TransactionContract.TransactionEntry.COLUMN_STATUS, 0)
                values.put(TransactionContract.TransactionEntry.COLUMN_AMOUNT, transferAmount)
                db.insert(TransactionContract.TransactionEntry.TABLE_NAME, null, values)
                Toast.makeText(this@SendToUserList, "Transaction Cancelled!", Toast.LENGTH_LONG)
                    .show()
                startActivity(Intent(this@SendToUserList, MainActivity3::class.java))
                finish()
            }.setNegativeButton("No", null)
        val alertExit = builder_exitButton.create()
        alertExit.show()
    }

    private fun displayDatabaseInfo() {
        // Create and/or open a database to read from it
        val db = dbHelper!!.readableDatabase
        val projection = arrayOf(
            UserContract.UserEntry.COLUMN_USER_NAME,
            UserContract.UserEntry.COLUMN_USER_ACCOUNT_BALANCE,
            UserContract.UserEntry.COLUMN_USER_ACCOUNT_NUMBER,
            UserContract.UserEntry.COLUMN_USER_PHONE_NO,
            UserContract.UserEntry.COLUMN_USER_EMAIL,
            UserContract.UserEntry.COLUMN_USER_IFSC_CODE
        )
        val cursor = db.query(
            UserContract.UserEntry.TABLE_NAME,  // The table to query
            projection,  // The columns to return
            null,  // The columns for the WHERE clause
            null,  // The values for the WHERE clause
            null,  // Don't group the rows
            null,  // Don't filter by row groups
            null
        ) // The sort order
        try {
            // Figure out the index of each column
            val phoneNoColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_PHONE_NO)
            val emailColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_EMAIL)
            val ifscCodeColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_IFSC_CODE)
            val accountNumberColumnIndex =
                cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_ACCOUNT_NUMBER)
            val nameColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_NAME)
            val accountBalanceColumnIndex =
                cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_ACCOUNT_BALANCE)

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
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
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close()
        }
    }
}