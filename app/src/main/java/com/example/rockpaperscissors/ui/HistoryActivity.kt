package com.example.rockpaperscissors.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.GameRepository
import com.example.rockpaperscissors.model.Game

import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.content_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {
    private val gameList = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(gameList)
    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        gameRepository = GameRepository(this)
        initViews()
    }

    private fun initViews() {
        rvGames.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvGames.adapter = gameAdapter
        getGameList()
        fab.setOnClickListener { deleteGames() }
    }

    private fun getGameList() {
        CoroutineScope(Dispatchers.Main).launch {
            val gameList = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@HistoryActivity.gameList.clear()
            this@HistoryActivity.gameList.addAll(gameList)
            this@HistoryActivity.gameAdapter.notifyDataSetChanged()
        }
    }

    private fun deleteGames() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getGameList()
        }
    }

}
