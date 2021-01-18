package com.seeitgrow.supervisor.ui.main.view.SplashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.seeitgrow.supervisor.databinding.SplashScreenBinding
import com.seeitgrow.supervisor.ui.main.view.LoginActivity.LoginActivity
import com.seeitgrow.supervisor.ui.main.view.ChampionList
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervidor_ViewModel

@Suppress("DEPRECATION")
class Splash : AppCompatActivity() {
    lateinit var binding: SplashScreenBinding
    private lateinit var supervidorViewmodel: Supervidor_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Loadui()


    }

    private fun Loadui() {
        supervidorViewmodel = ViewModelProvider(this).get(Supervidor_ViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({
            supervidorViewmodel.getSupervisorCount.observe(this, Observer { count ->
                if (count > 0) {
                    startActivity(Intent(this, ChampionList::class.java))
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }

            })

            finish()
        }, 3000)
    }
}