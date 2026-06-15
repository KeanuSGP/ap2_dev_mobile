package com.example.ap2_dev_mobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ap2_dev_mobile.R
import com.example.ap2_dev_mobile.dto.ContaFinanceiraResponse

class ContasAdapter (private val dataSet: List<ContaFinanceiraResponse>, val editarConta: (ContaFinanceiraResponse) -> Unit, val deletarConta: (ContaFinanceiraResponse) -> Unit):
    RecyclerView.Adapter<ContasAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView
        val item: TextView
        val valor: TextView
        val vencimento: TextView
        val editarBtn: ImageView
        val deletarBtn: ImageView


        init {
            id = view.findViewById(R.id.id_item)
            item = view.findViewById(R.id.valor_item)
            valor = view.findViewById(R.id.valor_valor)
            vencimento = view.findViewById(R.id.valor_vencimento)
            editarBtn = view.findViewById(R.id.editar_btn)
            deletarBtn = view.findViewById(R.id.deletar_btn)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ContasAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.entidade_padrao, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conta = dataSet[position]
        holder.id.text = conta.id.toString()
        holder.item.text = conta.nome
        holder.valor.text = "R$ ${conta.saldo}"
        holder.vencimento.text = conta.lancamentosQtd.toString()

        holder.editarBtn.setOnClickListener {
            editarConta(conta)
        }

        holder.deletarBtn.setOnClickListener {
            deletarConta(conta)
        }
    }

    override fun getItemCount() = dataSet.size

}