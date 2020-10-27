package com.harsh.tpf.ui.main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.harsh.tpf.databinding.ItemLayoutBinding
import com.harsh.tpf.model.User
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.*


class MainAdapter(private val users: ArrayList<User>) :
    RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    var onItemClicked: (User) -> Unit = {}

    class DataViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, onItemClicked: (User) -> Unit) {
            itemView.container.setBackgroundColor(
                Color.argb(
                    150,
                    Random().nextInt(256),
                    Random().nextInt(256),
                    Random().nextInt(256)
                )
            )
            itemView.tvTitle.text = user.name
            itemView.ivCheck.isVisible = user.isSelected
            itemView.setOnClickListener {
                user.isSelected = !user.isSelected
                itemView.ivCheck.isVisible = user.isSelected
                onItemClicked(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position], onItemClicked)

    fun addData(list: List<User>) {
        users.clear()
        users.addAll(list)
        notifyDataSetChanged()
    }
}