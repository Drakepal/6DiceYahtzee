package com.example.a6diceyahtzee.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.viewModels
import com.example.a6diceyahtzee.R
import com.example.a6diceyahtzee.databinding.ActivityMainBinding
import com.example.a6diceyahtzee.databinding.ContentMainBinding
import com.example.a6diceyahtzee.model.Dice
import com.example.a6diceyahtzee.model.Player
import com.example.a6diceyahtzee.model.Set
import com.example.a6diceyahtzee.viewmodel.DiceRecyclerViewAdapter
import com.example.a6diceyahtzee.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"
private const val DIALOGUE_NEW_GAME = 1


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NewGameDialogue.NewGameDialogEvents, DiceRecyclerViewAdapter.OnDiceClickListener {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var contentMainBinding: ContentMainBinding

    private val diceRecyclerViewAdapter = DiceRecyclerViewAdapter(ArrayList(), this)

    private val currentPlayer: Player? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateDialogResult(dialogId: Int, args: Bundle) {
        Log.d(TAG, "onCreateDialogResult: called with dialogId $dialogId")
        if(dialogId == DIALOGUE_NEW_GAME) {
            val players = args.getStringArray(NEW_GAME_DIALOGUE_PLAYERS_NAME)
            Log.d(
                TAG,
                "onCreateDialogResult: called with dialogId $dialogId, players name $players"
            )
            clearForm( contentMainBinding.linearLayoutScores)
            mainViewModel.createGame(players!!)
        }
        Log.d(TAG, "onCreateDialogResult: ends")
    }

    private fun clearForm(group: ViewGroup) {
        var i = 0
        val count = group.childCount
        while(i < count) {
            val view = group.getChildAt(i)
            if(view is EditText) {
                view.setText("")
            }
            if (view is ViewGroup && view.childCount > 0) clearForm(view)
            ++i
        }
    }

    override fun onSaveClick(diceItem: Dice) {
        Log.d(TAG, "onSaveClick: called with $diceItem")
        mainViewModel.savePlayerDice(diceItem)
        Log.d(TAG, "onSaveClick: ends with $diceItem")
    }

    private fun changeStateFirstPlayerBoxScores(enable: Boolean) {

        contentMainBinding.apply {
            enable.let {
                etDiceOneFirstPlayer.isEnabled = it
                etDiceTwoFirstPlayer.isEnabled = it
                etDiceThreeFirstPlayer.isEnabled = it
                etDiceFourFirstPlayer.isEnabled = it
                etDiceFiveFirstPlayer.isEnabled = it
                etDiceSixFirstPlayer.isEnabled = it
                etThreeOfKindFirstPlayer.isEnabled = it
                etFourOfKindFirstPlayer.isEnabled = it
                etFullHouseFirstPlayer.isEnabled = it
                etSmallStraightFirstPlayer.isEnabled = it
                etLargeStraightFirstPlayer.isEnabled = it
                etYahtzeeFirstPlayer.isEnabled = it
                etChanceFirstPlayer.isEnabled = it
            }
        }
    }

    private fun changeStateSecondPlayerBoxScores(enable: Boolean) {

        contentMainBinding.apply {
            enable.let {
                etDiceOneSecondPlayer.isEnabled = it
                etDiceTwoSecondPlayer.isEnabled = it
                etDiceThreeSecondPlayer.isEnabled = it
                etDiceFourSecondPlayer.isEnabled = it
                etDiceFiveSecondPlayer.isEnabled = it
                etDiceSixSecondPlayer.isEnabled = it
                etThreeOfKindSecondPlayer.isEnabled = it
                etFourOfKindSecondPlayer.isEnabled = it
                etFullHouseSecondPlayer.isEnabled = it
                etSmallStraightSecondPlayer.isEnabled = it
                etLargeStraightSecondPlayer.isEnabled = it
                etYahtzeeSecondPlayer.isEnabled = it
                etChanceSecondPlayer.isEnabled = it
            }
        }
    }

    private fun setPlayerTurnOn(player: Player) {
        diceRecyclerViewAdapter.loadNewDices(player.diceToRoll)
        when {
            player.rollCount <= 0 -> {
                contentMainBinding.btnRoll.isEnabled = false
            } else -> {
                contentMainBinding.btnRoll.isEnabled = true
            }
        }

        contentMainBinding.btnRoll.text = getString(
            R.string.btn_roll_in_game,
            player.name,
            player.rollCount.toString()
        )
    }

    private fun setFirstPlayer(firstPlayer: Player) {

        contentMainBinding.apply {
            firstPlayer.name.let {
                tvFirstPlayerLeft.text = it
                tvFirstPlayerRight.text = it
                tvFirstPlayerName.text = it

                firstPlayer.setScores.forEach {
                    when (it.key) {
                        Set.ACES -> {
                            etDiceOneFirstPlayer.setText(it.value.toString())
                        }
                        Set.TWOS -> {
                            etDiceTwoFirstPlayer.setText(it.value.toString())
                        }
                        Set.THREES -> {
                            etDiceThreeFirstPlayer.setText(it.value.toString())
                        }
                        Set.FOURS -> {
                            etDiceFourFirstPlayer.setText(it.value.toString())
                        }
                        Set.FIVES -> {
                            etDiceFiveFirstPlayer.setText(it.value.toString())
                        }
                        Set.SIXES -> {
                            etDiceSixFirstPlayer.setText(it.value.toString())
                        }
                        Set.THREE_OF_A_KIND -> {
                            etThreeOfKindFirstPlayer.setText(it.value.toString())
                        }
                        Set.FOUR_OF_A_KIND -> {
                            etFourOfKindFirstPlayer.setText(it.value.toString())
                        }
                        Set.FULL_HOUSE -> {
                            etFullHouseFirstPlayer.setText(it.value.toString())
                        }
                        Set.SMALL_STRAIGHT -> {
                            etSmallStraightFirstPlayer.setText(it.value.toString())
                        }
                        Set.LARGE_STRAIGHT -> {
                            etLargeStraightFirstPlayer.setText(it.value.toString())
                        }
                        Set.YAHTZEE -> {
                            etYahtzeeFirstPlayer.setText(it.value.toString())
                        }
                        Set.CHANCE -> {
                            etChanceFirstPlayer.setText(it.value.toString())
                        }
                    }
                }
            }
        }
    }
}