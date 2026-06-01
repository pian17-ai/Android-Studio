package com.example.kopinusantara.session

import android.content.Context

class SessionManager(context: Context) {

    private val pref = context.getSharedPreferences("APP", Context.MODE_PRIVATE)

    fun saveToken(token: String){
        pref.edit().putString("TOKEN", token).apply()
    }

    fun getToken(): String? {
        return pref.getString("TOKEN", null)
    }

    fun clear(){
        pref.edit().clear().apply()
    }

    fun clearSession() {
        pref.edit().clear().apply()
    }
}
