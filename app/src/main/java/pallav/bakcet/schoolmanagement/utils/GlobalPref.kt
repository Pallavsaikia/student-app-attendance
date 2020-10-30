package pallav.bakcet.schoolmanagement.utils


import android.content.Context
import android.content.SharedPreferences

class GlobalPref(val context: Context) {
    companion object {
        const val PREF_NAME = "student_App"
        const val TOKEN = "token"
        const val IS_LOGGED_IN = "isLoggedIn"

    }

    private var editor: SharedPreferences.Editor
    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.apply()
    }


    fun string(key: String, str: String) {
        editor.putString(key, str)
        editor.apply()
    }

    fun string(key: String): String? {
        return sharedPreferences.getString(key, null)

    }

    fun boolean(key: String, bol: Boolean) {
        editor.putBoolean(key, bol)
        editor.apply()
    }

    fun boolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)

    }

    fun token(str: String) {
        string(TOKEN, str)
    }

    fun token(): String? {
        return string(key = TOKEN)
    }

    fun loggedIn(bol: Boolean) {
        boolean(IS_LOGGED_IN, bol)
    }

    fun loggedIn(): Boolean {
        return boolean(key = IS_LOGGED_IN)
    }

}


