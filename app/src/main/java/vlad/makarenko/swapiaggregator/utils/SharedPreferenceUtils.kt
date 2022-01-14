package vlad.makarenko.swapiaggregator.utils

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Shared Preferences as Property Delegates (from the Kotlin for Android Developers book)
 * @author Alexander Gherschon
 */

private class Preference<T>(
    private val prefs: SharedPreferences,
    private val name: String,
    private val default: T
) : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun <T> findPreference(name: String, default: T): T = with(prefs) {
        val res = when (default) {
            is Long -> prefs.getLong(name, default)
            is String -> prefs.getString(name, default)
            is Int -> prefs.getInt(name, default)
            is Boolean -> prefs.getBoolean(name, default)
            is Float -> prefs.getFloat(name, default)
            else -> throw IllegalArgumentException("This type cannot be saved into Preferences")
        }

        @Suppress("UNCHECKED_CAST")
        res as T
    }

    private fun <T> putPreference(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type cannot be saved into Preferences")
        }.apply()
    }
}

fun SharedPreferences.boolean(name: String, default: Boolean = false): ReadWriteProperty<Any?, Boolean> = Preference(this, name, default)