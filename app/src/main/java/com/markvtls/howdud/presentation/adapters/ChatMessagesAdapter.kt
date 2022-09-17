package com.markvtls.howdud.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.markvtls.howdud.databinding.FragmentChatMessageItemBinding
import com.markvtls.howdud.domain.model.Message

class ChatMessagesAdapter: ListAdapter<Message, ChatMessagesAdapter.ViewHolder>(DiffCallback) {



    class ViewHolder(private val binding: FragmentChatMessageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.apply {
                text.text = message.text
                date.text = message.date.toDate().toString()
                messageView.updateLayoutParams<ConstraintLayout.LayoutParams> {

                }
            }
        }

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.author == newItem.author && oldItem.date == newItem.date
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentChatMessageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}