package com.gesdes.android.conductor.appi_user.Fragment

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.gesdes.android.conductor.appi_user.Login_activity

import com.gesdes.android.conductor.appi_user.R
import kotlinx.android.synthetic.main.fragment_perfil.*

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

        var role=preferencias.getString("role", "").toString()

          var nombre = preferencias.getString("nombre", "").toString()+" "+
                    preferencias.getString("apaterno", "").toString()+" "+preferencias.getString("amaterno", "").toString()

        var  telefono= preferencias.getString("telefono", "").toString()
        var correo= preferencias.getString("correo", "").toString()


        var imag = preferencias.getString("imagen", "").toString()

        val decodedString1 = Base64.decode(imag, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.size)
        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, decodedByte)
        roundedBitmapDrawable.isCircular = true
        fotouser.setImageDrawable(roundedBitmapDrawable)
        roltext.text= role
        nombretext.text=nombre
        telefonotext.text=telefono
        correotext.text =correo


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
