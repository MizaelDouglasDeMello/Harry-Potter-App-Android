package br.com.mizaeldouglas.harry_potter_app.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import br.com.mizaeldouglas.harry_potter_app.R
import br.com.mizaeldouglas.harry_potter_app.adapter.MainAdapter
import br.com.mizaeldouglas.harry_potter_app.databinding.ActivityMainBinding
import br.com.mizaeldouglas.harry_potter_app.view.book.BookActivity
import br.com.mizaeldouglas.harry_potter_app.view.character.CharacterListActivity
import br.com.mizaeldouglas.harry_potter_app.view.spells.SpellsActivity

class MainActivity : AppCompatActivity() {
    private val categories = listOf("Characters", "Books", "Spells", "Houses")

    private val imageResIds = listOf(
        R.drawable.characters,
        R.drawable.book,
        R.drawable.spells,
        R.drawable.houses
    )

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        with(binding) {
            recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
            recyclerView.adapter = MainAdapter(categories, imageResIds) { category ->
                when (category) {
                    "Characters" -> {
                        startActivity(Intent(this@MainActivity, CharacterListActivity::class.java))
                    }

                    "Books" -> {
                        startActivity(Intent(this@MainActivity, BookActivity::class.java))
                    }

                    "Spells" -> {
                        startActivity(Intent(this@MainActivity, SpellsActivity::class.java))
                    }

                    "Houses" -> {
                        Toast.makeText(this@MainActivity, "Houses", Toast.LENGTH_SHORT).show()
                    }
                }


            }
        }
    }


}

