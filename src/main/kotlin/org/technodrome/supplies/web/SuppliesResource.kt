package org.technodrome.supplies.web

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.technodrome.supplies.model.Supply
import org.technodrome.supplies.service.SupplyService
import org.technodrome.supplies.util.SupplyException

@RestController
class SuppliesResource(val service: SupplyService) {

    @GetMapping("/supplies")
    fun getSupplyByName(@RequestParam(value="name", required = false) name: String?) : List<Supply?> =
            if (name != null) service.findSupplyByName(name) else service.findAllSupplies()

    @GetMapping("/supplies/{id}")
    fun getSupplyByName(@PathVariable id: Long): Supply?  = service.findSupplyById(id)

    @PutMapping("/supplies/{id}")
    fun updateSupply(@PathVariable id: Long, @RequestBody supply: Supply) = service.updateSupply(supply)

    //TODO find better way to not null out values
    @PatchMapping("/supplies/{id}")
    fun patchSupply(@PathVariable id: Long, @RequestBody supply: Supply) = service.updateSupply(id, supply)

    @PostMapping("/supplies")
    fun createSupply(@RequestBody supply: Supply) = service.createSupply(supply)

    @DeleteMapping("/supplies/{id}")
    fun deleteSupplyById(@PathVariable id: Long) = service.deleteSupplyById(id)
}

@ControllerAdvice
class SupplyExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(SupplyException::class)
    fun handleException(ex: SupplyException, request:WebRequest): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }
}