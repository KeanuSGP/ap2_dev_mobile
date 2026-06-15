package com.example.ap2_dev_mobile.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ap2_dev_mobile.R
import com.example.ap2_dev_mobile.dto.TransacaoResponse
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class TransacaoAdapter (private val dataSet: List<TransacaoResponse>, val editar: (TransacaoResponse) -> Unit, val deletar: (TransacaoResponse) -> Unit):
    RecyclerView.Adapter<TransacaoAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView
        val item: TextView
        val valor: TextView
        val vencimento: TextView
        val situacao: TextView
        val editar: ImageView
        val deletar: ImageView

        init {
            id = view.findViewById(R.id.id_item)
            item = view.findViewById(R.id.valor_item)
            valor = view.findViewById(R.id.valor_valor)
            vencimento = view.findViewById(R.id.valor_vencimento)
            situacao = view.findViewById(R.id.valor_situacao)
            editar = view.findViewById(R.id.edit_btn)
            deletar = view.findViewById(R.id.delete_btn)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TransacaoAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.lancamento_entidade, viewGroup, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(lancamento: ViewHolder, position: Int) {
        val formatar = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formatar.timeZone = TimeZone.getTimeZone("UTC")
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())


        val transacao = dataSet[position]

        val data = parser.parse(transacao.dataVencimento)
        val dataFormatada = formatar.format(data)

        lancamento.id.text = transacao.id.toString()
        lancamento.item.text = "ITEM: ${transacao.item}"
        lancamento.valor.text = "VALOR: R$ ${transacao.total}"
        lancamento.vencimento.text = "VENCIMENTO: ${dataFormatada}"
        lancamento.situacao.text = "STATUS: ${transacao.status}"

        lancamento.editar.setOnClickListener {
            editar(transacao)
        }

        lancamento.deletar.setOnClickListener {
            deletar(transacao)
        }
    }

    override fun getItemCount() = dataSet.size

}