package org.wit.myfooddiary.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.ActivityLandingPageBinding
import org.wit.myfooddiary.databinding.ActivityMyfooddiaryBinding
import org.wit.myfooddiary.helpers.showImagePicker
import org.wit.myfooddiary.main.MainApp
import org.wit.myfooddiary.models.UserModel

class LandingPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingPageBinding
    var user = UserModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)
        var signedUp = false
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("user_signup")) {
            signedUp = true

//            // Get the Intent that started this activity and extract the string
//            val message = intent.extras?.getParcelable("user_signup")!!
//
//            // Capture the layout's TextView and set the string as its text
//            val textView = findViewById<TextView>(R.id.textView).apply {
//                text = message
//            }
            user = intent.extras?.getParcelable("user_signup")!!
            binding.welcomeMessage.setText("Welcome " + user.firstName)
        }

        binding.button1.setOnClickListener {
        }

        binding.button2.setOnClickListener {
        }

        binding.button3.setOnClickListener {
        }

        binding.button4.setOnClickListener {
        }



    }
}