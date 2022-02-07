package com.djv.presentation.transactions

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.djv.domain.model.Transaction
import com.djv.presentation.R
import com.djv.presentation.databinding.TransactionItemBinding

class TransactionAdapter(
    private val context: Context
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private var transactions: List<Transaction>? = null

    class ViewHolder(itemView: TransactionItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        val description = itemView.transactionDescription
        val amount = itemView.transactionAmount
        val type = itemView.transactionType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions!![position]
        holder.description.text = transaction.description
        holder.amount.text = transaction.amount.toString()

        if (transaction.type == 1) {
            holder.type.setImageResource(R.drawable.arrow_up)
        } else {
            holder.type.setImageResource(R.drawable.arrow_down)
        }
    }

    override fun getItemCount(): Int {
        return if (transactions.isNullOrEmpty()) 0 else transactions!!.size
    }

    fun setItems(ori: List<Transaction>) {
        transactions = ori
        notifyDataSetChanged()
    }
}