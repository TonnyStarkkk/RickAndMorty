package com.example.aidar_hw_5_2.ui.fragments.characters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.aidar_hw_5_2.R
import com.example.aidar_hw_5_2.data.model.characters.Character
import com.example.aidar_hw_5_2.databinding.ItemCharacterBinding

class CharacterAdapter(
    private val onItemClick: (Int) -> Unit
): ListAdapter<Character, CharacterAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemCharacterBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun onBind(character: Character) = with(binding) {
                    characterName.text = character.name
                    characterLocation.text = character.location.name
                    characterFirstSeen.text = character.origin.name
                    characterStatus.text = character.status
                    imgCharacter.load(character.image) {
                        crossfade(true)
                    }
                    colorIndicator.setImageResource(
                        when {
                            character.status?.contains("Dead") == true -> R.drawable.ic_circle_red
                            character.status?.contains("Alive") == true -> R.drawable.ic_circle_green
                            else -> R.drawable.ic_circle_grey
                        }
                    )

                    root.setOnClickListener {
                        onItemClick(character.id)
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)
        Log.d("BIND", "onBindViewHolder: ${character.name}")
        holder.onBind(character)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }
}