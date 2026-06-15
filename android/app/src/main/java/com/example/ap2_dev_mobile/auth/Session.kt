package com.example.ap2_dev_mobile.auth

import android.content.Context
import android.content.SharedPreferences
import com.example.ap2_dev_mobile.dto.Usuario
import com.google.gson.Gson

object Session {
    private lateinit var preference: SharedPreferences

    private lateinit var editor: SharedPreferences.Editor

    fun init(context: Context) {
        preference = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        editor = preference.edit()
    }

    fun createLoginSession(token: String, usuario: Usuario) {
        editor.putString("token", token).apply()
        editor.putString("usuario", Gson().toJson(usuario)).apply()
    }

    fun getToken():String? {
        return preference.getString("token", null)
    }

    fun getUsuario(): Usuario {
        return Gson().fromJson(preference.getString("usuario", null), Usuario::class.java)
    }

    fun logoff() {
        editor.clear().apply()
    }
}