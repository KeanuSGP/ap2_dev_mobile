package com.example.ap2_dev_mobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.ap2_dev_mobile.apiConfig.ApiService
import com.example.ap2_dev_mobile.apiConfig.RetrofitClient
import com.example.ap2_dev_mobile.auth.Session
import com.example.ap2_dev_mobile.dto.LoginRequest
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val api = RetrofitClient.retrofit.create(ApiService::class.java)

        val email = findViewById<EditText>(R.id.inputEmail)
        val senha = findViewById<EditText>(R.id.inputSenha)
        val login = findViewById<MaterialButton>(R.id.login_btn)

        login.setOnClickListener {
            val emailTxt = email.text.toString().trim()
            val senhaTxt = senha.text.toString().trim()
            if (emailTxt.isEmpty() || senhaTxt.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        var data = LoginRequest(emailTxt, senhaTxt)
                        val resposta = api.login(data)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@login, "Logando...", Toast.LENGTH_SHORT).show()
                            Session.init(applicationContext)
                            Session.createLoginSession(resposta.token, resposta.usuario)
                            Toast.makeText(this@login, "Usuario logado com sucesso!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@login, tela_inicial::class.java))
                            finish()
                        }
                    } catch (e: RuntimeException) {
                        Log.d("ERRO LOGIN", e.message.toString())
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@login, "Erro no envio dos dados. Verifique suas credenciais e tente novamente!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }

    }
}