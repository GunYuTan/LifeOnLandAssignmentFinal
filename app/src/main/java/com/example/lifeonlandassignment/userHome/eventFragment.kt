package com.example.lifeonlandassignment.userHome

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.lifeonlandassignment.EventHappenFragment
import com.example.lifeonlandassignment.R


class EventFragment : Fragment() {

    private lateinit var upcomingEventsPager: ViewPager2
    private lateinit var currentEventsPager: ViewPager2
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var myImageView: ImageView

    private val upcomingEventsHandler = Handler()
    private val currentEventsHandler = Handler()

    private lateinit var upcomingEventsAdapter: EventsAdapter
    private lateinit var currentEventsAdapter: EventsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.event_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        upcomingEventsPager = view.findViewById(R.id.viewPager5)
        currentEventsPager = view.findViewById(R.id.viewPager6)
        myImageView = view.findViewById(R.id.myImageView)
        drawerLayout = view.findViewById(R.id.event_screen)

        // Drawer and ImageView setup
        myImageView.setOnClickListener {
            val newImageDrawable: Drawable = resources.getDrawable(R.drawable.dashboard, requireActivity().theme)
            myImageView.setImageDrawable(newImageDrawable)
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val button: ImageView = view.findViewById(R.id.btnUpcomingEvent)
        button.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, UpcomingEventFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val button1: ImageView = view.findViewById(R.id.btnEventHappen)
        button1.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, EventHappenFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        toggle = ActionBarDrawerToggle(
            requireActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize ViewPager2 Adapters
        initializeAdaptersAndPagers()

        // Auto-scroll setup
        startAutoScroll(upcomingEventsHandler, upcomingEventsPager, 2000)
        startAutoScroll(currentEventsHandler, currentEventsPager, 2000)
    }

    private fun initializeAdaptersAndPagers() {
        val upcomingEvents = arrayListOf(R.drawable.javan_rhino2, R.drawable.javan_rhino2, R.drawable.javan_rhino2)
        val currentEvents = arrayListOf(R.drawable.javan_rhino2, R.drawable.javan_rhino2, R.drawable.javan_rhino2)

        upcomingEventsAdapter = EventsAdapter(upcomingEvents)
        currentEventsAdapter = EventsAdapter(currentEvents)

        upcomingEventsPager.adapter = upcomingEventsAdapter
        currentEventsPager.adapter = currentEventsAdapter

        registerAutoScrollCallback(upcomingEventsHandler, upcomingEventsPager)
        registerAutoScrollCallback(currentEventsHandler, currentEventsPager)
    }

    private fun startAutoScroll(handler: Handler, pager: ViewPager2, delay: Long) {
        handler.postDelayed({ pager.currentItem = (pager.currentItem + 1) % upcomingEventsAdapter.itemCount }, delay)
    }

    private fun registerAutoScrollCallback(handler: Handler, pager: ViewPager2) {
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                handler.removeCallbacksAndMessages(null)
                startAutoScroll(handler, pager, 2000)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        upcomingEventsHandler.removeCallbacksAndMessages(null)
        currentEventsHandler.removeCallbacksAndMessages(null)
    }

    override fun onResume() {
        super.onResume()
        startAutoScroll(upcomingEventsHandler, upcomingEventsPager, 2000)
        startAutoScroll(currentEventsHandler, currentEventsPager, 2000)
    }

    inner class EventsAdapter(private val images: List<Int>) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val imageView: ImageView = view.findViewById(R.id.imageView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = images.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.imageView.setImageResource(images[position])
        }
    }
}