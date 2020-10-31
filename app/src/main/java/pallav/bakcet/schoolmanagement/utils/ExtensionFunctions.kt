package pallav.bakcet.schoolmanagement.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.loading.view.*
import kotlinx.android.synthetic.main.session_expired_dialog.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import pallav.bakcet.schoolmanagement.BuildConfig
import pallav.bakcet.schoolmanagement.R
import pallav.bakcet.schoolmanagement.network.ApiResponse
import pallav.bakcet.schoolmanagement.utils.AppConstants.INVALID_TOKEN
import pallav.bakcet.schoolmanagement.utils.AppConstants.LOGOUT
import pallav.bakcet.schoolmanagement.utils.AppConstants.SESSION_OVER
import pallav.bakcet.schoolmanagement.views.activities.ErrorActivity
import pallav.bakcet.schoolmanagement.views.activities.LoginActivity


val ERROR_ACTIVITY = 3333

/**
 * Context
 */
fun Context.token(): String {
    return GlobalPref(this).token() ?: "No Token"
}

fun Context.logout() {
    GlobalPref(this).clearPref()
}

fun Context.token(string: String) {
    GlobalPref(this).token(string)
}


/**
 * activity
 */
fun Activity.showProgressDialog(
    header: String = "Loading",
    description: String = "Please wait. . . ",
    cancellable: Boolean = true
): AlertDialog {
    val builder = AlertDialog.Builder(this)
    val view = this.layoutInflater.inflate(R.layout.loading, null)
    builder.setView(view)
    builder.setCancelable(cancellable)
    val progress = builder.create()
    view.headerDialog.text = header
    view.descriptionDialog.text = description
    progress.show()
    return progress
}

/**
 * Textview
 */

fun TextView.underLine() {
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

/**
 * edit Text
 */
fun EditText.isStringEmpty(): Boolean {
    return this.text.isEmpty() || this.toStringText().replace(" ", "") == ""
}

fun EditText.toStringText() = this.text.toString()

fun EditText.checkEmailValid(): Boolean {
    var valid = true
    if (this.text.isEmpty()) {
        valid = false
        this.error = "invalid pattern"
    }
    if (!this.text.toString().isValidEmail()) {
        valid = false
        this.error = "invalid pattern"
    }
    return valid
}

/**
 * String
 */
fun String.addJWT(): String {
    return "JWT $this"
}

fun String?.containsDigit(): Boolean {
    var containsDigit = false

    if (this != null && this.isNotEmpty()) {
        for (c in this.toCharArray()) {
            if (Character.isDigit(c)) {
                containsDigit = true
                break
            }
        }
    }

    return containsDigit
}

fun String?.isValidEmail(): Boolean {
    if (this != null) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
    return false
}

fun String?.isEmpty(): Boolean {
    if (this.isNullOrBlank()) {
        return true
    }
    if (this.replace(" ", "") == "") {
        return true
    }
    return false
}

fun String.isStringEmpty(): Boolean {
    return this.isEmpty() || this.replace(" ", "") == ""
}

/**
 * integer
 */

fun Int?.checkAuthenticated(fragmentActivity: FragmentActivity) {
    if (this != null) {
        when (this) {
            SESSION_OVER, LOGOUT , INVALID_TOKEN-> {
                val builder = AlertDialog.Builder(fragmentActivity)
                val view =
                    fragmentActivity.layoutInflater.inflate(R.layout.session_expired_dialog, null)
                builder.setView(view)
                builder.setCancelable(false)

                val progress = builder.create()
                view.okaySession.setOnClickListener {
                    fragmentActivity.logout()
                    fragmentActivity.startActivity<LoginActivity>()
                    fragmentActivity.finishAffinity()
                    progress.dismiss()
                }
                progress.show()

            }
        }

    }
}


/**
 * api response
 */


//checks api Response as whole
fun ApiResponse.apiCallHasNoError(activity: FragmentActivity): Boolean {
    var noError = true

    if (this.hasNoServerOrInternalError(activity)) {
//        val any= castObject(type,this.type!!)
        val gson = Gson()
        val obj = JSONObject(gson.toJson(this.data!!))
        Log.d("asda", obj.toString())
        try {
            val msgType = obj.getBoolean("success")
            val errorCode = obj.getInt("error_code")
            val msg: String = if (obj.getString("error_message") == JSONObject.NULL) {
                if (msgType) {
                    "success"
                } else {
                    obj.getInt("error_code").toString() + " Error"
                }
            } else {
                obj.getString("error_message").toString()

            }

            if (!msgType) {
                errorCode.checkAuthenticated(activity)
                noError = false
                msgType.hasInternalServerError(activity, msg)

            }

        } catch (e: Exception) {
            Log.d("ASda", e.toString())
            this.hasNoServerOrInternalError(activity)
        }
    } else {
        noError = false
    }
    return noError
}


fun ApiResponse.hasNoServerOrInternalError(activity: FragmentActivity): Boolean {
    var error = false
    if (this.error != null) {
        activity.onError(this.error.toString())
        if (BuildConfig.API_DEBUG) {
            android.util.Log.d("serverError", this.error.toString())
        }
    } else {
        error = true
    }

    return error
}


/**
 * Fragment Activity
 */

//taskBarColor
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun FragmentActivity.setStatusBarBackground(background: Int = R.color.colorWhite) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
//        window.navigationBarColor = ContextCompat.getColor(this,android.R.color.transparent)
        window.setBackgroundDrawable(ContextCompat.getDrawable(this, background))
    }
}

//checks network availability
private fun FragmentActivity.isNetworkAvailable(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    if (Build.VERSION.SDK_INT < 23) {
        val ni = cm.activeNetworkInfo

        if (ni != null) {
            return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
        }
    } else {
        val n = cm.activeNetwork

        if (n != null) {
            val nc = cm.getNetworkCapabilities(n)
            return if (nc != null) {
                nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            } else {
                true
            }
        }
    }


    return false
}

//loads error activity on internet error
fun FragmentActivity.onError(error: String? = null) {
    if (!this.isNetworkAvailable()) {
        val intent = Intent(this, ErrorActivity::class.java)
        intent.putExtra("isNetWorkError", true)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        this.startActivityForResult(intent, ERROR_ACTIVITY)
    } else {
        val intent = Intent(this, ErrorActivity::class.java)
        intent.putExtra("isNetWorkError", false)
        intent.putExtra("error", error ?: "Server Error")
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        this.startActivityForResult(intent, ERROR_ACTIVITY)
    }
}


//checks internal api error such as field
fun Boolean.hasInternalServerError(activity: FragmentActivity, message: String?): Boolean {
    if (!this) {
        activity.toast(message ?: "error")
    }
    return this
}

