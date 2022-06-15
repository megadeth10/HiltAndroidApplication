package com.my.hiltapplication.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.my.hiltapplication.databinding.ItemSpendBinding
import com.my.hiltapplication.room.Spend

/**
 * Created by YourName on 2022/06/14.
 */
class SpendViewHolder(private val bind : ItemSpendBinding) : RecyclerView.ViewHolder(bind.root) {
    fun setItem(item : Spend) {
        bind.tvAmount.text = item.amount.toString()
        bind.tvDescription.text = item.description
        bind.tvDate.text = item.date.toString()
    }
}