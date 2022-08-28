package com.jones.diceroller

class Dice {
    companion object {
        fun roll(): Int = (1..6).random()
    }
}