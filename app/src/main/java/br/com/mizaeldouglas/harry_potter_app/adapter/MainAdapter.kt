package br.com.mizaeldouglas.harry_potter_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.mizaeldouglas.harry_potter_app.R

class MainAdapter(
    private val categories : List<String>,
    private val imageResIds: List<Int>,
    private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.category_name)
        val imageView: ImageView = itemView.findViewById(R.id.category_image)

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(categories[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.textView.text = categories[position]
        holder.imageView.setImageResource(imageResIds[position])
    }

    override fun getItemCount(): Int = categories.size
}



