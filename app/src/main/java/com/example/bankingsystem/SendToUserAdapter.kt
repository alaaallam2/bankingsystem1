package com.example.bankingsystem


import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Intent
import android.view.View

import androidx.cardview.widget.CardView
import android.widget.LinearLayout

import java.util.ArrayList

class SendToUserAdapter(
    private val userArrayList: ArrayList<User>,
    private val onUserListener: OnUserListener
) : RecyclerView.Adapter<SendToUserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.home_view, viewGroup, false)
        return ViewHolder(view, onUserListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tag = userArrayList[position]
        viewHolder.userName.text = userArrayList[position].name
        viewHolder.userAccountBalance.text = String.format("%d", userArrayList[position].balance)
    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }

    inner class ViewHolder(itemView: View, onUserListener: OnUserListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var userName: TextView
        var userAccountBalance: TextView
        var onUserListener: OnUserListener
        override fun onClick(v: View) {
            onUserListener.onUserClick(adapterPosition)
        }

        init {
            userName = itemView.findViewById(R.id.user_name)
            userAccountBalance = itemView.findViewById(R.id.balance_id)
            this.onUserListener = onUserListener
            itemView.setOnClickListener(this)
        }
    }

    interface OnUserListener {
        fun onUserClick(position: Int)
    }
}