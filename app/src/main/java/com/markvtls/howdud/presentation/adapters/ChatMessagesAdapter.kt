package com.markvtls.howdud.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.markvtls.howdud.R
import com.markvtls.howdud.databinding.FragmentChatMessageItemBinding
import com.markvtls.howdud.domain.model.Message
import com.markvtls.howdud.utils.parseDateTime

class ChatMessagesAdapter: ListAdapter<Message, ChatMessagesAdapter.ViewHolder>(DiffCallback) {



    class ViewHolder(private val binding: FragmentChatMessageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            val context = itemView.context
            binding.apply {
                text.text = message.text
                date.text = message.date.toDate().parseDateTime()

                messageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    if (message.author == FirebaseAuth.getInstance().currentUser?.phoneNumber.toString().replaceFirst("+","")) {
                        startToStart = binding.root.id
                        messageView.setCardBackgroundColor(context.resources.getColor(R.color.green))
                    } else {
                        endToEnd = binding.root.id
                        messageView.setCardBackgroundColor(context.resources.getColor(R.color.pale_yellow))
                    }

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