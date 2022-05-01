package com.ahmed.data.core

object Keys {
    init {
        System.loadLibrary("api-keys")
    }

    external fun getAPIKey(): String
    external fun getBaseUrl(): String
}