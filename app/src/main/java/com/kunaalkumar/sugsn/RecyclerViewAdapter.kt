package com.kunaalkumar.sugsn

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.sugsn.databinding.RecyclerViewItemBinding
import com.kunaalkumar.sugsn.util.ListItem
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class RecyclerViewAdapter :
    RecyclerView.Adapter<ViewHolder>(), ViewHolder.OnItemClickListener {
    override fun onItemClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        lateinit var bottomNavTransition: MotionLayout
    }

    private val data = ArrayList<ListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RecyclerViewItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycler_view_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.itemView.item_image.setImageDrawable(null)
    }

    fun addItem(item: ListItem) {
        data.add(item)
        notifyItemInserted(data.size)
    }

    fun updateItem(item: ListItem) {
        val itemPos = data.indexOf(item)
        data.set(itemPos, item)
        notifyItemChanged(itemPos)
    }
}

class ViewHolder(private val binding: RecyclerViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val TAG: String = "ViewHolder"

    fun bind(data: ListItem) {

        binding.item = data
        if (data.rottenRating != null) {
            if (data.rottenRating != "Not Found") { // Set appropriate rotten drawable based on rating
                binding.rottenIc =
                    when (data.rottenRating!!.substringBefore('%').toInt() >= 60) {
                        true -> ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_rotten_fresh
                        )
                        false -> ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_rotten_rotten
                        )
                    }
            } else if (data.rottenRating == "Not Found") { // hide if nothing to show
                itemView.rotten_icon.visibility = View.GONE
                itemView.rotten_rating.visibility = View.GONE
            }
        }

        if (data.imdbRating != null) { // Set imdb icon only if a rating was found
            binding.imdbIc = ContextCompat.getDrawable(itemView.context, R.drawable.ic_imdb)
        } else { // Hide if nothing to show
            itemView.imdb_icon.visibility = View.GONE
            itemView.imdb_rating.visibility = View.GONE
        }

        // Hide divider if only one rating to show
        if (itemView.rotten_rating.visibility == View.GONE || itemView.imdb_rating.visibility == View.GONE) {
            itemView.rating_divider.visibility = View.GONE
        }

        binding.root.setOnClickListener {
            Toast.makeText(itemView.context, "Clicked on ${data.title}", Toast.LENGTH_LONG)
                .show()
        }
    }

    interface OnItemClickListener{
        fun onItemClick()
    }


}

