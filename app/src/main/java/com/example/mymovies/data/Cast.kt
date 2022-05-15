package com.example.mymovies.data

data class Cast(
    val actors: List<String>
) {
    override fun toString(): String {
        return actors.joinToString()
    }
}
