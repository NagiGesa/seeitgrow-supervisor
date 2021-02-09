package com.seeitgrow.supervisor.ui.NavTest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.seeitgrow.supervisor.R
import com.seeitgrow.supervisor.databinding.ActivityNavGraphBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var _binding: ActivityNavGraphBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNavGraphBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val navController = findNavController(R.id.main_navigation)
       _binding.toolbar?.setupWithNavController(navController)
    }
}