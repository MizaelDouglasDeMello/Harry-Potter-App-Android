package br.com.mizaeldouglas.harry_potter_app.view.character

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import br.com.mizaeldouglas.harry_potter_app.adapter.character.CharacterAdapter
import br.com.mizaeldouglas.harry_potter_app.databinding.ActivityCharacterListBinding
import br.com.mizaeldouglas.harry_potter_app.model.Character
import br.com.mizaeldouglas.harry_potter_app.service.api.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class CharacterActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCharacterListBinding.inflate(layoutInflater)
    }
    private val api by lazy {
        RetrofitInstance.api
    }

    private lateinit var characterAdapter: CharacterAdapter
    private var jobCharacter: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews() // Inicializa as views
    }

    override fun onStart() {
        super.onStart()
        getCharacters() // Carrega os personagens quando a Activity começa
    }

    private fun initViews() {
        // Inicializa o adaptador com um callback para quando um item for clicado
        characterAdapter = CharacterAdapter { character ->
            // Lógica de clique no item, se necessário
            val intent = Intent(this, CharacterDetailsActivity::class.java)
            intent.putExtra("character", character)

            startActivity(intent)
        }

        // Configura o RecyclerView
        binding.recyclerViewCharacter.adapter = characterAdapter
        binding.recyclerViewCharacter.layoutManager = GridLayoutManager(this, 2)
    }

    private fun getCharacters() {
        jobCharacter = CoroutineScope(Dispatchers.IO).launch {
            var response: Response<List<Character>>? = null // Altere para List<Character>

            try {
                response = api.getCharacters() // Certifique-se de que o método retorne a lista de personagens
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("error_api", "getCharacters: ${e.message}")
            }

            if (response != null && response.isSuccessful) {
                val characters = response.body() ?: emptyList() // Obtenha a lista de personagens

                withContext(Dispatchers.Main) {
                    characterAdapter.addList(characters) // Adiciona a lista ao adaptador
//                    Log.i("apichamada", "getCharacters: ${characters.map { it.fullName }}") // Log para verificar os personagens
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        jobCharacter?.cancel()
    }
}




//
//class DetalhesActivity : AppCompatActivity() {
//
//    private val binding by lazy {
//        ActivityDetalhesBinding.inflate(layoutInflater)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//
//        val bundle = intent.extras
//        if (bundle != null) {
//            val filme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                bundle.getParcelable("filme", Filme::class.java)
//            } else {
//                bundle.getParcelable("filme") as Filme?
//            }
//
//            if (filme != null) {
//                binding.textFilmeTitulo.text = filme.title
//                binding.txtDescricao.text = filme.overview
//                binding.txtNota.text = String.format("%.2f", filme.vote_average)
//
//                val nomeFilme = filme.backdrop_path
//                val tamanhoFilme = "w780"
//                val urlBase = RetrofitService.BASE_URL_IMAGEM
//
//                val urlFilme = urlBase + tamanhoFilme + nomeFilme
//
//                Picasso.get()
//                    .load(urlFilme)
//                    .into(binding.imgPoster)
//            }
//
//        }
//
//    }
//}
