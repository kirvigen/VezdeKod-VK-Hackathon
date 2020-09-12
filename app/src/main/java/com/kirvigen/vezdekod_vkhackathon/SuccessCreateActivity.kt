package com.kirvigen.vezdekod_vkhackathon

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kirvigen.vezdekod_vkhackathon.Objects.Collecting
import com.kirvigen.vezdekod_vkhackathon.Utils.SnippetFill
import kotlinx.android.synthetic.main.activity_success_create.*
import kotlinx.android.synthetic.main.activity_success_create.content
import kotlinx.android.synthetic.main.activity_success_create.text
import kotlinx.android.synthetic.main.create_post_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import maes.tech.intentanim.CustomIntent
import java.io.ByteArrayOutputStream

class SuccessCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_create)
        val data = intent.getParcelableExtra("data") as Collecting
        var photo:Bitmap? = null
        if (intent.hasExtra("byteArray")) {
            photo = BitmapFactory.decodeByteArray(
                intent.getByteArrayExtra("byteArray"), 0, intent.getByteArrayExtra("byteArray").size
            )
        }

        text.text = data.text
        data.photo = photo
        val snippetFill = SnippetFill(content,data)
        snippetFill.onClickListener = {
            Log.e("SSSSSS",data.real_sum.toString())
            data.text = text.text.toString()
            val intent = Intent(this, ViewCollectActivity::class.java)
            data.real_sum = snippetFill.collect.real_sum

            intent.putExtra("data", data)
            val bs = ByteArrayOutputStream()

            photo?.compress(Bitmap.CompressFormat.JPEG, 50, bs)
            intent.putExtra("byteArray", bs.toByteArray())

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            CustomIntent.customType(this, "left-to-right")

        }
        snippetFill.bind(true)
        data.photo = null


    }
}