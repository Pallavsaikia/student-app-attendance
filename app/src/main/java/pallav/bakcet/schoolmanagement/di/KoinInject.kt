package pallav.bakcet.schoolmanagement.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pallav.bakcet.schoolmanagement.network.ApiClient
import pallav.bakcet.schoolmanagement.network.ApiInterface
import pallav.bakcet.schoolmanagement.views.viewmodel.AttendanceViewModel
import pallav.bakcet.schoolmanagement.views.viewmodel.LoginViewModel
import pallav.bakcet.schoolmanagement.views.viewmodel.RegisterViewModel


val viewModelApp = module {
    single {
        ApiClient().getApi(get())!!.create(ApiInterface::class.java)
    }

    viewModel {LoginViewModel(get(),get())}
    viewModel {RegisterViewModel(get(),get())}
    viewModel {AttendanceViewModel(get(),get())}


}