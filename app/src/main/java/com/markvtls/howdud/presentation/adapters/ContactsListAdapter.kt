package com.markvtls.howdud.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.markvtls.howdud.data.dto.Friend
import com.markvtls.howdud.databinding.FragmentChatFromContactsBinding
import com.markvtls.howdud.databinding.FragmentChatsListItemBinding
import com.markvtls.howdud.domain.model.Chat

class ContactsListAdapter(
    private val toChatsList: (String) -> Unit
): ListAdapter<Friend, ContactsListAdapter.ViewHolder>(DiffCallback) {



    class ViewHolder(private val binding: FragmentChatsListItemBinding, private val toChatsList: (String) -> Unit ): RecyclerView.ViewHolder(binding.root) {
        fun bind(friend: Friend) {
            binding.apply {
                chatTitle.text = friend.name
                lastMessage.text = friend.id
                //chatIcon
                chatItem.setOnClickListener {
                    toChatsList(friend.id)
                }
            }
        }
    }


        companion object {
            private val DiffCallback = object : DiffUtil.ItemCallback<Friend>() {
                override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
                    return oldItem.id == newItem.id
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
                toChatsList
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(getItem(position))
        }
    }
