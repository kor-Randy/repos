package com.example.line_challenges.view.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.line_challenges.MainActivity
import com.example.line_challenges.R
import kotlinx.android.synthetic.main.dialog_addimage.view.*
import kotlinx.android.synthetic.main.dialog_urlimage.view.*



class AddImageDialog(
    private val galleryOnClickListener: () -> Unit = {},
    private val cameraOnClickListener: () -> Unit = {},
    private val urlOnClickListener: () -> Unit = {}
) : DialogFragment()
{

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val view = it.layoutInflater.inflate(R.layout.dialog_addimage, null).apply {

                iv_add_gallary.run {
                   setOnClickListener {
                        dismiss()
                        galleryOnClickListener()
                    }
                }

                iv_add_camera.run {
                    this.setOnClickListener {
                        dismiss()
                        cameraOnClickListener()
                    }
                }

                iv_add_url.run {
                    this.setOnClickListener {
                        dismiss()
                        urlOnClickListener()
                    }
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