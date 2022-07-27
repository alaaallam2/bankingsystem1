package com.example.bankingsystem

import android.database.sqlite.SQLiteOpenHelper

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

import android.content.ContentValues

class TransactionContract private constructor() {
    object TransactionEntry : BaseColumns {
        /**Name of database table for pets */
        const val TABLE_NAME = "Transaction_table"

        /**Table Fields */
        const val _ID = BaseColumns._ID
        const val COLUMN_FROM_NAME = "from_name"
        const val COLUMN_TO_NAME = "to_name"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_STATUS = "status"
    }
}