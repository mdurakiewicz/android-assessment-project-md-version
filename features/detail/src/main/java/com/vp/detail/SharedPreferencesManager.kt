package com.vp.detail

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class SharedPreferencesManager(private val context: Context) {

    companion object {
        const val FAVORITES = "FAVORITES"
    }

    val gson: Gson = GsonBuilder().create()
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

    fun getFavorites(): List<String> {
        return gson.fromJson(sharedPref.getString(FAVORITES, null)) ?: listOf()
    }

    fun addFavorites(favorite: String?) {
        favorite ?: return
        val currentList = getFavorites().toMutableList()
        if(currentList.contains(favorite)) return
        currentList.add(favorite)
        saveFavorites(currentList)
    }

    fun removeFavorites(favorite: String?) {
        favorite ?: return
        val currentList = getFavorites().toMutableList()
        currentList.remove(favorite)
        saveFavorites(currentList)
    }

    private fun saveFavorites(favorites: List<String>) {
        val editor = sharedPref.edit()
        editor.putString(FAVORITES, gson.toJson(favorites))
        editor.commit()
    }
}

inline fun <reified T> Gson.fromJson(json: String?) = if (json != null) {
    fromJson<T>(json, object : TypeToken<T>() {}.type)
} else {
    null
}