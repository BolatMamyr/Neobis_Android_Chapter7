package com.example.authapplication.ui.reg

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.authapplication.R
import com.example.authapplication.databinding.FragmentEmailConfirmationBinding
import com.example.authapplication.other.MyAlertDialog

class EmailConfirmationFragment : Fragment() {

    private var _binding: FragmentEmailConfirmationBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<EmailConfirmationFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmailConfirmationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tbConfirmEmail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvConfirmationSent.text =
            getString(R.string.letter_sent_to_email).replace("{email}", args.email)

        binding.tvNoEmail.setOnClickListener {
            MyAlertDialog {
                setTitle(
                    getString(R.string.another_confirmation_msg_sent).replace(
                        "{email}",
                        args.email
                    )
                )
                setMessage(getString(R.string.dont_forget_to_check_spam))
                setPositiveButton(getString(R.string.got_it)) {
                    dismiss()
                }
                show()
            }

        }

    }


}