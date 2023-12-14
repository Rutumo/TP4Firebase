package com.eric.tp4firebase.notification

open class Evenements<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentOrNull(): T? {
        return if (hasBeenHandled) {
            null
        }
        else {
            hasBeenHandled = true
            content
        }
    }
}