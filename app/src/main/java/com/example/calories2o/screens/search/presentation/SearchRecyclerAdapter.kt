package com.example.calories2o.screens.search.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.calories2o.core.extensions.divideFoodName
import com.example.calories2o.databinding.SearchRecyclerItemBinding
import com.example.calories2o.screens.search.model.FoodEntity

class SearchRecyclerAdapter(val plusOnClick: (foodEntity: FoodEntity) -> Unit): RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder>() {

    private var foodList = mutableListOf<FoodEntity>()

    fun setItems(list: List<FoodEntity>){
        foodList = list.toMutableList()
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(private val binding: SearchRecyclerItemBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(foodEntity: FoodEntity){
            binding.imageView.setOnClickListener {
                plusOnClick(foodEntity)
            }
            val foodName = foodEntity.name.divideFoodName()
            binding.subNameTv.isVisible = foodName.name != foodName.brand
            binding.nameTv.text = foodEntity.name.divideFoodName().name
            binding.subNameTv.text = foodEntity.name.divideFoodName().brand
            binding.calloriesTv.text = "${foodEntity.calorie} Ккал (100 г.)"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(SearchRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount(): Int = foodList.size
}