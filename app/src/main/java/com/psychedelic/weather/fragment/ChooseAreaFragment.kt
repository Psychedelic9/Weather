package com.psychedelic.weather.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arcb.laborunion.base.ui.BaseFragment

import com.psychedelic.weather.R
import com.psychedelic.weather.databinding.FragmentChooseAreaBinding
import com.psychedelic.weather.mvvm.MainView
import com.psychedelic.weather.mvvm.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ChooseAreaFragment:BaseFragment<MainViewModel,MainView,FragmentChooseAreaBinding>() {
    override val mViewModel: MainViewModel
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val layoutResID: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun initView() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_area, container, false)
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)

    }




}
