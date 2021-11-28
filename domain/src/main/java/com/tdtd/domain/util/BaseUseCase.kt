package com.tdtd.domain.util

interface BaseUseCase {

    interface NoParam<R> {
        suspend operator fun invoke(): R
    }

    interface WithParam<P, R> {
        suspend operator fun invoke(param: P): R
    }

    interface WithMultipleParam<P1, P2, R> {
        suspend operator fun invoke(param1: P1, param2: P2): R
    }
}