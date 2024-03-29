package com.example.line_challenges.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view)
{
    abstract fun onBind(data: Any)
}