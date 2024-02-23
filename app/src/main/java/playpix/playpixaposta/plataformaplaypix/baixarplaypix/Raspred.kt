package playpix.playpixaposta.plataformaplaypix.baixarplaypix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Raspred : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raspred)
        val game = findViewById<Button>(R.id.game)
        val about = findViewById<Button>(R.id.about)
        val calc = findViewById<Button>(R.id.calc)
        val exit = findViewById<Button>(R.id.exit)
        game.setText("Game")
        about.setText("About")
        calc.setText("Calc")
        exit.setText("Exit")
        game.setOnClickListener {
            startActivity(Intent(this, Game::class.java))
        }
        about.setOnClickListener {
            startActivity(Intent(this, About::class.java))
        }
        calc.setOnClickListener {
            startActivity(Intent(this, Calc::class.java))
        }
        exit.setOnClickListener {
            finishAffinity()
        }

    }
}