package com.example.line_challenges.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<listType : Any> : RecyclerView.Adapter<BaseViewHolder>()
{
    abstract val layoutId: Int
    abstract fun viewHolder(@LayoutRes layout: Int, view: View): BaseViewHolder
    private var itemList = ArrayList<listType>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(layoutId, parent, false)
        return viewHolder(viewType, view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int)
    {
        try {
            holder.onBind(itemList[position])

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setList(_itemList: List<listType>)
    {
        itemList = ArrayList(_itemList)
        notifyDataSetChanged()
    }

    fun setImage(_itemList: List<listType>)
    {
        itemList = ArrayList(_itemList)
        notifyDataSetChanged()
    }
}