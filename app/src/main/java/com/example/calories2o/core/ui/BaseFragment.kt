package com.example.calories2o.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.calories2o.MainActivity


abstract class BaseFragment<VB: ViewBinding>(
    private val bindingInflate: (layoutInflater: LayoutInflater,viewGroup: ViewGroup?, attach: Boolean) -> VB
): Fragment(){

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = requireActivity() as MainActivity
        prepareUi(view)

    }


    abstract fun prepareUi(view: View)


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}