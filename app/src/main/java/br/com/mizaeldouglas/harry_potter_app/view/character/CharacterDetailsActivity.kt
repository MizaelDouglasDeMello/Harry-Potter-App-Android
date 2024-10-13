package br.com.mizaeldouglas.harry_potter_app.view.character

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.mizaeldouglas.harry_potter_app.R
import br.com.mizaeldouglas.harry_potter_app.databinding.ActivityCharacterDetailsBinding
import br.com.mizaeldouglas.harry_potter_app.model.Character
import com.squareup.picasso.Picasso

class CharacterDetailsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCharacterDetailsBinding.inflate(layoutInflater)
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

        val bundle = intent.extras

        if (bundle != null){
            val character = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("character", Character::class.java)
            }else{
                bundle.getParcelable("character") as Character?
            }


            if (character != null){
                binding.txtNameCharacter.text = character.nickname
                binding.txtFullNameCharacter.text = character.fullName
                binding.txtHogwartsHouse.text = character.hogwartsHouse
                binding.txtInterpretedBy.text = character.interpretedBy

                binding.txtHogwartsHouse.text = character.hogwartsHouse

                val houseImageRes = when (character.hogwartsHouse) {
                    "Grifinória" -> R.drawable.grifinoria
                    "Corvinal" -> R.drawable.corvinal
                    "Sonserina" -> R.drawable.sonserina
                    "Lufa-lufa" -> R.drawable.lufa_lufa
                    else -> R.drawable.houses // Imagem padrão se a casa não for reconhecida
                }

                binding.imgHouseCharacter.setImageResource(houseImageRes)



                Picasso.get()
                    .load(character.image)
                    .into(binding.characterCoverImageView)
            }
        }
    }
}