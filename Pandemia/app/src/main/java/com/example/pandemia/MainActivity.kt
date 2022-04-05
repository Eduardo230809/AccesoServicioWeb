package com.example.pandemia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.SearchView
import android.widget.Toast
import com.example.pandemia.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.svDatos.setOnQueryTextListener(this)

    }

    fun getRetrofit():Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://disease.sh/v3/covid-19/countries/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun buscarDatosPandemia(query:String) {
        CoroutineScope(Dispatchers.Main).launch{
            try{

                val call = getRetrofit().create(ApiService::class.java).getDatosPandemia("$query")

                if(call.isSuccessful){
                    val pais:String = call.body()?.country.toString()
                    val poblacion:String = call.body()?.population.toString()
                    val casos:String = call.body()?.cases.toString()
                    val recuperados:String = call.body()?.recovered.toString()
                    val bandera:String = call.body()?.fla?.flag.toString()

                    binding.TvPais.text = "Pais: $pais"
                    binding.TvPoblacion.text = "Poblacion: $poblacion"
                    binding.TvCasos.text = "Casos: $casos"
                    binding.TvRecuperados.text = "Recuperados: $recuperados"
                    Picasso.get().load(bandera).into(binding.IvFlag)
                }
            } catch (ex: Exception){
                val msn = Toast.makeText(this@MainActivity, "Error de conexion", Toast.LENGTH_LONG)
                msn.setGravity(Gravity.CENTER,0,0)
                msn.show()
            }

        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            buscarDatosPandemia(query.toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
       return true
    }

}

