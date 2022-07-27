package com.example.bankingsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity2 : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var myAdapter: RecyclerView.Adapter<*>? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var transactionArrayList: ArrayList<Transaction>? = null

    // Database
    private var dbHelper: TransactionHelper? = null
    var emptyList: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Get TextView
        emptyList = findViewById(R.id.empty_text)

        // Create Transaction History List
        transactionArrayList = ArrayList()

        // Create Table in the Database
        dbHelper = TransactionHelper(this)

        // Display database info
        displayDatabaseInfo()
        recyclerView = findViewById(R.id.transactions_list)
        recyclerView?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)
        myAdapter = TransactionHistoryAdapter(this, transactionArrayList!!)
        recyclerView?.setAdapter(myAdapter)
    }

    private fun displayDatabaseInfo() {
        Log.d("TAG", "displayDataBaseInfo()")

        // Create and/or open a database to read from it
        val db = dbHelper!!.readableDatabase
        Log.d("TAG", "displayDataBaseInfo()1")
        val projection = arrayOf(
            TransactionContract.TransactionEntry.COLUMN_FROM_NAME,
            TransactionContract.TransactionEntry.COLUMN_TO_NAME,
            TransactionContract.TransactionEntry.COLUMN_AMOUNT,
            TransactionContract.TransactionEntry.COLUMN_STATUS
        )
        Log.d("TAG", "displayDataBaseInfo()2")
        val cursor = db.query(
            TransactionContract.TransactionEntry.TABLE_NAME,  // The table to query
            projection,  // The columns to return
            null,  // The columns for the WHERE clause
            null,  // The values for the WHERE clause
            null,  // Don't group the rows
            null,  // Don't filter by row groups
            null
        ) // The sort order
        try {
            // Figure out the index of each column
            val fromNameColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_FROM_NAME)
            val ToNameColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_TO_NAME)
            val amountColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_AMOUNT)
            val statusColumnIndex = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_STATUS)
            Log.d("TAG", "displayDataBaseInfo()3")

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                val fromName = cursor.getString(fromNameColumnIndex)
                val ToName = cursor.getString(ToNameColumnIndex)
                val accountBalance = cursor.getInt(amountColumnIndex)
                val status = cursor.getInt(statusColumnIndex)


                //Log.d("TAG", "displayDataBaseInfo()4");

                // Display the values from each column of the current row in the cursor in the TextView
                transactionArrayList!!.add(Transaction(fromName, ToName, accountBalance, status))
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close()
        }
        if (transactionArrayList!!.isEmpty()) {
            emptyList!!.visibility = View.VISIBLE
        } else {
            emptyList!!.visibility = View.GONE
        }
    }
}