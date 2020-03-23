package com.example.coagusearch.modules.appointment.model

import com.example.coagusearch.modules.base.model.KeyType
import com.example.coagusearch.modules.users.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DoctorAppointmentsRepository : JpaRepository<DoctorAppointments, KeyType> {
   fun findAllByDoctor(doctor: User): List <DoctorAppointments>
   fun findAllByPatient(patient: User): List <DoctorAppointments>
}