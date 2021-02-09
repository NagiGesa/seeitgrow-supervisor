package com.seeitgrow.supervisor.ui.main.view.SplashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.seeitgrow.supervisor.databinding.SplashScreenBinding
import com.seeitgrow.supervisor.ui.main.view.ChampionList
import com.seeitgrow.supervisor.ui.main.view.LoginActivity.LoginActivity
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervisor_ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Suppress("DEPRECATION")
class Splash : AppCompatActivity() {
    lateinit var binding: SplashScreenBinding
    private val mSupervisorViewModel: Supervisor_ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

    }


    override fun onStart() {
        super.onStart()
        Handler().postDelayed({
            mSupervisorViewModel.getSupervisorCount.observe(this, Observer { count ->
                if (count > 0) {
                    startActivity(Intent(this, ChampionList::class.java))
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }

            })

            finish()
        }, 3000)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
        finish()
    }
}