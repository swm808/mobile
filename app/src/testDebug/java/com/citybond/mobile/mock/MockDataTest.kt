package com.citybond.mobile.mock

import com.citybond.mobile.model.*
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

class MockDataTest {
    @Test fun businessReferencesResolveAndIdsAreUnique() {
        val organizations = MockData.organizations.map { it.id }.toSet()
        val users = MockData.users.map { it.id }.toSet()
        val groups = mapOf(
            BusinessKind.PROJECT to MockData.projects.map { it.id },
            BusinessKind.DEBT to MockData.debts.map { it.id },
            BusinessKind.REPAYMENT to MockData.repayments.map { it.id },
            BusinessKind.DOCUMENT to MockData.documents.map { it.id },
        )
        val allGroups = groups.values + listOf(MockData.organizations.map { it.id }, MockData.users.map { it.id },
            MockData.attachments.map { it.id }, MockData.tasks.map { it.id },
            MockData.conversations.map { it.id }, MockData.messages.map { it.id }, MockData.drafts.map { it.id })
        allGroups.forEach { assertEquals(it.size, it.toSet().size) }
        fun exists(ref: BusinessRef) = assertTrue(ref.id in groups.getValue(ref.kind))
        MockData.users.forEach { assertTrue(it.organizationId in organizations) }
        MockData.projects.forEach {
            assertTrue(it.debtorId in organizations)
            assertTrue(it.ownerUserId in users)
        }
        MockData.debts.forEach {
            assertTrue(it.projectId in groups.getValue(BusinessKind.PROJECT))
            assertTrue(organizations.containsAll(it.debtorIds + it.creditorIds))
            assertTrue(MockData.attachments.map { file -> file.id }.containsAll(it.attachmentIds))
        }
        MockData.repayments.forEach { exists(BusinessRef(BusinessKind.DEBT, it.debtId)) }
        MockData.cashEntries.forEach { it.debtId?.let { id -> exists(BusinessRef(BusinessKind.DEBT, id)) } }
        MockData.attachments.forEach { exists(it.business) }
        MockData.documents.forEach { exists(it.relatedBusiness); assertTrue(it.applicantUserId in users) }
        MockData.tasks.forEach {
            assertTrue(it.ownerUserId in users)
            it.relatedBusiness?.let(::exists)
            it.resultAttachmentId?.let { id -> assertTrue(MockData.attachments.any { file -> file.id == id }) }
            assertTrue(it.progressPercent == null || it.progressPercent in 0..100)
        }
    }

    @Test fun conversationOwnershipAndMessageOrderRemainConsistent() {
        MockData.conversations.forEach { conversation ->
            assertTrue(MockData.users.any { it.id == conversation.ownerUserId })
            val messages = MockData.messages.filter { it.conversationId == conversation.id }
            assertEquals((1..messages.size).toList(), messages.map { it.sequence })
        }
        MockData.messages.forEach { message ->
            assertTrue(MockData.conversations.any { it.id == message.conversationId })
            message.draftId?.let { id ->
                assertEquals(message.conversationId, MockData.drafts.single { it.id == id }.conversationId)
            }
        }
        MockData.drafts.forEach { draft ->
            assertEquals(draft.ownerUserId, MockData.conversations.single { it.id == draft.conversationId }.ownerUserId)
            assertTrue(draft.projectId == null || MockData.projects.any { it.id == draft.projectId })
            assertTrue(draft.creditorId == null || MockData.organizations.any { it.id == draft.creditorId })
        }
    }

    @Test fun noticeAmountMatchesLinkedRepaymentWithoutFloatingPointLoss() {
        val notice = MockData.documents.single { it.kind == DocumentKind.REPAYMENT_NOTICE }
        val repayment = MockData.repayments.single { it.id == notice.relatedBusiness.id }
        assertEquals(0, notice.amount.yuan.compareTo(repayment.principal.yuan + repayment.interest.yuan))
        assertEquals(BigDecimal("1023333.33"), notice.amount.yuan)
        assertTrue(MockData.debts.filter { it.status == DebtStatus.SETTLED }
            .all { it.outstandingPrincipal.yuan.signum() == 0 })
    }
}
