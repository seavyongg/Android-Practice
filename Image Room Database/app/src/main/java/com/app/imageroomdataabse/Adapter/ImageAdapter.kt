package com.app.imageroomdataabse.Adapter

import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.imageroomdataabse.Model.Images
import com.app.imageroomdataabse.Util.ConvertImage
import com.app.imageroomdataabse.View.MainActivity
import com.app.imageroomdataabse.View.UpdateImageActivity
import com.app.imageroomdataabse.databinding.ImageItemBinding

class ImageAdapter(val activity: MainActivity) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
   private var imageList = emptyList<Images>()
    fun setImageList(imageList: List<Images>){
        this.imageList = imageList
        notifyDataSetChanged()
    }
    class ImageViewHolder( val itemBinding: ImageItemBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = imageList[position]
        with(holder){
            itemBinding.txtTitle.text = image.imageTitle
            itemBinding.txtDescription.text = image.imageDescription
            val imageAsBitmap = ConvertImage.convertToBitmap(image.imageString)
            itemBinding.imgImage.setImageBitmap(imageAsBitmap)
            itemBinding.cardView.setOnClickListener{
                val intent = Intent(activity, UpdateImageActivity::class.java)
                intent.putExtra("imageId", image.imageId)
                activity.startActivity(intent)
            }
        }
    }
    fun returnItemAtGivenPosition(position: Int): Images {
        return imageList[position]
    }

}
//class ImageAdapter(val activity: MainActivity) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
//    private var imageList = emptyList<Images>()
//    fun setImageList(imageList: List<Images>) {
//        this.imageList = imageList
//        notifyDataSetChanged()
//    }
//    class ImageViewHolder(val itemBinding: ImageItemBinding) : RecyclerView.ViewHolder(itemBinding.root)
//
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ImageAdapter.ImageViewHolder {
//       val view = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ImageViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ImageAdapter.ImageViewHolder, position: Int) {
//        val image = imageList[position]
//        with(holder){
//            itemBinding.txtTitle.text = image.imageTitle
//            itemBinding.txtDescription.text = image.imageDescription
//            val imageAsBitmap = ConvertImage.convertToBitmap(image.imageString)
//            itemBinding.imgImage.setImageBitmap(imageAsBitmap)
//            itemBinding.cardView.setOnClickListener{
//                val intent = Intent(activity, UpdateImageActivity::class.java)
//                intent.putExtra("imageId", image.imageId)
//                activity.startActivity(intent)
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return imageList.size
//    }
//    fun returnItemAtGivenPosition(position: Int): Images {
//        return imageList[position]
//    }
//
//
//}