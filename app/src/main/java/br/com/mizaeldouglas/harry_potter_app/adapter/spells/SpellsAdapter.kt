package br.com.mizaeldouglas.harry_potter_app.adapter.spells

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mizaeldouglas.harry_potter_app.R
import br.com.mizaeldouglas.harry_potter_app.databinding.ItemBookBinding
import br.com.mizaeldouglas.harry_potter_app.databinding.ItemSpellsBinding
import br.com.mizaeldouglas.harry_potter_app.model.Book
import br.com.mizaeldouglas.harry_potter_app.model.Spell
import com.squareup.picasso.Picasso

class SpellsAdapter(
    private val onItemClick: (Spell) -> Unit
) : RecyclerView.Adapter<SpellsAdapter.SpellsViewHolder>() {

    private var spellsList = mutableListOf<Spell>()


    fun addList(list: List<Spell>){
        this.spellsList.clear()
        this.spellsList.addAll(list)
        notifyDataSetChanged()
    }

    inner class SpellsViewHolder(val binding: ItemSpellsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(spell: Spell) {

            binding.spellsName.text = spell.spell
            binding.spellsImage.setImageResource(R.drawable.spells2)
            binding.root.setOnClickListener {
                onItemClick(spell)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSpellsBinding.inflate(
            layoutInflater, parent, false
        )
        return  SpellsViewHolder(binding)
    }

    override fun getItemCount(): Int = spellsList.size

    override fun onBindViewHolder(holder: SpellsViewHolder, position: Int) {
        val spell = spellsList[position]
        holder.bind(spell)
    }

}