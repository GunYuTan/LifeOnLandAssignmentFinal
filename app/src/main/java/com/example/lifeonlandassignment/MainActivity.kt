package com.example.lifeonlandassignment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.lifeonlandassignment.login.LoginFragment
import com.example.lifeonlandassignment.userHome.EventFragment
import com.example.lifeonlandassignment.userHome.FavouriteFragment
import com.example.lifeonlandassignment.userHome.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var myImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupNavigationDrawer()
        setupBottomNavigation()
        setupImageView()
        setupFragmentBackStackListener()
        setupInitialFragment(savedInstanceState)
    }

    private fun initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        myImageView = findViewById(R.id.myImageView)
    }

    private fun setupNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            val selectedFragment = when (menuItem.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_profile -> ProfileFragment()
                R.id.nav_notification -> NotificationFragment()
                R.id.nav_event -> EventFragment()
                R.id.nav_favourite -> FavouriteFragment()
                R.id.nav_logout -> LoginFragment()
                else -> null
            }
            syncBottomNavigationWithDrawer(selectedFragment) // Sync with BottomNavigationView
            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .addToBackStack(null)
                    .commit()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setupImageView() {
        myImageView.setOnClickListener {
            val newImageDrawable = resources.getDrawable(R.drawable.dashboard, theme)
            myImageView.setImageDrawable(newImageDrawable)
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun setupBottomNavigation() {
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(
                Color.parseColor("#FFA500"),
                Color.parseColor("#FFFFFFFF")
            )
        )
        bottomNavigationView.apply {
            itemIconTintList = colorStateList
            itemTextColor = colorStateList
            setOnItemSelectedListener { item ->
                val selectedFragment = when (item.itemId) {
                    R.id.btnToHomeScreen -> HomeFragment()
                    R.id.btnNotification -> NotificationFragment()
                    R.id.btnFavourite -> FavouriteFragment()
                    R.id.btnEvent -> EventFragment()
                    R.id.btnProfile -> ProfileFragment()
                    else -> null
                }
                selectedFragment?.let {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, it)
                        .commit()
                }
                true
            }
        }
    }

    private fun setupFragmentBackStackListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            val isTargetFragment = currentFragment?.javaClass?.simpleName in arrayOf(
                "HomeFragment",
                "NotificationFragment",
                "FavouriteFragment",
                "EventFragment",
                "ProfileFragment"
            )
            myImageView.visibility = if (isTargetFragment) View.VISIBLE else View.GONE
            bottomNavigationView.visibility = if (isTargetFragment) View.VISIBLE else View.GONE

            // Enable/Disable drawer swipe
            drawerLayout.setDrawerLockMode(
                if (isTargetFragment) DrawerLayout.LOCK_MODE_UNLOCKED
                else DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START
            )
        }
    }

    private fun setupInitialFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                setReorderingAllowed(true)
                add(R.id.fragment_container, LoginFragment())
                myImageView.visibility = View.GONE
                bottomNavigationView.visibility = View.GONE
                commit()
            }
        }
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun syncBottomNavigationWithDrawer(selectedFragment: Fragment?) {
        // Update the BottomNavigationView based on the fragment launched from Drawer
        selectedFragment?.let {
            val menuItemId = when (it) {
                is HomeFragment -> R.id.btnToHomeScreen
                is ProfileFragment -> R.id.btnProfile
                is NotificationFragment -> R.id.btnNotification
                is EventFragment -> R.id.btnEvent
                is FavouriteFragment -> R.id.btnFavourite
                else -> null
            }
            menuItemId?.let { id ->
                bottomNavigationView.selectedItemId = id
            }
        }
    }
}
