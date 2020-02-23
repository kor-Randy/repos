package com.example.line_challenges.view.ui.fragment

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.line_challenges.MainActivity
import com.example.line_challenges.R
import com.example.line_challenges.base.BaseAdapter
import com.example.line_challenges.databinding.FragmentMainBinding
import com.example.line_challenges.view.adapter.ImageListAdapter
import com.example.line_challenges.view.adapter.MemoListAdapter
import com.example.line_challenges.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment()
{
    private lateinit var binding : FragmentMainBinding
    private lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let{
           viewModel= (activity as MainActivity).viewModel
            binding.activity = activity as MainActivity
            binding.viewModel = viewModel
            binding.lifecycleOwner=this
            viewModel.getAll()
            rv_memolist.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = MemoListAdapter()
            }
        }
    }

    override fun onResume() {
        viewModel.getAll()
        super.onResume()
    }
}