package br.com.mizaeldouglas.harry_potter_app.adapter.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mizaeldouglas.harry_potter_app.databinding.ItemCharacterBinding
import br.com.mizaeldouglas.harry_potter_app.model.Character
import com.squareup.picasso.Picasso

class CharacterAdapter(
    private val onItemClick: (Character) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private var charactersList = mutableListOf<Character>()

    fun addList(list: List<Character>) {
        this.charactersList.clear()  // Limpa a lista antes de adicionar novos itens
        this.charactersList.addAll(list)
        notifyDataSetChanged()
    }


inner class CharacterViewHolder(val binding: ItemCharacterBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(character: Character){

        binding.characterName.text = character.nickname
        binding.characterHogwartsHouse.text = character.hogwartsHouse
        Picasso.get()
            .load(character.image)
            .into(binding.characterImage)
        binding.root.setOnClickListener {
            onItemClick(character)
        }

    }

}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(
            layoutInflater, parent, false
        )
        return CharacterViewHolder( binding )

    }


    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = charactersList[position]
        holder.bind( character )
    }

    override fun getItemCount(): Int = charactersList.size
}
