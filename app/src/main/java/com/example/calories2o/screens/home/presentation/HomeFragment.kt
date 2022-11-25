package com.example.calories2o.screens.home.presentation

import android.app.AlertDialog
import android.graphics.Color
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calories2o.R
import com.example.calories2o.core.database.FoodDao
import com.example.calories2o.core.extensions.divideFoodName
import com.example.calories2o.core.extensions.logPrint
import com.example.calories2o.core.ui.BaseFragment
import com.example.calories2o.databinding.AlertDialogHomeSettingsBinding
import com.example.calories2o.databinding.DinnerAddAlertDialogBinding
import com.example.calories2o.databinding.FragmentHomeBinding
import com.example.calories2o.screens.home.model.DayInfoEntity
import com.example.calories2o.screens.home.model.DinnerInfoEntity
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.skydoves.progressview.progressView
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*


class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate){
    private val datePicker = MaterialDatePicker.Builder.datePicker().build()
    private val gregorianCalendar = GregorianCalendar()
    private val dateFormat: DateFormat = SimpleDateFormat("EEEE, d MMMM")
    private lateinit var databaseDao: FoodDao
    private var currentDay: MutableLiveData<String> = MutableLiveData()
    private lateinit var currentDayEntity: DayInfoEntity
    private lateinit var currentDinnersList: List<DinnerInfoEntity>
    private val emptyDinnerEntity = DinnerInfoEntity("","",0.0,0f,0f,0f)

    override fun prepareUi(view:View) {
        databaseDao = mainActivity.foodDatabaseDao
        currentDay.value = dateFormat.format(gregorianCalendar.time)

        binding.recyclerDishes.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.recyclerDishes.adapter = RecyclerDishesAdapter()

        currentDay.observe(this){ day ->
            lifecycleScope.launch{
                currentDayEntity = if (databaseDao.getDayInfo(day) == null){
                    databaseDao.insertDayInfoEntity(DayInfoEntity(day))
                    databaseDao.getDayInfo(day)!!
                }else{
                    databaseDao.getDayInfo(day)!!
                }

                binding.datePickTv.text = day
                currentDinnersList = databaseDao.getDinnersByDay(day)
                statisticsInit(currentDayEntity,currentDinnersList)
                (binding.recyclerDishes.adapter as RecyclerDishesAdapter).setItems(currentDinnersList)
            }
        }

        binding.settingsBtn.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                val binding = AlertDialogHomeSettingsBinding.inflate(layoutInflater)
                binding.maxCalories.setText(currentDayEntity.maxCalories.toString())
                binding.maxFat.setText(currentDayEntity.maxFat.toString())
                binding.maxBelok.setText(currentDayEntity.maxProtein.toString())
                binding.maxUglevod.setText(currentDayEntity.maxUglevod.toString())
                setCancelable(true)
                setView(binding.root)
                setPositiveButton("OK") { _,_ ->
                    lifecycleScope.launch {
                        if ( binding.maxCalories.text.isNullOrEmpty() ||
                            binding.maxFat.text.isNullOrEmpty() ||
                            binding.maxBelok.text.isNullOrEmpty() ||
                            binding.maxUglevod.text.isNullOrEmpty())
                        {
                            Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
                        }else{
                            databaseDao.updateDayInfo(
                                DayInfoEntity(
                                    currentDay.value!!,
                                    binding.maxCalories.text.toString().toDouble(),
                                    binding.maxFat.text.toString().toFloat(),
                                    binding.maxBelok.text.toString().toFloat(),
                                    binding.maxUglevod.text.toString().toFloat()
                                )
                            )
                            statisticsInit(currentDayEntity, currentDinnersList)
                        }
                    }
                }
            }.create().show()
        }

        dataPickerInit()

    }

    private fun statisticsInit(dayInfoEntity: DayInfoEntity, dinnersInfoEntityList: List<DinnerInfoEntity>){
        if (dinnersInfoEntityList.isEmpty()){
            caloriePbInit(dayInfoEntity, emptyDinnerEntity)
            progressBarsInit(dayInfoEntity, emptyDinnerEntity)
        }else{
            caloriePbInit(dayInfoEntity, calculateDinnersStatistics(currentDinnersList))
            progressBarsInit(dayInfoEntity, calculateDinnersStatistics(currentDinnersList))
        }
    }


    private fun dataPickerInit(){
        binding.datePickTv.setOnClickListener {
            datePicker.apply {
                addOnPositiveButtonClickListener {
                    gregorianCalendar.time = Date(it)
                    currentDay.value = dateFormat.format(gregorianCalendar.time)
                }
            }
            if(!datePicker.isAdded){
                datePicker.show(childFragmentManager,"tag")
            }
        }
    }

    private fun caloriePbInit(dayInfoEntity: DayInfoEntity, dinnerInfoEntity: DinnerInfoEntity){
        with(binding.caloriesProgressIndicator){
            setProgress(dinnerInfoEntity.calories,dayInfoEntity.maxCalories)
            setProgressTextAdapter {
                "${dinnerInfoEntity.calories.toInt()} Ккал / ${binding.caloriesProgressIndicator.maxProgress.toInt()} Ккал"
            }
        }
    }


    private fun progressBarsInit(dayInfoEntity: DayInfoEntity, dinnerInfoEntity: DinnerInfoEntity){
        binding.proteinPb.apply {
            setOnProgressChangeListener {
                binding.proteinInfoTv.text = progressInfoString(dinnerInfoEntity.protein,dayInfoEntity.maxProtein)
            }
            if (dinnerInfoEntity.protein > dayInfoEntity.maxProtein){
                max = dinnerInfoEntity.protein
                progress = dinnerInfoEntity.protein
                max.logPrint()
            }else{
                max = dayInfoEntity.maxProtein
                progress = dinnerInfoEntity.protein
            }
        }
        binding.fatPb.apply {
            setOnProgressChangeListener {
                binding.fatInfoTv.text = progressInfoString(dinnerInfoEntity.protein,dayInfoEntity.maxProtein)
            }
            if (dinnerInfoEntity.fat > dayInfoEntity.maxFat){
                max = dinnerInfoEntity.fat
                progress = dinnerInfoEntity.fat
                max.logPrint()
            }else{
                max = dayInfoEntity.maxFat
                progress = dinnerInfoEntity.fat
            }
        }
        binding.uglevodPb.apply {
            setOnProgressChangeListener {
                binding.uglevodInfoTv.text = progressInfoString(dinnerInfoEntity.protein,dayInfoEntity.maxProtein)
            }
            if (dinnerInfoEntity.uglevod > dayInfoEntity.maxUglevod){
                max = dinnerInfoEntity.uglevod
                progress = dinnerInfoEntity.uglevod
                max.logPrint()
            }else{
                max = dayInfoEntity.maxUglevod
                progress = dinnerInfoEntity.uglevod
            }
        }

    }

    private fun calculateDinnersStatistics(list: List<DinnerInfoEntity>): DinnerInfoEntity{
        var calories = 0.0
        var fat = 0f
        var protein = 0f
        var uglevod = 0f
        list.forEach {
            calories += it.calories
            fat += it.fat
            protein += it.protein
            uglevod += it.uglevod
        }
        return list[0].copy(calories = calories, fat = fat, protein = protein, uglevod = uglevod)
    }

    private fun progressInfoString(current: Float, max: Float): String{
        return "$current/$max гр."
    }

}

