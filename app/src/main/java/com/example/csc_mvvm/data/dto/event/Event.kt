package com.example.csc_mvvm.data.dto.event

open class Event(var key: String)

class ValueEvent<T>(keyValue: String, var value: T): Event(keyValue)