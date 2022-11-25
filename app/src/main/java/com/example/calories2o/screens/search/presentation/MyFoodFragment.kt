package com.example.calories2o.screens.search.presentation

import android.app.AlertDialog
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calories2o.R
import com.example.calories2o.core.extensions.divideFoodName
import com.example.calories2o.core.extensions.logPrint
import com.example.calories2o.core.network.Network
import com.example.calories2o.core.ui.BaseFragment
import com.example.calories2o.databinding.DinnerAddAlertDialogBinding
import com.example.calories2o.databinding.FragmentMyFoodBinding
import com.example.calories2o.databinding.FragmentSearchRecyclerBinding
import com.example.calories2o.screens.home.model.DinnerInfoEntity
import com.example.calories2o.screens.search.domain.FoodRepository
import com.example.calories2o.screens.search.model.FoodEntity
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MyFoodFragment: BaseFragment<FragmentMyFoodBinding>(FragmentMyFoodBinding::inflate) {
    private lateinit var foodRepository: FoodRepository
    override fun prepareUi(view: View) {
        foodRepository = FoodRepository(Network.foodInfoApi, mainActivity.foodDatabaseDao)

        val searchRecyclerAdapter = SearchRecyclerAdapter{
            AlertDialog.Builder(requireContext()).apply {
                var calendar = GregorianCalendar()
                val dateFormat: DateFormat = SimpleDateFormat("EEEE, d MMMM")
                val binding = DinnerAddAlertDialogBinding.inflate(layoutInflater)
                setView(binding.root)
                binding.dishNameTv.text = it.name.divideFoodName().name
                binding.calorieTv.text = "100 Гр. - ${it.calorie} Ккал"
                var date: String = dateFormat.format(calendar.time)
                binding.datePickTv.text = date

                setPositiveButton("OK") { dialogInterface, i ->
                    lifecycleScope.launch{
                        val count = binding.uglevodEt.text.toString().toInt()
                        mainActivity.foodDatabaseDao.insertDinner( DinnerInfoEntity(
                            binding.dishNameEt.text.toString(),
                            binding.datePickTv.text.toString(),
                            it.calorie.toDouble() * count,
                            it.fat * count,
                            it.protein * count,
                            it.carbohydrates * count
                        )
                        )
                    }
                }
                binding.datePickTv.setOnClickListener {
                    val datePicker = MaterialDatePicker.Builder.datePicker().build().apply {
                        addOnPositiveButtonClickListener {
                            calendar.time = Date(it)
                            date = dateFormat.format(calendar.time)
                            binding.datePickTv.text = date
                        }
                    }
                    if(!datePicker.isAdded){
                        datePicker.show(childFragmentManager,"tag")
                    }
                }
                create()
            }.show()
        }

        binding.recyclerView.apply {
            adapter = searchRecyclerAdapter.apply {
                lifecycleScope.launch {
                    setItems(foodRepository.getMyFood())
                }
            }
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        }

        binding.addDishBtn.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragmentMy_to_dishAddFragment)
        }

        binding.searchEt.addTextChangedListener { searchText ->
            lifecycleScope.launch {
                delay(300)
                val searchFoodResult = foodRepository.getMyFood()
                if (searchText.isNullOrEmpty()){
                    searchRecyclerAdapter.setItems(searchFoodResult)
                }else{
                    val list: MutableList<FoodEntity> = mutableListOf()
                    searchFoodResult.forEach {
                        if(it.name.contains(searchText,true))
                            list.add(it)
                    }
                    searchRecyclerAdapter.setItems(list)
                }
            }
        }
    }

}

