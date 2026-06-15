package com.example.ap2_dev_mobile

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ap2_dev_mobile.adapter.TransacaoAdapter

import com.example.ap2_dev_mobile.apiConfig.ApiService
import com.example.ap2_dev_mobile.apiConfig.RetrofitClient
import com.example.ap2_dev_mobile.auth.Session

import com.example.ap2_dev_mobile.dialog.TransacaoDialog
import com.example.ap2_dev_mobile.dto.TransacaoResponse

import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class lancamentos : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var api: ApiService

    private lateinit var adapter: TransacaoAdapter

    private lateinit var transacoes: List<TransacaoResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lancamentos)
        api = RetrofitClient.retrofit.create(ApiService::class.java)
        rv = findViewById<RecyclerView>(R.id.lancamentos_rv)

        carregarTransacoes()

        val criar = findViewById<MaterialButton>(R.id.criar_btn)
        val filtroReceita = findViewById<MaterialButton>(R.id.filtroReceita)
        val filtroDespesa = findViewById<MaterialButton>(R.id.filtroDespesa)
        val filtroAberta = findViewById<MaterialButton>(R.id.filtroAberta)
        val filtroQuitada = findViewById<MaterialButton>(R.id.filtroQuitada)

        criar.setOnClickListener {
            val dialog = TransacaoDialog("criar", null) {carregarTransacoes()}
            dialog.show(supportFragmentManager, "CriarTransacaoDialog")
        }

        var filtroReceitaAtivo = false
        var filtroDespesaAtivo = false
        var filtroAbertaAtivo = false
        var filtroQuitadaAtivo = false

        filtroReceita.setOnClickListener {
            if (filtroReceitaAtivo) {

                filtroReceitaAtivo = false
                filtroReceita.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                filtroReceita.setTextColor(ContextCompat.getColor(this, R.color.main))

                var receitas: List<TransacaoResponse>
                if (filtroAbertaAtivo) {
                    receitas = transacoes.filter{ it.status == "ABERTO" }
                } else if (filtroQuitadaAtivo) {
                    receitas = transacoes.filter{ it.status == "QUITADO" }
                } else {
                    receitas = transacoes
                }

                rv.layoutManager = LinearLayoutManager(this)
                rv.adapter = TransacaoAdapter(receitas, editar = ::editar, deletar = ::deletarTransacao)
            } else {
                filtroReceitaAtivo = true
                filtroReceita.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.main))
                filtroReceita.setTextColor(ContextCompat.getColor(this, R.color.white))

                filtroDespesaAtivo = false
                filtroDespesa.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                filtroDespesa.setTextColor(ContextCompat.getColor(this, R.color.main))

                var receitas: List<TransacaoResponse>

                if (filtroAbertaAtivo) {
                    receitas = transacoes.filter{ it.tipo == "RECEITA" && it.status == "ABERTO" }
                } else if (filtroQuitadaAtivo) {
                    receitas = transacoes.filter{ it.tipo == "RECEITA" && it.status == "QUITADO" }
                } else {
                    receitas = transacoes.filter{ it.tipo == "RECEITA" }
                }

                rv.layoutManager = LinearLayoutManager(this)
                rv.adapter = TransacaoAdapter(receitas, editar = ::editar, deletar = ::deletarTransacao)

            }
        }

        filtroDespesa.setOnClickListener {
            if (filtroDespesaAtivo) {
                filtroDespesaAtivo = false

                filtroDespesa.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                filtroDespesa.setTextColor(ContextCompat.getColor(this, R.color.main))

                var receitas: List<TransacaoResponse>
                if (filtroAbertaAtivo) {
                    receitas = transacoes.filter{ it.status == "ABERTO" }
                } else if (filtroQuitadaAtivo) {
                    receitas = transacoes.filter{ it.status == "QUITADO" }
                } else {
                    receitas = transacoes
                }

                rv.layoutManager = LinearLayoutManager(this)
                rv.adapter = TransacaoAdapter(receitas, editar = ::editar, deletar = ::deletarTransacao)
            } else {
                filtroDespesaAtivo = true
                filtroDespesa.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.main))
                filtroDespesa.setTextColor(ContextCompat.getColor(this, R.color.white))

                filtroReceitaAtivo = false
                filtroReceita.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                filtroReceita.setTextColor(ContextCompat.getColor(this, R.color.main))

                var despesas: List<TransacaoResponse>
                if (filtroAbertaAtivo) {
                    despesas = transacoes.filter{ it.tipo == "DESPESA" && it.status == "ABERTO" }
                } else if (filtroQuitadaAtivo) {
                    despesas = transacoes.filter{ it.tipo == "DESPESA" && it.status == "QUITADO" }
                } else {
                    despesas = transacoes.filter{ it.tipo == "DESPESA" }
                }
                rv.layoutManager = LinearLayoutManager(this)
                rv.adapter = TransacaoAdapter(despesas, editar = ::editar, deletar = ::deletarTransacao)
            }
        }

        filtroAberta.setOnClickListener {
            if (filtroAbertaAtivo) {
                filtroAbertaAtivo = false
                filtroAberta.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                filtroAberta.setTextColor(ContextCompat.getColor(this, R.color.main))

                var filtradas: List<TransacaoResponse>
                if (filtroReceitaAtivo) {
                    filtradas = transacoes.filter{ it.tipo == "RECEITA"}
                } else if (filtroDespesaAtivo) {
                    filtradas = transacoes.filter{ it.tipo == "DESPESA"}
                } else {
                    filtradas = transacoes
                }

                rv.layoutManager = LinearLayoutManager(this)
                rv.adapter = TransacaoAdapter(filtradas, editar = ::editar, deletar = ::deletarTransacao)

            } else {
                filtroQuitadaAtivo = false
                filtroQuitada.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                filtroQuitada.setTextColor(ContextCompat.getColor(this, R.color.main))


                filtroAbertaAtivo = true
                filtroAberta.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.main))
                filtroAberta.setTextColor(ContextCompat.getColor(this, R.color.white))

                var transacoesFiltradas: List<TransacaoResponse>
                if (filtroReceitaAtivo) {
                    transacoesFiltradas = transacoes.filter { it.status == "ABERTO" && it.tipo == "RECEITA" }
                } else if (filtroDespesaAtivo) {
                    transacoesFiltradas = transacoes.filter { it.status == "ABERTO" && it.tipo == "DESPESA" }
                } else {
                    transacoesFiltradas = transacoes.filter { it.status == "ABERTO" }
                }
                rv.layoutManager = LinearLayoutManager(this)
                rv.adapter = TransacaoAdapter(transacoesFiltradas, editar = ::editar, deletar = ::deletarTransacao)
            }
        }

        filtroQuitada.setOnClickListener {
            if (filtroQuitadaAtivo) {
                filtroQuitadaAtivo = false
                filtroQuitada.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                filtroQuitada.setTextColor(ContextCompat.getColor(this, R.color.main))

                var filtradas: List<TransacaoResponse>
                if (filtroReceitaAtivo) {
                    filtradas = transacoes.filter{ it.tipo == "RECEITA"}
                } else if (filtroDespesaAtivo) {
                    filtradas = transacoes.filter{ it.tipo == "DESPESA"}
                } else {
                    filtradas = transacoes
                }

                rv.layoutManager = LinearLayoutManager(this)
                rv.adapter = TransacaoAdapter(filtradas, editar = ::editar, deletar = ::deletarTransacao)
            } else {
                filtroAbertaAtivo = false
                filtroAberta.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                filtroAberta.setTextColor(ContextCompat.getColor(this, R.color.main))

                filtroQuitadaAtivo = true
                filtroQuitada.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.main))
                filtroQuitada.setTextColor(ContextCompat.getColor(this, R.color.white))

                var transacoesFiltradas: List<TransacaoResponse>
                if (filtroReceitaAtivo) {
                    transacoesFiltradas = transacoes.filter { it.status == "QUITADO" && it.tipo == "RECEITA" }
                } else if (filtroDespesaAtivo) {
                    transacoesFiltradas = transacoes.filter { it.status == "QUITADO" && it.tipo == "DESPESA" }
                } else {
                    transacoesFiltradas = transacoes.filter { it.status == "QUITADO" }
                }
                rv.layoutManager = LinearLayoutManager(this)
                rv.adapter = TransacaoAdapter(transacoesFiltradas, editar = ::editar, deletar = ::deletarTransacao)
            }
        }
    }

    private fun editar(transacao: TransacaoResponse) {
        val dialog = TransacaoDialog("editar", transacao) {
            carregarTransacoes()
        }
        dialog.show(supportFragmentManager, "EditarContaDialog")
    }

    private fun deletarTransacao(transacao: TransacaoResponse) {
        val dialog = AlertDialog.Builder(this@lancamentos)
        dialog.setMessage("Tem certeza que quer deletar a transação ${transacao.item}")
            .setPositiveButton("Sim") { dialog, id ->
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        var response = api.deletarTransacao(Session.getToken(), transacao.id)
                        if (response.isSuccessful) {
                            withContext(Dispatchers.Main){
                                Toast.makeText(this@lancamentos, "Transação deletada com sucesso", Toast.LENGTH_SHORT).show()
                                carregarTransacoes()
                            }
                        }


                    } catch(e: RuntimeException) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@lancamentos, "Erro ao tentar deletar transação.", Toast.LENGTH_SHORT).show()
                            Log.d("ERRO AO DELETAR", e.message.toString())
                        }
                    }
                }
            }.setNegativeButton("Não") { dialog, id -> }.create().show()
    }

    private fun carregarTransacoes() {
        lifecycleScope.launch {
            transacoes = api.getTransacoes(Session.getToken())
            rv.layoutManager = LinearLayoutManager(this@lancamentos)
            rv.adapter = TransacaoAdapter(transacoes, editar = ::editar, deletar = ::deletarTransacao)
        }
    }
}