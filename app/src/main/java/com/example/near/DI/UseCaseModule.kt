package com.example.near.DI

import com.example.near.common.storage.EmailVerificationStorage
import com.example.near.community.usecase.UpdateCommunityUseCase
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
import com.example.near.domain.shared.storage.SettingsDataStorage
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
import com.example.near.user.usecase.GetAllCommunitiesUseCase
import com.example.near.user.usecase.SearchCommunityUseCase
import com.example.near.user.usecase.SearchUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideUserLoginUseCase(
        userRepository: UserRepository,
        authDataStorage: AuthDataStorage,
    ): LoginUserUseCase {
        return LoginUserUseCase(userRepository, authDataStorage)
    }

    @Provides
    fun provideCommunityLoginUseCase(
        communityRepository: CommunityRepository,
        authDataStorage: AuthDataStorage,
    ): LoginCommunityUseCase {
        return LoginCommunityUseCase(communityRepository, authDataStorage)
    }

    @Provides
    fun provideLoadUserUseCase(
        userRepository: UserRepository,
        communityRepository: CommunityRepository,
        authDataStorage: AuthDataStorage,
        emailVerificationStorage: EmailVerificationStorage,
        loginUserUseCase: LoginUserUseCase,
        loginCommunityUseCase: LoginCommunityUseCase
    ): LoadUserUseCase {
        return LoadUserUseCase(
            userRepository = userRepository,
            communityRepository = communityRepository,
            authDataStorage = authDataStorage,
            emailVerificationStorage = emailVerificationStorage,
            loginUserUseCase = loginUserUseCase,
            loginCommunityUseCase = loginCommunityUseCase
        )
    }

    @Provides
    fun provideUserSignUpUseCase(
        userRepository: UserRepository,
        emailVerificationStorage: EmailVerificationStorage
    ): SignUpUserUseCase {
        return SignUpUserUseCase(userRepository, emailVerificationStorage)
    }

    @Provides
    fun provideCommunitySignUpUseCase(
        communityRepository: CommunityRepository,
        emailVerificationStorage: EmailVerificationStorage
    ): SignUpCommunityUseCase {
        return SignUpCommunityUseCase(communityRepository, emailVerificationStorage)
    }

    @Provides
    fun provideLogOutUseCase(
        authDataStorage: AuthDataStorage
    ): LogOutUseCase {
        return LogOutUseCase(authDataStorage)
    }

    @Provides
    fun provideGetUserUseCase(userRepository: UserRepository): GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    @Provides
    fun provideGetCommunityUseCase(communityRepository: CommunityRepository): GetCommunityUseCase {
        return GetCommunityUseCase(communityRepository)
    }

    @Provides
    fun provideGetUserByIdUseCase(userRepository: UserRepository): GetUserByIdUseCase {
        return GetUserByIdUseCase(userRepository)
    }

    @Provides
    fun provideUpdateUserUseCase(userRepository: UserRepository): UpdateUserUseCase {
        return UpdateUserUseCase(userRepository)
    }

    @Provides
    fun provideUpdateCommunityUseCase(communityRepository: CommunityRepository): UpdateCommunityUseCase {
        return UpdateCommunityUseCase(communityRepository)
    }

    @Provides
    fun provideGetNotificationOptionsUseCase(userRepository: UserRepository): GetNotificationOptionsUseCase {
        return GetNotificationOptionsUseCase(userRepository)
    }

    @Provides
    fun provideGetAllFriendsInfoUseCase(userRepository: UserRepository): GetAllFriendsInfoUseCase {
        return GetAllFriendsInfoUseCase(userRepository)
    }

    @Provides
    fun provideSendFriendRequestUseCase(userRepository: UserRepository): SendFriendRequestUseCase {
        return SendFriendRequestUseCase(userRepository)
    }

    @Provides
    fun provideAddFriendRequestUseCase(userRepository: UserRepository): AddFriendRequestUseCase {
        return AddFriendRequestUseCase(userRepository)
    }

    @Provides
    fun provideRejectFriendRequestUseCase(userRepository: UserRepository): RejectFriendRequestUseCase {
        return RejectFriendRequestUseCase(userRepository)
    }

    @Provides
    fun provideRemoveFriendUseCase(userRepository: UserRepository): RemoveFriendUseCase {
        return RemoveFriendUseCase(userRepository)
    }

    @Provides
    fun provideGetThemeUseCase(settingsDataStorage: SettingsDataStorage): GetThemeUseCase {
        return GetThemeUseCase(settingsDataStorage)
    }

    @Provides
    fun provideSetThemeUseCase(settingsDataStorage: SettingsDataStorage): SetThemeUseCase {
        return SetThemeUseCase(settingsDataStorage)
    }

    @Provides
    fun provideCreateGroupUseCase(userRepository: UserRepository): CreateGroupUseCase {
        return CreateGroupUseCase(userRepository)
    }

    @Provides
    fun provideUpdateGroupUseCase(userRepository: UserRepository): UpdateGroupUseCase {
        return UpdateGroupUseCase(userRepository)
    }

    @Provides
    fun provideDeleteGroupUseCase(userRepository: UserRepository): DeleteGroupUseCase {
        return DeleteGroupUseCase(userRepository)
    }

    // --- Template ---

    @Provides
    fun provideCreateTemplateUseCase(userRepository: UserRepository): CreateTemplateUseCase {
        return CreateTemplateUseCase(userRepository)
    }

    @Provides
    fun provideUpdateTemplateUseCase(userRepository: UserRepository): UpdateTemplateUseCase {
        return UpdateTemplateUseCase(userRepository)
    }

    @Provides
    fun provideDeleteTemplateUseCase(userRepository: UserRepository): DeleteTemplateUseCase {
        return DeleteTemplateUseCase(userRepository)
    }

    @Provides
    fun provideGetAllCommunitiesUseCase(userRepository: UserRepository): GetAllCommunitiesUseCase {
        return GetAllCommunitiesUseCase(userRepository)
    }

    @Provides
    fun provideSearchCommunityUseCase(userRepository: UserRepository): SearchCommunityUseCase {
        return SearchCommunityUseCase(userRepository)
    }

    @Provides
    fun provideSearchUsersUseCase(userRepository: UserRepository): SearchUsersUseCase {
        return SearchUsersUseCase(userRepository)
    }

    @Provides
    fun provideUserSubscribeUseCase(userRepository: UserRepository): UserSubscribeUseCase {
        return UserSubscribeUseCase(userRepository)
    }

    @Provides
    fun provideUserCancelSubscribeUseCase(userRepository: UserRepository): UserCancelSubscribeUseCase {
        return UserCancelSubscribeUseCase(userRepository)
    }
}