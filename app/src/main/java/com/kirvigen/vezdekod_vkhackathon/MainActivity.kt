package com.kirvigen.vezdekod_vkhackathon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kirvigen.vezdekod_vkhackathon.Fragments.TypedCollectFragment
import kotlinx.android.synthetic.main.activity_main.*
import maes.tech.intentanim.CustomIntent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCreate.setOnClickListener {
            val intent = Intent(this,CollectActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            CustomIntent.customType(this,"left-to-right")
        }

        content()
}

    private fun content() {

    }


}