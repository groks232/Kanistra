package com.groks.kanistra.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

object Constants {
    const val BASE_URL = "http://80.249.147.238/"
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

}