package com.citybond.mobile.model

import java.time.Instant

// 只定义本地数据形状，不包含联网、数据库或加密存储实现。
data class Conversation(
    val id: String,
    val ownerUserId: String,
    val title: String,
    val summary: String?,
    val createdAt: Instant,
)

enum class MessageRole { USER, ASSISTANT }
enum class MessageStatus { PENDING, COMPLETE, FAILED }

data class ChatMessage(
    val id: String,
    val conversationId: String,
    val sequence: Int,
    val role: MessageRole,
    val text: String,
    val status: MessageStatus,
    val createdAt: Instant,
    val draftId: String? = null,
)

// 不完整输入与正式债务分开；聊天和表单后续共用同一份草稿。
data class DebtDraft(
    val id: String,
    val conversationId: String,
    val ownerUserId: String,
    val version: Int,
    val projectId: String?,
    val amount: Money?,
    val creditorId: String?,
    val parentDraftId: String? = null,
)
