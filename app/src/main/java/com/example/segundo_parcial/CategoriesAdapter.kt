package com.example.segundo_parcial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoriesAdapter(private val categories: List<String>): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>(){

    lateinit var onItemClickListener: (String) -> Unit

    inner class CategoryViewHolder(private  val view: View): RecyclerView.ViewHolder(view) {
        private val textViewCategory: TextView = view.findViewById(R.id.textViewCategory)

        fun bind(categoryItem: String) {
            textViewCategory.text = categoryItem

            view.setOnClickListener{
                onItemClickListener(categoryItem)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(layoutInflater.inflate(R.layout.categoryitem, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = categories[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return categories.size
    }


}