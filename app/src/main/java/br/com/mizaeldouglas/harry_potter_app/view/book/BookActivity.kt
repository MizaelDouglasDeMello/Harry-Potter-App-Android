package br.com.mizaeldouglas.harry_potter_app.view.book

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import br.com.mizaeldouglas.harry_potter_app.adapter.book.BookAdapter
import br.com.mizaeldouglas.harry_potter_app.databinding.ActivityBookBinding
import br.com.mizaeldouglas.harry_potter_app.model.Book
import br.com.mizaeldouglas.harry_potter_app.service.api.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class BookActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityBookBinding.inflate(layoutInflater)
    }

    private val api by lazy {
        RetrofitInstance.api
    }

    private lateinit var bookAdapter: BookAdapter
    private var jobBook: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    override fun onStart() {
        super.onStart()
        getBooks()
    }

    private fun initViews() {
        bookAdapter = BookAdapter { book ->
            // Lógica de clique no item

            val intent = Intent(this, BookDetailsActivity::class.java)
            intent.putExtra("book", book)

            startActivity(intent)

//            startActivity(Intent(this@BookActivity, BookDetailsActivity::class.java))

        }

        binding.recyclerViewBook.adapter = bookAdapter
        binding.recyclerViewBook.layoutManager = GridLayoutManager(this, 2)
    }

    private fun getBooks() {
        jobBook = CoroutineScope(Dispatchers.IO).launch {
            var response: Response<List<Book>>? =  null

            try {
                response = api.getBooks()
            }catch (e: Exception){
                e.printStackTrace()
                Log.i("error_api", "getBooks: ${e.message}")
            }

            if (response != null && response.isSuccessful){
                val books = response.body() ?: emptyList()

                withContext(Dispatchers.Main){
                    bookAdapter.addList(books)
                }
            }
        }
    }


    override fun onStop() {
        super.onStop()
        jobBook?.cancel()
    }
}