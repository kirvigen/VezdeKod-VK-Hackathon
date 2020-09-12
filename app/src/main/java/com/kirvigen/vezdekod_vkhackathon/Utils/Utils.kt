package com.kirvigen.vezdekod_vkhackathon.Utils

import android.content.Context
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import com.kirvigen.vezdekod_vkhackathon.R


class Utils {
    companion object{
        fun reactEditTextOutline(editText: EditText){
            editText.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    editText.setBackgroundResource(R.drawable.bg_selectable_active)
                } else {
                    editText.setBackgroundResource(R.drawable.bg_selectable)
                }
            }
        }
        fun getdifferenceDay(unixdate1: Long, unixdate2: Long): Int {
            val milliseconds = unixdate2 - unixdate1
            return (milliseconds / (24 * 60 * 60 * 1000)).toInt()
        }
        fun dpToPixels(dp: Int, context: Context): Float {
            return dp * context.resources.displayMetrics.density
        }
    }

}