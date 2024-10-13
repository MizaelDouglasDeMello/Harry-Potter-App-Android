package br.com.mizaeldouglas.harry_potter_app.adapter.house

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.mizaeldouglas.harry_potter_app.R
import br.com.mizaeldouglas.harry_potter_app.databinding.ItemHouseBinding
import br.com.mizaeldouglas.harry_potter_app.model.House

class HouseAdapter(
    private val imageResIds: List<Int>,
    private val onItemClick: (House) -> Unit
) :
    RecyclerView.Adapter<HouseAdapter.HouseViewHolder>() {

    private var houseList = mutableListOf<House>()

    fun addList(list: List<House>) {
        this.houseList.clear()
        this.houseList.addAll(list)
        notifyDataSetChanged()
    }

    inner class HouseViewHolder(val binding: ItemHouseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = itemView.findViewById(R.id.house_image)

        fun bind(house: House){
            binding.houseName.text = house.house
            binding.houseHogwartsHouse.text = house.founder

            binding.root.setOnClickListener{
                onItemClick(house)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemHouseBinding.inflate(
            layoutInflater, parent, false
        )
        return HouseViewHolder(binding)
    }

    override fun getItemCount(): Int = houseList.size

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        val house = houseList[position]
        holder.bind(house)
        holder.imageView.setImageResource(imageResIds[position])
    }
}