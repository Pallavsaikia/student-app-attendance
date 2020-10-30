package pallav.bakcet.schoolmanagement

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import pallav.bakcet.schoolmanagement.di.viewModelApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if(GlobalContext.getOrNull()==null){
            startKoin {
                androidContext(applicationContext)
                modules(
                    listOf(
                        viewModelApp
                    )
                )
            }


        }
    }
}