package com.gesdes.android.conductor.appi_user

import android.app.ProgressDialog
import android.content.Context
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_detalle.*
import kotlinx.android.synthetic.main.fragment_perfil.*
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import android.graphics.Bitmap
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.LayoutInflater
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_login_activity.*
import org.json.JSONArray


class Detalle : AppCompatActivity() {

var ciudadano=""
    var telefono=""
    var pk = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
        pk = intent.getStringExtra("PK").toString()
        progress_Bar2.visibility=View.VISIBLE
        adinciona.visibility=View.INVISIBLE

        detalle()

    }

    fun detalle() {


        val datos = JSONObject()
        try {
            datos.put("PK1", pk)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val requstQueue = Volley.newRequestQueue(this@Detalle)

        val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.POST, "https://appis-apizaco.gesdesapplication.com/api/GetYellowIncidentDetail", datos,
                object : Response.Listener<JSONObject> {
                    override fun onResponse( response: JSONObject) {

                        try {

                            val result = response.get("result") as Int
progress_Bar2.visibility=View.INVISIBLE
                            adinciona.visibility=View.VISIBLE
                            if (result == 1) {
                                try {

                                    val guias = response.getJSONObject("alerta")
                                    dfecha.text = guias.getString("fechA_R")
                                    ciudadano = guias.getString("estatus")
                                    telefono = guias.getString("telefono")
                                    var imagen = guias.getString("evidenciA_USUARIO")
                                    dciudadano.text = ciudadano
                                    dcelular.text =telefono
                                    ddireccion.text = guias.getString("direccioN_USUARIO")
                                    ddescripcion.text = guias.getString("descripcioN_USUARIO")
                                    val decodedString1 = Base64.decode(imagen, Base64.DEFAULT)
                                    val decodedByte = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.size)
                                    val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, decodedByte)
                                    adinciona.setImageDrawable(roundedBitmapDrawable)
                                    val comentarios = guias.getJSONArray("comentarios")

                                    for (i in 1..comentarios.length()) {

                                        if (comentarios.getJSONObject(i).getString("pK_CIUDADANO").isEmpty()) {
                                            addComment(false, "CENTRO DE MANDO", comentarios.getJSONObject(i).getString("comentario"), comentarios.getJSONObject(i).getString("usuariO_IMG"));
                                        } else {
                                            addComment(true, "CIUDADANO", comentarios.getJSONObject(i).getString("comentario"), comentarios.getJSONObject(i).getString("ciudadanO_IMG"));
                                        }
                                        progress_Bar2.visibility=View.INVISIBLE

                                    }
                                } catch (es: Exception) {
                                    Log.d("sergio1", "" + es.toString())
                                    progress_Bar2.visibility=View.INVISIBLE

                                }

                            } else {
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                            progress_Bar2.visibility=View.INVISIBLE

                        }

                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(this@Detalle, "falta login  on error:" + error, Toast.LENGTH_LONG).show()

                    }
                }
        ) {

            //here I want to post data to sever
        }

        val MY_SOCKET_TIMEOUT_MS = 15000
        val maxRetries = 2
        jsonObjectRequest.setRetryPolicy(
                DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        maxRetries,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        )

        requstQueue.add(jsonObjectRequest)


    }


    private fun addComment(right: Boolean, nombre: String, comentario: String, img: String) {
        val constraintLayout: ConstraintLayout
        val inflater = LayoutInflater.from(this)
        val tvuser: TextView
        val tvComment: TextView
        val ivUser: ImageView
        var id = R.layout.left_coment
        if (!right) {
            id = R.layout.rigth_comment
            constraintLayout = inflater.inflate(id, null, false) as ConstraintLayout
            tvuser = constraintLayout.findViewById(R.id.tvUserRigthComment) as TextView
            tvComment = constraintLayout.findViewById(R.id.tvCommentRigthCooment) as TextView
            ivUser = constraintLayout.findViewById(R.id.ivrigthUserComent) as ImageView
        } else {
            id = R.layout.left_coment
            constraintLayout = inflater.inflate(id, null, false) as ConstraintLayout
            tvuser = constraintLayout.findViewById(R.id.tvUserComentLeft) as TextView
            tvComment = constraintLayout.findViewById(R.id.tvComentLeft) as TextView
            ivUser = constraintLayout.findViewById(R.id.ivrLeftUserComent) as ImageView
        }

        tvuser.text = nombre
        tvComment.text = comentario

        if (!img.isEmpty()) {
            val decodedString = Base64.decode(img, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, decodedByte)
            roundedBitmapDrawable.isCircular = true
            ivUser.setImageDrawable(roundedBitmapDrawable)
        }

        llComentarios.addView(constraintLayout)
        llComentarios.computeScroll()

    }

    fun registrarComentario(view: View) {
        var com = etComentarioAlertaAmarillaAdd.getText().toString()
        if(com!="") {

            button4.isEnabled = false

            registraComentarioDetalle()

        }
    }

    fun registraComentarioDetalle() {
        progress_Bar1.visibility=View.VISIBLE

        val preferencias = this.getSharedPreferences("variables", Context.MODE_PRIVATE)
       var comentario = etComentarioAlertaAmarillaAdd.getText().toString()

        var pkuser=preferencias.getString("pK1", "").toString()
        var tel=preferencias.getString("telefono", "").toString()
        var foto=preferencias.getString("imagen", "").toString()

        val datos = JSONObject()
        try {
            datos.put("PK_INCIDENCIA",pk);
            datos.put("PK_USUARIO",pkuser);
            datos.put("COMENTARIO",comentario);
            datos.put("USUARIO_C",pkuser);


        } catch (e: Exception) {
            e.printStackTrace()
        }

        val requstQueue = Volley.newRequestQueue(this@Detalle)

        val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.POST, "https://appis-apizaco.gesdesapplication.com/api/IncidenciasUserComentariosCreate", datos,

                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject) {

                        try {

                            val result = response.get("result") as Int

                            if (result == 1) {
                                try {
                                    progress_Bar1.visibility=View.INVISIBLE
                                    etComentarioAlertaAmarillaAdd?.setText("")
                                    addComment(false,"USUARIO",comentario,foto);
                                    button4.isEnabled=true

                                } catch (es: Exception) {
                                    Log.d("sergio1", "" + es.toString())

                                    etComentarioAlertaAmarillaAdd.getText().clear(); //or you can use editText.setText("");

                                    progress_Bar1.visibility=View.INVISIBLE

                                }

                            } else {
                                button4.isEnabled=true
                                button4.text=""
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                            button4.isEnabled=true
                            button4.text=""
                        }

                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(this@Detalle, "falta login  on error:" + error, Toast.LENGTH_LONG).show()

                    }
                }
        ) {

            //here I want to post data to sever
        }

        val MY_SOCKET_TIMEOUT_MS = 15000
        val maxRetries = 2
        jsonObjectRequest.setRetryPolicy(
                DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        maxRetries,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        )

        requstQueue.add(jsonObjectRequest)


    }
}
