package org.wit.myfooddiary.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.myfooddiary.databinding.ActivityMyfooddiaryBinding
import timber.log.Timber
import timber.log.Timber.i

class MyFoodDiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyfooddiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        Timber.i("Placemark Activity started..")
        binding = ActivityMyfooddiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAdd.setOnClickListener() {
            val placemarkTitle = binding.placemarkTitle.text.toString()
            if (placemarkTitle.isNotEmpty()) {
                i("add Button Pressed: $placemarkTitle")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }
}