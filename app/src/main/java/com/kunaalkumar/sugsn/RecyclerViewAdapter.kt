package com.kunaalkumar.sugsn

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.sugsn.databinding.RecyclerViewItemBinding
import com.kunaalkumar.sugsn.util.ListItem

class RecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
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

    fun addItem(item: ListItem) {
        data.add(item)
        notifyItemInserted(data.size)
    }

    fun updateItem(item: ListItem) {
        val itemPos = data.indexOf(item)
        data.set(itemPos, item)
        notifyItemChanged(itemPos)
    }

    class ViewHolder(private val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val TAG: String = "ViewHolder"

        fun bind(data: ListItem) {
            binding.item = data
            if (data.rottenRating != null) {
                binding.rottenIc = when (data.rottenRating!!.substringBefore('%').toInt() >= 60) {
                    true -> ContextCompat.getDrawable(itemView.context, R.drawable.ic_rotten_fresh)
                    false -> ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_rotten_rotten
                    )
                }
            }

            binding.root.setOnClickListener {
                Toast.makeText(itemView.context, "Clicked on ${data.title}", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}