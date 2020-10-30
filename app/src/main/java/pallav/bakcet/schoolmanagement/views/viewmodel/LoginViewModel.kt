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
import pallav.bakcet.schoolmanagement.pojo.LoginResponse
import pallav.bakcet.schoolmanagement.utils.GlobalPref
import pallav.bakcet.schoolmanagement.utils.token

class LoginViewModel(val context: Context, val apiService: ApiInterface) : ViewModel() {
    private val disposible = CompositeDisposable()

    fun login(username: String, password: String): LiveData<ApiResponse> {
        val mld = MutableLiveData<ApiResponse>()
        disposible.add(
            apiService.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<LoginResponse>() {
                    override fun onSuccess(t: LoginResponse) {
                        if (t.success) {
                            context.token(t.data!!.token)
                            GlobalPref(context).loggedIn(true)
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