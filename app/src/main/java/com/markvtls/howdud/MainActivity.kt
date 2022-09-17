package com.markvtls.howdud

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.markvtls.howdud.databinding.ActivityMainBinding
import com.markvtls.howdud.presentation.ChatsListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    /** Navigation component controller */
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private val viewModel: ChatsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)
        requestPermissions(101)
        appBarConfiguration = AppBarConfiguration(navController.graph,binding.drawerLayout)
        binding.topAppBar.setupWithNavController(navController,appBarConfiguration)
        //NavigationUI.setupWithNavController(binding.navView,navController)
       // setupActionBarWithNavController(navController, appBarConfiguration)
        if (FirebaseAuth.getInstance().currentUser == null) signInLauncher.launch(signInIntent) else println(
            FirebaseAuth.getInstance().currentUser?.phoneNumber
        )


    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun permissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(applicationContext, it) == PackageManager.PERMISSION_GRANTED
    }


    private fun requestPermissions(requestCode: Int) {

        if (requestCode == REQUEST_CODE) {
            if (!permissionsGranted()) {
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS.toTypedArray(),
                    requestCode)
            }
        }


    }

    companion object {

        private val providers = arrayListOf(
           // AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
           // AuthUI.IdpConfig.GoogleBuilder().build(),
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()


        private const val REQUEST_CODE = 101
        private val REQUIRED_PERMISSIONS = mutableListOf(
            //Manifest.permission.READ_EXTERNAL_STORAGE,
            //Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.INTERNET
        )

    }


    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            // ...
            if (user != null) {
                println(user.phoneNumber)
                viewModel.saveNewUser(user.phoneNumber!!)
            }
            println("Success!")
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            println("Failure!")
        }
    }


}