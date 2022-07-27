package com.example.bankingsystem


import android.provider.BaseColumns

import android.content.ContentValues

class UserContract private constructor() {
    object UserEntry : BaseColumns {
        /** Name of database table for pets  */
        const val TABLE_NAME = "user"

        /** Table Fields  */
        const val _ID = BaseColumns._ID
        const val COLUMN_USER_NAME = "name"
        const val COLUMN_USER_ACCOUNT_NUMBER = "accountNo"
        const val COLUMN_USER_EMAIL = "email"
        const val COLUMN_USER_IFSC_CODE = "ifscCode"
        const val COLUMN_USER_PHONE_NO = "phoneNo"
        const val COLUMN_USER_ACCOUNT_BALANCE = "balance"
    }
}