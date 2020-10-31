package pallav.bakcet.schoolmanagement.views.viewmodel


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import pallav.bakcet.schoolmanagement.network.ApiInterface
import pallav.bakcet.schoolmanagement.network.ApiResponse
import pallav.bakcet.schoolmanagement.pojo.mark_attendance.MarkAttendanceResponse

class AttendanceViewModel(val context: Context, val apiService: ApiInterface) : ViewModel() {
    private val disposible = CompositeDisposable()

    fun mark_attendance(qrCode: String): LiveData<ApiResponse> {
        val mld = MutableLiveData<ApiResponse>()
        disposible.add(
            apiService.mark_attendance(qrCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MarkAttendanceResponse>() {
                    override fun onSuccess(t: MarkAttendanceResponse) {
                        mld.postValue(ApiResponse(t))
                    }

                    override fun onError(e: Throwable) {
                        mld.postValue(ApiResponse(e))
                    }
                })
        )
        return mld
    }
}