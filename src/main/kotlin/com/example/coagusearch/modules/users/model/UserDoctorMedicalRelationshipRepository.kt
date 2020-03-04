package com.example.coagusearch.modules.users.model

import org.springframework.data.jpa.repository.JpaRepository


interface UserDoctorMedicalRelationshipRepository : JpaRepository<UserDoctorMedicalRelationship, Long> {
    fun findByDoctor(doctor: User) : List<UserDoctorMedicalRelationship>
    fun findByMedical(medical:User) : UserDoctorMedicalRelationship?
}
