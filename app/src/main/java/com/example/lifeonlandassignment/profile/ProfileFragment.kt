package com.example.lifeonlandassignment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.lifeonlandassignment.login.LoginFragment
import kotlin.math.abs

class ProfileFragment : Fragment() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPager3: ViewPager2
    private val handler2 = Handler()
    private val handler3 = Handler()
    private lateinit var adapter2: ImageAdapter2
    private lateinit var adapter3: ImageAdapter3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_screen, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager2 = view.findViewById(R.id.viewPager2)
        viewPager3 = view.findViewById(R.id.viewPager3)

        init()
        setUpTransformer()

        handler2.postDelayed(runnable2, 2000)
        handler3.postDelayed(runnable3, 2000)

        viewPager2.registerOnPageChangeCallback(getPageChangeCallback(handler2, runnable2))
        viewPager3.registerOnPageChangeCallback(getPageChangeCallback(handler3, runnable3))
    }

    override fun onPause() {
        super.onPause()
        handler2.removeCallbacks(runnable2)
        handler3.removeCallbacks(runnable3)
    }

    override fun onResume() {
        super.onResume()
        handler2.postDelayed(runnable2, 2000)
        handler3.postDelayed(runnable3, 2000)
    }

    private val runnable2 = Runnable { viewPager2.currentItem = (viewPager2.currentItem + 1) % adapter2.itemCount }
    private val runnable3 = Runnable { viewPager3.currentItem = (viewPager3.currentItem + 1) % adapter3.itemCount }

    private fun getPageChangeCallback(handler: Handler, runnable: Runnable) = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 2000)
        }
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager2.setPageTransformer(transformer)
        viewPager3.setPageTransformer(transformer)
    }

    private fun init() {
        val imageList2 = ArrayList<Int>()
        imageList2.add(R.drawable.javan_rhino)
        imageList2.add(R.drawable.profile_picture)
        imageList2.add(R.drawable.javan_rhino)
        imageList2.add(R.drawable.profile_picture)
        imageList2.add(R.drawable.javan_rhino)
        imageList2.add(R.drawable.profile_picture)

        val imageList3 = ArrayList<Int>()
        imageList3.add(R.drawable.profile_picture)
        imageList3.add(R.drawable.javan_rhino)
        imageList3.add(R.drawable.profile_picture)
        imageList3.add(R.drawable.javan_rhino)
        imageList3.add(R.drawable.profile_picture)
        imageList3.add(R.drawable.javan_rhino)

        adapter2 = ImageAdapter2(imageList2)
        adapter3 = ImageAdapter3(imageList3)
        viewPager2.adapter = adapter2
        viewPager3.adapter = adapter3
    }

    inner class ImageAdapter2(private val imageList: ArrayList<Int>) : RecyclerView.Adapter<ImageAdapter2.ImageViewHolder>() {
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

    inner class ImageAdapter3(private val imageList: ArrayList<Int>) : RecyclerView.Adapter<ImageAdapter3.ImageViewHolder>() {
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