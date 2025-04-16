package com.example.near.DI

import android.content.Context
import com.example.near.data.API.UserService
import com.example.near.data.datastore.AuthDataStorage
import com.example.near.data.datastore.SessionManager
import com.example.near.data.repository.UserRepositoryImpl
import com.example.near.domain.repository.UserRepository
import com.example.near.domain.usecase.LoginUserUseCase
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
    fun provideAuthDataStorage(
        @ApplicationContext context: Context
    ): AuthDataStorage = AuthDataStorage(context)

    @Provides
    @Singleton
    fun provideSessionManager(): SessionManager = SessionManager()
}