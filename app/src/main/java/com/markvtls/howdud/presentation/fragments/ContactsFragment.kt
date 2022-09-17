package com.markvtls.howdud.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.markvtls.howdud.data.dto.Friend
import com.markvtls.howdud.databinding.FragmentContactsBinding
import com.markvtls.howdud.presentation.ChatsListViewModel
import com.markvtls.howdud.presentation.adapters.ChatsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [ContactsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChatsListViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        binding.root
        return binding.root
    }




}