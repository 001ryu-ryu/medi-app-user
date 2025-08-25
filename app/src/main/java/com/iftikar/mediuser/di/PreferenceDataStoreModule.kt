package com.iftikar.mediuser.di

import android.content.Context
import com.iftikar.mediuser.util.PreferenceDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceDataStoreModule {
    @Singleton
    @Provides
    fun providePrefDataStore(@ApplicationContext context: Context): PreferenceDataStore {
        return PreferenceDataStore(context)
    }
}