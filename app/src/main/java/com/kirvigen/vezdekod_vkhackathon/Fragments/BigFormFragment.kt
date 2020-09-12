package com.kirvigen.vezdekod_vkhackathon.Fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kirvigen.vezdekod_vkhackathon.CollectActivity
import com.kirvigen.vezdekod_vkhackathon.Objects.Collecting
import com.kirvigen.vezdekod_vkhackathon.R
import com.kirvigen.vezdekod_vkhackathon.Utils.ManageAllComplete
import com.kirvigen.vezdekod_vkhackathon.Utils.Utils
import kotlinx.android.synthetic.main.fragment_additionaly.view.*
import kotlinx.android.synthetic.main.fragment_big_form.view.*
import kotlinx.android.synthetic.main.fragment_big_form.view.back
import kotlinx.android.synthetic.main.fragment_big_form.view.name
import kotlinx.android.synthetic.main.fragment_big_form.view.next
import maes.tech.intentanim.CustomIntent
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class BigFormFragment : Fragment(),ManageAllComplete.OnCompleteChange {

    lateinit var mainView: View
    lateinit var manager: ManageAllComplete

    companion object {
        fun newInstance(regular: Boolean): BigFormFragment {
            val fr = BigFormFragment()
            val args = Bundle()
            args.putBoolean("regular", regular)
            fr.arguments = args
            return fr
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_big_form, container, false)
        manager = ManageAllComplete(5,this)

        mainView.image_picker.setOnClickListener { (activity as OnImageSelect).onImageSelect() }

        if(arguments?.getBoolean("regular")!!){
            mainView.label_sum.text = "Сумма в месяц, ₽"
            mainView.title.text = "Регулярный сбор"
            mainView.text_next.text = "Создать сбор"
            mainView.profile_block.visibility = View.VISIBLE
            mainView.sum.hint = "Сколько нужно в месяц?"
            mainView.target.hint = "Например, для поддержки приюта"
            mainView.author.text = (activity as CollectActivity).data.author
        }

        mainView.pay_type.text = (activity as CollectActivity).data.pay_type

        mainView.back.setOnClickListener {
            val intent = Intent(context,CollectActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            CustomIntent.customType(context,"right-to-left")
            activity?.finish()
        }

        mainView.delete_image.setOnClickListener {
            manager.deleteParam(0)
            mainView.image_block.visibility = View.GONE
            mainView.image_picker.visibility = View.VISIBLE
        }

        initEditText(1,mainView.name)
        initEditText(2,mainView.sum)
        initEditText(3,mainView.target)
        initEditText(4,mainView.desc)

        return mainView
    }

    fun initEditText(index:Int,editText: EditText){
        Utils.reactEditTextOutline(editText)
        editText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().trim() != "" ){
                    if(editText == mainView.sum) {
                        if (p0.toString().toInt() >= 1)
                            manager.completeParam(index)
                    }else{
                        manager.completeParam(index)
                    }
                }else{
                    manager.deleteParam(index)
                }
            }
        })
    }
    override fun OnComplete() {
        mainView.next.alpha = 1f
        mainView.next.setOnClickListener {
            (activity as CollectActivity).data.name = mainView.name.text.toString()
            (activity as CollectActivity).data.sum = mainView.sum.text.toString().toInt()
            (activity as CollectActivity).data.target = mainView.target.text.toString()
            (activity as CollectActivity).data.pay_type = mainView.pay_type.text.toString()
            (activity as CollectActivity).data.desk = mainView.desc.text.toString()
            if(!arguments?.getBoolean("regular")!!) {
                (activity as CollectActivity).moveViewPager(false)
            }else{
                (activity as CollectActivity).moveToPostCreate()
            }
        }
    }

    override fun OnDisabled() {
        mainView.next.alpha = 0.5f
        mainView.next.setOnClickListener{
            Toast.makeText(context, "Заполните все поля!", Toast.LENGTH_SHORT).show()
        }
    }

    fun initPhoto(uri: Uri){

        val inputStream: InputStream? = uri.let {
            activity?.contentResolver?.openInputStream(
                it
            )
        }

        manager.completeParam(0)
        mainView.image_picker.visibility = View.GONE
        mainView.image_block.visibility = View.VISIBLE
        val b = BitmapFactory.decodeStream(inputStream)
        mainView.image.setImageBitmap(b)
        (activity as CollectActivity).data.image = uri
        (activity as CollectActivity).data.photo = b
    }

    interface OnImageSelect{
        fun onImageSelect()
    }
}