package com.example.coagusearch.modules.bloodOrderAndRecomendation.model

import org.springframework.data.jpa.repository.JpaRepository


interface BloodOrderRepository : JpaRepository<BloodOrder, Long> {

}
