package com.example.line_challenges.view.viewholder

import android.view.View
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.line_challenges.MainActivity
import com.example.line_challenges.R
import com.example.line_challenges.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_image.view.*

class ImageListViewHolder(view: View) : BaseViewHolder(view)
{
    override fun onBind(data: Any)
    {
        val path = data as String

        itemView.run {
            Glide.with(view.context).load(path).error(R.drawable.ic_visibility_off_24px).into(iv_image)

            setOnClickListener{
                val builder = AlertDialog.Builder(context)
                builder.setTitle("이미지를 삭제하시겠습니까?")
                builder.setPositiveButton("예") { dialog, which ->
                    (context as MainActivity).viewModel.nowPathList.removeAt(adapterPosition)
                    (context as MainActivity).memoFragment.checkImage()
                }
                builder.show()
            }
        }
    }
}