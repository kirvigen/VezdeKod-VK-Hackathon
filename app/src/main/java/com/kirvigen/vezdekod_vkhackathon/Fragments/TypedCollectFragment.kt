package com.kirvigen.vezdekod_vkhackathon.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kirvigen.vezdekod_vkhackathon.R
import kotlinx.android.synthetic.main.fragment_type_collect.*
import kotlinx.android.synthetic.main.fragment_type_collect.view.*
import maes.tech.intentanim.CustomIntent

class TypedCollectFragment:Fragment() {

    lateinit var mainView:View

    companion object {
        fun newInstance(): TypedCollectFragment {
            return TypedCollectFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_type_collect, container, false)

        mainView.back.setOnClickListener {
            activity?.onBackPressed()
            CustomIntent.customType(context,"right-to-left")
        }
        val onTypeVote = activity as OnTypeVote
        mainView.regular_type.setOnClickListener { onTypeVote.OnTypeCreate(true) }
        mainView.target_type.setOnClickListener { onTypeVote.OnTypeCreate(false) }
        return mainView
    }

    interface OnTypeVote{
        fun OnTypeCreate(regular:Boolean)
    }
}