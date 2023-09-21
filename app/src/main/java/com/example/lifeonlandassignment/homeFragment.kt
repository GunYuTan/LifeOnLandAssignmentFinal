package com.example.lifeonlandassignment

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
import kotlin.math.abs

class HomeFragment : Fragment() {
    private lateinit var viewPager4: ViewPager2
    private val handler4 = Handler()
    private lateinit var adapter4: ImageAdapter4

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_screen, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager4 = view.findViewById(R.id.viewPager4)

        init()
        setUpTransformer()

        viewPager4.registerOnPageChangeCallback(getPageChangeCallback(handler4))

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
}