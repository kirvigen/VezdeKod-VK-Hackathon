package com.kirvigen.vezdekod_vkhackathon

import android.R.attr.bitmap
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.kirvigen.vezdekod_vkhackathon.Adapters.FragmentAdapter
import com.kirvigen.vezdekod_vkhackathon.Fragments.AdditionalyFragment
import com.kirvigen.vezdekod_vkhackathon.Fragments.BigFormFragment
import com.kirvigen.vezdekod_vkhackathon.Fragments.TypedCollectFragment
import com.kirvigen.vezdekod_vkhackathon.Objects.Collecting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import maes.tech.intentanim.CustomIntent
import java.io.ByteArrayOutputStream
import kotlin.coroutines.CoroutineContext


class CollectActivity : FragmentActivity(),CoroutineScope,TypedCollectFragment.OnTypeVote,BigFormFragment.OnImageSelect {

    lateinit var fragmentAdapter: FragmentAdapter
    lateinit var viewPager:ViewPager2
    val PICK_PHOTO = 101
    var data: Collecting = Collecting()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect)

        fragmentAdapter = FragmentAdapter(this)
        fragmentAdapter.addFragments(TypedCollectFragment.newInstance())
        viewPager = findViewById(R.id.viewPager)

        viewPager.adapter = fragmentAdapter
        viewPager.isUserInputEnabled = false

    }

    fun moveToPostCreate(){
        val intent = Intent(this, CreatePostActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val bs = ByteArrayOutputStream()
        data.photo?.compress(Bitmap.CompressFormat.JPEG, 50, bs)
        intent.putExtra("byteArray", bs.toByteArray())
        data.photo = null
        intent.putExtra("data", data)
        startActivity(intent)

        CustomIntent.customType(this, "left-to-right")
        finish()
    }

    fun moveViewPager(back: Boolean){
        viewPager.setCurrentItem(
            if (back) viewPager.currentItem - 1 else viewPager.currentItem + 1,
            true
        )
    }

    override fun OnTypeCreate(regular: Boolean) {
        fragmentAdapter.addFragments(BigFormFragment.newInstance(regular))
        if(!regular){
            fragmentAdapter.addFragments(AdditionalyFragment.newInstance())
        }
        data.regular = regular
        viewPager.adapter = fragmentAdapter
        viewPager.run { currentItem = 1 }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO && resultCode == RESULT_OK) {
            if (data == null) {
                return
            }
            Log.e("SSSSS", data.data?.encodedPath)
            data.data?.let { (fragmentAdapter.getFragment(1) as BigFormFragment).initPhoto(it) }
        }
    }



    override fun onImageSelect() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_PHOTO)
    }

    override fun onBackPressed() {
        if(viewPager.currentItem >= 2){
            moveViewPager(true)
        }else if(viewPager.currentItem == 1){
            val intent = Intent(this, CollectActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            CustomIntent.customType(this, "right-to-left")
            finish()
        }else{
            super.onBackPressed()
            CustomIntent.customType(this, "right-to-left")
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}