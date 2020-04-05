package com.reihanalavi.jetpackdogs.views

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reihanalavi.jetpackdogs.R
import com.reihanalavi.jetpackdogs.models.Dog
import com.reihanalavi.jetpackdogs.utils.getProgressDrawable
import com.reihanalavi.jetpackdogs.utils.loadImage
import kotlinx.android.synthetic.main.items_dog.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class DogAdapter(private val dogs: ArrayList<Dog>, private val listener: (Dog) -> Unit) :
    RecyclerView.Adapter<DogAdapter.ViewHolder>(){

    fun updateAllDogs(data: List<Dog>) {
        dogs.clear()
        dogs.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindData(data: Dog, listener: (Dog) -> Unit) {
            itemView.items_textView.text = "${data.dogBreed} | ${data.lifeSpan}"
            itemView.items_imageView.loadImage(data.imageUrl, getProgressDrawable(itemView.context))

            itemView.onClick {
                listener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val views = LayoutInflater.from(parent.context).inflate(R.layout.items_dog, parent, false)
        return ViewHolder(views)
    }

    override fun getItemCount(): Int = dogs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(dogs[position], listener)
    }
}