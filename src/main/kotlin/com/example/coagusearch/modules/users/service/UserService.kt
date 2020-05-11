package com.example.coagusearch.modules.users.service

import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserRepository
import com.example.coagusearch.modules.appointment.model.DoctorAppointmentsRepository
import com.example.coagusearch.modules.appointment.response.SingleAppointmentResponse
import com.example.coagusearch.modules.appointment.service.AppointmentDataMapService
import com.example.coagusearch.modules.appointment.service.toEpochSecond
import com.example.coagusearch.modules.base.model.Language
import com.example.coagusearch.modules.bloodOrderAndRecomendation.service.BloodService
import com.example.coagusearch.modules.notification.model.Notification
import com.example.coagusearch.modules.notification.model.NotificationRepository
import com.example.coagusearch.modules.notification.response.NotificationResponse
import com.example.coagusearch.modules.patientData.service.PatientDataService
import com.example.coagusearch.modules.regularMedication.service.DrugService
import com.example.coagusearch.modules.users.model.UserBloodType
import com.example.coagusearch.modules.users.model.UserBodyInfo
import com.example.coagusearch.modules.users.model.UserBodyInfoRepository
import com.example.coagusearch.modules.users.model.UserCaseType
import com.example.coagusearch.modules.users.model.UserDoctorMedicalRelationshipRepository
import com.example.coagusearch.modules.users.model.UserDoctorPatientRelationshipRepository
import com.example.coagusearch.modules.users.model.UserEmergencyInfo
import com.example.coagusearch.modules.users.model.UserEmergencyInfoRepository
import com.example.coagusearch.modules.users.model.UserGender
import com.example.coagusearch.modules.users.model.UserRhType
import com.example.coagusearch.modules.users.request.PatientBodyInfoSaveRequest
import com.example.coagusearch.modules.users.request.UserBodyInfoSaveRequest
import com.example.coagusearch.modules.users.response.DoctorMainScreen
import com.example.coagusearch.modules.users.response.EmergencyPatientDetail
import com.example.coagusearch.modules.users.response.PatientAppointmentTimeResponse
import com.example.coagusearch.modules.users.response.PatientDetailScreen
import com.example.coagusearch.modules.users.response.PatientGeneralInfoResponse
import com.example.coagusearch.modules.users.response.PatientMainScreen
import com.example.coagusearch.modules.users.response.TodayPatientDetail
import com.example.coagusearch.modules.users.response.UserResponse
import com.example.coagusearch.shared.MultiLanguageRepository
import com.example.coagusearch.shared.MultiLanguageString
import com.example.coagusearch.shared.RestException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class UserService @Autowired constructor(
        private val userRepository: UserRepository,
        private val userBodyInfoRepository: UserBodyInfoRepository,
        private val userDoctorPatientRelationshipRepository: UserDoctorPatientRelationshipRepository,
        private val userDoctorMedicalRelationshipRepository: UserDoctorMedicalRelationshipRepository,
        private val doctorAppointmentsRepository: DoctorAppointmentsRepository,
        private val appointmentDataMapService: AppointmentDataMapService,
        private val drugService: DrugService,
        private val bloodService: BloodService,
        private val patientDataService: PatientDataService,
        private val userEmergencyInfoRepository: UserEmergencyInfoRepository,
        private val notificationRepository: NotificationRepository,
        private val multiLanguageRepository: MultiLanguageRepository
) {
    fun getUserById(id: Long): User =
            userRepository.findById(id).orElseThrow {
                RestException("Auth.invalidUser", HttpStatus.BAD_REQUEST)
            }

    fun getBodyInfoByUser(user: User): UserBodyInfo? =
            userBodyInfoRepository.findFirstByUserOrderByIdDesc(user)

    fun updateUser(user: User) =
            userRepository.save(user)

    fun getMyUserResponse(user: User): UserResponse {
        val bodyInfo: UserBodyInfo? = userBodyInfoRepository.findFirstByUserOrderByIdDesc(user)
        if (bodyInfo == null) {
            throw RestException(
                    "Exception.notFound",
                    HttpStatus.NOT_FOUND,
                    "User",
                    user.id!!
            )
        } else {
            return UserResponse(
                    identityNumber = user.identityNumber,
                    type = user.type.toString(),
                    userId = user.id!!,
                    name = bodyInfo?.name,
                    surname = bodyInfo?.surname,
                    birthDay = bodyInfo?.birthDay,
                    birthMonth = bodyInfo?.birthMonth,
                    birthYear = bodyInfo?.birthYear,
                    height = bodyInfo?.height,
                    weight = bodyInfo?.weight,
                    bloodType = if (bodyInfo?.bloodType != null) bodyInfo.bloodType.toString() else null,
                    rhType = if (bodyInfo?.rhType != null) bodyInfo.rhType.toString() else null,
                    gender = if (bodyInfo?.gender != null) bodyInfo.gender.toString() else null
            )
        }
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
                            birthDay = userBodyInfoSaveRequest.birthDay,
                            birthMonth = userBodyInfoSaveRequest.birthMonth,
                            birthYear = userBodyInfoSaveRequest.birthYear,
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
                            birthDay = userBodyInfoSaveRequest.birthDay,
                            birthMonth = userBodyInfoSaveRequest.birthMonth,
                            birthYear = userBodyInfoSaveRequest.birthYear,
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

    fun saveBodyInfoOfPatient(user: User, userBodyInfoSaveRequest: PatientBodyInfoSaveRequest) {
        var bodyInfo = userBodyInfoRepository.findFirstByUserOrderByIdDesc(user)
        if (bodyInfo == null) {
            saveBodyInfo(
                    UserBodyInfo(
                            user = user,
                            name = userBodyInfoSaveRequest.name!!,
                            surname = userBodyInfoSaveRequest.surname!!,
                            birthDay = userBodyInfoSaveRequest.birthDay,
                            birthMonth = userBodyInfoSaveRequest.birthMonth,
                            birthYear = userBodyInfoSaveRequest.birthYear,
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
                            birthDay = userBodyInfoSaveRequest.birthDay,
                            birthMonth = userBodyInfoSaveRequest.birthMonth,
                            birthYear = userBodyInfoSaveRequest.birthYear,
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
    fun getMyPatients(user: User): List<PatientGeneralInfoResponse> {
        if (user.type == UserCaseType.Doctor) {
            return userDoctorPatientRelationshipRepository.findByDoctor(user).map {
                val patientBodyInfo = userBodyInfoRepository.findFirstByUserOrderByIdDesc(it.patient)
                PatientGeneralInfoResponse(
                        userId = it.patient.id!!,
                        name = patientBodyInfo?.name,
                        surname = patientBodyInfo?.surname

                )
            }
        } else if (user.type == UserCaseType.Medical) {
            val doctor = userDoctorMedicalRelationshipRepository.findByMedical(user)
            //TODO: return error if the doctor is null
            if (doctor == null) {
                throw RestException(
                        "Exception.notFound",
                        HttpStatus.NOT_FOUND,
                        "User",
                        user.id!!
                )
            } else {
                return userDoctorPatientRelationshipRepository.findByDoctor(doctor!!.doctor).map {
                    val patientBodyInfo = userBodyInfoRepository.findFirstByUserOrderByIdDesc(it.patient)
                    PatientGeneralInfoResponse(
                            userId = it.patient.id!!,
                            name = patientBodyInfo?.name,
                            surname = patientBodyInfo?.surname
                    )
                }
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

    fun getPatientMainScreen(user: User, language : Language): PatientMainScreen {
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
                            id = nextAppointment.id!!,
                            doctorName = doctorInfo?.name,
                            doctorSurname = doctorInfo?.surname,
                            day = nextAppointment.day,
                            month = nextAppointment.month,
                            minute = nextAppointment.minute,
                            year = nextAppointment.year,
                            hour = nextAppointment.hour
                    ) else null,
                patientNotifications = notificationRepository.findAllByUserAndAddedAtLessThanEqual(user).map {
                    NotificationResponse(
                            notificationString = it.notificationString.stringByLanguage(language)
                    )
                }
        )

    }


    fun getDoctorMainScreen(user: User): DoctorMainScreen {
        val now = LocalDateTime.now(ZoneId.of("Turkey"))
        val nowInstant = now.toInstant(ZoneId.of("Europe/Istanbul").getRules().getOffset(Instant.now()))
        return DoctorMainScreen(
                emergencyPatients = userEmergencyInfoRepository.findByDoctorAndHospitalReachTimeGreaterThanEqual(user,nowInstant)
                        .map {
                    var bodyInfo = userBodyInfoRepository.findFirstByUserOrderByIdDesc(it.patient)
                    EmergencyPatientDetail(
                            patientId = it.patient.id!!,
                            userName = bodyInfo?.name ?: "",
                            userSurname = bodyInfo?.surname ?: "",
                            arrivalHour = PatientAppointmentTimeResponse(
                                    hour = it.hospitalReachTime.atZone(ZoneId.of("Turkey")).hour,
                                    minute = it.hospitalReachTime.atZone(ZoneId.of("Turkey")).minute
                            ),
                            isDataReady =   nowInstant > it.dataReadyTime,
                            isUserAtAmbulance =it.hospitalReachTime >nowInstant
                    )
                },
                todayAppointments = doctorAppointmentsRepository
                        .findAllByDoctor(user).filter {
                            it.year == now.year &&
                                    it.month == now.monthValue &&
                                    it.day == now.dayOfMonth
                        }
                        .map {
                            val patientBodyInfo = userBodyInfoRepository
                                    .findFirstByUserOrderByIdDesc(it.patient)
                            TodayPatientDetail(
                                    patientId = it.patient.id!!,
                                    userName = patientBodyInfo?.name ?: "",
                                    userSurname = patientBodyInfo?.surname ?: "",
                                    appointmentHour = PatientAppointmentTimeResponse(
                                            hour = it.hour,
                                            minute = it.minute
                                    )
                            )
                        }
        )
    }

    //TODO: Check if the user is doctor and patientId's doctor is the user
    fun getPatientDetailScreen(user: User, patientId: Long, language: Language): PatientDetailScreen {
        val patient: User = getUserById(patientId)
        val doctor: User = userDoctorPatientRelationshipRepository.findByPatient(patient)!!.doctor
        //if (doctor.id == user.id) {
            return PatientDetailScreen(
                    patientResponse = getMyUserResponse(patient),
                    userAppointmentResponse = appointmentDataMapService.getByUser(patient, language),
                    lastDataAnalysisTime = patientDataService.getPatientLastAnalysis(patient),
                    patientDrugs = drugService.getByUser(patient, language).userDrugs,
                    previousBloodOrders = bloodService.getPreviousOrdersByPatient(patient, language)
                            .filter{it.kind.equals("Blood")}
            )
            /*
        } else {
            throw RestException(
                    "You do not have permission to reach this resource",
                    HttpStatus.UNAUTHORIZED,
                    "User",
                    user.id!!
            )
        }

          */
    }

    fun getUserByIdentityNumber(userIdentityNumber: String): User?  {
        return userRepository.findByIdentityNumber(userIdentityNumber)
    }

    fun emergencyPatientAdd(patient: User) : Boolean {
        var nowInstant = LocalDateTime.now(ZoneId.of("Turkey"))
                .toInstant(ZoneId.of("Europe/Istanbul").getRules().getOffset(Instant.now()))
        val doctor = userDoctorPatientRelationshipRepository.findByPatient(patient)?.doctor ?: return false
        userEmergencyInfoRepository.save(
                UserEmergencyInfo(
                        doctor= doctor,
                        patient = patient,
                        dataReadyTime = nowInstant.plusSeconds(60*bloodDataTimeDelay),
                        hospitalReachTime = nowInstant.plusSeconds(60*hospitalReachTimeDelay)
                )
        )
        patientDataService.addRandomPatientData(patient,nowInstant.plusSeconds(60*bloodDataTimeDelay))
        addHospital(doctor,patient,nowInstant.plusSeconds(60*hospitalReachTimeDelay))
        addBloodData(doctor,patient,nowInstant.plusSeconds(60*bloodDataTimeDelay))
        return true
    }
    fun addBloodData(doctor: User, patient: User,  addedAt: Instant) {
        val patientBodyInfo = getBodyInfoByUser(patient)
        val patientName = patientBodyInfo?.name
        val patientSurname = patientBodyInfo?.surname

        val multiLanguageString = multiLanguageRepository.save(MultiLanguageString(
                key = UUID.randomUUID().toString(),
                tr_string = "Hastanız ${patientName} ${patientSurname}'ın kan verisi hazır",
                en_string = "Blood data of patient ${patientName} ${patientSurname} is ready"

        ))
        notificationRepository.save(
                Notification(
                        user = doctor,
                        notificationString = multiLanguageString,
                        addedAt = addedAt
                )

        )
    }

    fun addHospital(doctor: User, patient: User,  addedAt: Instant) {
        val patientBodyInfo = getBodyInfoByUser(patient)
        val patientName = patientBodyInfo?.name
        val patientSurname = patientBodyInfo?.surname

        val multiLanguageString = multiLanguageRepository.save(MultiLanguageString(
                key = UUID.randomUUID().toString(),
                tr_string = "Hastanız ${patientName} ${patientSurname}'ın kan verisi hazır",
                en_string = "Blood data of patient ${patientName} ${patientSurname} is ready"

        ))
        notificationRepository.save(
                Notification(
                        user = doctor,
                        notificationString = multiLanguageString,
                        addedAt = addedAt
                )

        )
    }


    companion object {
        val logger = LoggerFactory.getLogger(UserService::class.java)
        val bloodDataTimeDelay = 5L
        val hospitalReachTimeDelay =20L
    }
}
