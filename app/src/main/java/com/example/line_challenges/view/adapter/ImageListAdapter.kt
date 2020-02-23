package com.example.line_challenges.view.adapter

import android.view.View
import com.example.line_challenges.R
import com.example.line_challenges.base.BaseAdapter
import com.example.line_challenges.base.BaseViewHolder
import com.example.line_challenges.model.Memo
import com.example.line_challenges.view.viewholder.ImageListViewHolder
import com.example.line_challenges.view.viewholder.MemoListViewHolder

class ImageListAdapter : BaseAdapter<String>()
{
    override val layoutId: Int
        get() = R.layout.item_image

    override fun viewHolder(layout: Int, view: View) = ImageListViewHolder(view)
}