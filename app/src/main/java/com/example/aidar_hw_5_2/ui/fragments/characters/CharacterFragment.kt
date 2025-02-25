package com.example.aidar_hw_5_2.ui.fragments.characters

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aidar_hw_5_2.R
import com.example.aidar_hw_5_2.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_character) {

    private val binding by viewBinding(FragmentCharacterBinding::bind)
    private val viewModel: CharacterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupObserve()

        binding.pgCharacter.visibility = View.VISIBLE
        viewModel.getAllCharacters()
    }

    private fun initialize() {
        val characterAdapter = CharacterAdapter { characterId ->
            val bundle = Bundle().apply {
                putInt("character_id", characterId)
            }
            findNavController().navigate(R.id.action_characterFragment_to_charactersDetailFragment, bundle)
        }

        binding.rvCharacter.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObserve() {
        viewModel.localCharacters.observe(viewLifecycleOwner) { characters ->
            (binding.rvCharacter.adapter as CharacterAdapter).submitList(characters)

            binding.pgCharacter.visibility = View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            binding.pgCharacter.visibility = View.GONE
        }
    }
}