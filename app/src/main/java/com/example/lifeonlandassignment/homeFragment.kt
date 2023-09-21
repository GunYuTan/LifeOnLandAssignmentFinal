package com.example.lifeonlandassignment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import kotlin.math.abs

class HomeFragment : Fragment() {
    private lateinit var viewPager4: ViewPager2
    private val handler4 = Handler()
    private lateinit var adapter4: ImageAdapter4
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var myImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_screen, container, false)

        viewPager4 = view.findViewById(R.id.viewPager4)
        drawerLayout = view.findViewById(R.id.home_screen)
        navigationView = view.findViewById(R.id.nav_view)
        myImageView = view.findViewById(R.id.myImageView)

        init()
        setUpTransformer()

        viewPager4.registerOnPageChangeCallback(getPageChangeCallback(handler4))

        myImageView.setOnClickListener {
            // Change ImageView
            val newImageDrawable: Drawable = resources.getDrawable(R.drawable.dashboard, requireActivity().theme)
            myImageView.setImageDrawable(newImageDrawable)

            // Open Drawer
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Initialize the ActionBarDrawerToggle instance
        toggle = ActionBarDrawerToggle(
            requireActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Show hamburger icon on ActionBar
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return view
    }

    override fun onPause() {
        super.onPause()
        handler4.removeCallbacks(runnable4)
    }

    override fun onResume() {
        super.onResume()
        handler4.postDelayed(runnable4, 2000)
    }

    private val runnable4 = Runnable { viewPager4.currentItem = (viewPager4.currentItem + 1) % adapter4.itemCount }

    private fun getPageChangeCallback(handler: Handler) = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            handler.removeCallbacks(runnable4)
            handler.postDelayed(runnable4, 2000)
        }
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager4.setPageTransformer(transformer)
    }

    private fun init() {
        val imageList4 = ArrayList<Int>()
        imageList4.add(R.drawable.sunde_island)
        imageList4.add(R.drawable.sunde_island)
        imageList4.add(R.drawable.sunde_island)

        adapter4 = ImageAdapter4(imageList4)
        viewPager4.adapter = adapter4
    }

    inner class ImageAdapter4(private val imageList: ArrayList<Int>) : RecyclerView.Adapter<ImageAdapter4.ImageViewHolder>() {
        inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.imageView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
            return ImageViewHolder(view)
        }

        override fun getItemCount(): Int = imageList.size

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            holder.imageView.setImageResource(imageList[position])
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}