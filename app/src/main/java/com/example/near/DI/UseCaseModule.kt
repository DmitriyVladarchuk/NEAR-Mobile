package com.example.near.DI

import com.example.near.common.storage.EmailVerificationStorage
import com.example.near.common.usecase.GetCommunityByIdUseCase
import com.example.near.community.usecase.GetEmergencyTypeUseCase
import com.example.near.community.usecase.UpdateCommunityUseCase
import com.example.near.common.storage.AuthDataStorage
import com.example.near.domain.community.repository.CommunityRepository
import com.example.near.domain.user.repository.UserRepository
import com.example.near.domain.user.usecase.friends.AddFriendRequestUseCase
import com.example.near.domain.shared.usecase.GetThemeUseCase
import com.example.near.domain.shared.usecase.GetUserByIdUseCase
import com.example.near.domain.shared.usecase.GetUserUseCase
import com.example.near.domain.user.usecase.auth.LogOutUseCase
import com.example.near.feature.auth.domain.usecase.LoginUserUseCase
import com.example.near.domain.user.usecase.friends.RejectFriendRequestUseCase
import com.example.near.domain.user.usecase.friends.RemoveFriendUseCase
import com.example.near.domain.user.usecase.friends.SendFriendRequestUseCase
import com.example.near.domain.shared.usecase.SetThemeUseCase
import com.example.near.domain.community.usecase.GetCommunityUseCase
import com.example.near.feature.auth.domain.usecase.LoginCommunityUseCase
import com.example.near.feature.auth.domain.usecase.SignUpCommunityUseCase
import com.example.near.domain.shared.storage.SettingsDataStorage
import com.example.near.domain.user.usecase.GetNotificationOptionsUseCase
import com.example.near.domain.user.usecase.UpdateUserUseCase
import com.example.near.domain.user.usecase.UserCancelSubscribeUseCase
import com.example.near.domain.user.usecase.UserSubscribeUseCase
import com.example.near.feature.auth.domain.usecase.LoadUserUseCase
import com.example.near.feature.auth.domain.usecase.SignUpUserUseCase
import com.example.near.domain.user.usecase.friends.GetAllFriendsInfoUseCase
import com.example.near.domain.user.usecase.group.CreateGroupUseCase
import com.example.near.domain.user.usecase.group.DeleteGroupUseCase
import com.example.near.domain.user.usecase.group.UpdateGroupUseCase
import com.example.near.feature.template.domain.usecase.CreateTemplateUseCase
import com.example.near.feature.template.domain.usecase.DeleteTemplateUseCase
import com.example.near.feature.template.domain.usecase.UpdateTemplateUseCase
import com.example.near.feature.auth.domain.repository.CommunityAuthRepository
import com.example.near.feature.auth.domain.repository.UserAuthRepository
import com.example.near.feature.template.domain.repository.TemplateRepository
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
        userAuthRepository: UserAuthRepository,
        authDataStorage: AuthDataStorage,
    ): LoginUserUseCase {
        return LoginUserUseCase(userAuthRepository, authDataStorage)
    }

    @Provides
    fun provideCommunityLoginUseCase(
        communityAuthRepository: CommunityAuthRepository,
        authDataStorage: AuthDataStorage,
    ): LoginCommunityUseCase {
        return LoginCommunityUseCase(communityAuthRepository, authDataStorage)
    }

    @Provides
    fun provideLoadUserUseCase(
        userAuthRepository: UserAuthRepository,
        communityAuthRepository: CommunityAuthRepository,
        authDataStorage: AuthDataStorage,
        emailVerificationStorage: EmailVerificationStorage,
        loginUserUseCase: LoginUserUseCase,
        loginCommunityUseCase: LoginCommunityUseCase
    ): LoadUserUseCase {
        return LoadUserUseCase(
            userRepository = userAuthRepository,
            communityRepository = communityAuthRepository,
            authDataStorage = authDataStorage,
            emailVerificationStorage = emailVerificationStorage,
            loginUserUseCase = loginUserUseCase,
            loginCommunityUseCase = loginCommunityUseCase
        )
    }

    @Provides
    fun provideUserSignUpUseCase(
        userAuthRepository: UserAuthRepository,
        emailVerificationStorage: EmailVerificationStorage
    ): SignUpUserUseCase {
        return SignUpUserUseCase(userAuthRepository, emailVerificationStorage)
    }

    @Provides
    fun provideCommunitySignUpUseCase(
        communityAuthRepository: CommunityAuthRepository,
        emailVerificationStorage: EmailVerificationStorage
    ): SignUpCommunityUseCase {
        return SignUpCommunityUseCase(communityAuthRepository, emailVerificationStorage)
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
    fun provideGetCommunityByIdUseCase(userRepository: UserRepository): GetCommunityByIdUseCase {
        return GetCommunityByIdUseCase(userRepository)
    }

    @Provides
    fun provideGetEmergencyTypeUseCase(communityRepository: CommunityRepository): GetEmergencyTypeUseCase {
        return GetEmergencyTypeUseCase(communityRepository)
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
    fun provideCreateTemplateUseCase(templateRepository: TemplateRepository): CreateTemplateUseCase {
        return CreateTemplateUseCase(templateRepository)
    }

    @Provides
    fun provideUpdateTemplateUseCase(templateRepository: TemplateRepository): UpdateTemplateUseCase {
        return UpdateTemplateUseCase(templateRepository)
    }

    @Provides
    fun provideDeleteTemplateUseCase(templateRepository: TemplateRepository): DeleteTemplateUseCase {
        return DeleteTemplateUseCase(templateRepository)
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