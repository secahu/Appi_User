package com.gesdes.android.conductor.polarlocatario

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.gesdes.android.conductor.polarlocatario.Fragment.mAdapter
import com.gesdes.android.conductor.polarlocatario.Fragment.mRecyclerView
import kotlinx.android.synthetic.main.activity_detalle.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class Detalle : AppCompatActivity() {
    private var listNotes = ArrayList<Note>()
var URL:String = ""
    var URL2:String = ""

    var ciudadano = ""
    var telefono = ""
    var pk = String()
    var imagen = String()
    var notesAdapter = NotesAdapter(this, listNotes)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
        URL = getString(R.string.URL) + "ObtenerPedidosPorPkTienda"
        URL2 = getString(R.string.URL) + "CambiaEstatusPedidoTienda"

        pk = intent.getStringExtra("PK").toString()
        getGuiasSocio()

        lvNotes.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
          //  Toast.makeText(this, "Click on " + listNotes[position].title, Toast.LENGTH_SHORT).show()
        }
        tvTarifaPedidoDetalle.setOnClickListener(){
enviar()
        }
    }

    inner class NotesAdapter : BaseAdapter {

        private var notesList = ArrayList<Note>()
        private var context: Context? = null

        constructor(context: Context, notesList: ArrayList<Note>) : super() {
            this.notesList = notesList
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val view: View?
            val vh: ViewHolder

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.note, parent, false)
                vh = ViewHolder(view)
                view.tag = vh
                Log.i("JSA", "set Tag for ViewHolder, position: " + position)
            } else {
                view = convertView
                vh = view.tag as ViewHolder
            }

            vh.tvTitle.text = notesList[position].title
            vh.tvContent.text = notesList[position].content

            return view
        }

        override fun getItem(position: Int): Any {
            return notesList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return notesList.size
        }
    }

    private class ViewHolder(view: View?) {
        val tvTitle: TextView
        val tvContent: TextView

        init {
            this.tvTitle = view?.findViewById(R.id.tvTitle) as TextView
            this.tvContent = view?.findViewById(R.id.tvContent) as TextView
        }
    }


    fun getGuiasSocio()
    {

        val preferencias = this.getSharedPreferences("variables", Context.MODE_PRIVATE)
        var pk2 = preferencias.getString("pk", "")


        val datos = JSONObject()
        try {
            datos.put("PK_TIENDA",pk2 )

        } catch (e: Exception) {
            e.printStackTrace()
        }
        val requstQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.POST, URL, datos,
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject) {

                        try {

                            var mensaje = response.getInt("resultado")


                            if (mensaje == 1) {

                                try {

                                    val guias = response.getJSONArray("pedidos")
                                    val guias2 = guias.getJSONObject(0)

                                    for(i in 0..(guias.length()-1)){
                                        var pklist = guias.getJSONObject(i).getString("pk")

                                        if(pk == pklist ){
                                            val categorias: JSONArray = guias2.getJSONArray("lista")
                                            for(i in 0..(categorias.length()-1)){
                                                var pklist = categorias.getJSONObject(i).getString("producto")
                                              print(pklist)
                                                listNotes.add(Note(i, pklist, pklist))
                                            }
                                        }


                                    }


                                    lvNotes.adapter = notesAdapter

                                }catch (es :Exception){
                                }

                            }else{
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {

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
    fun enviar()
    {

        val preferencias = this.getSharedPreferences("variables", Context.MODE_PRIVATE)
        var pk2 = preferencias.getString("pk", "")


        val datos = JSONObject()
        try {
                datos.put("PK", pk)
            datos.put("PK_ESTATUS", "2")

        } catch (e: Exception) {
            e.printStackTrace()
        }
        val requstQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.POST, URL2, datos,
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject) {

                        try {

                            var mensaje = response.getInt("resultado")


                            if (mensaje == 1) {

                                try {

                                    Toast.makeText(this@Detalle, "Producto actualizado " , Toast.LENGTH_SHORT).show()


                                }catch (es :Exception){
                                }

                            }else{
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {

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