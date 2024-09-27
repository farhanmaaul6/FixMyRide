package com.bangkit.fixmyrideapp.data.utils

import android.content.Context
import android.content.SharedPreferences
import com.bangkit.fixmyrideapp.data.response.Email
import com.bangkit.fixmyrideapp.data.response.Token
import com.bangkit.fixmyrideapp.data.response.Username
import com.bangkit.fixmyrideapp.data.utils.Constant.KEY_EMAIL
import com.bangkit.fixmyrideapp.data.utils.Constant.KEY_IS_LOGIN
import com.bangkit.fixmyrideapp.data.utils.Constant.KEY_NAME
import com.bangkit.fixmyrideapp.data.utils.Constant.KEY_TOKEN
import com.bangkit.fixmyrideapp.data.utils.Constant.PREFS_NAME

class SessionManager (context: Context) {

    private var sharedPref: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val editor = sharedPref.edit()

    fun setBooleanPref(prefBoolean: String, value: Boolean) {
        editor.putBoolean(prefBoolean, value)
        editor.apply()
    }

    fun setStringPref(prefString: String, value: String) {
        editor.putString(prefString, value)
        editor.apply()
    }

    fun clearData() {
        editor.clear().apply()
    }

    val isLogin = sharedPref.getBoolean(KEY_IS_LOGIN, false)
    val getToken = sharedPref.getString(KEY_TOKEN, "")
    val getUsername = sharedPref.getString(KEY_NAME, "")
    val getEmail = sharedPref.getString(KEY_EMAIL, "")

    fun getEmail(): Email {
        val email = sharedPref.getString(KEY_EMAIL, "")
        return Email(email)
    }

    fun getUsername(): Username {
        val username = sharedPref.getString(KEY_NAME, "")
        return Username(username)
    }

    fun getToken(): Token {
        val token = sharedPref.getString(KEY_TOKEN, "")
        return Token(token)
    }
}