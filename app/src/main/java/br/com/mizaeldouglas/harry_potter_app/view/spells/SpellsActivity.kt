package br.com.mizaeldouglas.harry_potter_app.view.spells

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import br.com.mizaeldouglas.harry_potter_app.adapter.spells.SpellsAdapter
import br.com.mizaeldouglas.harry_potter_app.databinding.ActivitySpellsBinding
import br.com.mizaeldouglas.harry_potter_app.model.Spell
import br.com.mizaeldouglas.harry_potter_app.service.api.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SpellsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySpellsBinding.inflate(layoutInflater)
    }

    private val api by lazy {
        RetrofitInstance.api
    }

    private lateinit var spellsAdapter: SpellsAdapter
    private var jobSpells: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    override fun onStart() {
        super.onStart()
        getSpells()
    }
    private fun initViews() {

        spellsAdapter = SpellsAdapter { spell: Spell ->

            Toast.makeText(this, spell.use, Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewSpell.adapter = spellsAdapter
        binding.recyclerViewSpell.layoutManager = GridLayoutManager(this, 2)


    }

    private fun getSpells() {
        jobSpells = CoroutineScope(Dispatchers.IO).launch {
            var response: Response<List<Spell>>? = null

            try {
                response = api.getSpells()
            }catch (e: Exception){
                Log.e("ERROR API", "getSpells: ${e.message}", )
            }

            if (response != null && response.isSuccessful){
                val spells = response.body() ?: emptyList()

                withContext(Dispatchers.Main){
                    spellsAdapter.addList(spells)
                }
            }


        }
    }



    override fun onStop() {
        super.onStop()
        jobSpells?.cancel()
    }
}