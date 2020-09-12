package com.kirvigen.vezdekod_vkhackathon

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.kirvigen.vezdekod_vkhackathon.Objects.Collecting
import com.kirvigen.vezdekod_vkhackathon.Utils.Utils
import kotlinx.android.synthetic.main.activity_view_collect.*
import maes.tech.intentanim.CustomIntent
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class ViewCollectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_collect)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window // in Activity's onCreate() for instance
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        val data = intent.getParcelableExtra("data") as Collecting

        if (intent.hasExtra("byteArray")) {
            data.photo = BitmapFactory.decodeByteArray(
                intent.getByteArrayExtra("byteArray"), 0, intent.getByteArrayExtra("byteArray").size
            )
            image.setImageBitmap(data.photo)
        }
        back.setOnClickListener {
            onBackPressed()
            CustomIntent.customType(this, "right-to-left")
        }
        name.setText(data.name)

        if(data.lock_date && !data.regular) {
            val netDate = SimpleDateFormat("yyyy-MM-dd").parse(data.date)

            val defference = Utils.getdifferenceDay(Date().time, netDate.time)
            var day_word = "дней"

            val last_sym = defference.toString()[defference.toString().length - 1].toString()
            if(last_sym == "1" && !defference.toString().contains("11")) day_word = "день"
            else if(last_sym == "2")day_word = "дня"
            else if(last_sym == "3")day_word = "дня"
            else if(last_sym == "4")day_word = "дня"

            val sdf = SimpleDateFormat("d MMMM")
            deadline.text ="Нужно собрать до "+sdf.format(netDate)
            text_deadline.text = "Сбор закончится через $defference $day_word"

        }else if(!data.lock_date && !data.regular){
            text_deadline.text = ""
        }
        else if(data.regular){
            deadline.text = "Нужно собрать в сентябре"
            text_deadline.text = "Помощь нужна каждый месяц"
        }
        desc.setText(data.desk)

        end_sum.text = "${data.sum} ₽"

        val procent = (data.real_sum.toFloat()/data.sum.toFloat())
        val paramProgress = progress.layoutParams
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        paramProgress.width = (((width-Utils.dpToPixels(20,this))*procent).toFloat()).toInt()
        progress.layoutParams = paramProgress
        if(data.real_sum == data.sum){
            real_sum.visibility = View.GONE
            complete_sum.visibility = View.VISIBLE
            complete_sum.text = "${data.real_sum} ₽ собраны!"
        }else{

            complete_sum.visibility = View.GONE
            real_sum.visibility = View.VISIBLE
            real_sum.text = "${data.real_sum} ₽"
        }
        progress_text.text = "Собрано "+(data.real_sum).toInt().toString()+" ₽ из ${data.sum} ₽"
        progress_small.progress = ((data.real_sum.toFloat()/data.sum.toFloat())*100f).roundToInt()
    }
}