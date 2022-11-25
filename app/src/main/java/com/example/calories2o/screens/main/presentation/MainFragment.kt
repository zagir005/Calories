package com.example.calories2o.screens.main.presentation

import android.graphics.Color
import android.view.View
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.get
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.calories2o.MainActivity
import com.example.calories2o.R
import com.example.calories2o.core.ui.BaseFragment
import com.example.calories2o.databinding.FragmentMainBinding

class MainFragment: BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate){
    override fun prepareUi(view: View) {

        val navHostFragment = childFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        bottomNavViewInit()

        binding.plusBtn.setOnClickListener {
//            val parentNavHostFragment = parentFragmentManager.findFragmentById(R.id.activity_fragment_container) as NavHostFragment
//            val navController = parentNavHostFragment.navController
//
//            navController.navigate(R.id.action_fragmentMain_to_searchFragmentMy)

            findNavController().navigate(R.id.action_fragmentMain_to_searchFragmentMy)
        }
    }

    private fun bottomNavViewInit(){
        binding.bottomNavigationView.apply {
            background = null
            menu[1].isEnabled = false
        }
    }
}
