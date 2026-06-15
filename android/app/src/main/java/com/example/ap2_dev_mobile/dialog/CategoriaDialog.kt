package com.example.ap2_dev_mobile.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.ap2_dev_mobile.R
import com.example.ap2_dev_mobile.apiConfig.ApiService
import com.example.ap2_dev_mobile.apiConfig.RetrofitClient
import com.example.ap2_dev_mobile.auth.Session
import com.example.ap2_dev_mobile.dto.CategoriaRequest
import com.example.ap2_dev_mobile.dto.CategoriaResponse
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriaDialog (val context: String, val categoria: CategoriaResponse?, val sucessoEdicaoOuCriacao: (() -> Unit)?): DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.formulario_categoria, container, false)

        val titulo = view.findViewById<TextView>(R.id.id_item)
        val fechar = view.findViewById<ImageView>(R.id.btn_fechar)
        val acaoBtn = view.findViewById<MaterialButton>(R.id.acao_btn)
        val nomeInput = view.findViewById<EditText>(R.id.categoria_nome_input)
        val corInput = view.findViewById<EditText>(R.id.cor_input)
        val api = RetrofitClient.retrofit.create(ApiService::class.java)

        if (context == "criar") {
            acaoBtn.text = "Criar"
        }

        if (context == "editar") {
            acaoBtn.text = "Salvar"
            titulo.text = "Editar Categoria"
        }

        if (context == "editar") {
            Log.d("CONTA NO DIALOG", categoria.toString())
            nomeInput.setText(categoria?.nome)
            corInput.setText(categoria?.cor.toString())
        }

        acaoBtn.setOnClickListener {
            val nomeTxt = nomeInput.text.toString().trim()
            val corTxt = corInput.text.toString().trim()
            if (context == "criar") {
                if (nomeTxt.isEmpty() || corTxt.isEmpty()) {
                    Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                } else if (corTxt.length < 3 || corTxt.length > 6) {
                    Toast.makeText(requireContext(), "A cor deve ter entre 3 e 6 caracteres", Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch(Dispatchers.IO) {
                        try {
                            api.criarCategoria(Session.getToken(), CategoriaRequest(nomeTxt, corTxt))
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Categoria criada com sucesso!", Toast.LENGTH_SHORT).show()
                                sucessoEdicaoOuCriacao?.invoke()
                            }
                            dismiss()
                        } catch (e: RuntimeException) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Erro na criação da categoria", Toast.LENGTH_SHORT).show()
                            }
                            Log.d("ERRO", e.message.toString())
                        }
                    }
                }
            } else {
                if (nomeTxt.isEmpty() || corTxt.isEmpty()) {
                    Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                } else if (corTxt.length < 3 || corTxt.length > 6) {
                    Toast.makeText(requireContext(), "A cor deve ter entre 3 e 6 caracteres", Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch(Dispatchers.IO) {
                        if (categoria != null) {
                            api.atualizarCategoria(Session.getToken(), categoria.id, CategoriaRequest( nomeTxt, corTxt))
                            sucessoEdicaoOuCriacao?.invoke()
                            dismiss()
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Categoria atualizada!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        fechar.setOnClickListener {
            dismiss()
        }

        return view
    }
}