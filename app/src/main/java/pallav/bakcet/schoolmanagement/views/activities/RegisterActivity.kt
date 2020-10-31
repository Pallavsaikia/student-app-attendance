package pallav.bakcet.schoolmanagement.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.viewmodel.ext.android.viewModel
import pallav.bakcet.schoolmanagement.R
import pallav.bakcet.schoolmanagement.utils.apiCallHasNoError
import pallav.bakcet.schoolmanagement.utils.setStatusBarBackground
import pallav.bakcet.schoolmanagement.utils.showProgressDialog
import pallav.bakcet.schoolmanagement.utils.toStringText
import pallav.bakcet.schoolmanagement.views.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private var progress: AlertDialog? = null
    private val registerViewModel: RegisterViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setStatusBarBackground()
        loginTxtView.setOnClickListener {
            onBackPressed()
        }
        registerBtn.setOnClickListener {
            if(!fieldsHaveError()){
                progress = showProgressDialog("Signing Up", cancellable = false)
                registerViewModel.register(username = usernameEditTxt.toStringText(),
                    email = emailEditTxt.toStringText(),
                    year = yearEditTxt.toStringText().toInt(),
                    password = passwordEditTxt.toStringText()).observe(this, Observer {
                    progress?.cancel()

                    if(it.apiCallHasNoError(this)) {

                    }
                })
            }
        }
    }

    private fun fieldsHaveError(): Boolean {
        var error = false
        return error
    }


}
