package com.example.ap2_dev_mobile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


class navbar : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navbar = inflater.inflate(R.layout.fragment_navbar, container, false)
        val inicio = navbar.findViewById<ImageView>(R.id.inicio_btn)
        val inicioIndicador = navbar.findViewById<View>(R.id.inicio_indicador)
        val conta = navbar.findViewById<ImageView>(R.id.contas_btn)
        val contaIndicador = navbar.findViewById<View>(R.id.contas_indicador)
        val categoria = navbar.findViewById<ImageView>(R.id.categorias_btn)
        val categoriaIndicador = navbar.findViewById<View>(R.id.categorias_indicador)
        val transacao = navbar.findViewById<ImageView>(R.id.transacoes_btn)
        val transacaoIndicador = navbar.findViewById<View>(R.id.transacoes_indicador)

        if (requireActivity() is tela_inicial) {
            inicioIndicador.visibility = View.VISIBLE
        }

        if (requireActivity() is contas_financeiras) {
            contaIndicador.visibility = View.VISIBLE
        }

        if (requireActivity() is categorias) {
            categoriaIndicador.visibility = View.VISIBLE
        }

        if (requireActivity() is lancamentos) {
            transacaoIndicador.visibility = View.VISIBLE
        }

        inicio.setOnClickListener {
            startActivity(Intent(requireContext(), tela_inicial::class.java))
            requireActivity().overridePendingTransition(0, 0)
            requireActivity().finish()
        }

        conta.setOnClickListener {
            startActivity(Intent(requireContext(), contas_financeiras::class.java))
            requireActivity().overridePendingTransition(0, 0)
            requireActivity().finish()
        }

        categoria.setOnClickListener {
            startActivity(Intent(requireContext(), categorias::class.java))
            requireActivity().overridePendingTransition(0, 0)
            requireActivity().finish()
        }

        transacao.setOnClickListener {
            startActivity(Intent(requireContext(), lancamentos::class.java))
            requireActivity().overridePendingTransition(0, 0)
            requireActivity().finish()
        }

        return navbar
    }

}