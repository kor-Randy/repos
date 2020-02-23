package com.example.line_challenges.view.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.line_challenges.MainActivity
import com.example.line_challenges.R
import kotlinx.android.synthetic.main.dialog_urlimage.view.*


class UrlDialog(
    private val cancelMsg: String? = "cancel",
    private val confirmMsg: String? = "confirm"
) : DialogFragment()
{
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        return activity?.let {
            val view = it.layoutInflater.inflate(R.layout.dialog_urlimage, null).apply {
                val url = this.et_text_url
                bu_confirm_url.run {
                    text = confirmMsg
                    setOnClickListener {
                        dismiss()
                        (activity as MainActivity).viewModel.putImagePath(url.text.toString())
                    }
                }
                bu_cancel_url.run {
                    text = cancelMsg
                    setOnClickListener { dismiss() }
                }
            }

            AlertDialog.Builder(it).apply {
                setView(view)
            }.create().apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}