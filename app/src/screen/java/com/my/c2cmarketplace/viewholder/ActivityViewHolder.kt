package com.my.c2cmarketplace.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.my.c2cmarketplace.scene.ShortCutActivity
import com.my.hiltapplication.databinding.LayoutActivityItemBinding

class ActivityViewHolder constructor(view : View, viewBinding : LayoutActivityItemBinding) : RecyclerView.ViewHolder(view) {
    private var contentBinding : LayoutActivityItemBinding = viewBinding
    var textView : TextView = this.contentBinding.title

    fun setItem(item : ShortCutActivity.ActivityItem) {
        this.contentBinding.title.text = item.name
    }
}