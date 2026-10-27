package ru.vguvt_zist3.tictactoe

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var tvStatusText: TextView
    private lateinit var tvCurrentPlayer: TextView
    private lateinit var buttons: Array<Array<Button>>

    private var currentPlayer = "X"
    private var gameActive = true
    private var board = Array(3) { arrayOfNulls<String>(3) }

    private val colorX = "#39FF14"
    private val colorO = "#FFEA00"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        )
        setContentView(R.layout.activity_main)

        tvStatusText = findViewById(R.id.tvStatusText)
        tvCurrentPlayer = findViewById(R.id.tvCurrentPlayer)

        buttons = arrayOf(
            arrayOf(findViewById(R.id.btn00), findViewById(R.id.btn01), findViewById(R.id.btn02)),
            arrayOf(findViewById(R.id.btn10), findViewById(R.id.btn11), findViewById(R.id.btn12)),
            arrayOf(findViewById(R.id.btn20), findViewById(R.id.btn21), findViewById(R.id.btn22))
        )

        for (row in 0..2) {
            for (col in 0..2) {
                buttons[row][col].setOnClickListener {
                    onCellClicked(row, col)
                }
            }
        }

        findViewById<Button>(R.id.btnReset).setOnClickListener {
            resetGame()
        }

        findViewById<Button>(R.id.btnAuthors).setOnClickListener {
            val intent = Intent(this, AuthorsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onCellClicked(row: Int, col: Int) {
        if (board[row][col] != null || !gameActive) return

        board[row][col] = currentPlayer

        if (currentPlayer == "X") {
            buttons[row][col].text = "✕" // Тонкий крестик
            buttons[row][col].textSize = 60f
            buttons[row][col].setTextColor(Color.parseColor(colorX))
            buttons[row][col].setShadowLayer(15f, 0f, 0f, Color.parseColor(colorX))
            buttons[row][col].paint.setShadowLayer(15f, 0f, 0f, Color.parseColor(colorX))
        } else {
            buttons[row][col].text = "◯"
            buttons[row][col].textSize = 65f
            buttons[row][col].setTextColor(Color.parseColor(colorO))
            // Усиленное свечение для нолика
            buttons[row][col].setShadowLayer(25f, 0f, 0f, Color.parseColor(colorO))
            buttons[row][col].paint.setShadowLayer(25f, 0f, 0f, Color.parseColor(colorO))
        }

        if (checkWin()) {
            tvStatusText.text = "Победил "
            tvCurrentPlayer.text = currentPlayer
            val winColor = if (currentPlayer == "X") colorX else colorO
            tvCurrentPlayer.setTextColor(Color.parseColor(winColor))
            tvCurrentPlayer.setShadowLayer(15f, 0f, 0f, Color.parseColor(winColor))
            gameActive = false
        } else if (isBoardFull()) {
            tvStatusText.text = "Ничья!"
            tvCurrentPlayer.text = ""
            tvStatusText.setTextColor(Color.parseColor("#FF00FF"))
            gameActive = false
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            tvStatusText.text = "Ход: "
            tvCurrentPlayer.text = currentPlayer
            val nextColor = if (currentPlayer == "X") colorX else colorO
            tvCurrentPlayer.setTextColor(Color.parseColor(nextColor))
            tvCurrentPlayer.setShadowLayer(15f, 0f, 0f, Color.parseColor(nextColor))
        }
    }

    private fun checkWin(): Boolean {
        for (i in 0..2) {
            if (board[i][0] != null && board[i][0] == board[i][1] && board[i][0] == board[i][2]) return true
        }
        for (i in 0..2) {
            if (board[0][i] != null && board[0][i] == board[1][i] && board[0][i] == board[2][i]) return true
        }
        if (board[0][0] != null && board[0][0] == board[1][1] && board[0][0] == board[2][2]) return true
        if (board[0][2] != null && board[0][2] == board[1][1] && board[0][2] == board[2][0]) return true
        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == null) return false
            }
        }
        return true
    }

    private fun resetGame() {
        board = Array(3) { arrayOfNulls<String>(3) }
        currentPlayer = "X"
        gameActive = true

        tvStatusText.text = "Ход: "
        tvStatusText.setTextColor(Color.parseColor("#AAAAAA"))
        tvCurrentPlayer.text = "X"
        tvCurrentPlayer.setTextColor(Color.parseColor(colorX))
        tvCurrentPlayer.setShadowLayer(15f, 0f, 0f, Color.parseColor(colorX))

        for (row in 0..2) {
            for (col in 0..2) {
                buttons[row][col].text = ""
                buttons[row][col].setShadowLayer(0f, 0f, 0f, Color.TRANSPARENT)
                buttons[row][col].paint.setShadowLayer(0f, 0f, 0f, Color.TRANSPARENT)
                buttons[row][col].textSize = 60f
            }
        }
    }
}