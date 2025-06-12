package com.example.near.DI

import com.example.near.data.datastore.AuthDataStorage
import com.example.near.data.datastore.SessionManager
import com.example.near.data.datastore.SettingsDataStorage
import com.example.near.domain.repository.CommunityRepository
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
import com.example.near.domain.usecase.community.GetCommunityUseCase
import com.example.near.domain.usecase.community.LoginCommunityUseCase
import com.example.near.domain.usecase.community.SignUpCommunityUseCase
import com.example.near.domain.usecase.user.UpdateUserUseCase
import com.example.near.domain.usecase.user.UserCancelSubscribeUseCase
import com.example.near.domain.usecase.user.UserSubscribeUseCase
import com.example.near.domain.usecase.user.auth.LoadUserUseCase
import com.example.near.domain.usecase.user.auth.SignUpUserUseCase
import com.example.near.domain.usecase.user.friends.GetAllFriendsInfoUseCase
import com.example.near.domain.usecase.user.group.CreateGroupUseCase
import com.example.near.domain.usecase.user.group.DeleteGroupUseCase
import com.example.near.domain.usecase.user.group.UpdateGroupUseCase
import com.example.near.domain.usecase.user.template.CreateTemplateUseCase
import com.example.near.domain.usecase.user.template.DeleteTemplateUseCase
import com.example.near.domain.usecase.user.template.UpdateTemplateUseCase
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
    fun provideCommunityLoginUseCase(
        communityRepository: CommunityRepository,
        authDataStorage: AuthDataStorage,
        sessionManager: SessionManager
    ): LoginCommunityUseCase {
        return LoginCommunityUseCase(communityRepository, authDataStorage, sessionManager)
    }

    @Provides
    @Singleton
    fun provideLoadUserUseCase(
        userRepository: UserRepository,
        communityRepository: CommunityRepository,
        authDataStorage: AuthDataStorage,
        sessionManager: SessionManager
    ): LoadUserUseCase {
        return LoadUserUseCase(userRepository, communityRepository, authDataStorage, sessionManager)
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
    fun provideCommunitySignUpUseCase(
        communityRepository: CommunityRepository,
        loginCommunityUseCase: LoginCommunityUseCase
    ): SignUpCommunityUseCase {
        return SignUpCommunityUseCase(communityRepository, loginCommunityUseCase)
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
    fun provideGetCommunityUseCase(communityRepository: CommunityRepository): GetCommunityUseCase {
        return GetCommunityUseCase(communityRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserByIdUseCase(userRepository: UserRepository): GetUserByIdUseCase {
        return GetUserByIdUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateUserUseCase(userRepository: UserRepository): UpdateUserUseCase {
        return UpdateUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllFriendsInfoUseCase(userRepository: UserRepository): GetAllFriendsInfoUseCase {
        return GetAllFriendsInfoUseCase(userRepository)
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

    fun provideCreateGroupUseCase(userRepository: UserRepository): CreateGroupUseCase {
        return CreateGroupUseCase(userRepository)
    }

    fun provideUpdateGroupUseCase(userRepository: UserRepository): UpdateGroupUseCase {
        return UpdateGroupUseCase(userRepository)
    }

    fun provideDeleteGroupUseCase(userRepository: UserRepository): DeleteGroupUseCase {
        return DeleteGroupUseCase(userRepository)
    }

    // --- Template ---

    fun provideCreateTemplateUseCase(userRepository: UserRepository): CreateTemplateUseCase {
        return CreateTemplateUseCase(userRepository)
    }

    fun provideUpdateTemplateUseCase(userRepository: UserRepository): UpdateTemplateUseCase {
        return UpdateTemplateUseCase(userRepository)
    }

    fun provideDeleteTemplateUseCase(userRepository: UserRepository): DeleteTemplateUseCase {
        return DeleteTemplateUseCase(userRepository)
    }


    fun provideUserSubscribeUseCase(userRepository: UserRepository): UserSubscribeUseCase {
        return UserSubscribeUseCase(userRepository)
    }

    fun provideUserCancelSubscribeUseCase(userRepository: UserRepository): UserCancelSubscribeUseCase {
        return UserCancelSubscribeUseCase(userRepository)
    }
}