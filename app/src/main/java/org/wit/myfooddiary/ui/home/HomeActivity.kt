package org.wit.myfooddiary.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.HomeBinding
import org.wit.myfooddiary.databinding.NavHeaderBinding
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.ui.auth.LoggedInViewModel
import org.wit.myfooddiary.ui.auth.Login
import org.wit.myfooddiary.utils.customTransformation

class HomeActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var homeBinding : HomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var loggedInViewModel : LoggedInViewModel
    private lateinit var navHeaderBinding : NavHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeBinding = HomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        drawerLayout = homeBinding.drawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            toolbar.title = "${title}: ${user.email}"
        }else {
            toolbar.setTitle(R.string.app_name)
        }
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.myFoodDiaryFragment, R.id.myFoodListFragment, R.id.apiMyFoodListFragment
        ,R.id.foodLocation), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val navView = homeBinding.navView
        navView.setupWithNavController(navController)

    }

    public override fun onStart() {
        super.onStart()
        loggedInViewModel = ViewModelProvider(this).get(LoggedInViewModel::class.java)
        loggedInViewModel.liveFirebaseUser.observe(this, Observer { firebaseUser ->
            if (firebaseUser != null)
                updateNavHeader(loggedInViewModel.liveFirebaseUser.value!!)
        })

        loggedInViewModel.loggedOut.observe(this, Observer { loggedout ->
            if (loggedout) {
                startActivity(Intent(this, Login::class.java))
            }
        })
    }

    private fun updateNavHeader(currentUser: FirebaseUser) {
        val headerView = homeBinding.navView.getHeaderView(0)
        navHeaderBinding = NavHeaderBinding.bind(headerView)
        if(currentUser.photoUrl != null && currentUser.displayName != null) {
            navHeaderBinding.navHeaderName.text = currentUser.displayName
            Picasso.get().load(currentUser.photoUrl)
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .into(navHeaderBinding.navHeaderImage)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun signOut(item: MenuItem) {
        loggedInViewModel.logOut()
        //Launch Login activity and clear the back stack to stop navigating back to the Home activity
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


}