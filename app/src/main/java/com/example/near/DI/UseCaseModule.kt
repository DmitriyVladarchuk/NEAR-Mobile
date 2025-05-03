package com.example.near.DI

import com.example.near.data.datastore.AuthDataStorage
import com.example.near.data.datastore.SessionManager
import com.example.near.data.datastore.SettingsDataStorage
import com.example.near.domain.repository.UserRepository
import com.example.near.domain.usecase.user.friends.AddFriendRequestUseCase
import com.example.near.domain.usecase.GetThemeUseCase
import com.example.near.domain.usecase.GetUserByIdUseCase
import com.example.near.domain.usecase.GetUserUseCase
import com.example.near.domain.usecase.user.auth.LogOutUseCase
import com.example.near.domain.usecase.user.auth.LoginUserUseCase
import com.example.near.domain.usecase.user.friends.RejectFriendRequestUseCase
import com.example.near.domain.usecase.user.friends.RemoveFriendUseCase
import com.example.near.domain.usecase.user.friends.SendFriendRequestUseCase
import com.example.near.domain.usecase.SetThemeUseCase
import com.example.near.domain.usecase.user.auth.SignUpUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideUserLoginUseCase(
        userRepository: UserRepository,
        authDataStorage: AuthDataStorage,
        sessionManager: SessionManager
    ): LoginUserUseCase {
        return LoginUserUseCase(userRepository, authDataStorage, sessionManager)
    }

    @Provides
    @Singleton
    fun provideUserSignUpUseCase(
        userRepository: UserRepository,
        loginUserUseCase: LoginUserUseCase
    ): SignUpUserUseCase {
        return SignUpUserUseCase(userRepository, loginUserUseCase)
    }

    @Provides
    @Singleton
    fun provideLogOutUseCase(
        sessionManager: SessionManager,
        authDataStorage: AuthDataStorage
    ): LogOutUseCase {
        return LogOutUseCase(sessionManager, authDataStorage)
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(userRepository: UserRepository): GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserByIdUseCase(userRepository: UserRepository): GetUserByIdUseCase {
        return GetUserByIdUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideSendFriendRequestUseCase(userRepository: UserRepository): SendFriendRequestUseCase {
        return SendFriendRequestUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideAddFriendRequestUseCase(userRepository: UserRepository): AddFriendRequestUseCase {
        return AddFriendRequestUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideRejectFriendRequestUseCase(userRepository: UserRepository): RejectFriendRequestUseCase {
        return RejectFriendRequestUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideRemoveFriendUseCase(userRepository: UserRepository): RemoveFriendUseCase {
        return RemoveFriendUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetThemeUseCase(settingsDataStorage: SettingsDataStorage): GetThemeUseCase {
        return GetThemeUseCase(settingsDataStorage)
    }

    @Provides
    @Singleton
    fun provideSetThemeUseCase(settingsDataStorage: SettingsDataStorage): SetThemeUseCase {
        return SetThemeUseCase(settingsDataStorage)
    }
}