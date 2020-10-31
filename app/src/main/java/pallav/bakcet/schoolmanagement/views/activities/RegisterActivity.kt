package pallav.bakcet.schoolmanagement.views.activities

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel
import pallav.bakcet.schoolmanagement.R
import pallav.bakcet.schoolmanagement.pojo.register.RegisterResponse
import pallav.bakcet.schoolmanagement.utils.*
import pallav.bakcet.schoolmanagement.views.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private var progress: AlertDialog? = null
    private val registerViewModel: RegisterViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (GlobalPref(this).loggedIn()) {
            startActivity<HomeActivity>()
            finishAffinity()
        }
        setContentView(R.layout.activity_register)
        setStatusBarBackground()
        loginTxtView.setOnClickListener {
            onBackPressed()
        }
        registerBtn.setOnClickListener {
            if (!fieldsHaveError()) {
                progress = showProgressDialog("Signing Up", cancellable = false)
                registerViewModel.register(
                    username = usernameEditTxt.toStringText(),
                    email = emailEditTxt.toStringText(),
                    year = yearEditTxt.toStringText().toInt(),
                    password = passwordEditTxt.toStringText()
                ).observe(this, Observer {
                    progress?.cancel()

                    if (it.apiCallHasNoError(this)) {
                        val data = it.data as RegisterResponse
                        if (data.success) {
                            startActivity<HomeActivity>()
                        }
                    }
                })
            }
        }
    }

    private fun fieldsHaveError(): Boolean {
        var error = false
        if (!emailEditTxt.checkEmailValid()) {
            error = true
        }
        if (usernameEditTxt.isStringEmpty()) {
            error = true
            usernameEditTxt.error = "cannot be empty"
        }
        if (yearEditTxt.isStringEmpty()) {
            error = true
            yearEditTxt.error = "cannot be empty"
        }
        if (passwordEditTxt.isStringEmpty()) {
            error = true
            passwordEditTxt.error = "cannot be empty"
        }

        if (!passwordEditTxt.toStringText().equals(confirmPasswordEditTxt.toStringText(), false)) {
            error = true
            confirmPasswordEditTxt.error = "passwords do not match"
            passwordEditTxt.error = "passwords do not match"
        }
        return error
    }


}
