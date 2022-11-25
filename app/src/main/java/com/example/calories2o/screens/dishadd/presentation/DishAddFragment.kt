package com.example.calories2o.screens.dishadd.presentation

import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.calories2o.core.ui.BaseFragment
import com.example.calories2o.databinding.FragmentFoodAddBinding
import com.example.calories2o.screens.search.model.FoodEntity
import kotlinx.coroutines.launch

class DishAddFragment:BaseFragment<FragmentFoodAddBinding>(FragmentFoodAddBinding::inflate) {

    override fun prepareUi(view: View) {
        binding.addDishBtn.setOnClickListener {
            if (
                binding.nameFoodEt.text.isNullOrEmpty() ||
                binding.proteinEt.text.isNullOrEmpty() ||
                binding.fatEt.text.isNullOrEmpty() ||
                binding.uglevodEt.text.isNullOrEmpty() ||
                binding.calorieEt.text.isNullOrEmpty()
            ){
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }else{
                val name = binding.nameFoodEt.text.toString()
                val calorie = binding.calorieEt.text.toString().toFloat()
                val fat = binding.fatEt.text.toString().toFloat()
                val protein = binding.proteinEt.text.toString().toFloat()
                val uglevod = binding.uglevodEt.text.toString().toFloat()

                val foodEntity = FoodEntity(name, calorie, protein, fat, uglevod, 1, 1)

                lifecycleScope.launch {
                    mainActivity.foodDatabaseDao.insertFood(foodEntity)
                    findNavController().popBackStack()
                }
            }
        }
    }
}