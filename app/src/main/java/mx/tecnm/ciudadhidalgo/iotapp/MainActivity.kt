package mx.tecnm.ciudadhidalgo.iotapp

import android.app.DownloadManager.Request
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request.Method

class MainActivity : AppCompatActivity() {
    lateinit var etLoginUser: EditText //declarar una variable que va inicializar despues
    lateinit var etLoginPassword: EditText
    lateinit var btnLoginStart: Button
    lateinit var sesion:SharedPreferences
    //var etLoginPassword: EditText? = null //declaramos la variable ya inicializada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etLoginUser = findViewById(R.id.etLoginUser)
        etLoginPassword = findViewById(R.id.etLoginPassword)
        btnLoginStart = findViewById(R.id.btnLoginStart)
        sesion = getSharedPreferences("sesion", 0) //modo 0, permite leer y escribir en el archivo

        //accion que va realizar el boton iniciar
        btnLoginStart.setOnClickListener { longin() }
    }

    private fun longin() {
        val url = Uri.parse(Config.URL + "login")
            .buildUpon()
            .build().toString()

        val peticion = object:StringRequest(Method.POST, url, {
            response -> with(sesion.edit()){
            putString("jwt", response)
            putString("username", etLoginUser.text.toString())
            apply()
          }
            startActivity(Intent(this, MainActivity2::class.java))
        }, {
            error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show() //esta renombrado a error el it
        }){
            override fun getParams(): Map<String, String>{
                val body: MutableMap<String, String> = HashMap()
                body["username"] = etLoginUser.text.toString()
                body.put("password", etLoginPassword.text.toString())
                return body             //los dos valores los guarda y los envia al servidor (se los manda en body)
            }
        }
        MySingleton.getInstance(applicationContext).addToRequestQueue(peticion) //es un meeseeks
    }
}