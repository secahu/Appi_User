package com.gesdes.android.conductor.polarlocatario.Fragment

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import com.gesdes.android.conductor.polarlocatario.Login_activity
import com.gesdes.android.conductor.polarlocatario.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.fragment_perfil.*
import java.net.URL

class Perfil : Fragment() {
    companion object {
        fun newInstance(): Perfil = Perfil()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_perfil, container, false)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(state: Bundle?)
    {
        super.onActivityCreated(state)
        val preferencias = this.requireActivity().getSharedPreferences("variables", Context.MODE_PRIVATE)

        var role=preferencias.getString("encargado", "").toString()

          var nombre = preferencias.getString("nombre", "").toString()
        var direccion = preferencias.getString("direccion", "").toString()

        var  telefono= preferencias.getString("telefono", "").toString()
        var correo= preferencias.getString("correo", "").toString()

        var Imgbase64 = preferencias.getString("imagen", "").toString()
      Picasso.get().load(Imgbase64).into(fotouser);

        roltext.text= role
        nombretext.text=nombre
        telefonotext.text=telefono
        correotext.text =correo
        direcciontext.text = direccion


    }
    fun cerrarsesion(view: View){
        val preferencias = this.requireActivity().getSharedPreferences("variables", Context.MODE_PRIVATE)

        val editor = preferencias.edit()
        editor.putString("login", "sesioncerrada")
        editor.commit()
        val intent = Intent(activity, Login_activity::class.java)

        // start your next activity
        startActivity(intent)
        this.requireActivity().finish();
    }


}
