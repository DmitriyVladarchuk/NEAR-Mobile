package com.example.near.DI

import android.content.Context
import com.example.near.common.storage.EmailVerificationStorage
import com.example.near.core.network.service.CommunityService
import com.example.near.core.network.service.UserService
import com.example.near.data.storage.AuthDataStorageImpl
import com.example.near.data.storage.SessionManager
import com.example.near.data.storage.SettingsDataStorageImpl
import com.example.near.data.community.repositories.CommunityRepositoryImpl
import com.example.near.data.user.repositories.UserRepositoryImpl
import com.example.near.domain.shared.storage.AuthDataStorage
import com.example.near.domain.community.repository.CommunityRepository
import com.example.near.domain.shared.storage.SettingsDataStorage
import com.example.near.domain.user.repository.UserRepository
import com.example.near.service.FcmTokenManager
import com.example.near.storage.EmailVerificationStorageImpl
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
    fun provideUserRepository(
        userService: UserService,
        sessionManager: SessionManager,
        authDataStorage: AuthDataStorage,
        emailVerificationStorage: EmailVerificationStorage
    ): UserRepository {
        return UserRepositoryImpl(userService, sessionManager, authDataStorage, emailVerificationStorage)
    }

    @Provides
    @Singleton
    fun provideCommunityRepository(
        communityService: CommunityService,
        sessionManager: SessionManager,
        authDataStorage: AuthDataStorage,
        emailVerificationStorage: EmailVerificationStorage
    ): CommunityRepository {
        return CommunityRepositoryImpl(communityService, sessionManager, authDataStorage, emailVerificationStorage)
    }

    @Provides
    @Singleton
    fun provideAuthDataStorage(
        @ApplicationContext context: Context
    ): AuthDataStorage = AuthDataStorageImpl(context)

    @Provides
    @Singleton
    fun provideEmailVerificationStorage(
        @ApplicationContext context: Context
    ): EmailVerificationStorage = EmailVerificationStorageImpl(context)

    @Provides
    @Singleton
    fun provideSettingsDataStore(
        @ApplicationContext context: Context
    ): SettingsDataStorage = SettingsDataStorageImpl(context)

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