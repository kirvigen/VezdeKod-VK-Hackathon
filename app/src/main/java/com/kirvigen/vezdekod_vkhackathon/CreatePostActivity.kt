package com.kirvigen.vezdekod_vkhackathon

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirvigen.vezdekod_vkhackathon.Objects.Collecting
import com.kirvigen.vezdekod_vkhackathon.Utils.SnippetFill
import kotlinx.android.synthetic.main.create_post_activity.*
import maes.tech.intentanim.CustomIntent
import java.io.ByteArrayOutputStream


class CreatePostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_post_activity)
        val data = intent.getParcelableExtra("data") as Collecting

        if (intent.hasExtra("byteArray")) {
                data.photo = BitmapFactory.decodeByteArray(
                    intent.getByteArrayExtra("byteArray"), 0, intent.getByteArrayExtra("byteArray").size
                )
        }

        SnippetFill(content, data).bind(false)

        close.setOnClickListener {
             val intent = Intent(this, MainActivity::class.java)
             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
             startActivity(intent)
             CustomIntent.customType(this, "right-to-left")
             finish()
         }

        author_name.text = data.author.split(" ")[0]

        success.setOnClickListener {
            data.text = text.text.toString()
            val intent = Intent(this, SuccessCreateActivity::class.java)
            intent.putExtra("data", data)
            val bs = ByteArrayOutputStream()
            data.photo?.compress(Bitmap.CompressFormat.JPEG, 50, bs)
            intent.putExtra("byteArray", bs.toByteArray())
            data.photo = null
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            CustomIntent.customType(this, "left-to-right")
            finish()
        }
    }
}