package com.example.bankingsystem

import android.database.sqlite.SQLiteOpenHelper

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

import android.content.ContentValues
import android.content.Context

class TransactionHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        // Create a String that contains the SQL statement to create the pets table
        val SQL_CREATE_TRANSACTION_TABLE = ("CREATE TABLE " + TransactionContract.TransactionEntry.TABLE_NAME + " ("
                + TransactionContract.TransactionEntry.COLUMN_FROM_NAME + " VARCHAR, "
                + TransactionContract.TransactionEntry.COLUMN_TO_NAME + " VARCHAR, "
                + TransactionContract.TransactionEntry.COLUMN_AMOUNT + " INTEGER, "
                + TransactionContract.TransactionEntry.COLUMN_STATUS + " INTEGER);")

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TRANSACTION_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TransactionContract.TransactionEntry.TABLE_NAME)
            onCreate(db)
        }
    }

    fun insertTransferData(
        fromName: String?,
        toName: String?,
        amount: String?,
        status: Int
    ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TransactionContract.TransactionEntry.COLUMN_FROM_NAME, fromName)
        contentValues.put(TransactionContract.TransactionEntry.COLUMN_TO_NAME, toName)
        contentValues.put(TransactionContract.TransactionEntry.COLUMN_AMOUNT, amount)
        contentValues.put(TransactionContract.TransactionEntry.COLUMN_STATUS, status)
        val result = db.insert(TransactionContract.TransactionEntry.TABLE_NAME, null, contentValues)
        return if (result == -1L) {
            false
        } else {
            true
        }
    }

    companion object {
        /** Name of the database file  */
        private const val DATABASE_NAME = "transaction.db"

        /**
         * Database version. If you change the database schema, you must increment the database version. */
        private const val DATABASE_VERSION = 1
    }
}