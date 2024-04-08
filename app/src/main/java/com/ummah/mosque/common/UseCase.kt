package com.ummah.mosque.common


interface UseCase<R> {
    operator fun invoke(): R
}

interface ParamUseCase<P, R> {
    operator fun invoke(param: P): R
}
interface SuspendUseCase<R> {
    suspend operator fun invoke(): R
}
interface SuspendParameterizedUseCase<P, R> {
    suspend operator fun invoke(param: P): R
}