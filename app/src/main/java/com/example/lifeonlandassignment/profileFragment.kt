package com.example.lifeonlandassignment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import android.graphics.drawable.Drawable
import android.widget.Button
import androidx.drawerlayout.widget.DrawerLayout

import kotlin.math.abs

class ProfileFragment : Fragment() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPager3: ViewPager2
    private val handler = Handler()
    private lateinit var adapter: ImageAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var myImageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager2 = view.findViewById(R.id.viewPager2)
        viewPager3 = view.findViewById(R.id.viewPager3)
        drawerLayout = view.findViewById(R.id.profile_screen)
        myImageView = view.findViewById(R.id.myImageView)

        init()
        setUpTransformer()

        val runnable = getRunnable(viewPager2, adapter)
        handler.postDelayed(runnable, 2000)

        val runnable3 = getRunnable(viewPager3, adapter)
        handler.postDelayed(runnable3, 2000)

        viewPager2.registerOnPageChangeCallback(getPageChangeCallback(handler, runnable))
        viewPager3.registerOnPageChangeCallback(getPageChangeCallback(handler, runnable3))

        // Drawer and ImageView setup
        myImageView.setOnClickListener {
            val newImageDrawable: Drawable = resources.getDrawable(R.drawable.dashboard, requireActivity().theme)
            myImageView.setImageDrawable(newImageDrawable)
            drawerLayout.openDrawer(GravityCompat.START)
        }

        toggle = ActionBarDrawerToggle(
            requireActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getRunnable(viewPager: ViewPager2, adapter: ImageAdapter) = Runnable {
        viewPager.currentItem = (viewPager.currentItem + 1) % adapter.itemCount
    }

    private fun getPageChangeCallback(handler: Handler, runnable: Runnable) = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 2000)
        }
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.14f
            }
        }
        viewPager2.setPageTransformer(transformer)
        viewPager3.setPageTransformer(transformer)
    }

    private fun init() {
        val imageList = arrayListOf(R.drawable.javan_rhino, R.drawable.profile_picture)
        adapter = ImageAdapter(imageList)
        viewPager2.adapter = adapter
        viewPager3.adapter = adapter
    }

    inner class ImageAdapter(private val imageList: List<Int>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

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
}
