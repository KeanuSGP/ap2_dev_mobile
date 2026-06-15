package com.example.ap2_dev_mobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ap2_dev_mobile.R
import com.example.ap2_dev_mobile.dto.CategoriaResponse
import com.example.ap2_dev_mobile.dto.ContaFinanceiraResponse

class CategoriaAdapter (private val dataSet: List<CategoriaResponse>, val editarCategoria: (CategoriaResponse) -> Unit, val deletarCategoria: (CategoriaResponse) -> Unit):
    RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView
        val item: TextView
        val cor: TextView
        val lancamentos: TextView
        val editarBtn: ImageView
        val deletarBtn: ImageView


        init {
            id = view.findViewById(R.id.id_item)
            item = view.findViewById(R.id.valor_item)
            cor = view.findViewById(R.id.valor_valor)
            lancamentos = view.findViewById(R.id.valor_vencimento)
            editarBtn = view.findViewById(R.id.editar_btn)
            deletarBtn = view.findViewById(R.id.deletar_btn)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CategoriaAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.entidade_padrao, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = dataSet[position]
        holder.id.text = categoria.id.toString()
        holder.item.text = categoria.nome
        holder.cor.text = categoria.cor
        holder.lancamentos.text = categoria.lancalmentosQtd.toString()

        holder.editarBtn.setOnClickListener {
            editarCategoria(categoria)
        }

        holder.deletarBtn.setOnClickListener {
            deletarCategoria(categoria)
        }
    }

    override fun getItemCount() = dataSet.size

}