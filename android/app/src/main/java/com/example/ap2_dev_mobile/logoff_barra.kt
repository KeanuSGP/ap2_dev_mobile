package com.example.ap2_dev_mobile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.flagging.Flags
import com.example.ap2_dev_mobile.auth.Session

class logoff_barra : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val barra = inflater.inflate(R.layout.fragment_logoff_barra, container, false)

        val logoffBtn = barra.findViewById<ImageView>(R.id.logoff_btn)

        logoffBtn.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Tem certeza que deseja sair?")
                .setPositiveButton("Sim") { dialog, id ->
                    Session.logoff()
                    val intent = Intent(requireContext(), login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
        }
                .setNegativeButton("Não") { dialog, id ->

                }

            builder.create().show()

        }

        return barra
    }


}