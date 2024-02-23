package playpix.playpixaposta.plataformaplaypix.baixarplaypix

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class Game : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val gameSurfaceView = findViewById<GameSurfaceView>(R.id.gameSurfaceView)
        gameSurfaceView.startGame()
    }
}
private val playerTank = Tank(100.0, 100.0, Color.CYAN) // Бирюзовый цвет
private val GOLD_COLOR = Color.parseColor("#FFD700") // Золотой цвет

class Tank(internal var x: Double, internal var y: Double, private val tankColor: Int = Color.BLUE ){
    private val TANK_SIZE = 100.0
    private val TANK_SPEED = 5
    private val SHOOT_COOLDOWN = 1000L
    private val BULLET_RADIUS = 10.0
    private val BULLET_SPEED = 10

    private val paint = Paint().apply {
        color = tankColor
        style = Paint.Style.FILL // Заливка для круглого танка
    }
    var angle = 0.0
    private var shootCooldown = 0L

    fun draw(canvas: Canvas) {
        canvas.drawCircle(x.toFloat(), y.toFloat(), 50.0F, paint) // Используйте drawCircle
    }


    fun update() {
        // Логика обновления положения танка
        x += (TANK_SPEED * cos(angle)).toFloat()
        y += (TANK_SPEED * sin(angle)).toFloat()
    }

    fun shoot(): Bullet? {
        val currentTime = System.currentTimeMillis()
        if (currentTime - shootCooldown > SHOOT_COOLDOWN) {
            shootCooldown = currentTime
            val bulletX = (x + TANK_SIZE / 2).toFloat()
            val bulletY = (y + TANK_SIZE / 2).toFloat()
            return Bullet(bulletX, bulletY, angle)
        }
        return null
    }

}

class Bullet(internal var x: Float, internal var y: Float, private var angle: Double) {
    private val BULLET_RADIUS = 10.0
    private val BULLET_SPEED = 10

    private val paint = Paint().apply {
        color = Color.RED
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, BULLET_RADIUS.toFloat(), paint)
    }

    fun update() {
        // Логика обновления положения пули
        x += (BULLET_SPEED * cos(angle)).toFloat()
        y += (BULLET_SPEED * sin(angle)).toFloat()
    }
}

class EnemyTank(internal var x: Float, internal var y: Float) {
    private val TANK_SIZE = 100.0
    private val TANK_SPEED = 2
    private val SHOOT_COOLDOWN = 1000L
    private val BULLET_RADIUS = 10.0
    private val BULLET_SPEED = 5

    private val paint = Paint().apply {
        color = GOLD_COLOR // Золотой цвет для врагов
    }
    private var angle = Random.nextDouble(0.0, 2 * Math.PI)

    fun draw(canvas: Canvas) {
        canvas.drawRect(x.toFloat(), y.toFloat(), (x + TANK_SIZE).toFloat(), (y + TANK_SIZE).toFloat(), paint)
    }

    fun update(playerX: Float, playerY: Float) {
        // Логика обновления положения вражеского танка
        val dx = playerX - x
        val dy = playerY - y
        angle = Math.atan2(dy.toDouble(), dx.toDouble())
        x += (TANK_SPEED * cos(angle)).toFloat()
        y += (TANK_SPEED * sin(angle)).toFloat()
    }
}

