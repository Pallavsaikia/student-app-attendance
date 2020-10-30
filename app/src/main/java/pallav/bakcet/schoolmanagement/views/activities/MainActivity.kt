package pallav.bakcet.schoolmanagement.views.activities

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import pallav.bakcet.schoolmanagement.R
import pallav.bakcet.schoolmanagement.utils.apiCallHasNoError
import pallav.bakcet.schoolmanagement.utils.isStringEmpty
import pallav.bakcet.schoolmanagement.utils.showProgressDialog
import pallav.bakcet.schoolmanagement.utils.toStringText
import pallav.bakcet.schoolmanagement.views.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {

    private var progress:AlertDialog?=null
    private val loginViewModel:LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginBtn.setOnClickListener {
            if (!fieldsHaveError()) {
                progress=showProgressDialog("Signing in", cancellable = false)
                loginViewModel.login(usernameEditTxt.toStringText(),passwordEditTxt.toStringText()).observe(this,
                    Observer {
                        progress?.cancel()
                        if(it.apiCallHasNoError(this)){

                        }
                    })
            }
        }
    }


    fun fieldsHaveError(): Boolean {
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
