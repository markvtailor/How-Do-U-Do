package com.markvtls.howdud.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.markvtls.howdud.databinding.FragmentChatBinding
import com.markvtls.howdud.domain.model.Message
import com.markvtls.howdud.presentation.MessengerViewModel
import com.markvtls.howdud.presentation.adapters.ChatMessagesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MessengerViewModel by viewModels()
    private var currentUser = ""
    private val navArgs: ChatFragmentArgs by navArgs()
    //

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        currentUser = FirebaseAuth.getInstance().currentUser?.phoneNumber?.replaceFirst("+","").toString()
        viewModel.loadMessages(navArgs.chatId)
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSend.setOnClickListener {
            sendMessage()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.messages.collect {
                loadMessages(it)
            }
        }

    }

    private fun loadMessages(messagesList: List<Message?>) {
        val messages = binding.messages
        messages.layoutManager = LinearLayoutManager(this.context)
        val adapter = ChatMessagesAdapter()
        messages.adapter = adapter
        adapter.submitList(messagesList)
    }

    private fun sendMessage() {

        if (!binding.messageInputField.text.isNullOrBlank()) {
            val messageText = binding.messageInputField.text.toString()
            val message = Message(currentUser, Timestamp.now(),true, messageText)
            val chatId = navArgs.chatId
            viewModel.sendMessage(chatId, message)
        }

    }
}