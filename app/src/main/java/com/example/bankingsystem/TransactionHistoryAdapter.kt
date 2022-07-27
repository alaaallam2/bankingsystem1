package com.example.bankingsystem

import android.content.Context

import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Intent
import android.graphics.Color
import android.view.View

import androidx.cardview.widget.CardView
import android.widget.LinearLayout

import java.util.ArrayList

class TransactionHistoryAdapter(
    context: Context?,
    private val transactionArrayList: ArrayList<Transaction>
) : RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fromName: TextView
        var toName: TextView
        var amountTransferred: TextView
        var date: TextView
        var time: TextView
        var cardView: CardView
        var toUserInfo: LinearLayout

        init {
            fromName = itemView.findViewById(R.id.t_from_name)
            toName = itemView.findViewById(R.id.t_to_name)
            amountTransferred = itemView.findViewById(R.id.t_amount)
            cardView = itemView.findViewById(R.id.transaction_card_view)
            toUserInfo = itemView.findViewById(R.id.to_user_info)
            date = itemView.findViewById(R.id.t_date)
            time = itemView.findViewById(R.id.t_time)
            itemView.setOnClickListener {
                // still to be implemented
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.transaction_view, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tag = transactionArrayList[position]
        viewHolder.fromName.text = transactionArrayList[position].fromUser
        viewHolder.toName.text = transactionArrayList[position].toUser
        viewHolder.amountTransferred.text =
            String.format("%d", transactionArrayList[position].amountTransferred)
        if (transactionArrayList[position].status == 1) {
            viewHolder.cardView.setCardBackgroundColor(Color.argb(100, 105, 187, 105))
        } else {
            viewHolder.cardView.setCardBackgroundColor(Color.argb(100, 239, 100, 100))
        }
    }

    override fun getItemCount(): Int {
        return transactionArrayList.size
    }
}