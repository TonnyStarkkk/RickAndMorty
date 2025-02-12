package com.example.aidar_hw_5_2.ui.characters

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aidar_hw_5_2.R
import com.example.aidar_hw_5_2.databinding.FragmentCharacterBinding
import dev.androidbroadcast.vbpd.viewBinding

class CharacterFragment : Fragment(R.layout.fragment_character) {

    private val binding by viewBinding(FragmentCharacterBinding::bind)
    private val characterAdapter = CharacterAdapter()
    private val viewModel: CharacterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupObserve()

        binding.pgCharacter.visibility = View.VISIBLE
        viewModel.getAllCharacters()
    }

    private fun initialize() {
        binding.rvCharacter.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = characterAdapter
        }
    }

    private fun setupObserve() {
        viewModel.characters.observe(viewLifecycleOwner) { response ->
            response.results?.let { results ->
                Log.d("UI_UPDATE", "Refresh list : ${results.size}")
                characterAdapter.submitList(results)

                with(binding) {
                    rvCharacter.visibility = View.VISIBLE
                    pgCharacter.visibility = View.GONE
                }
            }
        }
        viewModel.error.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            binding.pgCharacter.visibility = View.GONE
        }
    }
}