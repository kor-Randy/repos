package com.example.line_challenges.view.adapter

import android.view.View
import com.example.line_challenges.R
import com.example.line_challenges.base.BaseAdapter
import com.example.line_challenges.model.Memo
import com.example.line_challenges.view.viewholder.MemoListViewHolder



class MemoListAdapter : BaseAdapter<Memo>()
{
    override val layoutId: Int
        get() = R.layout.item_memolist

    override fun viewHolder(layout: Int, view: View) = MemoListViewHolder(view)
}
