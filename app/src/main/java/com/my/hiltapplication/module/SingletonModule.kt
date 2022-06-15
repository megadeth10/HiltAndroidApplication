package com.my.hiltapplication.module

import android.content.Context
import com.my.hiltapplication.network.Network
import com.my.hiltapplication.noupdate.header.HeaderInterceptor
import com.my.hiltapplication.noupdate.service.CategoryService
import com.my.hiltapplication.noupdate.service.VersionService
import com.my.hiltapplication.room.SpendDao
import com.my.hiltapplication.room.SpendDatabase
import com.my.hiltapplication.room.datatracker.SpendsTrackerDataSource
import com.my.hiltapplication.securepreference.SecureSharedPreferences
import com.my.hiltapplication.store.DataStore
import com.my.hiltapplication.store.TokenStore
import com.my.hiltapplication.store.UserStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun getTokenStore(userStore : UserStore, secureSharedPreferences:SecureSharedPreferences) : TokenStore {
        return TokenStore(userStore, secureSharedPreferences)
    }

    @Provides
    @Singleton
    fun getDataStore(@ApplicationContext context : Context) : DataStore {
        return DataStore(context)
    }

    @Provides
    @Singleton
    fun getUserStore(@ApplicationContext context : Context) : UserStore {
        return UserStore(context)
    }


    @Provides
    @Singleton
    fun getNetwork(tokenStore : TokenStore) : Network {
        return Network(HeaderInterceptor(tokenStore))
    }

    @Provides
    @Singleton
    fun getVersionService(tokenStore : TokenStore) : VersionService {
        return VersionService(getNetwork(tokenStore))
    }

    @Provides
    @Singleton
    fun getCategoryService(tokenStore : TokenStore) : CategoryService {
        return CategoryService(getNetwork(tokenStore))
    }

    @Provides
    @Singleton
    fun getSecurePreference(@ApplicationContext context : Context): SecureSharedPreferences {
        return SecureSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun getSpendDatabase(@ApplicationContext context : Context) : SpendDatabase {
        return SpendDatabase.invoke(context)
    }

    @Provides
    @Singleton
    fun getSpendDAO(spendDatabase:SpendDatabase) : SpendDao {
        return spendDatabase.getSpendDao()
    }

    @Provides
    @Singleton
    fun getSpendDataTracker(spendDatabase:SpendDatabase) : SpendsTrackerDataSource {
        return SpendsTrackerDataSource(spendDatabase.getSpendDao())
    }
}