package org.wit.myfooddiary.helpers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.ActivityHelperBinding
import org.wit.myfooddiary.ui.barcodescanner.BarcodeScannerFragment

class HelperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelperBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        addFragment()
    }

    private fun addFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.rootContainer, BarcodeScannerFragment())
            .commit()
    }


}
