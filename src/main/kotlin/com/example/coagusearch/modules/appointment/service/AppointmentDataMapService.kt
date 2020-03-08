package com.example.coagusearch.modules.appointment.service

import com.example.coagusearch.modules.appointment.model.DoctorAppointments
import com.example.coagusearch.modules.appointment.model.DoctorAppointmentsRepository
import com.example.coagusearch.modules.appointment.request.SaveAppointmentsForUserRequest
import com.example.coagusearch.modules.appointment.response.DailyAvailablityResponse
import com.example.coagusearch.modules.appointment.response.HoursAvailablityResponse
import com.example.coagusearch.modules.appointment.response.WeeklyAvalibilityResponse
import com.example.coagusearch.modules.users.model.User
import com.example.coagusearch.modules.users.model.UserDoctorPatientRelationshipRepository
import com.example.coagusearch.modules.users.model.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AppointmentDataMapService @Autowired constructor(
        private val doctorPatientRelationshipRepository: UserDoctorPatientRelationshipRepository,
        private val doctorAppointmentsRepository: DoctorAppointmentsRepository
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
        return hours.hours.map { hour->
            hours.minutes.map {minute->
                HoursAvailablityResponse(
                        hour = hour,
                        minute =minute
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
        var doctorsBusyTime = doctorAppointmentsRepository.findAllByDoctor(doctor)
        doctorsBusyTime.map { app->
          response.week.find { it.day == app.day &&
                  it.month==app.month &&
                  it.year == app.year
          }?.hours?.find {  it.hour == app.hour && it.minute == app.minute }?.available = false
        }
        return response
    }
    //TODO: Add doctor patient check
    //TODO: check if patient has an appointment for the future
    fun saveAppointmentForPatient(patient: User,
                                  saveAppointmentsForUserRequest: SaveAppointmentsForUserRequest) {
        val doctor = doctorPatientRelationshipRepository.findByPatient(patient)!!.doctor
        doctorAppointmentsRepository.save(DoctorAppointments(
                doctor=doctor,
                patient = patient,
                day = saveAppointmentsForUserRequest.day,
                month = saveAppointmentsForUserRequest.month,
                year = saveAppointmentsForUserRequest.year,
                hour = saveAppointmentsForUserRequest.hour,
                minute = saveAppointmentsForUserRequest.minute
        ))

    }

    data class Hours(
            var hours: List<Int> = listOf(9, 10, 11, 13, 14, 15, 16),
            var minutes: List<Int> = listOf(0, 20, 40)
    )


}


