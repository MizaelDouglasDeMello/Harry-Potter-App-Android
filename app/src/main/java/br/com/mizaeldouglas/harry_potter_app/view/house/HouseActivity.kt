package br.com.mizaeldouglas.harry_potter_app.view.house

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import br.com.mizaeldouglas.harry_potter_app.R
import br.com.mizaeldouglas.harry_potter_app.adapter.house.HouseAdapter
import br.com.mizaeldouglas.harry_potter_app.databinding.ActivityHouseBinding
import br.com.mizaeldouglas.harry_potter_app.model.House
import br.com.mizaeldouglas.harry_potter_app.service.api.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class HouseActivity : AppCompatActivity() {
    private val imageResIds = listOf(
        R.drawable.grifinoria,
        R.drawable.lufa_lufa,
        R.drawable.corvinal,
        R.drawable.sonserina
    )
    private val binding by lazy {
        ActivityHouseBinding.inflate(layoutInflater)
    }

    private val api by lazy {
        RetrofitInstance.api
    }
    private lateinit var houseAdapter: HouseAdapter
    private var jobHouse: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()

    }

    override fun onStart() {
        super.onStart()
        getHouse()
    }
    private fun initViews() {
        houseAdapter = HouseAdapter(imageResIds){house ->
            Log.i("TAG", "initViews: ${house.house}")
        }
        binding.recyclerViewHouse.adapter = houseAdapter
        binding.recyclerViewHouse.layoutManager = GridLayoutManager(this, 2)
    }

    private fun getHouse() {
        jobHouse = CoroutineScope(Dispatchers.IO).launch {
            var response: Response<List<House>>? = null

            try {
                response = api.getHouses()
            }catch (e: Exception){
                Log.i("error_api","getHouse: ${e.message}")
            }

            if (response != null && response.isSuccessful){
                val house = response.body() ?: emptyList()

                withContext(Dispatchers.Main){
                    houseAdapter.addList(house)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        jobHouse?.cancel()
    }
}