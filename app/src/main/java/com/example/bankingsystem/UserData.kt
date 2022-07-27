package com.example.bankingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class UserData : AppCompatActivity() {
    var name: TextView? = null
    var email: TextView? = null
    var accountNo: TextView? = null
    var balance: TextView? = null
    var ifscCode: TextView? = null
    var phoneNo: TextView? = null
    var transferMoney: Button? = null
    var dialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)
        name = findViewById(R.id.name_user)
        email = findViewById(R.id.email_id)
        accountNo = findViewById(R.id.account_id)
        balance = findViewById(R.id.currentbalance_id)
        phoneNo = findViewById(R.id.phone_id)
        transferMoney = findViewById(R.id.transfer)

        // Getting the intent
        val intent = intent
        val extras = intent.extras

        // Extracting the data
        if (extras != null) {
            name?.setText(extras.getString("NAME"))
            accountNo?.setText(extras.getInt("ACCOUNT_NO").toString())
            email?.setText(extras.getString("EMAIL"))
            phoneNo?.setText(extras.getString("PHONE_NO"))
            ifscCode?.setText(extras.getString("IFSC_CODE"))
            balance?.setText(extras.getString("BALANCE"))
        } else {
            Log.d("TAG", "Empty Intent")
        }
        transferMoney?.setOnClickListener(View.OnClickListener { enterAmount() })
    }

    private fun enterAmount() {
        val mBuilder = AlertDialog.Builder(this@UserData)
        val mView = layoutInflater.inflate(R.layout.add_amount, null)
        mBuilder.setTitle("Enter Amount").setView(mView).setCancelable(false)
        val mAmount = mView.findViewById<View>(R.id.enter_money) as EditText
        mBuilder.setPositiveButton("SEND") { dialogInterface, i -> }
            .setNegativeButton("CANCEL") { dialog, which ->
                dialog.dismiss()
                transactionCancel()
            }
        dialog = mBuilder.create()
        dialog!!.show()
        dialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            // Checking whether amount entered is correct or not
            val currentBalance = balance!!.text.toString().toInt()
            if (mAmount.text.toString().isEmpty()) {
                mAmount.error = "Amount can't be empty"
            } else if (mAmount.text.toString().toInt() > currentBalance) {
                mAmount.error = "Your account don't have enough balance"
            } else {
                val intent = Intent(this@UserData, SendToUserList::class.java)
                intent.putExtra(
                    "FROM_USER_ACCOUNT_NO",
                    accountNo!!.text.toString().toInt()
                ) // PRIMARY_KEY
                intent.putExtra("FROM_USER_NAME", name!!.text)
                intent.putExtra("FROM_USER_ACCOUNT_BALANCE", balance!!.text)
                intent.putExtra("TRANSFER_AMOUNT", mAmount.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }

    private fun transactionCancel() {
        val builder_exitbutton = AlertDialog.Builder(this@UserData)
        builder_exitbutton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
            .setPositiveButton("yes") { dialogInterface, i ->
                Toast.makeText(
                    this@UserData,
                    "Transaction Cancelled!",
                    Toast.LENGTH_LONG
                ).show()
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
                enterAmount()
            }
        val alertexit = builder_exitbutton.create()
        alertexit.show()
    }}
