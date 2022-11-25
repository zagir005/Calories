package com.example.calories2o.screens.statistics.presentation

import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.calories2o.core.ui.BaseFragment
import com.example.calories2o.databinding.FragmentStatisticsBinding

class StatisticsFragment: BaseFragment<FragmentStatisticsBinding>(FragmentStatisticsBinding::inflate) {
    override fun prepareUi(view: View) {
        Toast.makeText(requireContext(), "statistics... ", Toast.LENGTH_SHORT).show()
    }
}