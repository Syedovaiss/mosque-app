package com.ovais.mosqueapp.common

interface Mapper<From, To> {
    fun map(data: From): To
}