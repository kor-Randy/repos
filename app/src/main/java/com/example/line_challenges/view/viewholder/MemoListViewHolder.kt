package com.example.line_challenges.view.viewholder


import android.view.View
import com.bumptech.glide.Glide
import com.example.line_challenges.MainActivity
import com.example.line_challenges.R
import com.example.line_challenges.base.BaseViewHolder
import com.example.line_challenges.model.Memo
import kotlinx.android.synthetic.main.item_memolist.view.*

class MemoListViewHolder(view: View) : BaseViewHolder(view)
{
    override fun onBind(data: Any)
    {
        val memo = data as Memo

        itemView.run {
            tv_name.text = memo.title
            tv_content.text = memo.content
            Glide.with(view.context).load(memo.repImage).placeholder(R.drawable.ic_launcher_foreground).into(iv_rep_image)

            setOnClickListener{
                (context as MainActivity).viewModel.selectMemo(adapterPosition)
            }
        }
    }
}