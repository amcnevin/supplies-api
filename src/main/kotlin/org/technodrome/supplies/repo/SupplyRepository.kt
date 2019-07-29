package org.technodrome.supplies.repo

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.technodrome.supplies.model.Supply

@Repository
interface SupplyRepository : CrudRepository<Supply, Long> {

    fun findByName(name: String): List<Supply>

}