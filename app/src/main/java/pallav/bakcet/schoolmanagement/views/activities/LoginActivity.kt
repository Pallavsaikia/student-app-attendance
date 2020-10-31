package pallav.bakcet.schoolmanagement.views.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel
import pallav.bakcet.schoolmanagement.R
import pallav.bakcet.schoolmanagement.utils.*
import pallav.bakcet.schoolmanagement.views.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private var progress: AlertDialog? = null
    private val loginViewModel: LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerTxtView.underLine()
        setStatusBarBackground()
        clickListeners()

    }

    private fun clickListeners() {
        registerTxtView.setOnClickListener {
            startActivity<RegisterActivity>()
        }
        loginBtn.setOnClickListener {
            if (!fieldsHaveError()) {
                progress = showProgressDialog("Signing in", cancellable = false)
                loginViewModel.login(usernameEditTxt.toStringText(), passwordEditTxt.toStringText())
                    .observe(this,
                        Observer {
                            progress?.cancel()

                            if(it.apiCallHasNoError(this)) {

                            }
                        })
            }
        }
    }


    private fun fieldsHaveError(): Boolean {
        var error = false
        if (usernameEditTxt.isStringEmpty()) {
            error = true
            usernameEditTxt.error = "Cannot be  empty"
        }

        if (passwordEditTxt.isStringEmpty()) {
            error = true
            passwordEditTxt.error = "Cannot be  empty"
        }
        return error
    }
}
