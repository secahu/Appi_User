package com.gesdes.android.conductor.polarlocatario

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login_activity.*
import org.json.JSONException
import org.json.JSONObject
import java.util.logging.Logger


class Login_activity : AppCompatActivity() {
    private var progressBar: ProgressBar? = null
    private var i = 0
    private var txtView: TextView? = null
    private val handler = Handler()
    var URL:String= ""
    var  PERMISSIONS_REQUEST = 100;
    var cell = ""
    var password = ""
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.INTERNET)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        URL = getString(R.string.URL) + "TiendasLogin"
        setContentView(R.layout.activity_login_activity)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
verifyStoragePermissions(this)
        }

        val preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE)
        var sesion=preferencias.getString("login", "").toString()
        if(sesion=="sesioniniciada"){
                val intent = Intent(this@Login_activity, MainActivity::class.java)
                startActivity(intent)
                finish();
        }
        progressBar = findViewById<ProgressBar>(R.id.progress_Bar) as ProgressBar

    }

fun verifyStoragePermissions(activity: Activity) {
        // Check if we have write permission
         var permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.INTERNET);

        if ((permission != PackageManager.PERMISSION_GRANTED)
        ){
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    fun entrar( view:View){
        logiin.setBackgroundColor(Color.parseColor("#E89C54"))
        cell = _cellText.getText().toString()
        password = _passwordText.getText().toString()
        Logger.getLogger(Login_activity::class.java.name).warning(cell)
        Logger.getLogger(Login_activity::class.java.name).warning(password)

        progress_Bar.visibility=View.VISIBLE
        logiin.isEnabled=false;

        jsonfincion()


    }




    fun jsonfincion() {
        val datos = JSONObject()

        try {
            datos.put("TELEFONO", cell)
            datos.put("PASSWORD", password)
            Logger.getLogger(Login_activity::class.java.name).warning(cell)
            Logger.getLogger(Login_activity::class.java.name).warning(password)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val requstQueue = Volley.newRequestQueue(this)

            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.POST,URL , datos,
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject) {

                        try {
                            logiin.setBackgroundColor(Color.parseColor("#DF6F05"))

                            var mensaje = response.getInt("resultado")


                            if (mensaje == 1) {
                                logiin.isEnabled = true;
                                progress_Bar.visibility=View.INVISIBLE

                                val intent = Intent(this@Login_activity, MainActivity::class.java)


                                // start your next activity
                                var json_data = response.getJSONObject("tienda")

                                var pK1 = json_data.getString("pk")
                                var nombre = json_data.getString("nombre")
                                var direccion = json_data.getString("direccion")
                                var imagen = json_data.getString("imagen")

                                var banco = json_data.getString("banco")
                                var folio = json_data.getString("folio")
                                var correo = json_data.getString("correo")

                                var telefono = json_data.getString("telefono")
                                var cuenta = json_data.getString("cuenta")
                                var clabe = json_data.getString("clabe")
                                var encargado = json_data.getString("encargado")

                                val preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE)
                                val editor = preferencias.edit()


                                editor.putString("pk", pK1)
                                editor.putString("nombre", nombre)
                                editor.putString("encargado", encargado)
                                editor.putString("clabe", clabe)
                                editor.putString("imagen", imagen)
                                editor.putString("cuenta", cuenta)
                                editor.putString("folio", folio)
                                editor.putString("banco", banco)
                                editor.putString("direccion", direccion)
                                editor.putString("correo", correo)
                                editor.putString("telefono", telefono)
                                if(checkBox.isChecked) {
                                    editor.putString("login", "sesioniniciada")
                                }

                                editor.commit()

                                startActivity(intent)
                                finish();
                            } else {
                                modal()
                                logiin.isEnabled = true
                                progress_Bar.visibility=View.INVISIBLE

                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }


                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        logiin.isEnabled = true;
                        progress_Bar.visibility=View.INVISIBLE

                        modal()
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
    fun modal(){
        // Initialize a new instance of
        val builder = AlertDialog.Builder(this@Login_activity)

        // Set the alert dialog title
        builder.setTitle("Datos incorrectos")

        // Display a message on alert dialog
        builder.setMessage("Contraseña o usuario incorrecto")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("Aceptar"){dialog, which ->
            // Do something when user press the positive button

            // Change the app background color
        }



        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    }


