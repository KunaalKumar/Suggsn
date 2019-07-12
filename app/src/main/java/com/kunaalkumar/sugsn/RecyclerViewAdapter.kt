package com.kunaalkumar.sugsn

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunaalkumar.sugsn.databinding.RecyclerViewItemBinding
import com.kunaalkumar.sugsn.util.ListItem

class RecyclerViewAdapter(private val context: Context) :
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
        holder.bind(context, data[position])
    }

    fun addItem(item: ListItem) {
        data.add(item)
        notifyItemInserted(data.size)
    }

    class ViewHolder(private val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val TAG: String = "ViewHolder"

        fun bind(context: Context, data: ListItem) {
            binding.item = data

            binding.root.setOnClickListener {
                Toast.makeText(context, "Clicked on ${data.title}", Toast.LENGTH_LONG).show()
            }
        }
    }
}