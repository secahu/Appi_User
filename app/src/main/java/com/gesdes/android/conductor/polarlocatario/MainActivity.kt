package com.gesdes.android.conductor.polarlocatario

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.gesdes.android.conductor.polarlocatario.Fragment.Incidencias
import com.gesdes.android.conductor.polarlocatario.Fragment.Perfil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId

import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    var TOKEN: String=""
    var URL:String= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       URL =  getString(R.string.URL) + "ActualizaTokenTiendas"

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationViewinicio)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val vent = Incidencias.newInstance()
        openFragment(vent)
agregaToken()

<<<<<<< Updated upstream:app/src/main/java/com/gesdes/android/conductor/polarlocatario/MainActivity.kt
    }
    override fun onBackPressed() { // Añade más funciones si fuese necesario
        super.onBackPressed() // Invoca al método
        val vent = Incidencias.newInstance()
        openFragment(vent)
=======


>>>>>>> Stashed changes:app/src/main/java/com/gesdes/android/conductor/appi_user/MainActivity.kt
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.perfi -> {
                val artistsFragment = Perfil.newInstance()
                openFragment(artistsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.incidencia -> {
                val inci = Incidencias.newInstance()
                openFragment(inci)
                return@OnNavigationItemSelectedListener true
            }


        }
        false
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
fun cerrarsesion(view: View){
    val preferencias = this.getSharedPreferences("variables", Context.MODE_PRIVATE)

    val editor = preferencias.edit()
        editor.putString("login", "sesioncerrada")
        editor.commit()
        val intent = Intent(this, Login_activity::class.java)

        // start your next activity
        startActivity(intent)
        this.finish();
    }
    fun agregaToken(){
try{
        FirebaseApp.initializeApp(this)
        TOKEN = FirebaseInstanceId.getInstance().getToken().toString()
        RegisterToken()
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    }

    fun RegisterToken() {
        val preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE)
        var pk = preferencias.getString("pk", "")

        val datos = JSONObject()
        try {
            datos.put("PK", pk)
            datos.put("TOKEN", TOKEN)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val requestQueue = Volley.newRequestQueue(this)

<<<<<<< Updated upstream:app/src/main/java/com/gesdes/android/conductor/polarlocatario/MainActivity.kt
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.POST,URL, datos,
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject) {

                        try {

                            var mensaje = response.getInt("resultado")




                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }


                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {

                    }
                }
=======
        val jsonObjectRequest = object : JsonObjectRequest(com.android.volley.Request.Method.POST, "http://appis-apizaco.gesdesapplication.com/api/EditTokenUsuarios", datos,
                Response.Listener { },
                Response.ErrorListener { }
>>>>>>> Stashed changes:app/src/main/java/com/gesdes/android/conductor/appi_user/MainActivity.kt
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

        requestQueue.add(jsonObjectRequest)
    }


}
