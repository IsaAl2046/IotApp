package mx.tecnm.ciudadhidalgo.iotapp

import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class MainActivity3 : AppCompatActivity() {
    lateinit var txNewId: TextView
    lateinit var etNewName: EditText
    lateinit var etNewType: EditText
    lateinit var etNewValue: EditText
    lateinit var  btnNewCancel: Button
    lateinit var  btnNewSave: Button

    lateinit var sesion:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        txNewId = findViewById(R.id.txNewId)
        etNewName = findViewById(R.id.etNewName)
        etNewType = findViewById(R.id.etNewType)
        etNewValue = findViewById(R.id.etNewValue)
        btnNewCancel = findViewById(R.id.btnNewCancel)
        btnNewSave = findViewById(R.id.btnNewSave)

        sesion = getSharedPreferences("sesion",0)

        btnNewCancel.setOnClickListener { finish() }

        if(intent.extras != null){
            txNewId.text = intent.extras!!.getString("id")
            etNewName.setText(intent.extras!!.getString("name"))
            etNewType.setText(intent.extras!!.getString("type"))
            etNewValue.setText(intent.extras!!.getString("value"))
            btnNewSave.setOnClickListener { saveChanges() }
        }else{
            btnNewSave.setOnClickListener { saveNew() }
        }
    }

    private fun saveNew() {
        val url = Uri.parse(Config.URL + "sensors")
            .buildUpon()
            .build().toString()

        val body = JSONObject()
        body.put("name", etNewName.text.toString())
        body.put("type", etNewType.text.toString())
        body.put("value", etNewValue.text.toString())

        val peticion = object: JsonObjectRequest(Method.POST, url, body,{ response ->
            Toast.makeText(this, "guardado".toString(), Toast.LENGTH_LONG).show()
            finish()
        }, {
                error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            Log.e("error", error.stackTraceToString())
        }){
            override fun getHeaders(): Map<String, String>{
                val body: MutableMap<String, String> = HashMap()
                body["Authorization"] = sesion.getString("jwt", "").toString()
                return body
            }
        }
        MySingleton.getInstance(applicationContext).addToRequestQueue(peticion) //es un meeseeks
    }

    private fun saveChanges() {
        val url = Uri.parse(Config.URL + "sensors/" + txNewId.text )
            .buildUpon()
            .build().toString()

        val body = JSONObject()
        body.put("name", etNewName.text.toString())
        body.put("type", etNewType.text.toString())
        body.put("value", etNewValue.text.toString())

        val peticion = object: JsonObjectRequest(Method.PUT, url, body,{ response ->
            Toast.makeText(this, "guardado".toString(), Toast.LENGTH_LONG).show()
            finish()
        }, {
                error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            Log.e("error", error.stackTraceToString())
        }){
            override fun getHeaders(): Map<String, String>{
                val body: MutableMap<String, String> = HashMap()
                body["Authorization"] = sesion.getString("jwt", "").toString()
                return body
            }
        }
        MySingleton.getInstance(applicationContext).addToRequestQueue(peticion) //es un meeseeks
    }
}