package com.example.dogapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.dogapi.api.EndPoint
import com.example.dogapi.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    // 1 passo
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPesquisar.setOnClickListener{ getImage() }

    }

    // 5 e 6 passo
    private fun getImage() {

        val url = "https://dog.ceo/"
        val retrofitClient = retrofitInstance(url)
        val endPoint = retrofitClient.create(EndPoint::class.java)
        val raca = binding.editRaca.text.toString()

        endPoint.getDog(raca).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val urlImage = response.body()?.get("message")?.asString
                Picasso.get().load(urlImage).into(binding.imageDog)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(applicationContext, "Erro ao acessar", Toast.LENGTH_LONG )
            }

        })

    }

    // 2 passo
    private fun retrofitInstance(url: String): Retrofit {
        return Retrofit
              .Builder()
              .baseUrl(url)
              .addConverterFactory(GsonConverterFactory.create())
              .build()
    }
}