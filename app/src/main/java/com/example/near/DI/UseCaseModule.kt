package com.example.near.DI

import com.example.near.data.storage.SessionManager
import com.example.near.data.storage.SettingsDataStorageImpl
import com.example.near.domain.shared.storage.AuthDataStorage
import com.example.near.domain.community.repository.CommunityRepository
import com.example.near.domain.user.repository.UserRepository
import com.example.near.domain.user.usecase.friends.AddFriendRequestUseCase
import com.example.near.domain.shared.usecase.GetThemeUseCase
import com.example.near.domain.shared.usecase.GetUserByIdUseCase
import com.example.near.domain.shared.usecase.GetUserUseCase
import com.example.near.domain.user.usecase.auth.LogOutUseCase
import com.example.near.domain.user.usecase.auth.LoginUserUseCase
import com.example.near.domain.user.usecase.friends.RejectFriendRequestUseCase
import com.example.near.domain.user.usecase.friends.RemoveFriendUseCase
import com.example.near.domain.user.usecase.friends.SendFriendRequestUseCase
import com.example.near.domain.shared.usecase.SetThemeUseCase
import com.example.near.domain.community.usecase.GetCommunityUseCase
import com.example.near.domain.community.usecase.LoginCommunityUseCase
import com.example.near.domain.community.usecase.SignUpCommunityUseCase
import com.example.near.domain.user.usecase.GetNotificationOptionsUseCase
import com.example.near.domain.user.usecase.UpdateUserUseCase
import com.example.near.domain.user.usecase.UserCancelSubscribeUseCase
import com.example.near.domain.user.usecase.UserSubscribeUseCase
import com.example.near.domain.user.usecase.auth.LoadUserUseCase
import com.example.near.domain.user.usecase.auth.SignUpUserUseCase
import com.example.near.domain.user.usecase.friends.GetAllFriendsInfoUseCase
import com.example.near.domain.user.usecase.group.CreateGroupUseCase
import com.example.near.domain.user.usecase.group.DeleteGroupUseCase
import com.example.near.domain.user.usecase.group.UpdateGroupUseCase
import com.example.near.domain.shared.usecase.template.CreateTemplateUseCase
import com.example.near.domain.shared.usecase.template.DeleteTemplateUseCase
import com.example.near.domain.shared.usecase.template.UpdateTemplateUseCase
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
    ): LoginUserUseCase {
        return LoginUserUseCase(userRepository, authDataStorage)
    }

    @Provides
    @Singleton
    fun provideCommunityLoginUseCase(
        communityRepository: CommunityRepository,
        authDataStorage: AuthDataStorage,
    ): LoginCommunityUseCase {
        return LoginCommunityUseCase(communityRepository, authDataStorage)
    }

    @Provides
    @Singleton
    fun provideLoadUserUseCase(
        userRepository: UserRepository,
        communityRepository: CommunityRepository,
        authDataStorage: AuthDataStorage,
    ): LoadUserUseCase {
        return LoadUserUseCase(userRepository, communityRepository, authDataStorage)
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
        authDataStorage: AuthDataStorage
    ): LogOutUseCase {
        return LogOutUseCase(authDataStorage)
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
    fun provideGetNotificationOptionsUseCase(userRepository: UserRepository): GetNotificationOptionsUseCase {
        return GetNotificationOptionsUseCase(userRepository)
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
    fun provideGetThemeUseCase(settingsDataStorage: SettingsDataStorageImpl): GetThemeUseCase {
        return GetThemeUseCase(settingsDataStorage)
    }

    @Provides
    @Singleton
    fun provideSetThemeUseCase(settingsDataStorage: SettingsDataStorageImpl): SetThemeUseCase {
        return SetThemeUseCase(settingsDataStorage)
    }

    @Provides
    @Singleton
    fun provideCreateGroupUseCase(userRepository: UserRepository): CreateGroupUseCase {
        return CreateGroupUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateGroupUseCase(userRepository: UserRepository): UpdateGroupUseCase {
        return UpdateGroupUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteGroupUseCase(userRepository: UserRepository): DeleteGroupUseCase {
        return DeleteGroupUseCase(userRepository)
    }

    // --- Template ---

    @Provides
    @Singleton
    fun provideCreateTemplateUseCase(userRepository: UserRepository): CreateTemplateUseCase {
        return CreateTemplateUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateTemplateUseCase(userRepository: UserRepository): UpdateTemplateUseCase {
        return UpdateTemplateUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteTemplateUseCase(userRepository: UserRepository): DeleteTemplateUseCase {
        return DeleteTemplateUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideUserSubscribeUseCase(userRepository: UserRepository): UserSubscribeUseCase {
        return UserSubscribeUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideUserCancelSubscribeUseCase(userRepository: UserRepository): UserCancelSubscribeUseCase {
        return UserCancelSubscribeUseCase(userRepository)
    }
}