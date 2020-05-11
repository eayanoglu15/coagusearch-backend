package com.example.coagusearch.modules.notification.service


import com.example.coagusearch.modules.base.model.Language
import com.example.coagusearch.modules.notification.model.Notification
import com.example.coagusearch.modules.notification.model.NotificationRepository
import com.example.coagusearch.modules.notification.request.CallPatientRequest
import com.example.coagusearch.modules.notification.request.NotifyMedicalRequest
import com.example.coagusearch.modules.notification.response.NotificationResponse
import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserDoctorMedicalRelationshipRepository
import com.example.coagusearch.modules.users.service.UserService
import com.example.coagusearch.shared.MultiLanguageRepository
import com.example.coagusearch.shared.MultiLanguageString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class NotificationService @Autowired constructor(
        private val multiLanguageRepository: MultiLanguageRepository,
        private val notificationRepository: NotificationRepository,
        private val userService: UserService,
        private val doctorMedicalRelationshipRepository: UserDoctorMedicalRelationshipRepository
) {

    fun notifyMedical(doctor: User, notifyMedicalRequest: NotifyMedicalRequest) {
        val medicalList = doctorMedicalRelationshipRepository.findByDoctor(doctor).map { it.medical }
        val patient = userService.getUserById(notifyMedicalRequest.patientId)
        val patientBodyInfo = userService.getBodyInfoByUser(patient)
        val doctorBodyInfo = userService.getBodyInfoByUser(doctor)
        val patientName = patientBodyInfo?.name
        val patientSurname = patientBodyInfo?.surname
        val doctorName = doctorBodyInfo?.name
        val doctorSurname = doctorBodyInfo?.surname
        medicalList.map {
            val multiLanguageString = multiLanguageRepository.save(MultiLanguageString(
                    key = UUID.randomUUID().toString(),
                    tr_string = "${doctorName} ${doctorSurname}, sizden ${patientName} ${patientSurname} durumunu incelemenizi istiyor.",
                    en_string = "${doctorName} ${doctorSurname}, wants you to investigate state of patient ${patientName} ${patientSurname}"

            ))
            notificationRepository.save(
                    Notification(
                            user = it,
                            notificationString = multiLanguageString,
                            addedAt = LocalDateTime.now(ZoneId.of("Turkey")).toInstant(ZoneId.of("Europe/Istanbul").getRules().getOffset(Instant.now()))
                    )

            )
        }

    }

    fun callPatient(doctor: User, callPatientRequest: CallPatientRequest) {
        val patient = userService.getUserById(callPatientRequest.patientId)
        val multiLanguageString = multiLanguageRepository.save(MultiLanguageString(
                key = UUID.randomUUID().toString(),
                tr_string = "Doktorunuz sizin yeni bir randevu almanızı istiyor.",
                en_string = "Your doctor wants you to get a new appointment"

        ))
        notificationRepository.save(
                Notification(
                        user = patient,
                        notificationString = multiLanguageString,
                        addedAt = LocalDateTime.now(ZoneId.of("Turkey")).toInstant(ZoneId.of("Europe/Istanbul").getRules().getOffset(Instant.now()))
                )

        )
    }


    fun getNotificationPage(user: User, language: Language): List<NotificationResponse> {

        return notificationRepository.findAllByUserAndAddedAtLessThanEqual(user).map {
            NotificationResponse(
                    notificationString = it.notificationString.stringByLanguage(language)
            )
        }

    }

}
