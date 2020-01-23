package uz.jamshid.exchangerate.data.local

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SharedPrefManager {

    const val INPUT_RATE = "INPUT_RATE"
    const val INPUT_VALUE = "INPUT_VALUE"
    const val OUTPUT_RATE = "OUTPUT_RATE"
    const val OUTPUT_VALUE = "OUTPUT_VALUE"


    lateinit var preferences: SharedPreferences

    fun initContext(context: Context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun<T> writePrefs(key: String, item: T){
        preferences.edit().apply {
            when(item) {
                is Int -> {
                    putInt(key, item)
                }
                is Float -> {
                    putFloat(key, item)
                }
                is Long -> {
                    putLong(key, item)
                }
                is String -> {
                    putString(key, item)
                }
                is Boolean -> {
                    putBoolean(key, item)
                }
            }
            apply()
        }
    }

    fun readFloat(key: String): Float{
        return preferences.getFloat(key, 0f)
    }

    fun readString(key: String): String{
        return preferences.getString(key, "")?:""
    }
}
