package com.example.lifeonlandassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class MainActivity: AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPager3: ViewPager2
//    private lateinit var viewPager4: ViewPager2
//    private lateinit var viewPager5: ViewPager2
//    private lateinit var viewPager6: ViewPager2
    private lateinit var viewPager7: ViewPager2
//    private lateinit var handler2: Handler
//    private lateinit var handler3: Handler
//    private lateinit var handler4: Handler
//    private lateinit var handler5: Handler
//    private lateinit var handler6: Handler
    private lateinit var handler7: Handler
//    private lateinit var adapter2: ImageAdapter2
//    private lateinit var adapter3: ImageAdapter3
//    private lateinit var adapter4: ImageAdapter4
//    private lateinit var adapter5: ImageAdapter5
//    private lateinit var adapter6: ImageAdapter6
    private lateinit var adapter7: ImageAdapter7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favourite_screen)

        init()
        setUpTransformer()

//        viewPager2.registerOnPageChangeCallback(getPageChangeCallback(handler2, runnable2))
//        viewPager3.registerOnPageChangeCallback(getPageChangeCallback(handler3, runnable3))
//        viewPager4.registerOnPageChangeCallback(getPageChangeCallback(handler4, runnable4))
//        viewPager5.registerOnPageChangeCallback(getPageChangeCallback(handler5, runnable5))
//        viewPager6.registerOnPageChangeCallback(getPageChangeCallback(handler6, runnable6))
        viewPager7.registerOnPageChangeCallback(getPageChangeCallback(handler7, runnable7))
    }
    override fun onPause() {
        super.onPause()
//        handler2.removeCallbacks(runnable2)
//        handler3.removeCallbacks(runnable3)
//        handler4.removeCallbacks(runnable4)
//        handler5.removeCallbacks(runnable5)
//        handler6.removeCallbacks(runnable6)
        handler7.removeCallbacks(runnable7)
    }
    override fun onResume() {
        super.onResume()
//        handler2.postDelayed(runnable2, 2000)
//        handler3.postDelayed(runnable3, 2000)
//        handler4.postDelayed(runnable4, 2000)
//        handler5.postDelayed(runnable5, 2000)
//        handler6.postDelayed(runnable6, 2000)
        handler7.postDelayed(runnable7, 2000)
    }

//    private val runnable2 = Runnable { viewPager2.currentItem = viewPager2.currentItem + 1 }
//    private val runnable3 = Runnable { viewPager3.currentItem = viewPager3.currentItem + 1 }
//    private val runnable4 = Runnable { viewPager4.currentItem = viewPager4.currentItem + 1 }
//    private val runnable5 = Runnable { viewPager5.currentItem = viewPager5.currentItem + 1 }
//    private val runnable6 = Runnable { viewPager6.currentItem = viewPager6.currentItem + 1 }
    private val runnable7 = Runnable { viewPager7.currentItem = viewPager7.currentItem + 1 }

    private fun getPageChangeCallback(handler: Handler, runnable: Runnable) = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 2000)
        }
    }
    private fun setUpTransformer() {
        // Apply the transformer to both view pagers
        val transformer = createTransformer()
        viewPager2.setPageTransformer(transformer)
        viewPager3.setPageTransformer(transformer)
//        viewPager4.setPageTransformer(transformer)
//        viewPager5.setPageTransformer(transformer)
//        viewPager6.setPageTransformer(transformer)
        viewPager7.setPageTransformer(transformer)
    }
    private fun createTransformer(): CompositePageTransformer {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        return transformer
    }
    private fun init() {
        viewPager2 = findViewById(R.id.viewPager2)
        viewPager3 = findViewById(R.id.viewPager3)
//        viewPager4 = findViewById(R.id.viewPager4)
//        viewPager5 = findViewById(R.id.viewPager5)
//        viewPager6 = findViewById(R.id.viewPager6)
        viewPager7 = findViewById(R.id.viewPager7)
//        handler2 = Handler(Looper.myLooper()!!)
//        handler3 = Handler(Looper.myLooper()!!)
//        handler4 = Handler(Looper.myLooper()!!)
//        handler5 = Handler(Looper.myLooper()!!)
//        handler6 = Handler(Looper.myLooper()!!)
        handler7 = Handler(Looper.myLooper()!!)

        // Add images as per your requirement
//        val imageList2 = ArrayList<Int>()
//        imageList2.add(R.drawable.javan_rhino)
//        imageList2.add(R.drawable.profile_picture)
//        imageList2.add(R.drawable.javan_rhino)
//        imageList2.add(R.drawable.profile_picture)
//        imageList2.add(R.drawable.javan_rhino)
//        imageList2.add(R.drawable.profile_picture)
//
//        val imageList3 = ArrayList<Int>()
//        imageList3.add(R.drawable.profile_picture)
//        imageList3.add(R.drawable.java_rhino)
//        imageList3.add(R.drawable.profile_picture)
//        imageList3.add(R.drawable.java_rhino)
//        imageList3.add(R.drawable.profile_picture)
//        imageList3.add(R.drawable.java_rhino)

//        val imageList4 = ArrayList<Int>()
//        imageList4.add(R.drawable.sunde_island)
//        imageList4.add(R.drawable.sunde_island)
//        imageList4.add(R.drawable.sunde_island)

//        val imageList5 = ArrayList<Int>()
//        imageList5.add(R.drawable.javan_rhino2)
//        imageList5.add(R.drawable.javan_rhino2)
//        imageList5.add(R.drawable.javan_rhino2)

//        val imageList6 = ArrayList<Int>()
//        imageList6.add(R.drawable.javan_rhino2)
//        imageList6.add(R.drawable.javan_rhino2)
//        imageList6.add(R.drawable.javan_rhino2)

        val imageList7 = ArrayList<Int>()
        imageList7.add(R.drawable.amur_leopard)
        imageList7.add(R.drawable.amur_leopard)
        imageList7.add(R.drawable.amur_leopard)
        imageList7.add(R.drawable.amur_leopard)

//        adapter2 = ImageAdapter2(imageList2, viewPager2)
//        adapter3 = ImageAdapter3(imageList3, viewPager3)
//        adapter4 = ImageAdapter4(imageList4, viewPager4)
//        adapter5 = ImageAdapter5(imageList5, viewPager5)
//        adapter6 = ImageAdapter5(imageList6, viewPager6)
        adapter7 = ImageAdapter7(imageList7, viewPager7)

//        viewPager2.adapter = adapter2
//        viewPager3.adapter = adapter3
//        viewPager4.adapter = adapter4
//        viewPager5.adapter = adapter5
//        viewPager6.adapter = adapter6
        viewPager7.adapter = adapter7
    }
}
