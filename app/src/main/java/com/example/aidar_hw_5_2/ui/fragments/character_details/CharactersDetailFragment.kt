package com.example.aidar_hw_5_2.ui.fragments.character_details

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.aidar_hw_5_2.R
import com.example.aidar_hw_5_2.databinding.FragmentCharacterDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding

@AndroidEntryPoint
class CharactersDetailFragment : Fragment(R.layout.fragment_character_detail) {

    private val binding by viewBinding(FragmentCharacterDetailBinding::bind)
    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterId = arguments?.getInt("character_id") ?: return
        viewModel.getCharacterById(characterId)

        observeViewModel()

        setupExpandableLayouts()

    }

    private fun observeViewModel() {
        viewModel.character.observe(viewLifecycleOwner) { character ->
            with(binding) {
                characterName.text = character.name
                characterStatus.text = character.status
                characterLocation.text = character.location.name
                characterGender.text = character.gender

                Glide.with(this@CharactersDetailFragment)
                    .load(character.image)
                    .into(characterImage)

                expandable.secondLayout.findViewById<TextView>(R.id.tv_character_info)?.text =
                    "ID: ${character.id}\nSpecies: ${character.species}\nType: ${character.type}"

                expandable2.secondLayout.findViewById<TextView>(R.id.tv_origin)?.text =
                    "Origin: ${character.origin.name}"

                expandable3.secondLayout.findViewById<TextView>(R.id.tv_first_seen)?.text =
                    "First seen in: ${character.episode.firstOrNull() ?: "Unknown"}"

            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupExpandableLayouts() {
        val expandables = listOf(
            binding.expandable,
            binding.expandable2,
            binding.expandable3
        )

        expandables.forEach { expandable ->
            expandable.parentLayout.setOnClickListener {
                if (expandable.isExpanded) {
                    expandable.collapse()
                } else {
                    expandable.expand()
                }
            }
        }
    }
}