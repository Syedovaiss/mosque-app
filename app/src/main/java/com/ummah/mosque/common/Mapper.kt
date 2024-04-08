package com.ummah.mosque.common

interface Mapper<From, To> {
    fun map(data: From): To
}