package br.com.mizaeldouglas.harry_potter_app.view.book

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.mizaeldouglas.harry_potter_app.R
import br.com.mizaeldouglas.harry_potter_app.databinding.ActivityBookDetailsBinding
import br.com.mizaeldouglas.harry_potter_app.model.Book
import com.squareup.picasso.Picasso

class BookDetailsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBookDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val bundle = intent.extras

        if (bundle != null) {
            val book = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("book", Book::class.java)
            } else {
                bundle.getParcelable("book") as Book?
            }

            if (book != null){
                binding.bookTitleTextView.text = book.title
                binding.yearTextView.text = getString(R.string.year_text, book.releaseDate)
                binding.pagesTextView.text = getString(R.string.pages_text, book.pages)
                binding.summaryTextView.text = book.description

                Picasso.get()
                    .load(book.cover)
                    .into(binding.bookCoverImageView)
            }
        }

    }
}