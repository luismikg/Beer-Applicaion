package com.luis.beerapplication.framework.presentation.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.luis.beerapplication.R
import com.luis.beerapplication.databinding.FragmentLoginBinding
import com.luis.beerapplication.framework.presentation.activity.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginShareViewModel = mainViewModel

        setupObservers()
    }

    private fun setupObservers() {
        mainViewModel.eventLoginMade.observe(viewLifecycleOwner) { loginMade(it) }
    }

    private fun loginMade(isMade: Boolean) {
        if (isMade) {
            goToWelcome()
            mainViewModel.goToMainScreenComplete()
        }
    }

    private fun goToWelcome() {
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
    }
}