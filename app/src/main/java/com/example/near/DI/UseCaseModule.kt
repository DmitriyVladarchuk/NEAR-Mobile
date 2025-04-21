package com.example.near.DI

import com.example.near.data.datastore.AuthDataStorage
import com.example.near.data.datastore.SessionManager
import com.example.near.domain.repository.UserRepository
import com.example.near.domain.usecase.AddFriendRequestUseCase
import com.example.near.domain.usecase.GetUserByIdUseCase
import com.example.near.domain.usecase.GetUserUseCase
import com.example.near.domain.usecase.LogOutUseCase
import com.example.near.domain.usecase.LoginUserUseCase
import com.example.near.domain.usecase.RejectFriendRequestUseCase
import com.example.near.domain.usecase.RemoveFriendUseCase
import com.example.near.domain.usecase.SendFriendRequestUseCase
import com.example.near.domain.usecase.SignUpUserUseCase
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
}