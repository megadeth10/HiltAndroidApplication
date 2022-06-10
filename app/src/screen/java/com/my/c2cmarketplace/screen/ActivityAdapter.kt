package com.my.c2cmarketplace.screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.my.hiltapplication.R
import com.my.hiltapplication.databinding.LayoutActivityItemBinding

class ActivityAdapter : RecyclerView.Adapter<ActivityViewHolder>(),
    View.OnClickListener {
    protected var TAG = ActivityAdapter::class.simpleName
    protected var list = ArrayList<ShortCutActivity.ActivityItem>(0)
    var onClickListener: View.OnClickListener? = null
    fun setAll(list: ArrayList<ShortCutActivity.ActivityItem>) {
        this.list = list.clone() as ArrayList<ShortCutActivity.ActivityItem>
        notifyDataSetChanged()
    }

    fun getAll(): ArrayList<ShortCutActivity.ActivityItem> = list

    override fun getItemCount() = this.list.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val bindView: LayoutActivityItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_activity_item,
            parent,
            false
        )
        return ActivityViewHolder(bindView.root, bindView)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val item = this.list[position]
        holder.setItem(item)
        holder.textView.tag = item
        holder.textView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        this.onClickListener?.let {
            it.onClick(v)
        }
    }
}