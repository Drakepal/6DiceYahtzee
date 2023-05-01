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
import com.example.a6diceyahtzee.model.YahtzeeConstants
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

                if (firstPlayer.rollCount != YahtzeeConstants.GameValues.ROLLING_ALLOWED) {
                    etDiceOneFirstPlayer.hint = firstPlayer.chooseSet(Set.ACES).toString()
                    etDiceTwoFirstPlayer.hint = firstPlayer.chooseSet(Set.TWOS).toString()
                    etDiceThreeFirstPlayer.hint = firstPlayer.chooseSet(Set.THREES).toString()
                    etDiceFourFirstPlayer.hint = firstPlayer.chooseSet(Set.FOURS).toString()
                    etDiceFiveFirstPlayer.hint = firstPlayer.chooseSet(Set.FIVES).toString()
                    etDiceSixFirstPlayer.hint = firstPlayer.chooseSet(Set.SIXES).toString()
                    etThreeOfKindFirstPlayer.hint = firstPlayer.chooseSet(Set.THREE_OF_A_KIND).toString()
                    etFourOfKindFirstPlayer.hint = firstPlayer.chooseSet(Set.FOUR_OF_A_KIND).toString()
                    etFullHouseFirstPlayer.hint = firstPlayer.chooseSet(Set.FULL_HOUSE).toString()
                    etSmallStraightFirstPlayer.hint = firstPlayer.chooseSet(Set.SMALL_STRAIGHT).toString()
                    etLargeStraightFirstPlayer.hint = firstPlayer.chooseSet(Set.LARGE_STRAIGHT).toString()
                    etYahtzeeFirstPlayer.hint = firstPlayer.chooseSet(Set.YAHTZEE).toString()
                    etChanceFirstPlayer.hint = firstPlayer.chooseSet(Set.CHANCE).toString()
                } else {
                    etDiceOneFirstPlayer.hint = ""
                    etDiceTwoFirstPlayer.hint = ""
                    etDiceThreeFirstPlayer.hint = ""
                    etDiceFourFirstPlayer.hint = ""
                    etDiceFiveFirstPlayer.hint = ""
                    etDiceSixFirstPlayer.hint = ""
                    etThreeOfKindFirstPlayer.hint = ""
                    etFourOfKindFirstPlayer.hint = ""
                    etFullHouseFirstPlayer.hint = ""
                    etSmallStraightFirstPlayer.hint = ""
                    etLargeStraightFirstPlayer.hint = ""
                    etYahtzeeFirstPlayer.hint = ""
                    etChanceFirstPlayer.hint = ""
                }

                etBonusFirstPlayer.setText(
                    getString(
                        R.string.et_bonus_value,
                        firstPlayer.getBonusValue().toString()
                    )
                )

                tvFirstPlayerResult.text = firstPlayer.getTotalResult().toString()
            }
        }
    }

    private fun setSecondPlayer(secondPlayer: Player) {

        contentMainBinding.apply {
            secondPlayer.name.let {
                tvSecondPlayerLeft.text = it
                tvSecondPlayerRight.text = it
                tvSecondPlayerName.text = it

                secondPlayer.setScores.forEach {
                    when (it.key) {
                        Set.ACES -> {
                            etDiceOneSecondPlayer.setText(it.value.toString())
                        }
                        Set.TWOS -> {
                            etDiceTwoSecondPlayer.setText(it.value.toString())
                        }
                        Set.THREES -> {
                            etDiceThreeSecondPlayer.setText(it.value.toString())
                        }
                        Set.FOURS -> {
                            etDiceFourSecondPlayer.setText(it.value.toString())
                        }
                        Set.FIVES -> {
                            etDiceFiveSecondPlayer.setText(it.value.toString())
                        }
                        Set.SIXES -> {
                            etDiceSixSecondPlayer.setText(it.value.toString())
                        }
                        Set.THREE_OF_A_KIND -> {
                            etThreeOfKindSecondPlayer.setText(it.value.toString())
                        }
                        Set.FOUR_OF_A_KIND -> {
                            etFourOfKindSecondPlayer.setText(it.value.toString())
                        }
                        Set.FULL_HOUSE -> {
                            etFullHouseSecondPlayer.setText(it.value.toString())
                        }
                        Set.SMALL_STRAIGHT -> {
                            etSmallStraightSecondPlayer.setText(it.value.toString())
                        }
                        Set.LARGE_STRAIGHT -> {
                            etLargeStraightSecondPlayer.setText(it.value.toString())
                        }
                        Set.YAHTZEE -> {
                            etYahtzeeSecondPlayer.setText(it.value.toString())
                        }
                        Set.CHANCE -> {
                            etChanceSecondPlayer.setText(it.value.toString())
                        }
                    }
                }

                if (secondPlayer.rollCount != YahtzeeConstants.GameValues.ROLLING_ALLOWED) {
                    etDiceOneSecondPlayer.hint = secondPlayer.chooseSet(Set.ACES).toString()
                    etDiceTwoSecondPlayer.hint = secondPlayer.chooseSet(Set.TWOS).toString()
                    etDiceThreeSecondPlayer.hint = secondPlayer.chooseSet(Set.THREES).toString()
                    etDiceFourSecondPlayer.hint = secondPlayer.chooseSet(Set.FOURS).toString()
                    etDiceFiveSecondPlayer.hint = secondPlayer.chooseSet(Set.FIVES).toString()
                    etDiceSixSecondPlayer.hint = secondPlayer.chooseSet(Set.SIXES).toString()
                    etThreeOfKindSecondPlayer.hint = secondPlayer.chooseSet(Set.THREE_OF_A_KIND).toString()
                    etFourOfKindSecondPlayer.hint = secondPlayer.chooseSet(Set.FOUR_OF_A_KIND).toString()
                    etFullHouseSecondPlayer.hint = secondPlayer.chooseSet(Set.FULL_HOUSE).toString()
                    etSmallStraightSecondPlayer.hint = secondPlayer.chooseSet(Set.SMALL_STRAIGHT).toString()
                    etLargeStraightSecondPlayer.hint = secondPlayer.chooseSet(Set.LARGE_STRAIGHT).toString()
                    etYahtzeeSecondPlayer.hint = secondPlayer.chooseSet(Set.YAHTZEE).toString()
                    etChanceSecondPlayer.hint = secondPlayer.chooseSet(Set.CHANCE).toString()
                } else {
                    etDiceOneSecondPlayer.hint = ""
                    etDiceTwoSecondPlayer.hint = ""
                    etDiceThreeSecondPlayer.hint = ""
                    etDiceFourSecondPlayer.hint = ""
                    etDiceFiveSecondPlayer.hint = ""
                    etDiceSixSecondPlayer.hint = ""
                    etThreeOfKindSecondPlayer.hint = ""
                    etFourOfKindSecondPlayer.hint = ""
                    etFullHouseSecondPlayer.hint = ""
                    etSmallStraightSecondPlayer.hint = ""
                    etLargeStraightSecondPlayer.hint = ""
                    etYahtzeeSecondPlayer.hint = ""
                    etChanceSecondPlayer.hint = ""
                }

                etBonusSecondPlayer.setText(
                    getString(
                        R.string.et_bonus_value,
                        secondPlayer.getBonusValue().toString()
                    )
                )

                tvSecondPlayerResult.text = secondPlayer.getTotalResult().toString()

            }
        }
    }
}