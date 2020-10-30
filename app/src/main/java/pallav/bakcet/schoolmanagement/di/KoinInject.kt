package pallav.bakcet.schoolmanagement.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pallav.bakcet.schoolmanagement.network.ApiClient
import pallav.bakcet.schoolmanagement.network.ApiInterface
import pallav.bakcet.schoolmanagement.views.viewmodel.LoginViewModel


val viewModelApp = module {
    single {
        ApiClient().getApi(get())!!.create(ApiInterface::class.java)
    }

    viewModel {LoginViewModel(get(),get())}


}