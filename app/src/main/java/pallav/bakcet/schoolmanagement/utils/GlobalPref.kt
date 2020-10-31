package pallav.bakcet.schoolmanagement.utils


import android.content.Context
import android.content.SharedPreferences

class GlobalPref(val context: Context) {
    companion object {
        const val PREF_NAME = "student_App"
        const val TOKEN = "token"
        const val IS_LOGGED_IN = "isLoggedIn"
        const val EMAIL = "email"
        const val USERNAME = "username"
        const val FIRSTNAME = "firstname"
        const val LASTNAME = "lastname"

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

    fun email(str: String) {
        string(EMAIL, str)
    }

    fun email(): String? {
        return string(key = EMAIL)
    }

    fun username(str: String) {
        string(USERNAME, str)
    }

    fun username(): String? {
        return string(key = USERNAME)
    }

    fun firstname(str: String) {
        string(FIRSTNAME, str)
    }

    fun firstname(): String? {
        return string(key = FIRSTNAME)
    }

    fun lastname(str: String) {
        string(LASTNAME, str)
    }

    fun lastname(): String? {
        return string(key = LASTNAME)
    }

    fun loggedIn(bol: Boolean) {
        boolean(IS_LOGGED_IN, bol)
    }

    fun loggedIn(): Boolean {
        return boolean(key = IS_LOGGED_IN)
    }

    fun clearPref(){
        editor.clear()
        editor.apply()
        editor.commit()
    }

}


