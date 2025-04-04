package com.smartplant.smartplantandroid.main.components.auth.services.user

import com.smartplant.smartplantandroid.main.components.auth.entities.UserPrivate
import com.smartplant.smartplantandroid.main.components.auth.repositories.user.UserRepository
import com.smartplant.smartplantandroid.main.components.auth.repositories.user.UserRepositoryImplST
import com.smartplant.smartplantandroid.main.components.auth.services.auth.AuthServiceImplST

class UserServiceImplST: UserService {
    companion object {
        @Volatile
        private var _instance: UserServiceImplST? = null
        val instance: UserServiceImplST get() = _instance ?: throw RuntimeException("Singleton class has not been initialized. Call createInstance() first")
        fun createInstance() = _instance ?: synchronized(this) { _instance ?: UserServiceImplST().also { _instance = it } }
    }

    private val userRepository: UserRepository get() = UserRepositoryImplST.instance

    override suspend fun getMe(): Result<UserPrivate> {
        return userRepository.getMe().onSuccess { user ->
            AuthServiceImplST.instance.user = user
        }
    }
}
