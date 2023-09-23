package com.example.lifeonlandassignment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.lifeonlandassignment.database.AssignmentDatabase
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.databinding.ProfileScreenBinding
import com.example.lifeonlandassignment.user.ProfileViewModel
import com.example.lifeonlandassignment.user.ProfileViewModelFactory
import com.example.lifeonlandassignment.user.UpdateProfileFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.abs

class ProfileFragment : Fragment() {
    private val requestCodeSelectImage = 100
    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: ProfileScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

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
        _binding = DataBindingUtil.inflate(inflater, R.layout.profile_screen, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = ProfileViewModelFactory(repository, application)
        profileViewModel = ViewModelProvider(this,factory).get(ProfileViewModel::class.java)
        binding.profileViewModel = profileViewModel
        binding.lifecycleOwner = this

        profileViewModel.getUserImagePath(Global.loginUser)
        profileViewModel.messageLiveData.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                //imagePath = message
                Glide.with(requireContext())
                    .load(message)
                    .into(binding.profilePic)
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager2 = view.findViewById(R.id.viewPager2)
        viewPager3 = view.findViewById(R.id.viewPager3)

        init()
        setUpTransformer()

        val button: Button = view.findViewById(R.id.btnUpdateProfileScreen)
        button.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, UpdateProfileFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        handler2.postDelayed(runnable2, 2000)
        handler3.postDelayed(runnable3, 2000)

        viewPager2.registerOnPageChangeCallback(getPageChangeCallback(handler2, runnable2))
        viewPager3.registerOnPageChangeCallback(getPageChangeCallback(handler3, runnable3))

        binding.btnChangePicture.setOnClickListener {
            Log.i("Testing", "Change Picture Button Pressed")
            selectProfilePicture()
        }

        binding.txtProfileUsername.text = runBlocking { profileViewModel.getUser(Global.loginUser)?.username }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            Log.i("Testing", "onActivityResult1")
            if (requestCode == requestCodeSelectImage && resultCode == Activity.RESULT_OK && data != null) {
                val selectedImageUri = data?.data
                selectedImageUri?.let { uri ->
                    uiScope.launch {
                        // Pass the selected image URI to the ViewModel
                        Log.i("Testing", "onActivityResult2")
                        profileViewModel.updateProfileImage(uri)

                        Glide.with(requireContext())
                            .load(uri)
                            .placeholder(R.drawable.profile_picture)
                            .into(binding.profilePic)
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }catch (e: Exception){
            Log.e("Error", "Error message", e)
        }

    }

    private fun selectProfilePicture() {
        try {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            Log.i("Testing", "selectProfile1")
            intent.type = "image/*"
            Log.i("Testing", "selectProfile2")
            startActivityForResult(intent, requestCodeSelectImage)
            Log.i("Testing", "selectProfile3")
        }catch (e: Exception){
            Log.e("Error", "Error message", e)
        }

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