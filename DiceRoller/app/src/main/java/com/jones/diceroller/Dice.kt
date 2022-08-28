package com.jones.diceroller

class Dice(val faces: Int) {

    fun roll() : Int = (1..faces).random()
}