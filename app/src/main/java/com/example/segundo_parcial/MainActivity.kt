package com.example.segundo_parcial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var textViewJoke: TextView
    private lateinit var textViewCategory: TextView
    private lateinit var buttonRandomJoke: Button
    private lateinit var buttonCategories: Button
    private lateinit var imageViewShare: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewJoke = findViewById(R.id.textViewJoke)
        textViewCategory = findViewById(R.id.textViewHintCategory)
        buttonRandomJoke = findViewById(R.id.buttonRandom)
        buttonCategories = findViewById(R.id.buttonCategories)
        imageViewShare = findViewById(R.id.imageViewShare)

        val bundle = intent.extras
        val intentCategory = bundle?.getString("category", "")

        if(intentCategory.isNullOrBlank()){
            getRandomJoke("")
        }else{
            getRandomJoke(intentCategory)
        }

        buttonRandomJoke.setOnClickListener{
            getRandomJoke("")
        }

        imageViewShare.setOnClickListener{
            val intentSend = Intent(Intent.ACTION_SEND)
            intentSend.type = "text/plain"
            if(textViewJoke != null){
                intentSend.putExtra(Intent.EXTRA_TEXT, textViewJoke.text)
                try {
                    startActivity(intentSend)
                }catch (e : Exception){
                    Toast.makeText(this, "No se puede enviar", Toast.LENGTH_SHORT).show()
                }
            }


        }


        buttonCategories.setOnClickListener{
            val intent = Intent(this, ListCategoriesActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_Jokes)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun itemClicked(content: String){
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }

    private fun getRandomJoke(category: String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = if (category.isNullOrBlank()) {
                getRetrofit().create(ApiService::class.java).randomJoke()
            }else{
                getRetrofit().create(ApiService::class.java).getJokeByCategory(category)
            }
            val response = call.body()

            runOnUiThread{
                if(call.isSuccessful){
                    if(response != null){
                        textViewJoke.text = response.value
                        textViewCategory.text =
                            if(!response.categories.isNullOrEmpty())
                                response.categories.toString()
                            else ""
                    }
                }else{
                    val error = call.errorBody().toString()
                    Log.e("error", error)
                }
            }
        }
    }

    companion object {
        const val URL_Jokes = "https://api.chucknorris.io/jokes/"
    }
}