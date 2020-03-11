package com.gesdes.android.conductor.appi_user

import android.content.Context
import android.content.Intent
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
import org.json.JSONException
import org.json.JSONObject
import android.graphics.Color
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView


class Detalle : AppCompatActivity() {

    var ciudadano = ""
    var telefono = ""
    var pk = String()
    var imagen = String()
    var URL:String="https://appis-apizaco.gesdesapplication.com/api/"
    var URL2:String="https://appis-apizaco.gesdesapplication.com/api/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
        URL +="GetYellowIncidentDetail"
        URL2 +="IncidenciasUserComentariosCreate"
        pk = intent.getStringExtra("PK").toString()
        progress_Bar2.visibility = View.VISIBLE

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
                Request.Method.POST, URL, datos,
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject) {

                        try {
                            progress_Bar2.visibility = View.INVISIBLE

                            val result = response.get("result") as Int

                            if (result == 1) {
                                try {

                                    val guias = response.getJSONObject("alerta")
                                    dfecha.text = guias.getString("fechA_R")
                                    ciudadano = guias.getString("delito")
                                    telefono = guias.getString("telefono")
                                    imagen = guias.getString("evidenciA_USUARIO")
                                    dciudadano.text = ciudadano
                                    dcelular.text = telefono
                                    ddireccion.text = guias.getString("direccioN_USUARIO")
                                    ddescripcion.text = guias.getString("descripcioN_USUARIO")
                                    val decodedString1 = Base64.decode(imagen, Base64.DEFAULT)
                                    val decodedByte = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.size)
                                    val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, decodedByte)
                                    adinciona.setImageDrawable(roundedBitmapDrawable)
                                    val preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE)
                                    val editor = preferencias.edit()

                                    editor.putString("imagentemporal", imagen)
                                    editor.commit()

                                    val comentarios = guias.getJSONArray("comentarios")

                                    for (i in 0..comentarios.length()) {

                                        if (comentarios.getJSONObject(i).getString("pK_CIUDADANO").isEmpty()) {
                                            addComment(false, "CENTRO DE MANDO", comentarios.getJSONObject(i).getString("comentario"), comentarios.getJSONObject(i).getString("usuariO_IMG"));
                                        } else {
                                            addComment(true, "CIUDADANO", comentarios.getJSONObject(i).getString("comentario"), comentarios.getJSONObject(i).getString("ciudadanO_IMG"));
                                        }
                                        progress_Bar2.visibility = View.INVISIBLE

                                    }
                                } catch (es: Exception) {
                                    Log.d("sergio1", "" + es.toString())
                                    progress_Bar2.visibility = View.INVISIBLE

                                }

                            } else {
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                            progress_Bar2.visibility = View.INVISIBLE

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
            button4.setBackgroundColor(Color.parseColor("#5C85BF"))

            button4.isEnabled = false

            registraComentarioDetalle()

        }
    }



    fun registraComentarioDetalle() {
        progress_Bar1.visibility=View.VISIBLE

        val preferencias = this.getSharedPreferences("variables", Context.MODE_PRIVATE)
       var comentario = etComentarioAlertaAmarillaAdd.getText().toString()
        etComentarioAlertaAmarillaAdd.setText("")
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
                Request.Method.POST, URL2, datos,

                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject) {

                        try {
                            button4.setBackgroundColor(Color.parseColor("#052A5F"))

                            val result = response.get("result") as Int

                            if (result == 1) {
                                try {
                                    progress_Bar1.visibility=View.INVISIBLE

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

fun imagengrande(view: View){

    val intent = Intent(this, Main2Activity::class.java)
    startActivity(intent)


}

}
