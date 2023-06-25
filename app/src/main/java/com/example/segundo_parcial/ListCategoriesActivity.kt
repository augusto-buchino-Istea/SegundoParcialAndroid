package com.example.segundo_parcial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListCategoriesActivity : AppCompatActivity() {

    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var adapterCategory: CategoriesAdapter
    private var listOfCategories : MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_categories)

        recyclerViewCategory = findViewById(R.id.recyclerViewCategories)
        recyclerViewCategory.layoutManager = LinearLayoutManager(this)
        adapterCategory = CategoriesAdapter(listOfCategories)
        adapterCategory.onItemClickListener = {
            itemClicked(it)
        }

        recyclerViewCategory.adapter = adapterCategory


        getListOfCategories()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_Jokes)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun itemClicked(content: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("category", content)
        startActivity(intent)
    }

    private fun getListOfCategories(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getAllCategories()
            val response = call.body()

            runOnUiThread{
                if(call.isSuccessful){
                    if(response != null){
                        listOfCategories.addAll(response)
                        adapterCategory.notifyDataSetChanged()
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