package com.gesdes.android.conductor.appi_user

import android.content.Context
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val preferencias = this.getSharedPreferences("variables", Context.MODE_PRIVATE)
        var img=preferencias.getString("imagentemporal", "").toString()
        val decodedString1 = Base64.decode(img, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.size)
        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, decodedByte)
        imagengrande.setImageDrawable(roundedBitmapDrawable)


    }

    fun terminar(view: View){
        finish()
    }
}
