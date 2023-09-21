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

class EventFragment : Fragment() {
    private lateinit var viewPager5: ViewPager2
    private lateinit var viewPager6: ViewPager2
    private val handler5 = Handler()
    private val handler6 = Handler()
    private lateinit var adapter5: ImageAdapter5
    private lateinit var adapter6: ImageAdapter6

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.event_screen, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager5 = view.findViewById(R.id.viewPager5)
        viewPager6 = view.findViewById(R.id.viewPager6)

        init()

        handler5.postDelayed(runnable5, 2000)
        handler6.postDelayed(runnable6, 2000)

    }

    override fun onPause() {
        super.onPause()
        handler5.removeCallbacks(runnable5)
        handler6.removeCallbacks(runnable6)
    }

    override fun onResume() {
        super.onResume()
        handler5.postDelayed(runnable5, 2000)
        handler6.postDelayed(runnable6, 2000)
    }

    private val runnable5 = Runnable { viewPager5.currentItem = (viewPager5.currentItem + 1) % adapter5.itemCount }
    private val runnable6 = Runnable { viewPager6.currentItem = (viewPager6.currentItem + 1) % adapter6.itemCount }

    private fun getPageChangeCallback(handler: Handler, runnable: Runnable) = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 2000)
        }
    }

    private fun init() {
        val imageList5 = ArrayList<Int>()
        imageList5.add(R.drawable.javan_rhino2)
        imageList5.add(R.drawable.javan_rhino2)
        imageList5.add(R.drawable.javan_rhino2)

        val imageList6 = ArrayList<Int>()
        imageList6.add(R.drawable.javan_rhino2)
        imageList6.add(R.drawable.javan_rhino2)
        imageList6.add(R.drawable.javan_rhino2)

        adapter5 = ImageAdapter5(imageList5)
        adapter6 = ImageAdapter6(imageList6)
        viewPager5.adapter = adapter5
        viewPager6.adapter = adapter6

        viewPager5.registerOnPageChangeCallback(getPageChangeCallback(handler5, runnable5))
        viewPager6.registerOnPageChangeCallback(getPageChangeCallback(handler6, runnable6))
    }

    inner class ImageAdapter5(private val imageList: ArrayList<Int>) : RecyclerView.Adapter<ImageAdapter5.ImageViewHolder>() {
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

    inner class ImageAdapter6(private val imageList: ArrayList<Int>) : RecyclerView.Adapter<ImageAdapter6.ImageViewHolder>() {
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