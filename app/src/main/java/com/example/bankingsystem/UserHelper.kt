package com.example.bankingsystem

import android.database.sqlite.SQLiteOpenHelper

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

class UserHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    var TABLE_NAME: String? = UserContract.UserEntry.TABLE_NAME
    override fun onCreate(db: SQLiteDatabase) {
        // Create a String that contains the SQL statement to create the pets table
        val SQL_CREATE_USER_TABLE = ("CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " ("
                + UserContract.UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " INTEGER, "
                + UserContract.UserEntry.COLUMN_USER_NAME + " VARCHAR, "
                + UserContract.UserEntry.COLUMN_USER_EMAIL + " VARCHAR, "
                + UserContract.UserEntry.COLUMN_USER_IFSC_CODE + " VARCHAR, "
                + UserContract.UserEntry.COLUMN_USER_PHONE_NO + " VARCHAR, "
                + UserContract.UserEntry.COLUMN_USER_ACCOUNT_BALANCE + " INTEGER NOT NULL);")

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_USER_TABLE)

        // Insert Into Table
        db.execSQL("insert into $TABLE_NAME values(7860,'Tanishq Saini', 'tanishq@gmail.com','7584','7895641238', 15000)")
        db.execSQL("insert into $TABLE_NAME values(5862,'Gagan Tripathi', 'gagan@gmail.com','1258','8995641238', 5000)")
        db.execSQL("insert into $TABLE_NAME values(7895,'Surya Pratap', 'surya@gmail.com','8896','7595645896', 1000)")
        db.execSQL("insert into $TABLE_NAME values(1258,'Vikram Garasiya', 'vikram@gmail.com','7752','9995640038', 8000)")
        db.execSQL("insert into $TABLE_NAME values(7410,'Shivani Kumari', 'shivani@gmail.com','3669','9095648962', 7500)")
        db.execSQL("insert into $TABLE_NAME values(8529,'Piyush Joshi', 'piyush@gmail.com','9985','8855640238', 6500)")
        db.execSQL("insert into $TABLE_NAME values(3698,'Yash Pratap', 'yash@gmail.com','1207','8895640215', 4500)")
        db.execSQL("insert into $TABLE_NAME values(7853,'Khushi Jain', 'khushi@gmail.com','4522','9985021539', 2500)")
        db.execSQL("insert into $TABLE_NAME values(4562,'Ritik Sharma', 'ritik@gmail.com','6582','9309565238', 10500)")
        db.execSQL("insert into $TABLE_NAME values(2365,'Rohit Patidar', 'rohit@gmail.com','5450','8292591201', 9900)")
        db.execSQL("insert into $TABLE_NAME values(7854,'Anurag Sharma', 'anurag@gmail.com','2656','9015641200', 9800)")
        db.execSQL("insert into $TABLE_NAME values(3621,'Hitish Kumar', 'hitish@gmail.com','1203','9995641999', 11000)")
        db.execSQL("insert into $TABLE_NAME values(1122,'Naveen Chaturvedi', 'naveen@gmail.com','5566','9119541001', 5800)")
        db.execSQL("insert into $TABLE_NAME values(9512,'Gauri Parashar', 'gauri@gmail.com','2236','6254642205', 3500)")
        db.execSQL("insert into $TABLE_NAME values(7530,'Farhan Khan', 'farhan@gmail.com','6692','6893641266', 1010)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME)
            onCreate(db)
        }
    }

    fun readAllData(): Cursor {
        val db = this.writableDatabase
        return db.rawQuery("select * from " + UserContract.UserEntry.TABLE_NAME, null)
    }

    fun readParticularData(accountNo: Int): Cursor {
        val db = this.writableDatabase
        return db.rawQuery(
            "select * from " + UserContract.UserEntry.TABLE_NAME + " where " +
                    UserContract.UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " = " + accountNo, null
        )
    }

    fun updateAmount(accountNo: Int, amount: Int) {
        Log.d("TAG", "update Amount")
        val db = this.writableDatabase
        db.execSQL(
            "update " + UserContract.UserEntry.TABLE_NAME + " set " + UserContract.UserEntry.COLUMN_USER_ACCOUNT_BALANCE + " = " + amount + " where " +
                    UserContract.UserEntry.COLUMN_USER_ACCOUNT_NUMBER + " = " + accountNo
        )
    }

    companion object {
        /** Name of the database file  */
        private const val DATABASE_NAME = "User.db"

        /**
         * Database version. If you change the database schema, you must increment the database version. */
        private const val DATABASE_VERSION = 1
    }
}