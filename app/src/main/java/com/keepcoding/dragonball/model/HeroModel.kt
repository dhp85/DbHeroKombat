package com.keepcoding.dragonball.model

data class HeroModel(
    val id: String,
    val name: String,
    val photo: String,
    var currentLife: Int,
    var maxLife: Int = 100,
    var timesSelected: Int = 0)