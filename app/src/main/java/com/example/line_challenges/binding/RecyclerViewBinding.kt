package com.example.line_challenges.binding


import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.line_challenges.R
import com.example.line_challenges.model.Memo
import com.example.line_challenges.view.adapter.ImageListAdapter
import com.example.line_challenges.view.adapter.MemoListAdapter
import kotlinx.android.synthetic.main.item_image.view.*

@BindingAdapter("adapterMemoList")
fun bindAdapterMemoList(
    view: RecyclerView,
    memoList: List<Memo>?)
{
    memoList?.let { list ->
        (view.adapter as MemoListAdapter).run {
            setList(list)
        }
    }
}

@BindingAdapter("adapterImageList")
fun bindAdapterImageList(
    view: RecyclerView,
    imageList: List<String>?)
{
    imageList?.let { list ->
        (view.adapter as ImageListAdapter).run {
            setImage(list)
        }
    }
}


