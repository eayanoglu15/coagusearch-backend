package com.example.coagusearch.modules.users.service

import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserRepository
import com.example.coagusearch.shared.asOption
import arrow.core.Option
import com.example.coagusearch.modules.users.model.UserBodyInfo
import com.example.coagusearch.modules.users.model.UserBodyInfoRepository
import com.example.coagusearch.modules.users.response.UserResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
        private val userRepository: UserRepository,
        private val userBodyInfoRepository: UserBodyInfoRepository
) {
    fun getUserById(id: Long): Option<User> =
            userRepository.findById(id).asOption()


    fun updateUser(user: User) =
            userRepository.save(user)

    fun getMyUserResponse(user: User): UserResponse {
        val bodyInfo:UserBodyInfo? = userBodyInfoRepository.findByUser(user)
        return UserResponse(
                identityNumber = user.identityNumber,
                type = user.type.toString(),
                userId = user.id!!,
                name = bodyInfo?.name,
                surname = bodyInfo?.surname,
                dateOfBirth = bodyInfo?.dateOfBirth.toString(),
                height = bodyInfo?.height,
                weight = bodyInfo?.weight,
                bloodType = bodyInfo?.bloodType.toString(),
                rhType = bodyInfo?.rhType.toString(),
                gender = bodyInfo?.gender.toString()
        )
    }


    companion object {
        val logger = LoggerFactory.getLogger(UserService::class.java)
    }
}
