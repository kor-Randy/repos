package com.example.line_challenges.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.line_challenges.MainActivity
import com.example.line_challenges.R
import com.example.line_challenges.databinding.FragmentMemoBinding
import com.example.line_challenges.view.adapter.ImageListAdapter
import com.example.line_challenges.view.ui.dialog.AddImageDialog
import com.example.line_challenges.view.ui.dialog.UrlDialog
import com.example.line_challenges.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_memo.*

class MemoFragment : Fragment()
{
    val TAG = "MemoFragment.kt"
    private lateinit var binding : FragmentMemoBinding
    private lateinit var viewModel : MainViewModel
    val adap = ImageListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memo,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        activity?.let{
            viewModel= (activity as MainActivity).viewModel
            binding.activity = activity as MainActivity
            binding.viewModel = viewModel
            binding.lifecycleOwner=this

            rv_imagelist.apply {
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
                adapter = adap
            }
        }
        setEventListener()
    }

    fun setEventListener()
    {
        val supportFragmentManager = (context as MainActivity).supportFragmentManager

        iv_addimage_memo.setOnClickListener {
            AddImageDialog({
                //gallery
                (context as MainActivity).getGallery()
            },{
                //camera
                (context as MainActivity).dispatchTakePictureIntent()
            }){
                //url
                UrlDialog("취소","가져오기").show(supportFragmentManager,TAG)
            }.show(supportFragmentManager,TAG)
        }

    }

    fun deleteMemo()
    {
        val builder = AlertDialog.Builder(context as MainActivity)
        builder.setTitle("메모를 지우시겠습니까?")
        builder.setPositiveButton("예") { dialog, which ->
            viewModel.deleteMemo()
        }
        builder.show()
    }

    fun edit()
    {
        val builder = AlertDialog.Builder(context as MainActivity)
        builder.setTitle("글을 수정하시겠습니까?")
        builder.setPositiveButton("예") { dialog, which ->
            viewModel.getFocus()
        }
        builder.show()
    }

    fun saveMemo()
    {
        val builder = AlertDialog.Builder(context as MainActivity)
        builder.setTitle("글을 저장하시겠습니까?")
        builder.setPositiveButton("예") { dialog, which ->
            viewModel.insetMemo()
        }
        builder.show()
    }

    fun checkImage()
    {
        adap.setImage(viewModel.nowPathList)
    }
}