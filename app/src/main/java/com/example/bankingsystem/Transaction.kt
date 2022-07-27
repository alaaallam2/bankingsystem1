package com.example.bankingsystem

class Transaction(
    var fromUser: String,
    var toUser: String,
    var amountTransferred: Int,
    var status: Int
)