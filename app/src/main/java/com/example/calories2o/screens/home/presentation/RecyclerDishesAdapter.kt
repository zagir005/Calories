package com.example.calories2o.screens.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calories2o.databinding.RecyclerItemDishBinding
import com.example.calories2o.screens.home.model.DinnerInfoEntity

class RecyclerDishesAdapter: RecyclerView.Adapter<RecyclerDishesAdapter.DishViewHolder>() {

    private var dinnerList = listOf<DinnerInfoEntity>()

    fun setItems(list: List<DinnerInfoEntity>){
        dinnerList = list
        notifyDataSetChanged()
    }

    inner class DishViewHolder(private val binding: RecyclerItemDishBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(dinner: DinnerInfoEntity){
            binding.nameTv.text = dinner.name
            binding.caloryTv.text = "Съедено ${dinner.calories} Ккал"
            binding.fatTv.text = "Жир - ${dinner.fat}"
            binding.proteinTv.text = "Белок - ${dinner.protein}"
            binding.uglevodTv.text = "Углеводы - ${dinner.uglevod}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder =
        DishViewHolder(RecyclerItemDishBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.bind(dinnerList[position])
    }

    override fun getItemCount(): Int = dinnerList.size
}