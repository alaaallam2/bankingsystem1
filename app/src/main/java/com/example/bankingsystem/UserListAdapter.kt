package com.example.bankingsystem

import android.content.Context

import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Intent
import android.view.View

import java.util.ArrayList

class UserListAdapter(context: Context?, private val userArrayList: ArrayList<User>) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView
        var userAccountBalance: TextView

        init {
            userName = itemView.findViewById(R.id.user_name)
            userAccountBalance = itemView.findViewById(R.id.balance_id)
            itemView.setOnClickListener {
                // still to be implemented
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.home_view, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tag = userArrayList[position]
        viewHolder.userName.text = userArrayList[position].name
        viewHolder.userAccountBalance.text = String.format("%d", userArrayList[position].balance)
        viewHolder.itemView.setOnClickListener { v ->
            val intent = Intent(v.context, UserData::class.java)
            intent.putExtra("ACCOUNT_NO", userArrayList[position].accountNumber)
            intent.putExtra("NAME", userArrayList[position].name)
            intent.putExtra("EMAIL", userArrayList[position].email)
            intent.putExtra("IFSC_CODE", userArrayList[position].ifscCode)
            intent.putExtra("PHONE_NO", userArrayList[position].phoneNumber)
            intent.putExtra("BALANCE", userArrayList[position].balance.toString())
            v.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }
}