package io.gryteck.personservice.domain

interface Builder<T> {
    fun build(): T
}

fun <U, V: Builder<U>> create(builder: V, init: (V.() -> Unit) ?= null)
        = builder.apply { init?.invoke(this) }.build()

fun <T> List<Builder<T>>.build(): List<T> = this.map { it.build() }