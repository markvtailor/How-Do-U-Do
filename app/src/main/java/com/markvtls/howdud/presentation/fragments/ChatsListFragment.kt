package com.markvtls.howdud.presentation.fragments

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.markvtls.howdud.databinding.FragmentChatsListBinding
import com.markvtls.howdud.domain.model.Chat
import com.markvtls.howdud.presentation.ChatsListViewModel
import com.markvtls.howdud.presentation.adapters.ChatsListAdapter
import com.markvtls.howdud.utils.parseNumber
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch





/**
 * A simple [Fragment] subclass.
 * Use the [ChatsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ChatsListFragment : Fragment() {
    private var _binding: FragmentChatsListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChatsListViewModel by activityViewModels()

    private var currentUser = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        currentUser = FirebaseAuth.getInstance().currentUser?.phoneNumber.toString().replaceFirst("+","")
        _binding = FragmentChatsListBinding.inflate(inflater, container, false)
        viewModel.getUserChats(currentUser)
        viewModel.getChatsPreview(currentUser)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addNewChat.setOnClickListener {
            val action = ChatsListFragmentDirections.actionChatsListFragmentToChatFromContactsFragment()
            findNavController().navigate(action)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.chats.collect {
                    loadChats(it)
                }
            }
        }

        //viewModel.getUserChats()
    }

    private fun loadChats(chatsList: List<Chat>) {
        val chats = binding.chats
        chats.layoutManager = LinearLayoutManager(this.context)
        val adapter = ChatsListAdapter() {
            toChat(it)
        }
        chats.adapter = adapter
        adapter.submitList(chatsList)
    }

    private fun toChat(chatId: String) {
        val action = ChatsListFragmentDirections.actionChatsListFragmentToChatFragment(chatId)
        findNavController().navigate(action)
    }
}