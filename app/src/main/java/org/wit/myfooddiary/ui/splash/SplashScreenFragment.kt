package org.wit.myfooddiary.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.*
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.HomeBinding
import org.wit.myfooddiary.ui.home.HomeActivity
import kotlin.coroutines.CoroutineContext

class SplashScreenFragment : Fragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        launch {
            delay(3000)
            withContext(Dispatchers.Main){
                (activity as HomeActivity)
            }
        }
    }
}
