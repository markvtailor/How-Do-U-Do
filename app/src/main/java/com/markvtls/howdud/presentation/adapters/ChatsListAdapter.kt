package com.markvtls.howdud.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.markvtls.howdud.databinding.FragmentChatsListItemBinding
import com.markvtls.howdud.domain.model.Chat


class ChatsListAdapter(
    private val toChat: (String) -> Unit
): ListAdapter<Chat, ChatsListAdapter.ViewHolder>(DiffCallback) {



    class ViewHolder(private val binding: FragmentChatsListItemBinding, private val toChat: (String) -> Unit): RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.apply {
                chatItem.setOnClickListener {
                    toChat(chat.id)
                }
                chatTitle.text = chat.title
                lastMessage.text = chat.lastMessage
                date.text = chat.date
                newMessages.text = if (chat.newMessages == 0) "" else chat.newMessages.toString()
                //chatIcon
            }
        }

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Chat>() {
            override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem.title == newItem.title && oldItem.lastMessage == newItem.lastMessage && oldItem.newMessages == newItem.newMessages
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentChatsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            toChat
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}