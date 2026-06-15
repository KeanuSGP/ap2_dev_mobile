package com.example.ap2_dev_mobile

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ap2_dev_mobile.adapter.CategoriaAdapter
import com.example.ap2_dev_mobile.apiConfig.ApiService
import com.example.ap2_dev_mobile.apiConfig.RetrofitClient
import com.example.ap2_dev_mobile.auth.Session
import com.example.ap2_dev_mobile.dialog.CategoriaDialog
import com.example.ap2_dev_mobile.dto.CategoriaResponse
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class categorias : AppCompatActivity() {

    private lateinit var adapter: CategoriaAdapter
    private var categorias = mutableListOf<CategoriaResponse>()
    private lateinit var rv: RecyclerView
    private lateinit var api: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_categorias)
        api = RetrofitClient.retrofit.create(ApiService::class.java)
        val criarCategoria = findViewById<MaterialButton>(R.id.criar_categoria_btn)
        rv = findViewById<RecyclerView>(R.id.categoria_rv)

        carregarCategorias()

        criarCategoria.setOnClickListener {
            val dialog = CategoriaDialog("criar", null) {carregarCategorias()}
            dialog.show(supportFragmentManager, "CriarCategoriaDialog")
        }


    }

    private fun carregarCategorias() {
        lifecycleScope.launch {
            try {
                categorias = api.getCategorias(Session.getToken())
                adapter = CategoriaAdapter(categorias, editarCategoria = { categoria ->
                    val dialog = CategoriaDialog("editar", categoria) {
                        carregarCategorias()
                    }
                    dialog.show(supportFragmentManager, "EditarContaDialog")
                }, deletarCategoria = {categoria ->
                    val dialog = AlertDialog.Builder(this@categorias)
                    dialog.setMessage("Tem certeza que deseja deletar a categoria ${categoria.nome}")
                    dialog.setPositiveButton("Sim") {dialog, id ->
                        lifecycleScope.launch(Dispatchers.IO) {
                            try {
                                val response = api.deletarCategoria(Session.getToken(), categoria.id)
                                if (response.isSuccessful) {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(this@categorias, "Categoria deletada com sucesso!", Toast.LENGTH_SHORT).show()
                                        carregarCategorias()
                                    }
                                }

                            } catch (e: RuntimeException) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(this@categorias, "Erro ao deletar categoria.", Toast.LENGTH_SHORT).show()
                                    Log.d("Erro ao deletar categoria", e.message.toString())
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
            rv.layoutManager = LinearLayoutManager(this@categorias)
            rv.adapter = adapter
        }
    }
}