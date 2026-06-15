package com.example.ap2_dev_mobile.dialog

import android.os.Bundle
import android.text.Selection
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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
import com.example.ap2_dev_mobile.dto.CategoriaResponse
import com.example.ap2_dev_mobile.dto.ContaFinanceiraResponse
import com.example.ap2_dev_mobile.dto.TransacaoRequest
import com.example.ap2_dev_mobile.dto.TransacaoResponse
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class TransacaoDialog (val context: String, val transacao: TransacaoResponse?, val sucessoEdicaoOuCriacao: (() -> Unit)?): DialogFragment() {

    private lateinit var categorias: List<CategoriaResponse>
    private lateinit var contasFianceiras: List<ContaFinanceiraResponse>


    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View

        if (context == "criar" || context == "editar") {
            view = inflater.inflate(R.layout.formulario_lancamento_editavel, container, false)
        } else {
            view = inflater.inflate(R.layout.formulario_lancamento, container, false)
        }

        val situacao = view.findViewById<TextView>(R.id.situacao)
        val itemInput = view.findViewById<EditText>(R.id.item_valor)
        val dataCompraInput = view.findViewById<ImageView>(R.id.dt_compra_valor)
        val dataCompraSelecioanda = view.findViewById<TextView>(R.id.dt_compra_selecionada)
        val valor = view.findViewById<EditText>(R.id.valor_valor)
        val vencimento = view.findViewById<ImageView>(R.id.vencimento_valor)
        val dtVencimentoSelecionada = view.findViewById<TextView>(R.id.dt_vencimento_selecionada)
        val categoriaVer = view.findViewById<EditText>(R.id.categoriaValor)
        val categoriaEscolher = view.findViewById<AutoCompleteTextView>(R.id.categoriaInput)
        val tipo = view.findViewById<AutoCompleteTextView>(R.id.tipoInput)
        val descricao = view.findViewById<EditText>(R.id.descricao_valor)
        val fechar = view.findViewById<ImageView>(R.id.btn_fechar)
        val acaoBtn = view.findViewById<MaterialButton>(R.id.acao_btn)
        val contaFin = view.findViewById<AutoCompleteTextView>(R.id.contaFinanceiraInput)
        val api = RetrofitClient.retrofit.create(ApiService::class.java)
        val tipos = listOf("RECEITA", "DESPESA")
        var nomesCategorias: List<String>
        var nomesContasFin: List<String>

        var dataCompraParaEnviar = ""
        var dataVencimentoParaEnviar = ""
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formato.timeZone = TimeZone.getTimeZone("UTC")
        val formatoParaEnviar = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formatoParaEnviar.timeZone = TimeZone.getTimeZone("UTC")


        dataCompraInput.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecione a data da compra")
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                val data = formato.format(Date(selection))
                dataCompraParaEnviar = formatoParaEnviar.format(Date(selection))
                dataCompraSelecioanda.setText(data)
            }

            datePicker.show(parentFragmentManager, "DATA_COMPRA")
        }

        vencimento.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecione a data de vencimento")
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                formato.timeZone = TimeZone.getTimeZone("UTC")
                val data = formato.format(Date(selection))
                dataVencimentoParaEnviar = formatoParaEnviar.format(Date(selection))
                dtVencimentoSelecionada.setText(data)
            }

            datePicker.show(parentFragmentManager, "VENCIMENTO")
        }


        if (context == "criar") {
            situacao.text = "Criar nova transação"
            acaoBtn.text = "Criar"
            lifecycleScope.launch {
                categorias = api.getAllCategorias(Session.getToken())
                contasFianceiras = api.getAllContas(Session.getToken())
                nomesCategorias = categorias.map{ it.nome }
                nomesContasFin = contasFianceiras.map { it.nome }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, nomesCategorias)
                val adapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, nomesContasFin)
                val adapter3 = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, tipos)
                categoriaEscolher.setAdapter(adapter)
                contaFin.setAdapter(adapter2)
                tipo.setAdapter(adapter3)
            }
        }

        if (context == "editar") {

            // lifecycle para refazer as requisições das categorias e contas financeiras
            lifecycleScope.launch {
                categorias = api.getAllCategorias(Session.getToken())
                contasFianceiras = api.getAllContas(Session.getToken())
                nomesCategorias = categorias.map{ it.nome }
                nomesContasFin = contasFianceiras.map { it.nome }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, nomesCategorias)
                val adapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, nomesContasFin)
                val adapter3 = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, tipos)
                categoriaEscolher.setAdapter(adapter)
                contaFin.setAdapter(adapter2)
                tipo.setAdapter(adapter3)
            }

            // carregamento dos valores da transação nos inputs

            acaoBtn.text = "Salvar"
            situacao.text = "Editar Transação"

            Log.d("TRANSCAO", transacao.toString())

            itemInput.setText(transacao?.item)

            val data = formatoParaEnviar.parse(transacao?.dataCompra)
            dataCompraParaEnviar = transacao!!.dataCompra
            dataCompraSelecioanda.setText(formato.format(data))

            dataCompraInput.setOnClickListener {
                val millis = formatoParaEnviar.parse(dataCompraParaEnviar).time

                val datePicker = MaterialDatePicker.Builder.datePicker()
                    .setSelection(millis)
                    .build()

                datePicker.addOnPositiveButtonClickListener { selection ->
                    val data = formato.format(Date(selection))
                    dataCompraParaEnviar = formatoParaEnviar.format(Date(selection))
                    dataCompraSelecioanda.setText(data)
                }

                datePicker.show(parentFragmentManager, "DATA")
            }


            valor.setText(transacao?.total.toString())

            val dtVenc = formatoParaEnviar.parse(transacao?.dataVencimento)
            dtVencimentoSelecionada.setText(formato.format(dtVenc))
            dataVencimentoParaEnviar = formatoParaEnviar.format(dtVenc)

            vencimento.setOnClickListener {
                val millisVenc = formatoParaEnviar.parse(dataVencimentoParaEnviar).time
                val datePickerVenc = MaterialDatePicker.Builder.datePicker()
                    .setSelection(millisVenc)
                    .build()

                datePickerVenc.addOnPositiveButtonClickListener { selection ->
                    formato.timeZone = TimeZone.getTimeZone("UTC")
                    val data = formato.format(Date(selection))
                    dataVencimentoParaEnviar = formatoParaEnviar.format(Date(selection))
                    dtVencimentoSelecionada.setText(data)
                }

                datePickerVenc.show(parentFragmentManager, "DATA_VENCIMENTO")


            }

            contaFin.setText(transacao?.contaFinanceira?.nome)

            categoriaEscolher.setText(transacao?.categoria?.nome, false)
            tipo.setText(transacao?.tipo, false)
            descricao.setText(transacao?.descricao)
        }

        fun camposPreenchidos(
            item: String,
            dataCompra: String,
            valor: String,
            vencimento: String,
            categoria: String,
            tipo: String,
            descricao: String,
            conta: String
        ): Boolean {
            return item.isNotBlank() &&
                    dataCompra.isNotBlank() &&
                    valor.isNotBlank() &&
                    vencimento.isNotBlank() &&
                    categoria.isNotBlank() &&
                    tipo.isNotBlank() &&
                    descricao.isNotBlank() &&
                    conta.isNotBlank()
        }

        acaoBtn.setOnClickListener {
            val itemTexto = itemInput.text.toString().trim()
            val dataCompraTexto= dataCompraSelecioanda.text.toString().trim()
            val valorTexto = valor.text.toString().trim()
            val vencimentoTexto = dtVencimentoSelecionada.text.toString().trim()
            var categoriaTexto = categoriaEscolher.text.toString().trim()
            val tipoTexto = tipo.text.toString().trim()
            val descricaoTexto = descricao.text.toString().trim()
            val contaTexto = contaFin.text.toString().trim()

            val todosPreenchidos = camposPreenchidos(
                itemTexto,
                dataCompraTexto,
                valorTexto,
                vencimentoTexto,
                categoriaTexto,
                tipoTexto,
                descricaoTexto,
                contaTexto
            )

            if (context == "criar") {
                if (!todosPreenchidos) {
                    Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch(Dispatchers.IO) {
                        try {
                            var categoriaSelecionada = categorias.firstOrNull{ it.nome == categoriaTexto }
                            var contaSelecioanda = contasFianceiras.firstOrNull{ it.nome == contaTexto }
                            var t = TransacaoRequest(itemTexto, dataCompraParaEnviar, dataVencimentoParaEnviar, valorTexto.toFloat(), categoriaSelecionada, contaSelecioanda, tipoTexto, descricaoTexto)
                            api.criarTransacao(Session.getToken(), t)
                            withContext(Dispatchers.Main){
                                Toast.makeText(requireContext(), "Transacao criada com sucesso!", Toast.LENGTH_SHORT).show()
                            }
                            sucessoEdicaoOuCriacao?.invoke()
                            dismiss()
                        } catch (e: RuntimeException) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Erro na criação do lançamento", Toast.LENGTH_SHORT).show()
                            }
                            Log.d("ERRO", e.message.toString())
                        }
                    }
                }
            } else if (context == "editar") {
                if (!todosPreenchidos) {
                    Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch {
                        try {
                            if (transacao?.id != null) {
                                var categoriaSelecionada = categorias.firstOrNull{ it.nome == categoriaTexto }
                                var contaSelecioanda = contasFianceiras.firstOrNull{ it.nome == contaTexto }
                                var t = TransacaoRequest(itemTexto, dataCompraParaEnviar, dataVencimentoParaEnviar, valorTexto.toFloat(), categoriaSelecionada, contaSelecioanda, tipoTexto, descricaoTexto)
                                api.atualizarTransacao(Session.getToken(), transacao.id, t)
                                withContext(Dispatchers.Main){
                                    Toast.makeText(requireContext(), "Transação atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                                    sucessoEdicaoOuCriacao?.invoke()
                                    dismiss()
                                }
                            }
                        } catch (e: RuntimeException) {
                            withContext(Dispatchers.Main){
                                Toast.makeText(requireContext(), "Erro ao tentar atualizar a transação!", Toast.LENGTH_SHORT).show()
                                Log.d("ERRO ATUALIZAÇÃO TRANSACAO", e.message.toString())
                                dismiss()
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