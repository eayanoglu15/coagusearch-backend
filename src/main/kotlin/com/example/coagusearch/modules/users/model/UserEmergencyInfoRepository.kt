package com.example.coagusearch.modules.users.model

import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant


interface UserEmergencyInfoRepository : JpaRepository<UserEmergencyInfo, Long> {
    fun findByDoctorAndHospitalReachTimeGreaterThanEqual(doctor: User,
                                                         hospitalReachTime : Instant) : List<UserEmergencyInfo>
}
