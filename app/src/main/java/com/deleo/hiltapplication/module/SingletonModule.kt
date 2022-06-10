package com.deleo.hiltapplication.module

import android.content.Context
import android.content.SharedPreferences
import com.deleo.hiltapplication.network.Network
import com.deleo.hiltapplication.noupdate.header.HeaderInterceptor
import com.deleo.hiltapplication.noupdate.service.CategoryService
import com.deleo.hiltapplication.noupdate.service.VersionService
import com.deleo.hiltapplication.securepreference.SecureSharedPreferences
import com.deleo.hiltapplication.store.DataStore
import com.deleo.hiltapplication.store.TokenStore
import com.deleo.hiltapplication.store.UserStore
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
    fun getTokenStore(userStore : UserStore) : TokenStore {
        return TokenStore(userStore)
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
}