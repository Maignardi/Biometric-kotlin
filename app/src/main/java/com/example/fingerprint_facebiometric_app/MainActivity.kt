 package com.example.fingerprint_facebiometric_app

import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.fingerprint_facebiometric_app.databinding.ActivityMainBinding
import java.util.concurrent.Executor

 class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var executor: Executor
    lateinit var biometricPrompt:BiometricPrompt
    lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        start()

        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this@MainActivity,executor, object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                intent = Intent(this@MainActivity, SecretActivity::class.java )
                startActivity(intent)
                finish()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("login using fingerprint or face")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL or
                        BiometricManager.Authenticators.BIOMETRIC_WEAK
            )
            .setConfirmationRequired(false)
            .build()
            biometricPrompt.authenticate(promptInfo)
    }

     private fun start(){
         binding.buttonStart.setOnClickListener {
             biometricPrompt.authenticate(promptInfo)
         }
     }

}