package com.alejandro.minidesafiocompose.modelo

enum class Dificultad(val valor: Float) {
    FACIL(0f),NORMAL(1f),DIFICIL(2f);

    companion object {
        fun fromValor(valor: Float): Dificultad {
            return entries.find { it.valor == valor } ?: throw IllegalArgumentException("Valor de dificultad inválido: $valor")
        }
    }

}