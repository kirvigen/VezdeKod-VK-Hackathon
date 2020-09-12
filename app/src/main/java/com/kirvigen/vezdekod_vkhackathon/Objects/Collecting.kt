package com.kirvigen.vezdekod_vkhackathon.Objects

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Collecting() :Parcelable {
    var image: Uri? = null
    var photo:Bitmap? =null
    var name:String = ""
    var sum:Int = 0
    var real_sum = 0
    var desk:String = ""
    var target:String = ""
    var author:String = "Кирилл Конищев"
    var pay_type:String = "Счёт VK Pay · 1234"
    var lock_date = true
    var date = ""
    var regular = false
    var text = ""

    constructor(parcel: Parcel) : this() {
        photo = parcel.readParcelable(Bitmap::class.java.classLoader)
        image = parcel.readParcelable(Uri::class.java.classLoader)
        name = parcel.readString().toString()
        sum = parcel.readInt()
        real_sum = parcel.readInt()
        desk = parcel.readString().toString()
        target = parcel.readString().toString()
        author = parcel.readString().toString()
        pay_type = parcel.readString().toString()
        lock_date = parcel.readByte() != 0.toByte()
        date = parcel.readString().toString()
        regular = parcel.readByte() != 0.toByte()
        text = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(photo,flags)
        parcel.writeParcelable(image, flags)
        parcel.writeString(name)
        parcel.writeInt(sum)
        parcel.writeInt(real_sum)
        parcel.writeString(desk)
        parcel.writeString(target)
        parcel.writeString(author)
        parcel.writeString(pay_type)
        parcel.writeByte(if (lock_date) 1 else 0)
        parcel.writeString(date)
        parcel.writeByte(if (regular) 1 else 0)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Collecting> {
        override fun createFromParcel(parcel: Parcel): Collecting {
            return Collecting(parcel)
        }

        override fun newArray(size: Int): Array<Collecting?> {
            return arrayOfNulls(size)
        }
    }
}