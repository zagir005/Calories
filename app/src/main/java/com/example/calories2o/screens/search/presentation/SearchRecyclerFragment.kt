package com.example.calories2o.screens.search.presentation

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calories2o.core.extensions.divideFoodName
import com.example.calories2o.core.extensions.logPrint
import com.example.calories2o.core.network.Network
import com.example.calories2o.core.ui.BaseFragment
import com.example.calories2o.databinding.DinnerAddAlertDialogBinding
import com.example.calories2o.databinding.FragmentSearchRecyclerBinding
import com.example.calories2o.isOnline
import com.example.calories2o.screens.home.model.DinnerInfoEntity
import com.example.calories2o.screens.search.domain.FoodRepository
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class SearchRecyclerFragment:
    BaseFragment<FragmentSearchRecyclerBinding>(FragmentSearchRecyclerBinding::inflate) {

    private lateinit var foodRepository: FoodRepository

    override fun prepareUi(view: View) {
        foodRepository = FoodRepository(Network.foodInfoApi, mainActivity.foodDatabaseDao)

        val searchRecyclerAdapter = SearchRecyclerAdapter{

            val alertDialog = AlertDialog.Builder(requireContext()).apply {
                var calendar = GregorianCalendar()
                val dateFormat: DateFormat = SimpleDateFormat("EEEE, d MMMM")
                val binding = DinnerAddAlertDialogBinding.inflate(layoutInflater)
                setView(binding.root)
                binding.dishNameTv.text = it.name.divideFoodName().name
                binding.calorieTv.text = "100 Гр. - ${it.calorie} Ккал"
                var date: String = dateFormat.format(calendar.time)
                binding.datePickTv.text = date
                binding.dishNameEt.setText("Завтрак")
                binding.uglevodEt.setText("1")

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
                        ))
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
            adapter = searchRecyclerAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }


            binding.searchEt.addTextChangedListener {
                lifecycleScope.launch {
                    delay(300)
                    if(isOnline(requireContext())){
                        val searchFoodResult = foodRepository.searchFood(it.toString())
                        searchFoodResult.logPrint()
                        searchRecyclerAdapter.setItems(searchFoodResult)
                    }else{
                        AlertDialog.Builder(requireContext()).apply {
                            setTitle("Ошибка")
                            setMessage("Нет интернет-соединения")
                            setPositiveButton("OK"
                            ) { p0, p1 ->

                            }
                        }.create().show()
                    }

                }
            }


    }
}
