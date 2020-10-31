package pallav.bakcet.schoolmanagement.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel
import pallav.bakcet.schoolmanagement.R
import pallav.bakcet.schoolmanagement.pojo.mark_attendance.MarkAttendanceResponse
import pallav.bakcet.schoolmanagement.utils.apiCallHasNoError
import pallav.bakcet.schoolmanagement.utils.setStatusBarBackground
import pallav.bakcet.schoolmanagement.utils.showProgressDialog
import pallav.bakcet.schoolmanagement.views.viewmodel.AttendanceViewModel

class HomeActivity : AppCompatActivity() {

    private val attendanceViewModel: AttendanceViewModel by viewModel()
    private var progress: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setStatusBarBackground()
//        scanQR.setOnTouchListener { v, event ->
//            this.logout()
//            startActivity<LoginActivity>()
//            finishAffinity()
//            return@setOnTouchListener false
//        }
        clickListeners()
    }

    private fun clickListeners() {
        scanQR.setOnClickListener {
            IntentIntegrator(this).initiateScan()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
            } else {
                progress = showProgressDialog("Marking your attendance", cancellable = false)
                attendanceViewModel.mark_attendance(result.contents).observe(this, Observer {
                    progress?.cancel()
                    if (it.apiCallHasNoError(this)) {
                        val attendanceResponse = it.data as MarkAttendanceResponse
                        if (attendanceResponse.success) {
                            toast("Successfully marked")
                        }
                    }
                })
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
