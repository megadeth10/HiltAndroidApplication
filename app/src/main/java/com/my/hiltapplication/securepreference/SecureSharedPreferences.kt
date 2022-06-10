package com.my.hiltapplication.securepreference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.my.hiltapplication.securepreference.callback.SecureStoreCallback
import com.my.hiltapplication.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.json.JSONArray
import org.json.JSONException
import java.util.*
import javax.inject.Inject

class SecureSharedPreferences @Inject constructor(@ApplicationContext context : Context) {
    private val tagName = SecureSharedPreferences::class.simpleName
    private var sharedPreferences : SharedPreferences

    init {
        this.sharedPreferences = this.getPreference(context)
    }

    private fun getPreference(context : Context) : SharedPreferences {
        val masterKeyAlias : String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        return EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        // use the shared preferences and editor as you normally would
    }

    /**
     * @param key : store key name
     * @param value : store value
     * @param callback : Async thread to end callback(필수는 아님)
     */
    fun storeString(key : String, value : String, callback : SecureStoreCallback?) {
        val time = Calendar.getInstance().timeInMillis
        val preferenceObject = PreferencesObject(key, value, callback)
        CoroutineScope(Dispatchers.IO).run {
            val editor = sharedPreferences.edit()
            editor.putString(preferenceObject.key, preferenceObject.value as String)
            editor.apply()
            preferenceObject.function?.let {
                CoroutineScope(Dispatchers.Default).run {
                    Log.e(tagName, String.format("SecurePreferences.setValue() finish"))
                    it.storeFinish()
                }
            }
        }
//        Single.just(preferenceObject)
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(Schedulers.io())
//            .subscribe { t : PreferencesObject ->
//                val editor = sharedPreferences.edit()
//                editor.putString(t.key, t.value as String)
//                editor.apply()
//                t.function?.storeFinish()
//                Log.e(tagName, String.format("SecurePreferences.setValue() finish"))
//            }
        Log.e(
            tagName, String.format(
                "set() consumed time: %d",
                Calendar.getInstance().timeInMillis - time
            )
        )
    }

    fun storeInt(key : String, value : Int, callback : SecureStoreCallback?) {
        val preferenceObject =
            PreferencesObject(key, value, callback)
        Single.just(preferenceObject)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribe { t : PreferencesObject ->
                val editor = sharedPreferences.edit()
                editor.putInt(t.key, t.value as Int)
                editor.apply()
                t.function?.storeFinish()
            }
    }

    fun storeLong(key : String, value : Long, callback : SecureStoreCallback?) {
        val preferenceObject =
            PreferencesObject(key, value, callback)
        Single.just(preferenceObject)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribe { t : PreferencesObject ->
                val editor = sharedPreferences.edit()
                editor.putLong(t.key, t.value as Long)
                editor.apply()
                t.function?.storeFinish()
            }
    }

    fun storeFloat(key : String, value : Float, callback : SecureStoreCallback?) {
        val preferenceObject =
            PreferencesObject(key, value, callback)
        Single.just(preferenceObject)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribe { t : PreferencesObject ->
                val editor = sharedPreferences.edit()
                editor.putFloat(t.key, t.value as Float)
                editor.apply()
                t.function?.storeFinish()
            }
    }

    fun storeBoolean(
        key : String,
        value : Boolean,
        callback : SecureStoreCallback?
    ) {
        val preferenceObject =
            PreferencesObject(key, value, callback)
        Single.just(preferenceObject)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribe { t : PreferencesObject ->
                val editor = sharedPreferences.edit()
                editor.putBoolean(t.key, t.value as Boolean)
                editor.apply()
                t.function?.storeFinish()
            }
    }

    fun <T> storeArrayList(
        key : String,
        value : ArrayList<T>,
        callback : SecureStoreCallback?
    ) {
        val preferenceObject = PreferencesObject(key, value, callback)
        Single.just(preferenceObject)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribe { t : PreferencesObject ->
                val editor = sharedPreferences.edit()
                val jsonArray = JSONArray()
                val values = t.value as ArrayList<T>
                values.forEach {
                    jsonArray.put(it)
                }

                if (jsonArray.length() > 0) {
                    editor.putString(t.key, jsonArray.toString())
                } else {
                    editor.remove(t.key)
                }
                editor.apply()
                t.function?.storeFinish()
            }
    }

    fun getString(key : String, defaultValue : String) : String {
        return this.sharedPreferences.getString(key, defaultValue)!!
    }

    fun getBoolean(key : String, defaultValue : Boolean) : Boolean {
        return this.sharedPreferences.getBoolean(key, defaultValue)
    }

    fun getFloat(key : String, defaultValue : Float) : Float {
        return this.sharedPreferences.getFloat(key, defaultValue)
    }

    fun getInt(key : String, defaultValue : Int) : Int {
        return this.sharedPreferences.getInt(key, defaultValue).toInt()
    }

    fun getLong(key : String, defaultValue : Long) : Long {
        return this.sharedPreferences.getLong(key, defaultValue).toLong()
    }

    fun <T> getArrayList(key : String) : ArrayList<T> {
        val stringValue = getString(key, "")
        if (stringValue != "") {
            try {
                val returnArray = ArrayList<T>()

                val jsonArray = JSONArray(stringValue)
                for (jsonIdx in 0 until jsonArray.length()) {
                    returnArray.add(jsonArray.opt(jsonIdx) as T)
                }

                return returnArray
            } catch (e : JSONException) {

            }
        }

        return ArrayList<T>()
    }

    /**
     * preference 해당 데이터 삭제
     */
    fun remove(key : String) {
        val time = Calendar.getInstance().timeInMillis
        val preferences = this.sharedPreferences.edit()
        preferences.remove(key)
        preferences.apply()
        Log.e(
            tagName, String.format(
                "remove() consumed time: %d",
                Calendar.getInstance().timeInMillis - time
            )
        )
    }

    private class PreferencesObject(
        var key : String,
        var value : Any,
        var function : SecureStoreCallback?
    )
}