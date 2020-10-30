package pallav.bakcet.schoolmanagement.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_error.*
import pallav.bakcet.schoolmanagement.R

class ErrorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)

        val error=intent.getStringExtra("error")
        if(intent.getBooleanExtra("isNetWorkError",false)){
            errorType.text=getText(R.string.no_internet_connection)
        }else{
            errorType.text=error
        }
    }
}
