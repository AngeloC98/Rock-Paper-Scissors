package com.example.rockpaperscissors.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.model.Game
import kotlinx.android.synthetic.main.item_game.view.*

class GameAdapter (private val products: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(products[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            itemView.tvDate.text = game.date
            itemView.tvResult.text = game.result
            when (game.computerMove) {
                "rock" -> itemView.ivComputer.setImageResource(R.drawable.rock)
                "paper" -> itemView.ivComputer.setImageResource(R.drawable.paper)
                "scissors" -> itemView.ivComputer.setImageResource(R.drawable.scissors)
            }
            when (game.playerMove) {
                "rock" -> itemView.ivYou.setImageResource(R.drawable.rock)
                "paper" -> itemView.ivYou.setImageResource(R.drawable.paper)
                "scissors" -> itemView.ivYou.setImageResource(R.drawable.scissors)
            }
        }
    }
}