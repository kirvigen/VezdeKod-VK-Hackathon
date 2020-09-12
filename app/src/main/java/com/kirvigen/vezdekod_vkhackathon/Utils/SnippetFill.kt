package com.kirvigen.vezdekod_vkhackathon.Utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import com.kirvigen.vezdekod_vkhackathon.Objects.Collecting
import kotlinx.android.synthetic.main.snippet.view.*
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class SnippetFill(val v:View,var collect: Collecting) {
    var onClickListener:(()->Unit)? = null
    @SuppressLint("SetTextI18n")
    fun bind(enable:Boolean){
        v.target.text = collect.name

        collect.photo?.let {
            v.image.setImageBitmap(it)
        }
        if(collect.lock_date && !collect.regular) {
            val netDate = SimpleDateFormat("yyyy-MM-dd").parse(collect.date)

            val defference = Utils.getdifferenceDay(Date().time,netDate.time)
            var day_word = "дней"

            val last_sym = defference.toString()[defference.toString().length-1].toString()
             if(last_sym == "1" && !defference.toString().contains("11")) day_word = "день"
             else if(last_sym == "2")day_word = "дня"
             else if(last_sym == "3")day_word = "дня"
             else if(last_sym == "4")day_word = "дня"
            Log.e("AAa",last_sym.toString())
            v.text_deadline.text = collect.author + " • Закончится через $defference $day_word"
        }else if(!collect.lock_date && !collect.regular){
            v.text_deadline.text = collect.author
        }
        else if(collect.regular){
            v.text_deadline.text = collect.author + " • Помощь нужна каждый месяц"
        }
        v.progress_text.text = "Помогите первым"
        if(enable) {
            v.container.setOnClickListener {
                onClickListener?.let { it() }
            }
            v.button_helping.alpha = 1f
            v.button_helping.setOnClickListener {
                if(v.progress.progress != 100){
                    updateProgress(v.progress.progress+10)
                }
            }
        }else{
            v.button_helping.alpha = 0.5f
        }
    }

    @SuppressLint("SetTextI18n")
    fun updateProgress(progress:Int){
        collect.real_sum = (collect.sum*progress/100)
        v.progress_text.text = "Собрано в сентябре "+(collect.sum*progress/100).toInt().toString()+" ₽"
        v.progress.progress = progress
    }
}