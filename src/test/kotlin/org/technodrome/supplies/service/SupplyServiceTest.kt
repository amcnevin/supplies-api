package org.technodrome.supplies.service

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runners.model.MultipleFailureException.assertEmpty
import org.springframework.data.repository.findByIdOrNull
import org.technodrome.supplies.model.Supply
import org.technodrome.supplies.repo.SupplyRepository
import java.util.*

@ExtendWith(MockKExtension::class)
internal class SupplyServiceTest {

    val mockRepo = mockk<SupplyRepository>(relaxUnitFun = true)
    var supplyService = SupplyService(mockRepo)

    val supply1: Supply = Supply("wipes", 10f, 1)
    val supply2: Supply = Supply("diapers", 100f, 2)
    val supply3: Supply = Supply("rash cream", 1f, 3)

    val defaultSupplyList : List<Supply> = listOf(supply1, supply2, supply3)

    @BeforeEach
    fun setUp() {
        assertNotNull(supplyService)
    }

    @Test
    fun testFindAllSupplies() {
        every { mockRepo.findAll() } returns defaultSupplyList
        val supplyList: List<Supply> = supplyService.findAllSupplies()
        assertNotNull(supplyList);
        assertEquals(3, supplyList.size)
        assertEquals( "wipes", supplyList.get(0).name)
        verify(exactly = 1) { mockRepo.findAll()}
        confirmVerified(mockRepo)
    }

    @Test
    fun testFindSupplyById() {
        every { mockRepo.findById(1) } returns Optional.of(defaultSupplyList.get(1))
        val supply: Supply? = supplyService.findSupplyById(1)
        assertNotNull(supply)
        assertEquals("diapers", supply?.name)
        verify(exactly = 1) { mockRepo.findById(1)}
        confirmVerified(mockRepo)
    }

    @Test
    fun testFindSupplyByIdNotFound() {
        every { mockRepo.findById(100) } returns Optional.ofNullable(null)
        val supply : Supply? = supplyService.findSupplyById(100)
        assertNull(supply)
        verify(exactly = 1) { mockRepo.findById(100)}
        confirmVerified(mockRepo)
    }

    @Test
    fun testFindSupplyByName() {
        every { mockRepo.findByName("diapers")} returns listOf(supply2)
        val supplyList : List<Supply?> = supplyService.findSupplyByName("diapers")
        assertNotNull(supplyList)
        assertEquals(1, supplyList.size)
        assertEquals("diapers", supplyList.get(0)?.name)
        verify(exactly = 1) { mockRepo.findByName("diapers")}
        confirmVerified(mockRepo)
    }

    @Test
    fun testFindSupplyByNameNotFound() {
        every { mockRepo.findByName("burp cloth")} returns listOf()
        val supplyList : List<Supply?> = supplyService.findSupplyByName("burp cloth")
        assertNotNull(supplyList)
        assertTrue(supplyList.isEmpty())
        verify(exactly = 1) { mockRepo.findByName("burp cloth")}
        confirmVerified(mockRepo)
    }

    @Test
    fun testCreateSupply() {
        val supplyToBeSaved = Supply("bottles", 10f)
        val savedSupply = Supply("bottles", 10f, 123)
        every { mockRepo.save(any<Supply>())} returns savedSupply
        supplyService.createSupply(supplyToBeSaved)
        verify(exactly = 1) { mockRepo.save(supplyToBeSaved)}
        confirmVerified(mockRepo)
    }

    @Test
    fun testDeleteSupply() {
        val supplyToBeDeleted = Supply("bottles", 10f)
        // no setup as I don't have an answer or a return value
        supplyService.deleteSupply(supplyToBeDeleted)
        verify(exactly = 1) { mockRepo.delete(supplyToBeDeleted)}
        confirmVerified(mockRepo)
    }

    @Test
    fun testDeleteSupplyById() {
        supplyService.deleteSupplyById(100)
        verify(exactly = 1) { mockRepo.deleteById(100)}
        confirmVerified(mockRepo)
    }

    @Test
    fun testUpdateSupply() {
        val supplyToBeSaved = Supply("bottles", 10f, 100)
        every { mockRepo.save(any<Supply>())}  returns supplyToBeSaved
        val supply = supplyService.updateSupply(supplyToBeSaved)
        assertNotNull(supply)
        verify(exactly = 1) { mockRepo.save(supplyToBeSaved)}
        confirmVerified(mockRepo)
    }


}