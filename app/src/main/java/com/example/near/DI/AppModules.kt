package com.example.near.DI

import android.content.Context
import com.example.near.data.API.CommunityService
import com.example.near.data.API.UserService
import com.example.near.data.datastore.AuthDataStorage
import com.example.near.data.datastore.SessionManager
import com.example.near.data.datastore.SettingsDataStorage
import com.example.near.data.repository.CommunityRepositoryImpl
import com.example.near.data.repository.UserRepositoryImpl
import com.example.near.domain.repository.CommunityRepository
import com.example.near.domain.repository.UserRepository
import com.example.near.service.FcmTokenManager
import com.example.near.service.MyFirebaseMessagingService
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {
    @Provides
    @Singleton
    fun provideUserRepository(userService: UserService, sessionManager: SessionManager): UserRepository {
        return UserRepositoryImpl(userService, sessionManager)
    }

    @Provides
    @Singleton
    fun provideCommunityRepository(communityService: CommunityService, sessionManager: SessionManager): CommunityRepository {
        return CommunityRepositoryImpl(communityService, sessionManager)
    }

    @Provides
    @Singleton
    fun provideAuthDataStorage(
        @ApplicationContext context: Context
    ): AuthDataStorage = AuthDataStorage(context)

    @Provides
    @Singleton
    fun provideSettingsDataStore(
        @ApplicationContext context: Context
    ): SettingsDataStorage = SettingsDataStorage(context)

    @Provides
    @Singleton
    fun provideSessionManager(): SessionManager = SessionManager()

    @Provides
    @Singleton
    fun provideFcmTokenManager(
        userRepository: UserRepository,
        communityRepository: CommunityRepository,
        sessionManager: SessionManager,
        authDataStorage: AuthDataStorage
    ): FcmTokenManager {
        return FcmTokenManager(
            userRepository,
            communityRepository,
            sessionManager,
            authDataStorage
        )
    }

}