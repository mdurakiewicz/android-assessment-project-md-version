package com.vp.commonaddons

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.vp.commonaddons.extension.fromJson
import com.vp.commonaddons.model.ListItem
import javax.inject.Inject

class SharedPreferencesManager
@Inject constructor(private val context: Context, private val gson: Gson) {

    companion object {
        const val FAVORITES_ITEM = "FAVORITES_ITEM"
    }

    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

    fun getFavorites(): List<ListItem> {
        return gson.fromJson(sharedPref.getString(FAVORITES_ITEM, null)) ?: listOf()
    }

    fun addFavorite(favorite: ListItem?) {
        favorite ?: return
        val currentList = getFavorites().toMutableSet()
        currentList.add(favorite)
        saveFavorites(currentList)
    }

    fun removeFavorite(favorite: ListItem?) {
        favorite ?: return
        val currentList = getFavorites().toMutableSet()
        currentList.remove(favorite)
        saveFavorites(currentList)
    }

    private fun saveFavorites(favorites: Set<ListItem>) {
        val editor = sharedPref.edit()
        editor.putString(FAVORITES_ITEM, gson.toJson(favorites))
        editor.apply()
    }

    fun isFavourite(favorite: ListItem?): Boolean {
        return getFavorites().toMutableSet().contains(favorite)
    }
}
