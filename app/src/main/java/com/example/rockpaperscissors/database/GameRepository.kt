package com.example.rockpaperscissors.database

import android.content.Context
import com.example.rockpaperscissors.model.Game

class GameRepository(context: Context) {
    private val gameDao: GameDao

    init {
        val database =
            GameRoomDatabase.getDatabase(
                context
            )
        gameDao = database!!.gameDao()
    }

    suspend fun getAllProducts(): List<Game> = gameDao.getAllGames()

    suspend fun insertProduct(game: Game) = gameDao.insertGame(game)

    suspend fun deleteProduct(game: Game) = gameDao.deleteGame(game)

    suspend fun deleteAllProducts() = gameDao.deleteAllGames()
}