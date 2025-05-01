package com.smartplant.domain_user.service

import com.smartplant.domain_user.entities.UserPrivate
import com.smartplant.domain_user.repository.UserRepository
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    private val userRepository: UserRepository,
) : UserService {
    override val user: UserPrivate? get() = userRepository.user

    override suspend fun getOrFetchMe(): Result<UserPrivate> {
        return if (user != null) Result.success(user!!) else fetchMe()
    }

    override suspend fun fetchMe(): Result<UserPrivate> {
        return userRepository.fetchMe()
    }
}
