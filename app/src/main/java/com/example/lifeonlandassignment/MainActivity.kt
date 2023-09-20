package com.example.lifeonlandassignment

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(
                Color.parseColor("#FFA500"), // Active color
                Color.parseColor("#FFFFFFFF")  // Inactive color
            )
        )

        bottomNavigationView.itemIconTintList = colorStateList
        bottomNavigationView.itemTextColor = colorStateList
        // End of color setup

        bottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.btnToHomeScreen -> selectedFragment = HomeFragment()
//                R.id.btnNotification -> selectedFragment = NotificationFragment()
                R.id.btnFavourite -> selectedFragment = FavouriteFragment()
                R.id.btnEvent -> selectedFragment = EventFragment()
//                R.id.btnSearch -> selectedFragment = SearchFragment()
                R.id.btnProfile -> selectedFragment = ProfileFragment()
            }

            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
            }

            true
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                setReorderingAllowed(true)
                replace(R.id.fragment_container, HomeFragment())
                commit()
            }
        }
    }
}

