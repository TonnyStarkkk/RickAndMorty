package com.example.aidar_hw_5_2.ui.fragments.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.aidar_hw_5_2.R
import com.example.aidar_hw_5_2.data.local.CharacterEntity
import com.example.aidar_hw_5_2.data.model.characters.Character
import com.example.aidar_hw_5_2.databinding.ItemCharacterBinding

class CharacterAdapter(
    private val onItemClick: (Int) -> Unit
): ListAdapter<CharacterEntity, CharacterAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemCharacterBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun onBind(character: CharacterEntity) = with(binding) {
                    characterName.text = character.name
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
        holder.onBind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CharacterEntity>() {
            override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}