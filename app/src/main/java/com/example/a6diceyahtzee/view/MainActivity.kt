package com.example.a6diceyahtzee.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.viewModels
import com.example.a6diceyahtzee.R
import com.example.a6diceyahtzee.databinding.ActivityMainBinding
import com.example.a6diceyahtzee.databinding.ContentMainBinding
import com.example.a6diceyahtzee.model.Dice
import com.example.a6diceyahtzee.model.Player
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

    private fun clearForm(linearLayoutScores: LinearLayout) {
        TODO("Not yet implemented")
    }

    override fun onSaveClick(diceItem: Dice) {
        TODO("Not yet implemented")
    }
}