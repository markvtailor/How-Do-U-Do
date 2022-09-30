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
import androidx.lifecycle.lifecycleScope
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.markvtls.howdud.databinding.FragmentChatFromContactsBinding
import com.markvtls.howdud.presentation.ChatsListViewModel
import com.markvtls.howdud.presentation.adapters.ContactsListAdapter
import com.markvtls.howdud.utils.parseNumber
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFromContactsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFromContactsFragment : Fragment() {

    private var _binging: FragmentChatFromContactsBinding? = null
    private val binding get() = _binging!!
    private val viewModel: ChatsListViewModel by activityViewModels()

    private val loaderCallbacks: LoaderManager.LoaderCallbacks<Cursor> = object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            return CursorLoader(
                requireActivity(),
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
            )
        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            val contacts = mutableMapOf<String, String>()

            val nameColumnIndex = data?.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val numberColumnIndex = data?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)



            if (data != null && data.count != 0) {
                while (data.moveToNext()) {
                    val name = data.getString(nameColumnIndex!!)
                    val number = data.getString(numberColumnIndex!!)
                    if (number.length > 8) contacts[number.parseNumber()] = name
                }
            }

            viewModel.getUserFriends(contacts)
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
            Log.i("Contacts Loader", "Loader reset!")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentChatFromContactsBinding.inflate(inflater, container, false)
        loaderManager.initLoader(101, null, loaderCallbacks)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createList()
    }
    companion object {
        @SuppressLint("InlinedApi")
        private val PROJECTION: Array<out String> = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )


    }

    private fun createList() {
        val recyclerView = binding.contacts
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        val adapter = ContactsListAdapter {
            addFriendToUserChats(it)
        }
        recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.friends.collect {
                adapter.submitList(it)
            }

        }
    }

    private fun addFriendToUserChats(chatId: String) {
        val user = FirebaseAuth.getInstance().currentUser?.phoneNumber?.replaceFirst("+","")
        if (user != null) {
            viewModel.createNewChat(user, chatId)
        }
        val action = ChatFromContactsFragmentDirections.actionChatFromContactsFragmentToChatFragment(user+"_"+chatId)
        findNavController().navigate(action)
    }
}