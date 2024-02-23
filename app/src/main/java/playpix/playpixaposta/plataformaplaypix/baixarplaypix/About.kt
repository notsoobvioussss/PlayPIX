package playpix.playpixaposta.plataformaplaypix.baixarplaypix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val opis = findViewById<TextView>(R.id.opis)
        opis.setText("Tank Battle is an action-packed, strategic game that takes you back to the nostalgic era of classic arcade games. Immerse yourself in a thrilling battlefield where you command a powerful tank and engage in intense battles against enemy tanks.\n" +
                "\n" +
                "In this epic tank warfare game, you'll navigate through different terrains, including deserts, forests, and urban landscapes, each with its own unique challenges and obstacles. Your goal is to strategically maneuver your tank, take aim, and eliminate enemy tanks while avoiding their deadly attacks.\n" +
                "\n" +
                "With a wide range of tanks at your disposal, each with its own strengths and weaknesses, you'll need to carefully choose the perfect tank for each mission. Upgrade your tanks with powerful weapons, armor, and special abilities to gain the upper hand against your opponents.\n" +
                "\n" +
                "The game features multiple game modes, including a thrilling single-player campaign where you'll face off against increasingly challenging enemy tanks and bosses. Show your skills in the multiplayer mode, where you can battle against friends or players from around the world in intense, real-time tank battles.\n" +
                "\n" +
                "Tank Battle offers a realistic and immersive gaming experience, with stunning graphics, dynamic sound effects, and smooth controls that make you feel like you're really inside the tank. The thrilling gameplay, combined with strategic decision-making, will keep you engaged for hours on end.\n" +
                "\n" +
                "Prepare for an adrenaline-fueled battle as you command your tank, strategize your moves, and unleash destruction upon your enemies. Are you ready to become the ultimate tank commander and dominate the battlefield in Tank Battle? The fate of victory lies in your hands!")
    }
}