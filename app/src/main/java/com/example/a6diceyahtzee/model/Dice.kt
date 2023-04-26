package com.example.a6diceyahtzee.model

import java.util.UUID

private const val TAG = "Dice"

enum class DieResult(val value: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    NOT_ROLLED(-1)
}

class Dice (
    var id: UUID = UUID.randomUUID(),
    var result: DieResult = DieResult.NOT_ROLLED,
    var savedDie: Boolean = false,
        ) {
    
}