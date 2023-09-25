package com.example.lifeonlandassignment

import android.content.pm.ActivityInfo
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.lifeonlandassignment.login.LoginFragment
import com.example.lifeonlandassignment.userHome.EventFragment
import com.example.lifeonlandassignment.userHome.FavouriteFragment
import com.example.lifeonlandassignment.userHome.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            val isTargetFragment = currentFragment?.javaClass?.simpleName in arrayOf(
                "HomeFragment",
                "NotificationFragment",
                "FavouriteFragment",
                "EventFragment",
                "ProfileFragment"
            )
            bottomNavigationView.visibility = if (isTargetFragment) View.VISIBLE else View.GONE

            requestedOrientation = if (isTargetFragment) {
                ActivityInfo.SCREEN_ORIENTATION_SENSOR
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.btnToHomeScreen -> selectedFragment = HomeFragment()
                R.id.btnNotification -> selectedFragment = NotificationFragment()
                R.id.btnFavourite -> selectedFragment = FavouriteFragment()
                R.id.btnEvent -> selectedFragment = EventFragment()
                R.id.btnProfile -> selectedFragment = ProfileFragment()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit()
            }
            true
        }

        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(
                Color.parseColor("#FFA500"),  // Active color
                Color.parseColor("#FFFFFFFF") // Inactive color
            )
        )

        bottomNavigationView.itemIconTintList = colorStateList
        bottomNavigationView.itemTextColor = colorStateList

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                setReorderingAllowed(true)
                add(R.id.fragment_container, LoginFragment())
                bottomNavigationView.visibility = View.GONE
                commit()
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        val isTargetFragment = currentFragment?.javaClass?.simpleName in arrayOf(
            "HomeFragment",
            "NotificationFragment",
            "FavouriteFragment",
            "EventFragment",
            "ProfileFragment"
        )

        bottomNavigationView.visibility = if (isTargetFragment) View.VISIBLE else View.GONE
    }
}



