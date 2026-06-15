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
import com.example.ap2_dev_mobile.dto.ContaFinanceiraRequest
import com.example.ap2_dev_mobile.dto.ContaFinanceiraResponse
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CriarContaDialog(val context: String, val conta: ContaFinanceiraResponse?, val sucessoEdicaoOuCriacao: (() -> Unit)?): DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.formulario_conta_financeira, container, false)

        val titulo = view.findViewById<TextView>(R.id.id_item)
        val fechar = view.findViewById<ImageView>(R.id.btn_fechar)
        val acaoBtn = view.findViewById<MaterialButton>(R.id.acao_btn)
        val nomeInput = view.findViewById<EditText>(R.id.conta_nome_input)
        val saldoInput = view.findViewById<EditText>(R.id.saldo_conta_input)
        val api = RetrofitClient.retrofit.create(ApiService::class.java)

        if (context == "criar") {
            acaoBtn.text = "Criar Conta"
        }

        if (context == "editar") {
            acaoBtn.text = "Editar Conta"
            titulo.text = "Editar conta financeira"
        }

        if (context == "editar") {
            Log.d("CONTA NO DIALOG", conta.toString())
            nomeInput.setText(conta?.nome)
            saldoInput.setText(conta?.saldo.toString())
        }

        acaoBtn.setOnClickListener {
            val nomeTxt = nomeInput.text.toString().trim()
            val saldoTxt = saldoInput.text.toString().trim()
            if (context == "criar") {
                if (nomeTxt.isEmpty() || saldoTxt.isEmpty()) {
                    Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch(Dispatchers.IO) {
                        try {
                            api.criarContaFinanceira(Session.getToken(), ContaFinanceiraRequest(nomeTxt, saldoTxt.toFloat()))
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                                sucessoEdicaoOuCriacao?.invoke()
                            }
                            dismiss()
                        } catch (e: RuntimeException) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Erro na criação da conta", Toast.LENGTH_SHORT).show()
                            }
                            Log.d("ERRO", e.message.toString())
                        }
                    }
                }
            } else {
                if (nomeTxt.isEmpty() || saldoTxt.isEmpty()) {
                    Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch(Dispatchers.IO) {
                        if (conta != null) {
                            api.atualizarConta(Session.getToken(), conta.id, ContaFinanceiraRequest(nomeTxt, saldoTxt.toFloat()))
                            sucessoEdicaoOuCriacao?.invoke()
                            dismiss()
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Conta atualizada!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        fechar.setOnClickListener {
            dismiss()
        }

//        // Aqui você busca os componentes (ou usa ViewBinding)
//        val btnSalvar = view.findViewById<Button>(R.id.btnSalvar)
//
//        btnSalvar.setOnClickListener {
//            // Lógica para salvar os dados do formulário
//            dismiss() // Fecha o dialog
//        }

        return view
    }
}