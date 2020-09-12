package com.kirvigen.vezdekod_vkhackathon.Fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kirvigen.vezdekod_vkhackathon.CollectActivity
import com.kirvigen.vezdekod_vkhackathon.R
import com.kirvigen.vezdekod_vkhackathon.Utils.ManageAllComplete
import kotlinx.android.synthetic.main.fragment_additionaly.*
import kotlinx.android.synthetic.main.fragment_additionaly.view.*
import java.text.SimpleDateFormat
import java.util.*

class AdditionalyFragment: Fragment() {

    lateinit var mainView: View
    var onDateCollect = true
    var dateInput = ""

    companion object {
        fun newInstance(): AdditionalyFragment {
            return AdditionalyFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_additionaly, container, false)

        mainView.name.text = (activity as CollectActivity).data.author

        mainView.back.setOnClickListener { (activity as CollectActivity).moveViewPager(true) }

        mainView.date.setOnClickListener { openDatePicker() }
        mainView.typed_collected.check(R.id.onDate)

        mainView.typed_collected.setOnCheckedChangeListener { p0, p1 ->
            if(p1 == R.id.onDate){
                onDateCollect = true
                mainView.dateBlock.alpha = 1f
                mainView.date.setOnClickListener { openDatePicker() }
            }else{
                onDateCollect = false
                mainView.dateBlock.alpha = 0.5f
                mainView.date.setOnClickListener(null)
            }
        }

        val calendar = Calendar.getInstance()
        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH)
        val d = calendar.get(Calendar.DAY_OF_MONTH)+1

        setDate(y,m,d)

        mainView.buttonCreate.setOnClickListener {
            (activity as CollectActivity).data.lock_date = onDateCollect
            if(onDateCollect){
                (activity as CollectActivity).data.date = dateInput
            }
            (activity as CollectActivity).moveToPostCreate()
        }

        return mainView
    }

    fun setDate(year: Int,mont: Int,day: Int){
        dateInput = "$year-${mont+1}-$day"
        val sdf = SimpleDateFormat("d MMMM")
        val netDate = Date(year,mont,day)
        mainView.date.text = sdf.format(netDate)
    }

    fun openDatePicker(){
        val calendar = Calendar.getInstance()
        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH)
        val d = calendar.get(Calendar.DAY_OF_MONTH)

        context?.let {
            DatePickerDialog(it, { p0, p1, p2, p3 ->
                if (checkDate(p1, p2 + 1, p3)) {
                   setDate(p1,p2,p3)
                } else {
                    Toast.makeText(it, "Дата не может быть в прошедшем времени!", Toast.LENGTH_SHORT).show()
                }
            }, y, m, d).show()
        }

    }

    private fun checkDate(year: Int, mont: Int, day: Int): Boolean {
        val t_y1: Int =year * 31536000 + mont * 24 * 60 * 60 * 30 + day * 24 * 60 * 60
        val calendar = Calendar.getInstance()
        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH)+1
        val d = calendar.get(Calendar.DAY_OF_MONTH)
        val t_y2: Int =y * 31536000 + m * 24 * 60 * 60 * 30 + d * 24 * 60 * 60
        return (t_y1>=t_y2)
    }

}