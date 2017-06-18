package com.applications.whazzup.photomapp.di

import android.content.Context


class DaggerService {

    companion object {
        val SERVICE_NAME = "SERVICE_NAME"

        fun <T> getDaggerComponent(context: Context): T {
            @Suppress("UNCHECKED_CAST")
            return context.getSystemService(SERVICE_NAME) as T
        }
    }
}
