package playpix.playpixaposta.plataformaplaypix.baixarplaypix

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.sqrt
import kotlin.random.Random

class GameSurfaceView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs), Runnable, SurfaceHolder.Callback {
    private var thread: Thread? = null
    private var running = false
    private val surfaceHolder: SurfaceHolder = holder
    private val playerTank = Tank(100.0, 100.0)
    private var bullets = mutableListOf<Bullet>()
    private var enemyTanks = mutableListOf<EnemyTank>()
    private var score = 0
    private val TANK_SIZE = 100.0
    private val BULLET_RADIUS = 10.0
    private val TANK_SPEED = 5
    private val BULLET_SPEED = 10
    private val SHOOT_COOLDOWN = 1000L




    init {
        surfaceHolder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        startGame()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stopGame()
    }

    override fun run() {
        while (running) {
            val canvas = surfaceHolder.lockCanvas()
            canvas?.let {
                synchronized(surfaceHolder) {
                    update()
                    draw(it)
                }
                surfaceHolder.unlockCanvasAndPost(it)
            }
        }
    }

    private fun update() {
        playerTank.update()

        // Обновление положения и логика для пуль
        val bulletsToRemove = mutableListOf<Bullet>()
        for (bullet in bullets) {
            bullet.update()
            if (bulletIsOutOfBounds(bullet)) {
                bulletsToRemove.add(bullet)
            }
        }
        bullets.removeAll(bulletsToRemove)

        // Создание и обновление врагов
        val enemiesToRemove = mutableListOf<EnemyTank>()
        for (enemyTank in enemyTanks) {
            enemyTank.update(playerTank.x.toFloat(), playerTank.y.toFloat())
            if (enemyTankIsOutOfBounds(enemyTank)) {
                enemiesToRemove.add(enemyTank)
            }
        }
        enemyTanks.removeAll(enemiesToRemove)

        // Проверка столкновений пуль с врагами
        val bulletsToRemoveCollided = mutableListOf<Bullet>()
        val enemiesToRemoveCollided = mutableListOf<EnemyTank>()
        for (bullet in bullets) {
            for (enemyTank in enemyTanks) {
                if (collisionDetected(bullet, enemyTank)) {
                    bulletsToRemoveCollided.add(bullet)
                    enemiesToRemoveCollided.add(enemyTank)
                    score++
                }
            }
        }
        bullets.removeAll(bulletsToRemoveCollided)
        enemyTanks.removeAll(enemiesToRemoveCollided)

    }

    private fun bulletIsOutOfBounds(bullet: Bullet): Boolean {
        return bullet.x < 0 || bullet.x > width || bullet.y < 0 || bullet.y > height
    }

    private fun enemyTankIsOutOfBounds(enemyTank: EnemyTank): Boolean {
        return enemyTank.x < 0 || enemyTank.x > width || enemyTank.y < 0 || enemyTank.y > height
    }

    private fun collisionDetected(bullet: Bullet, enemyTank: EnemyTank): Boolean {
        val dx = bullet.x - enemyTank.x
        val dy = bullet.y - enemyTank.y
        val distance = sqrt(dx * dx + dy * dy)
        return distance < TANK_SIZE / 2 + BULLET_RADIUS
    }

    private val background: Drawable = resources.getDrawable(R.drawable.ffon2)

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        background.setBounds(0, 0, width, height)
        background.draw(canvas)
        playerTank.draw(canvas)
        for (bullet in bullets) {
            bullet.draw(canvas)
        }
        for (enemyTank in enemyTanks) {
            enemyTank.draw(canvas)
        }
        drawScore(canvas)
    }

    private fun drawScore(canvas: Canvas) {
        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 70f // Увеличьте размер текста
        }
        canvas.drawText("Score: $score", 20f, 60f, paint) // Измените координаты отрисовки
    }


    fun startGame() {
        running = true
        thread = Thread(this)
        thread?.start()
        spawnEnemyTanks()
    }

    fun stopGame() {
        running = false
        thread?.join()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val bullet = playerTank.shoot()
                bullet?.let { bullets.add(it) }
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - playerTank.x.toFloat()
                val dy = event.y - playerTank.y.toFloat()
                playerTank.angle = Math.atan2(dy.toDouble(), dx.toDouble())
            }
            MotionEvent.ACTION_UP -> {
                // Обработка отпускания
            }
            MotionEvent.ACTION_MOVE -> {
                val newX = event.x.coerceIn(0f, width.toFloat() - TANK_SIZE.toFloat())
                val newY = event.y.coerceIn(0f, height.toFloat() - TANK_SIZE.toFloat())
                playerTank.x = newX.toDouble()
                playerTank.y = newY.toDouble()
            }
        }
        return true

    }

    private fun spawnEnemyTanks() {
        val spawnInterval = 2000L // Интервал появления врагов
        val screenWidth = width.toFloat()
        val screenHeight = height.toFloat()

        val spawnTask = Runnable {
            while (running) {
                val spawnX = Random.nextFloat() * screenWidth
                val spawnY = Random.nextFloat() * screenHeight
                val enemyTank = EnemyTank(spawnX, spawnY)
                enemyTanks.add(enemyTank)
                Thread.sleep(spawnInterval)
            }
        }

        val spawnThread = Thread(spawnTask)
        spawnThread.start()
    }
}
