package com.iftikar.mediuser.navigation

import android.net.Uri
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.iftikar.mediuser.domain.model.User
import kotlinx.serialization.json.Json

object CustomNavType {
    val UserType = object : NavType<User>(
        isNullableAllowed = false
    ) {
        override fun put(
            bundle: SavedState,
            key: String,
            value: User
        ) {
            bundle.putString(key, Json.Default.encodeToString(value))
        }

        override fun serializeAsValue(value: User): String {
            return Uri.encode(Json.Default.encodeToString(value))
        }

        override fun parseValue(value: String): User {
            return Json.Default.decodeFromString(Uri.decode(value))
        }

        override fun get(
            bundle: SavedState,
            key: String
        ): User? {
            return Json.Default.decodeFromString(bundle.getString(key) ?: return null)
        }



    }
}