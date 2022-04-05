package com.example.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.example.users.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svUsers.setOnQueryTextListener(this)



    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/users/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun BuscarDatosUsers(query:String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {

                val call = getRetrofit().create(ApiService::class.java).getDatosUsers("$query")

                if (call.isSuccessful) {
                    val name: String = call.body()?.nombre.toString()
                    val usuario: String = call.body()?.usuario.toString()
                    val email:String = call.body()?.email.toString()
                    val telefono:String = call.body()?.telefono.toString()
                    val web:String = call.body()?.web.toString()
                    val city: String = call.body()?.city?.city.toString()


                    binding.TvName.text = "Nombre: $name"
                    binding.TvUsuario.text = "Usuario: $usuario"
                    binding.TvEmail.text = "Email: $email"
                    binding.TvTelf.text = "Telefono: $telefono"
                    binding.TvWeb.text = "Website: $web"
                    binding.TvCiudad.text = "Ciudad: $city"


                }
            } catch (ex: Exception) {
                val msn = Toast.makeText(this@MainActivity, "Error de conexion", Toast.LENGTH_LONG)
                msn.setGravity(Gravity.CENTER, 0, 0)
                msn.show()
            }


        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            BuscarDatosUsers(query.toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}