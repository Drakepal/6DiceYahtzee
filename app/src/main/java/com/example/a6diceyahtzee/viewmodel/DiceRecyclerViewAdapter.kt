package com.example.a6diceyahtzee.viewmodel

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a6diceyahtzee.model.Dice

private const val TAG = "DiceRecyclerViewAd"

class DiceRecyclerViewAdapter(private var diceList: List<Dice>, private val listener: OnDiceClickListener) :
 RecyclerView.Adapter<DiceViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiceViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: DiceViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}