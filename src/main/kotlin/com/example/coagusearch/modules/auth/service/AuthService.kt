package com.example.coagusearch.modules.auth.service

import com.example.coagusearch.modules.auth.model.RefreshToken
import com.example.coagusearch.modules.auth.model.RefreshTokenRepository
import com.example.coagusearch.shared.RestException
import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Authentication related service, manages user and refresh token repositories
 */
@Service
class AuthService @Autowired constructor(
        private val userRepository: UserRepository,
        private val refreshTokenRepository: RefreshTokenRepository
) {

    /**
     * Gets user by given id, throws notFound exception if user is not found
     * @param userId Id of the user
     * @return User with given userId
     * @throws RestException if there is no such user
     */
    fun getUserById(userId: Long): User {
        // Get the user for the id
        val userEntity = userRepository.findById(userId)
        return userEntity.orElseThrow {
            RestException(
                "Exception.notFound",
                HttpStatus.UNAUTHORIZED,
                "User",
                userId
            )
        }
    }
    /**
     * Creates a user and returns the created entity
     * @param user User object to create in the database
     * @return The created user entity
     */
    @Transactional
    fun createUser(user: User): User {
        // Make sure the id is 0, and the sex is all lowercase
        return userRepository.save(user.copy(id = 0))
    }

    /**
     * Update the given user in the repository
     * @param user User object to update in the repository
     * @return the entity returned by the save call
     */
    @Transactional
    fun updateUser(user: User): User {
        return userRepository.save(user)
    }

    /**
     * Change the password of the given user
     * @param user User to change password of
     * @param newPasswordEncoded New password with correct encoding
     * @return the user entity returned from the save call
     */
    @Transactional
    fun changePassword(user: User, newPasswordEncoded: String): User =
        updateUser(user.copy(password = newPasswordEncoded))

    /**
     * Save a refresh token for a given user, also invalidated per login
     * user tokens (e.g, FirebaseToken, GoogleAuthToken) since this method means a new login
     * @param user The user to set refresh token for
     * @param refreshToken Refresh token for the given user
     * @return A refresh token instance with correct user and refresh token
     */
    @Transactional
    fun saveRefreshToken(user: User, refreshToken: String): RefreshToken {
        // Get the token entity for the user
        val tokenEntity = refreshTokenRepository.findByUser(user)
        val entity = tokenEntity.orElseGet {
            RefreshToken(
                user,
                refreshToken = null
            )
        }.copy(refreshToken = refreshToken)
        val token = refreshTokenRepository.save(entity)

       // userTokenService.removePerLoginSessionTokensForUser(user)

        return token
    }

    /**
     * Checks whether the given refresh token is valid for that user
     * @param user Current user to check refresh token for
     * @param refreshToken Refresh token to compare with the users current refresh token
     * @return Whether the token is valid
     */
    fun validateRefreshToken(user: User, refreshToken: String?): Boolean {
        // Get the token entity for the user
        val tokenEntity = refreshTokenRepository.findByUser(user)
        val token = tokenEntity.orElseThrow {
            RestException(
                "RefreshToken.invalid",
                HttpStatus.UNAUTHORIZED
            ).apply {
                hiddenMessage = "Cannot find refresh token for user with ${user.identityNumber}"
            }
        }
        return refreshToken != null && token.refreshToken == refreshToken
    }

    /**
     * Revokes the refresh token for the given user
     * and invalidated per login user tokens (e.g FirebaseToken, GoogleAuthToken)
     * @param user User to revoke tokens for
     */
    @Transactional
    fun revokeRefreshToken(user: User) {
        //userTokenService.removePerLoginSessionTokensForUser(user)

        val tokenEntity = refreshTokenRepository.findByUser(user)
        val token = tokenEntity.orElse(null)
        token ?: return
        token.refreshToken = null
        refreshTokenRepository.save(token)
    }


}

