package com.example.coagusearch.modules.appointment.service

import com.example.coagusearch.modules.appointment.model.DoctorAppointments
import com.example.coagusearch.modules.appointment.model.DoctorAppointmentsRepository
import com.example.coagusearch.modules.appointment.request.DeleteAppointmentsForUserRequest
import com.example.coagusearch.modules.appointment.request.SaveAppointmentsForUserRequest
import com.example.coagusearch.modules.appointment.response.DailyAvailablityResponse
import com.example.coagusearch.modules.appointment.response.HoursAvailablityResponse
import com.example.coagusearch.modules.appointment.response.SingleAppointmentResponse
import com.example.coagusearch.modules.appointment.response.UserAppointmentResponse
import com.example.coagusearch.modules.appointment.response.WeeklyAvalibilityResponse
import com.example.coagusearch.modules.base.model.Language
import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserBodyInfoRepository
import com.example.coagusearch.modules.users.model.UserDoctorPatientRelationshipRepository
import com.example.coagusearch.shared.RestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@Service
class AppointmentDataMapService @Autowired constructor(
        private val doctorPatientRelationshipRepository: UserDoctorPatientRelationshipRepository,
        private val doctorAppointmentsRepository: DoctorAppointmentsRepository,
        private val userBodyInfoRepository: UserBodyInfoRepository
) {
    fun getNextSevenDaysPeriod(): WeeklyAvalibilityResponse {
        var weeklyList = getSevenDay()
        return WeeklyAvalibilityResponse(
                week = weeklyList.map { day ->
                    DailyAvailablityResponse(
                            day = day.dayOfMonth,
                            month = day.monthValue,
                            year = day.year,
                            hours = getHoursOfDay()
                    )
                }
        )
    }

    private fun getHoursOfDay(): List<HoursAvailablityResponse> {
        val hours = Hours()
        return hours.hours.map { hour ->
            hours.minutes.map { minute ->
                HoursAvailablityResponse(
                        hour = hour,
                        minute = minute
                )
            }
        }.toList().flatten()

    }

    fun getSevenDay(): List<LocalDate> {
        var today = LocalDate.now()
        var weeklyList = mutableListOf<LocalDate>()
        var day = today.plusDays(1)
        while (weeklyList.size < 7) {
            if (day.dayOfWeek.value < 6) {
                weeklyList.add(day)
            }
            day = day.plusDays(1)
        }
        return weeklyList
    }

    //TODO: Add doctor patient check
    fun getAppointmentTimes(patient: User)
            : WeeklyAvalibilityResponse {
        var response = getNextSevenDaysPeriod()
        var doctor = doctorPatientRelationshipRepository.findByPatient(patient)!!.doctor
        var bodyInfo = userBodyInfoRepository.findFirstByUserOrderByIdDesc(doctor)
        var doctorsBusyTime = doctorAppointmentsRepository.findAllByDoctor(doctor)
        doctorsBusyTime.map { app ->
            response.week.find {
                it.day == app.day &&
                        it.month == app.month &&
                        it.year == app.year
            }?.hours?.find { it.hour == app.hour && it.minute == app.minute }?.available = false
        }
        response.doctorName = bodyInfo?.name
        response.doctorSurname = bodyInfo?.surname
        return response
    }

    //TODO: Add doctor patient check
    //TODO: check if patient has an appointment for the future
    fun saveAppointmentForPatient(patient: User,
                                  saveAppointmentsForUserRequest: SaveAppointmentsForUserRequest) {
        val doctor = doctorPatientRelationshipRepository.findByPatient(patient)!!.doctor
        doctorAppointmentsRepository.save(DoctorAppointments(
                doctor = doctor,
                patient = patient,
                day = saveAppointmentsForUserRequest.day,
                month = saveAppointmentsForUserRequest.month,
                year = saveAppointmentsForUserRequest.year,
                hour = saveAppointmentsForUserRequest.hour,
                minute = saveAppointmentsForUserRequest.minute
        ))

    }

    fun getByUser(user: User, language: Language): UserAppointmentResponse {
        val now = LocalDateTime.now(ZoneId.of("Europe/Istanbul")).toEpochSecond()
        val doctor = doctorPatientRelationshipRepository.findByPatient(user)?.doctor
        var doctorInfo = if (doctor != null)
            userBodyInfoRepository.findFirstByUserOrderByIdDesc(doctor) else null
        val allAppointments = doctorAppointmentsRepository.findAllByPatient(user)
        val nextAppointments = allAppointments.firstOrNull {
            LocalDateTime.of(it.year, it.month, it.day, it.hour, it.minute).toEpochSecond() > now
        }
        val oldAppointments = allAppointments.filter {
            LocalDateTime.of(it.year, it.month, it.day, it.hour, it.minute).toEpochSecond() < now
        }
        return UserAppointmentResponse(
                nextAppointment = if (nextAppointments != null)
                    SingleAppointmentResponse(
                            id= nextAppointments.id!!,
                            doctorName = doctorInfo?.name,
                            doctorSurname = doctorInfo?.surname,
                            day = nextAppointments.day,
                            month = nextAppointments.month,
                            minute = nextAppointments.minute,
                            year = nextAppointments.year,
                            hour = nextAppointments.hour
                    ) else null,
                oldAppointment = oldAppointments.map {
                    SingleAppointmentResponse(
                            id = it.id!!,
                            doctorName = doctorInfo?.name,
                            doctorSurname = doctorInfo?.surname,
                            day = it.day,
                            month = it.month,
                            minute = it.minute,
                            year = it.year,
                            hour = it.hour
                    )
                }


        )

    }

    fun deleteAppointmentForPatient(user: User,
                                    deleteAppointmentsForUserRequest: DeleteAppointmentsForUserRequest) {
        var appointment = doctorAppointmentsRepository.findById(deleteAppointmentsForUserRequest.appointmentId)
                .map { it }
                .orElseThrow {  RestException(
                        "Exception.notFound",
                        HttpStatus.UNAUTHORIZED,
                        "Appointment",
                        deleteAppointmentsForUserRequest.appointmentId
                ) }
        doctorAppointmentsRepository.deleteById(appointment.id!!)


    }

    data class Hours(
            var hours: List<Int> = listOf(9, 10, 11, 13, 14, 15, 16),
            var minutes: List<Int> = listOf(0, 20, 40)
    )


}

fun LocalDateTime.toEpochSecond(): Long {
    val zone = ZoneId.of("Europe/Istanbul")
    return this.toEpochSecond(zone.rules.getOffset(this))

}


