package com.freesign.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.freesign.model.User

object Authenticated {
    lateinit var context: Context
    private const val KEY_IS_VALID_CACHE_MEMBER = "isValid"
    private const val KEY_USER = "user"
    private const val KEY_MEMBER = "member"
    private const val KEY_TOKEN = "token"
    private const val PREFS_NAME = "auth_pref"
    private lateinit var preferences : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val gson = Gson()

    fun init(context: Context){
        this.context = context
        preferences = Authenticated.context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    fun setUser(value: User) {
        editor.putString(KEY_USER, gson.toJson(value))
        editor.putBoolean(KEY_IS_VALID_CACHE_MEMBER, true)
        editor.commit()
    }

    fun getUser(): User {
        if(preferences.getString(KEY_USER, "")!="") {
            return gson.fromJson(preferences.getString(KEY_USER, ""), User::class.java)
        } else {
            return User()
        }
    }

    fun isValidCacheMember(): Boolean {
        return preferences.getBoolean(KEY_IS_VALID_CACHE_MEMBER, false)
    }

    fun destroySession() {
        editor.remove(KEY_USER)
        editor.remove(KEY_MEMBER)
        editor.remove(KEY_TOKEN)
        editor.putBoolean(KEY_IS_VALID_CACHE_MEMBER, false)
        editor.commit()
    }
}