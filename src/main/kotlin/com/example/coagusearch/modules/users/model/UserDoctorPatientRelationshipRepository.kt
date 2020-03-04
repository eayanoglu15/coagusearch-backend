package com.example.coagusearch.modules.users.model
import org.springframework.data.jpa.repository.JpaRepository

interface UserDoctorPatientRelationshipRepository : JpaRepository<UserDoctorPatientRelationship, Long> {
    fun findByDoctor(doctor: User) : List<UserDoctorPatientRelationship>
    fun findByPatient(patient:User) : UserDoctorPatientRelationship?
}
