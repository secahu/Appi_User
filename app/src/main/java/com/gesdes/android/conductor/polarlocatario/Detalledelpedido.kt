package com.gesdes.android.conductor.polarlocatario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Detalledelpedido : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalledelpedido)
       var pk = intent.getStringExtra("PK").toString()

    }
}
