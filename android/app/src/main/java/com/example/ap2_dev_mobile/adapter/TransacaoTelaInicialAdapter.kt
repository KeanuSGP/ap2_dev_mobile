package com.example.ap2_dev_mobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ap2_dev_mobile.R
import com.example.ap2_dev_mobile.dto.TransacaoResumoTelaInicial

class TransacaoTelaInicialAdapter(private val dataSet: List<TransacaoResumoTelaInicial>):
    RecyclerView.Adapter<TransacaoTelaInicialAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView
        val item: TextView
        val valor: TextView
        val vencimento: TextView

        init {
            id = view.findViewById(R.id.id_item)
            item = view.findViewById(R.id.valor_item)
            valor = view.findViewById(R.id.valor_valor)
            vencimento = view.findViewById(R.id.valor_vencimento)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TransacaoTelaInicialAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.lancamento_tela_inicial, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(lancamento: ViewHolder, position: Int) {
            val transacao = dataSet[position]
            lancamento.id.text = transacao.id.toString()
            lancamento.item.text = transacao.item
            lancamento.valor.text = transacao.valor.toString()
            lancamento.vencimento.text = transacao.vencimento.toString()

    }

    override fun getItemCount() = dataSet.size

}