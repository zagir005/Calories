package com.example.calories2o.screens.search.presentation

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.calories2o.R
import com.example.calories2o.core.ui.BaseFragment
import com.example.calories2o.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment: BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    override fun prepareUi(view: View) {

        binding.viewPager.adapter = SearchRecyclerFragmentStateAdapter(this)

        TabLayoutMediator(binding.tabLayout,binding.viewPager) { tab, position ->
            tab.text = when(position){
                0 -> "Каталог"
                else -> "Моя еда"
            }
        }.attach()
    }
}

class SearchRecyclerFragmentStateAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) SearchRecyclerFragment() else MyFoodFragment()
    }
}