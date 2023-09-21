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

class FavouriteFragment : Fragment() {
    private lateinit var viewPager7: ViewPager2
    private val handler7 = Handler()
    private lateinit var adapter7: ImageAdapter7

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.favourite_screen, container, false)
        // Your initialization code here
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager7 = view.findViewById(R.id.viewPager7)

        init()
        setUpTransformer()

        viewPager7.registerOnPageChangeCallback(getPageChangeCallback(handler7))
    }

    override fun onPause() {
        super.onPause()
        handler7.removeCallbacks(runnable7)
    }

    override fun onResume() {
        super.onResume()
        handler7.postDelayed(runnable7, 2000)
    }

    private val runnable7 = Runnable { viewPager7.currentItem = (viewPager7.currentItem + 1) % adapter7.itemCount }

    private fun getPageChangeCallback(handler: Handler) = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            handler.removeCallbacks(runnable7)
            handler.postDelayed(runnable7, 2000)
        }
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager7.setPageTransformer(transformer)
    }

    private fun init() {
        val imageList7 = ArrayList<Int>()
        imageList7.add(R.drawable.amur_leopard)
        imageList7.add(R.drawable.amur_leopard)
        imageList7.add(R.drawable.amur_leopard)
        imageList7.add(R.drawable.amur_leopard)

        adapter7 = ImageAdapter7(imageList7)
        viewPager7.adapter = adapter7
    }

    inner class ImageAdapter7(private val imageList: ArrayList<Int>) : RecyclerView.Adapter<ImageAdapter7.ImageViewHolder>() {
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