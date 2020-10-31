package pallav.bakcet.schoolmanagement.views.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import pallav.bakcet.schoolmanagement.network.ApiInterface
import pallav.bakcet.schoolmanagement.network.ApiResponse
import pallav.bakcet.schoolmanagement.pojo.register.RegisterResponse
import pallav.bakcet.schoolmanagement.utils.GlobalPref
import pallav.bakcet.schoolmanagement.utils.token

class RegisterViewModel(val context: Context, val apiService: ApiInterface) : ViewModel() {
    private val disposible = CompositeDisposable()

    fun register(
        username: String,
        email: String,
        year: Int,
        password: String
    ): LiveData<ApiResponse> {
        val mld = MutableLiveData<ApiResponse>()
        disposible.add(
            apiService.register(username, email, year, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RegisterResponse>() {
                    override fun onSuccess(t: RegisterResponse) {
                        Log.d("addd",t.toString())
                        if (t.success) {

                            context.token(t.data!!.token)
                            val globalPref=GlobalPref(context)
                            globalPref.loggedIn(true)
                            globalPref.username(t.data.username)
                            globalPref.firstname(t.data.first_name)
                            globalPref.lastname(t.data.last_name)
                            globalPref.email(t.data.email)
                        }
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