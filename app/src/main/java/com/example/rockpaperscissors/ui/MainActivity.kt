package com.example.rockpaperscissors.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.GameRepository
import com.example.rockpaperscissors.model.Game

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    private lateinit var productRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        productRepository = GameRepository(this)
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        ivRock.setOnClickListener { playGame("rock") }
        ivPaper.setOnClickListener { playGame("paper") }
        ivScissors.setOnClickListener { playGame("scissors") }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun playGame(move: String) {
        val options = listOf("rock", "paper", "scissors")
        var result = ""
        when (move) {
            "rock" -> ivYou.setImageResource(R.drawable.rock)
            "paper" -> ivYou.setImageResource(R.drawable.paper)
            "scissors" -> ivYou.setImageResource(R.drawable.scissors)
        }
        val computerMove = options.random()
        when (computerMove) {
            "rock" -> ivComputer.setImageResource(R.drawable.rock)
            "paper" -> ivComputer.setImageResource(R.drawable.paper)
            "scissors" -> ivComputer.setImageResource(R.drawable.scissors)
        }

        if (move == "rock" && computerMove == "rock") result = "draw"
        if (move == "rock" && computerMove == "paper") result = "lose"
        if (move == "rock" && computerMove == "scissors") result = "win"

        if (move == "paper" && computerMove == "rock") result = "win"
        if (move == "paper" && computerMove == "paper") result = "draw"
        if (move == "paper" && computerMove == "scissors") result = "lose"

        if (move == "scissors" && computerMove == "rock") result = "lose"
        if (move == "scissors" && computerMove == "paper") result = "win"
        if (move == "scissors" && computerMove == "scissors") result = "draw"

        when (result) {
            "win" -> tvResult.text = getString(R.string.win)
            "draw" -> tvResult.text = getString(R.string.draw)
            "lose" -> tvResult.text = getString(R.string.lose)
        }
        mainScope.launch {
            val game = Game(
                date = LocalDateTime.now().toString(),
                computerMove = computerMove,
                playerMove = move,
                result = result
            )

            withContext(Dispatchers.IO) {
                productRepository.insertGame(game)
            }
        }
    }

    private fun startHistoryActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_view_history -> {
                startHistoryActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
