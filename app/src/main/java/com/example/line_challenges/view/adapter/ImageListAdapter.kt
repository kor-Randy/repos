package com.example.line_challenges.view.adapter

import android.view.View
import com.example.line_challenges.R
import com.example.line_challenges.base.BaseAdapter
import com.example.line_challenges.view.viewholder.ImageListViewHolder

class ImageListAdapter : BaseAdapter<String>()
{
    override val layoutId: Int
        get() = R.layout.item_image

    override fun viewHolder(layout: Int, view: View) = ImageListViewHolder(view)
}