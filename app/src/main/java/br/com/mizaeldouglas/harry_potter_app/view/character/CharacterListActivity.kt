package br.com.mizaeldouglas.harry_potter_app.view.character

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import br.com.mizaeldouglas.harry_potter_app.adapter.CharacterAdapter
import br.com.mizaeldouglas.harry_potter_app.databinding.ActivityCharacterListBinding
import br.com.mizaeldouglas.harry_potter_app.model.Character
import br.com.mizaeldouglas.harry_potter_app.service.api.ApiService
import br.com.mizaeldouglas.harry_potter_app.service.api.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class CharacterListActivity : AppCompatActivity() {
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
            Log.i("Character Clicked", "Clicked: ${character.fullName}")
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
                    Log.i("apichamada", "getCharacters: ${characters.map { it.fullName }}") // Log para verificar os personagens
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        jobCharacter?.cancel() // Cancela a job quando a Activity para
    }
}
