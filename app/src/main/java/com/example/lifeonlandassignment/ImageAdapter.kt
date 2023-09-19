package com.example.lifeonlandassignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

// ImageAdapter2 and viewPager2 with ViewPager2
//class ImageAdapter2(private val imageList : ArrayList<Int>,
//                    private val viewPager2: ViewPager2)
//    : RecyclerView.Adapter<ImageAdapter2.ImageViewHolder>(){
//
//    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//        val imageView : ImageView = itemView.findViewById(R.id.imageView);
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
//        return ImageViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return imageList.size
//    }
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        holder.imageView.setImageResource(imageList[position])
//        if(position == imageList.size - 1){
//            viewPager2.post(runnable)
//        }
//    }
//
//    private val runnable = Runnable {
//        imageList.addAll(imageList)
//        notifyDataSetChanged()
//    }
//}

// ImageAdapter3 and viewPager3 with ViewPager2
//class ImageAdapter3(private val imageList: ArrayList<Int>,
//                    private val viewPager3: ViewPager2)
//    : RecyclerView.Adapter<ImageAdapter3.ImageViewHolder>() {
//    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//        val imageView : ImageView = itemView.findViewById(R.id.imageView);
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
//        return ImageViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return imageList.size
//    }
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        holder.imageView.setImageResource(imageList[position])
//        if(position == imageList.size - 1){
//            viewPager3.post(runnable)
//        }
//    }
//
//    private val runnable = Runnable {
//        imageList.addAll(imageList)
//        notifyDataSetChanged()
//    }
//}

// ImageAdapter4 and viewPager4 with ViewPager2
class ImageAdapter4(private val imageList: ArrayList<Int>,
                    private val viewPager4: ViewPager2)
    : RecyclerView.Adapter<ImageAdapter4.ImageViewHolder>() {
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView : ImageView = itemView.findViewById(R.id.imageView);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
        if(position == imageList.size - 1){
            viewPager4.post(runnable)
        }
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}

// ImageAdapter5 and viewPager5 with ViewPager2
class ImageAdapter5(private val imageList: ArrayList<Int>,
                    private val viewPager5: ViewPager2)
    : RecyclerView.Adapter<ImageAdapter5.ImageViewHolder>() {
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView : ImageView = itemView.findViewById(R.id.imageView);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
        if(position == imageList.size - 1){
            viewPager5.post(runnable)
        }
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}

// ImageAdapter6 and viewPager6 with ViewPager2
class ImageAdapter6(private val imageList: ArrayList<Int>,
                    private val viewPager6: ViewPager2)
    : RecyclerView.Adapter<ImageAdapter6.ImageViewHolder>() {
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView : ImageView = itemView.findViewById(R.id.imageView);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
        if(position == imageList.size - 1){
            viewPager6.post(runnable)
        }
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}

// ImageAdapter7 and viewPager7 with ViewPager2
class ImageAdapter7(private val imageList: ArrayList<Int>,
                    private val viewPager7: ViewPager2)
    : RecyclerView.Adapter<ImageAdapter7.ImageViewHolder>() {
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView : ImageView = itemView.findViewById(R.id.imageView);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
        if(position == imageList.size - 1){
            viewPager7.post(runnable)
        }
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}