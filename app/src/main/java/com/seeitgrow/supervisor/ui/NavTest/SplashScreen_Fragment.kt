package com.seeitgrow.supervisor.ui.NavTest

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.seeitgrow.supervisor.R
import com.seeitgrow.supervisor.databinding.SplashScreenBinding
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervisor_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreen_Fragment :
    Fragment() {

    lateinit var navController: NavController
    val mSupervisorViewModel: Supervisor_ViewModel by viewModels()
    lateinit var _binding: SplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SplashScreenBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        Handler().postDelayed({
            mSupervisorViewModel.getSupervisorCount.observe(viewLifecycleOwner, { count ->
                if (count > 0) {
                    navController.navigate(R.id.action_splash2_to_championList_Nav)
                } else {
                    navController.navigate(R.id.action_splash2_to_loginActivity)
                }

            })

        }, 3000)
    }
}