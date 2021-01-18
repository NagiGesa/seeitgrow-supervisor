package com.seeitgrow.supervisor.ui.main.view.LoginActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seeitgrow.supervisor.databinding.SignupLoginBinding
import com.seeitgrow.supervisor.ui.main.view.MainActivity
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.NetworkUtil

class LoginActivity : AppCompatActivity() {

    lateinit var binding: SignupLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignupLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.btnProceed.setOnClickListener {
            if (!NetworkUtil.getConnectivityStatusString(applicationContext)
                    .equals("Not connected to Internet")
            ) {
                when {
                    binding.edtMobileno.text.toString().isEmpty() -> {
                        AppUtils.showMessage(this, "Mobile number cannot be blank")
                    }
                    binding.edtMobileno.text.toString().length != 10 -> {
                        AppUtils.showMessage(this, "Please Enter Complete Mobile Number")
                    }
                    else -> {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                }
            } else {
                AppUtils.showMessage(this, "Not connected to Internet")
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}