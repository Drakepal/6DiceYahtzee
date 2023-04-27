package com.example.a6diceyahtzee.model

import android.util.Log
import java.util.UUID
import kotlin.collections.ArrayList

private const val TAG = "Player"

class Player (
    var id: UUID = UUID.randomUUID(),
    var name: String = "Player",
    var diceToRoll: ArrayList<Dice> = arrayListOf(Dice(), Dice(), Dice(), Dice(), Dice(), Dice()),
    var setScores: MutableMap<Set, Int> = mutableMapOf(),
    var playerTurn: Boolean = false,
    var rollCount: Int = YahtzeeConstants.GameValues.ROLLING_ALLOWED

    ) {

    override fun toString(): String {
        return "${this.id},${this.name}, ${this.diceToRoll}, ${this.setScores}, ${this.playerTurn}, ${this.rollCount}"
    }


    fun getSetsFullfillment(): Int {
        Log.d(TAG, "getSetsFullfillment: starts")
        return setScores.keys.count()
    }


    private fun getNewDice() {
        Log.d(TAG, "getNewDice: starts with $diceToRoll")
        val diceList = kotlin.collections.ArrayList<Dice>()
        for (i in 1..6) {
            diceList.add(Dice())
        }
        diceToRoll = diceList
        Log.d(TAG, "getNewDice: ends with $diceToRoll")
    }

    fun resetEndOfRound() {
        Log.d(TAG, "resetEndOfRound: starts")
        rollCount = YahtzeeConstants.GameValues.ROLLING_ALLOWED
        getNewDice()
        playerTurn = false
    }

    fun saveDice(dice: Dice) {
        Log.d(TAG, "saveDice: starts with $dice")
        dice.savedDie = dice.savedDie != true
        Log.d(TAG, "saveDice: ends with $dice")
    }

}