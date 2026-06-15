package com.example.ap2_dev_mobile

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ap2_dev_mobile.adapter.ContasAdapter
import com.example.ap2_dev_mobile.apiConfig.ApiService
import com.example.ap2_dev_mobile.apiConfig.RetrofitClient
import com.example.ap2_dev_mobile.auth.Session
import com.example.ap2_dev_mobile.dialog.CriarContaDialog
import com.example.ap2_dev_mobile.dto.ContaFinanceiraRequest
import com.example.ap2_dev_mobile.dto.ContaFinanceiraResponse
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class contas_financeiras : AppCompatActivity() {
    private lateinit var adapter: ContasAdapter
    private var contas = mutableListOf<ContaFinanceiraResponse>()
    private lateinit var rv: RecyclerView
    private lateinit var api: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contas_financeiras)

        api = RetrofitClient.retrofit.create(ApiService::class.java)
        rv = findViewById<RecyclerView>(R.id.contas_rv)
        val criarContaBtn = findViewById<MaterialButton>(R.id.criar_conta_btn)

        carregarContas()

        criarContaBtn.setOnClickListener {
            val dialog = CriarContaDialog("criar", null) {carregarContas()}
            dialog.show(supportFragmentManager, "CriarContaDialog")
        }
    }

    private fun carregarContas() {
        lifecycleScope.launch {
            try {
                contas = api.getContas(Session.getToken())
                adapter = ContasAdapter(contas, editarConta = {conta ->
                    val dialog = CriarContaDialog("editar", conta) {
                        carregarContas()
                    }
                    dialog.show(supportFragmentManager, "EditarContaDialog")
                }, deletarConta = {conta ->
                    val dialog = AlertDialog.Builder(this@contas_financeiras)
                    dialog.setMessage("Tem certeza que deseja deletar a conta ${conta.nome}")
                    dialog.setPositiveButton("Sim") {dialog, id ->
                        lifecycleScope.launch(Dispatchers.IO) {
                            try {
                                api.deletarConta(Session.getToken(), conta.id)
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(this@contas_financeiras, "Conta deletada com sucesso!", Toast.LENGTH_SHORT).show()
                                    carregarContas()
                                }
                            } catch (e: RuntimeException) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(this@contas_financeiras, "Erro ao deletar conta.", Toast.LENGTH_SHORT).show()
                                    Log.d("Erro ao deletar conta", e.message.toString())
                                }
                            }
                        }
                    }
                        .setNegativeButton("Cancel") { dialog, id ->
                        }
                    dialog.create().show()
                })
            } catch (e: RuntimeException) {
                Log.d("ERRO NA REQUISIÇÃO", e.message.toString())
            }
            rv.layoutManager = LinearLayoutManager(this@contas_financeiras)
            rv.adapter = adapter
        }
    }
}