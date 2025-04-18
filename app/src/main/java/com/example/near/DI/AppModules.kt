package com.example.near.DI

import android.content.Context
import com.example.near.data.API.UserService
import com.example.near.data.datastore.AuthDataStorage
import com.example.near.data.datastore.SessionManager
import com.example.near.data.repository.UserRepositoryImpl
import com.example.near.domain.repository.UserRepository
import com.example.near.domain.usecase.GetUserByIdUseCase
import com.example.near.domain.usecase.GetUserUseCase
import com.example.near.domain.usecase.LogOutUseCase
import com.example.near.domain.usecase.LoginUserUseCase
import com.example.near.domain.usecase.SendFriendRequestUseCase
import com.example.near.domain.usecase.SignUpUserUseCase
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
    fun provideUserRepository(userService: UserService): UserRepository {
        return UserRepositoryImpl(userService)
    }

    @Provides
    @Singleton
    fun provideUserLoginUseCase(userRepository: UserRepository, authDataStorage: AuthDataStorage): LoginUserUseCase {
        return LoginUserUseCase(userRepository, authDataStorage)
    }

    @Provides
    @Singleton
    fun provideUserSignUpUseCase(userRepository: UserRepository): SignUpUserUseCase {
        return SignUpUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideLogOutUseCase(sessionManager: SessionManager, authDataStorage: AuthDataStorage): LogOutUseCase {
        return LogOutUseCase(sessionManager, authDataStorage)
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(userRepository: UserRepository, sessionManager: SessionManager): GetUserUseCase {
        return GetUserUseCase(userRepository, sessionManager)
    }

    @Provides
    @Singleton
    fun provideGetUserByIdUseCase(userRepository: UserRepository, sessionManager: SessionManager): GetUserByIdUseCase {
        return GetUserByIdUseCase(userRepository, sessionManager)
    }

    @Provides
    @Singleton
    fun provideSendFriendRequestUseCase(userRepository: UserRepository, sessionManager: SessionManager): SendFriendRequestUseCase {
        return SendFriendRequestUseCase(userRepository, sessionManager)
    }

    @Provides
    @Singleton
    fun provideAuthDataStorage(
        @ApplicationContext context: Context
    ): AuthDataStorage = AuthDataStorage(context)

    @Provides
    @Singleton
    fun provideSessionManager(): SessionManager = SessionManager()
}