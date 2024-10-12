package br.com.mizaeldouglas.harry_potter_app.adapter.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mizaeldouglas.harry_potter_app.databinding.ItemBookBinding
import br.com.mizaeldouglas.harry_potter_app.model.Book
import com.squareup.picasso.Picasso


class BookAdapter(
    private val onItemClick: (Book) -> Unit
): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var booksList = mutableListOf<Book>()

    fun addList(list: List<Book>) {
        this.booksList.clear()
        this.booksList.addAll(list)
        notifyDataSetChanged()
    }

    inner class BookViewHolder(val binding: ItemBookBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            Picasso.get()
                .load(book.cover)
                .into(binding.bookImage)
            binding.root.setOnClickListener {
                onItemClick(book)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(
            layoutInflater, parent, false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = booksList[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int = booksList.size
}