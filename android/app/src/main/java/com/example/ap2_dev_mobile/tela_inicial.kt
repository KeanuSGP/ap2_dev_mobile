package com.example.ap2_dev_mobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ap2_dev_mobile.adapter.TransacaoTelaInicialAdapter
import com.example.ap2_dev_mobile.apiConfig.ApiService
import com.example.ap2_dev_mobile.apiConfig.RetrofitClient
import com.example.ap2_dev_mobile.auth.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormatSymbols
import java.util.Locale

class tela_inicial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_inicial)


        val api = RetrofitClient.retrofit.create(ApiService::class.java)
        val saudacaoMsg = findViewById<TextView>(R.id.bem_vindo_msg)
        val saldo = findViewById<TextView>(R.id.saldo)
        var tempSaldo = 0f
        val visaoBtn = findViewById<ImageView>(R.id.visao_btn)
        val receitas = findViewById<TextView>(R.id.receitas)
        val receitasMes = findViewById<TextView>(R.id.receitas_mes)
        val despesas = findViewById<TextView>(R.id.despesas)
        val despesasMes = findViewById<TextView>(R.id.despesas_mes)

        val dicas = findViewById<TextView>(R.id.intentImplicita)

        dicas.setOnClickListener {
            val link = Uri.parse("https://www.caixa.gov.br/educacao-financeira/Paginas/default.aspx")
            val intent = Intent(Intent.ACTION_VIEW, link)
            startActivity(intent)
        }

        val diaAtual = java.util.Calendar.getInstance()
        val mesAtual = diaAtual.get(java.util.Calendar.MONTH) + 1
        @Suppress("DEPRECATION")
        val nomeMes = DateFormatSymbols(Locale("pt", "BR")).months[mesAtual].uppercase()
        val anoAtual = diaAtual.get(java.util.Calendar.YEAR)

        val rv = findViewById<RecyclerView>(R.id.ultimos_lancamentos_rv)

        saudacaoMsg.text = "Bem vindo, ${Session.getUsuario().nome}"

        receitasMes.text = "RECEITAS ${nomeMes}/${anoAtual}"
        despesasMes.text = "DESPESAS ${nomeMes}/${anoAtual}"

        visaoBtn.setOnClickListener {
            if (saldo.text != "R$ -----") {
                saldo.text = "R$ -----"
            } else {
                saldo.text = "R$ ${tempSaldo}"
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val dashboard = api.dashboard(Session.getToken())
                Log.d("DEBUG", dashboard.toString())
                withContext(Dispatchers.Main) {
                    tempSaldo = dashboard.total
                    receitas.text = "R$ ${dashboard.receitasMes}0"
                    despesas.text = "R$ ${dashboard.despesasMes}0"
                    Log.d("DEBUG", "Quantidade: ${dashboard.ultimasTransacoes.size}")
                    val adapter = TransacaoTelaInicialAdapter(dashboard.ultimasTransacoes)

                    rv.layoutManager = LinearLayoutManager(this@tela_inicial)
                    rv.adapter = adapter
                }
            } catch (e: RuntimeException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@tela_inicial,"Erro ao carregar dados", Toast.LENGTH_SHORT).show()
                }
                Log.d("ERRO NA REQUISICÃO", e.message.toString())
            }
        }
    }
}