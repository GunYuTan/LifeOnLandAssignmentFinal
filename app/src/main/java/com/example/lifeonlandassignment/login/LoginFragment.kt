package com.example.lifeonlandassignment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lifeonlandassignment.userHome.HomeFragment
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.database.AssignmentDatabase
import com.example.lifeonlandassignment.database.AssignmentDatabaseRepository
import com.example.lifeonlandassignment.databinding.LoginScreenBinding
import com.example.lifeonlandassignment.register.RegisterFragment

class LoginFragment : Fragment(){
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: LoginScreenBinding = DataBindingUtil.inflate(inflater, R.layout.login_screen,container,false)
        val application = requireNotNull(this.activity).application
        val dataSource = AssignmentDatabase.getInstance(application).assignmentDatabaseDao
        val repository = AssignmentDatabaseRepository(dataSource)
        val factory = LoginViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this,factory).get(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        val button: Button = binding.btnRegisterScreen
        button.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, RegisterFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val button1: Button = binding.btnToAdminLogin
        button1.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, AdminLoginFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        loginViewModel.messageLiveData.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                // Use the context of the fragment to show the Toast
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })

        loginViewModel.navigatetoUserDetails.observe(this, Observer { hasFinished->
            if (hasFinished == true){
                val fragmentManager = parentFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, HomeFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        })

        return binding.root
    }

    private fun navigateToHome(){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, HomeFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}

