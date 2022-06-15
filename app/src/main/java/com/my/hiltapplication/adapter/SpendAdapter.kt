package com.my.hiltapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.my.hiltapplication.R
import com.my.hiltapplication.base.BaseRecycleViewAdapter
import com.my.hiltapplication.databinding.ItemSpendBinding
import com.my.hiltapplication.room.Spend
import com.my.hiltapplication.util.Log
import com.my.hiltapplication.viewholder.SpendViewHolder

/**
 * Created by YourName on 2022/06/14.
 */
class SpendAdapter : BaseRecycleViewAdapter<SpendViewHolder, Spend>() {
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : SpendViewHolder {
        val viewBinding : ItemSpendBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_spend, null, false)
        SpendViewHolder(viewBinding)
        return SpendViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder : SpendViewHolder, position : Int) {
        val item = this.list[position]
        holder.setItem(item)
        holder.itemView.tag = position
    }

    fun setAll(list : ArrayList<Spend>, firstVisiblePosition : Int, lastPosition : Int) {
        val offset = lastPosition - firstVisiblePosition
        Log.e(tag, "setAll() offset: $offset")
        this.setAllNotifyOffset(list, firstVisiblePosition, lastPosition)
    }

    fun addItem(spend : Spend) {
        val itemIndex = this.list.size
        this.list.add(itemIndex, spend)
        notifyItemChanged(itemIndex)
    }

    fun removeItem(index : Int) {
        this.list.removeAt(index)
        notifyItemChanged(index)
    }

    override fun getLogName() = SpendAdapter::class.simpleName

}