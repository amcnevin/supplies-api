package org.technodrome.supplies.service

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.technodrome.supplies.model.Supply
import org.technodrome.supplies.repo.SupplyRepository
import org.technodrome.supplies.util.SupplyException
import java.util.*


@Service
class SupplyService(val repo : SupplyRepository) {

    fun findAllSupplies() : List<Supply> = repo.findAll().toList()

    fun findSupplyById(id: Long): Supply? = repo.findByIdOrNull(id)

    fun findSupplyByName(name: String): List<Supply?> = repo.findByName(name)

    @Throws(SupplyException::class)
    fun createSupply(supply: Supply) {
        try {
            repo.save(supply)
        } catch (ex: Exception) {
            throw SupplyException("Unable to Save Supply", ex)
        }
    }

    fun deleteSupply(supply: Supply) = repo.delete(supply)

    fun deleteSupplyById( id: Long) {
        try {
            repo.deleteById(id)
        } catch (ex: EmptyResultDataAccessException) {
            throw SupplyException("Unable to Delete Supply", ex)
        }
    }

    fun updateSupply(supply: Supply) = repo.save(supply)

    @Throws(SupplyException::class)
    fun updateSupply(id: Long, supply: Supply?) {
        // TODO find way not to null out values

    }
}