package com.example.rockpaperscissors.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "game_table")
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "computer_move")
    var computerMove: String,

    @ColumnInfo(name = "player_move")
    var playerMove: String,

    @ColumnInfo(name = "result")
    var result: String
) : Parcelable