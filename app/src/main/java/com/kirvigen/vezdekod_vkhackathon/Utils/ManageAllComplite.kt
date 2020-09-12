package com.kirvigen.vezdekod_vkhackathon.Utils

import kotlin.math.sign

class ManageAllComplete(size:Int,val onCompleteChange: OnCompleteChange) {
    private var list:MutableList<Boolean> = mutableListOf()
    init {
        for(i in  0 until size)
            list.add(false)
    }
    interface OnCompleteChange{
        fun OnComplete()
        fun OnDisabled()
    }

    fun completeParam(index:Int){
        list[index] = true
        checking()
    }

    private fun checking(){
        var counter = 0
        for (i in list)
              if(i) counter+=1
        if(counter == list.size)
        onCompleteChange.OnComplete()
    }

    fun deleteParam(index:Int){
        list[index] = false
        onCompleteChange.OnDisabled()
    }
}