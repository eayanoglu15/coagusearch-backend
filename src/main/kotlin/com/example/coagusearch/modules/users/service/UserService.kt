package com.example.coagusearch.modules.users.service

import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserRepository
import com.example.coagusearch.shared.asOption
import arrow.core.Option
import com.example.coagusearch.modules.appointment.model.DoctorAppointmentsRepository
import com.example.coagusearch.modules.appointment.response.SingleAppointmentResponse
import com.example.coagusearch.modules.appointment.service.toEpochSecond
import com.example.coagusearch.modules.users.model.UserBloodType
import com.example.coagusearch.modules.users.model.UserBodyInfo
import com.example.coagusearch.modules.users.model.UserBodyInfoRepository
import com.example.coagusearch.modules.users.model.UserCaseType
import com.example.coagusearch.modules.users.model.UserDoctorMedicalRelationshipRepository
import com.example.coagusearch.modules.users.model.UserDoctorPatientRelationshipRepository
import com.example.coagusearch.modules.users.model.UserGender
import com.example.coagusearch.modules.users.model.UserRhType
import com.example.coagusearch.modules.users.request.UserBodyInfoSaveRequest
import com.example.coagusearch.modules.users.response.PatientMainScreen
import com.example.coagusearch.modules.users.response.UserResponse
import com.example.coagusearch.shared.RestException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class UserService @Autowired constructor(
        private val userRepository: UserRepository,
        private val userBodyInfoRepository: UserBodyInfoRepository,
        private val userDoctorPatientRelationshipRepository: UserDoctorPatientRelationshipRepository,
        private val userDoctorMedicalRelationshipRepository: UserDoctorMedicalRelationshipRepository,
        private val doctorAppointmentsRepository: DoctorAppointmentsRepository
) {
    fun getUserById(id: Long): Option<User> =
            userRepository.findById(id).asOption()


    fun updateUser(user: User) =
            userRepository.save(user)

    fun getMyUserResponse(user: User): UserResponse {
        val bodyInfo: UserBodyInfo? = userBodyInfoRepository.findFirstByUserOrderByIdDesc(user)
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

    fun saveBodyInfo(userBodyInfo: UserBodyInfo) {
        userBodyInfoRepository.save(userBodyInfo)

    }

    fun saveBodyInfoByUser(user: User,
                           userBodyInfoSaveRequest: UserBodyInfoSaveRequest) {
        var bodyInfo = userBodyInfoRepository.findFirstByUserOrderByIdDesc(user)

        if (bodyInfo == null) {
            saveBodyInfo(
                    UserBodyInfo(
                            user = user,
                            name = userBodyInfoSaveRequest.name!!,
                            surname = userBodyInfoSaveRequest.surname!!,
                            dateOfBirth = null,
                            height = userBodyInfoSaveRequest.height,
                            weight = userBodyInfoSaveRequest.weight,
                            bloodType = if (userBodyInfoSaveRequest.bloodType != null)
                                UserBloodType.valueOf(userBodyInfoSaveRequest.bloodType!!) else null,
                            rhType = if (userBodyInfoSaveRequest.rhType != null)
                                UserRhType.valueOf(userBodyInfoSaveRequest.rhType!!) else null,
                            gender = if (userBodyInfoSaveRequest.gender != null)
                                UserGender.valueOf(userBodyInfoSaveRequest.gender!!) else null

                    )
            )
        } else {
            userBodyInfoRepository.deleteById(bodyInfo.id!!)
            saveBodyInfo(
                    UserBodyInfo(
                            user = user,
                            name = userBodyInfoSaveRequest.name!!,
                            surname = userBodyInfoSaveRequest.surname!!,
                            dateOfBirth = null,
                            height = userBodyInfoSaveRequest.height,
                            weight = userBodyInfoSaveRequest.weight,
                            bloodType = if (userBodyInfoSaveRequest.bloodType != null)
                                UserBloodType.valueOf(userBodyInfoSaveRequest.bloodType!!) else null,
                            rhType = if (userBodyInfoSaveRequest.rhType != null)
                                UserRhType.valueOf(userBodyInfoSaveRequest.rhType!!) else null,
                            gender = if (userBodyInfoSaveRequest.gender != null)
                                UserGender.valueOf(userBodyInfoSaveRequest.gender!!) else null

                    )
            )
        }


    }

    //TODO: Add necessary checks and I assumed there is a doctor for medical but this is not the case
    fun getMyPatients(user: User): List<UserResponse> {
        if (user.type == UserCaseType.Doctor) {
            return userDoctorPatientRelationshipRepository.findByDoctor(user).map {
                val patientBodyInfo = userBodyInfoRepository.findFirstByUserOrderByIdDesc(it.patient)
                UserResponse(
                        identityNumber = it.patient.identityNumber,
                        type = it.patient.type.toString(),
                        userId = it.patient.id!!,
                        name = patientBodyInfo?.name,
                        surname = patientBodyInfo?.surname,
                        dateOfBirth = patientBodyInfo?.dateOfBirth.toString(),
                        height = patientBodyInfo?.height,
                        weight = patientBodyInfo?.weight,
                        bloodType = patientBodyInfo?.bloodType.toString(),
                        rhType = patientBodyInfo?.rhType.toString(),
                        gender = patientBodyInfo?.gender.toString()

                )
            }
        } else if (user.type == UserCaseType.Medical) {
            val doctor = userDoctorMedicalRelationshipRepository.findByMedical(user)
            return userDoctorPatientRelationshipRepository.findByDoctor(doctor!!.doctor).map {
                val patientBodyInfo = userBodyInfoRepository.findFirstByUserOrderByIdDesc(it.patient)
                UserResponse(
                        identityNumber = it.patient.identityNumber,
                        type = it.patient.type.toString(),
                        userId = it.patient.id!!,
                        name = patientBodyInfo?.name,
                        surname = patientBodyInfo?.surname,
                        dateOfBirth = patientBodyInfo?.dateOfBirth.toString(),
                        height = patientBodyInfo?.height,
                        weight = patientBodyInfo?.weight,
                        bloodType = patientBodyInfo?.bloodType.toString(),
                        rhType = patientBodyInfo?.rhType.toString(),
                        gender = patientBodyInfo?.gender.toString()

                )
            }
        } else {
            throw RestException(
                    "Exception.notFound",
                    HttpStatus.UNAUTHORIZED,
                    "User",
                    user.id!!
            )
        }

    }

    fun getPatientMainScreen(user: User): PatientMainScreen {
        val now = LocalDateTime.now(ZoneId.of("Turkey")).toEpochSecond()
        var bodyInfo = userBodyInfoRepository.findFirstByUserOrderByIdDesc(user)
        var nextAppointment = doctorAppointmentsRepository.findAllByPatient(user).firstOrNull {
            LocalDateTime.of(it.year, it.month, it.day, it.hour, it.minute).toEpochSecond() > now
        }
        var doctorInfo = if (nextAppointment != null)
            userBodyInfoRepository.findFirstByUserOrderByIdDesc(nextAppointment.doctor) else null
        return PatientMainScreen(
                patientMissingInfo = bodyInfo?.isMissing() ?: true,
                patientNextAppointment = if (nextAppointment != null)
                    SingleAppointmentResponse(
                            id= nextAppointment.id!!,
                            doctorName = doctorInfo?.name,
                            doctorSurname = doctorInfo?.surname,
                            day = nextAppointment.day,
                            month = nextAppointment.month,
                            minute = nextAppointment.minute,
                            year = nextAppointment.year,
                            hour = nextAppointment.hour
                    ) else null
        )

    }


    companion object {
        val logger = LoggerFactory.getLogger(UserService::class.java)
    }
}
