package com.citybond.mobile.model

import java.time.Instant

enum class BusinessKind { PROJECT, DEBT, REPAYMENT, DOCUMENT }
data class BusinessRef(val kind: BusinessKind, val id: String)

// 仅文件元数据；没有本地文件时 localUri 为 null，不伪造可下载地址。
data class Attachment(
    val id: String,
    val business: BusinessRef,
    val fileName: String,
    val mimeType: String,
    val sizeBytes: Long,
    val localUri: String?,
)

enum class DocumentKind { REPAYMENT_NOTICE, FEE_APPLICATION, COST_APPROVAL }
enum class DocumentStatus { DRAFT, PENDING, COMPLETED, VOIDED }

data class BusinessDocument(
    val id: String,
    val number: String,
    val kind: DocumentKind,
    val relatedBusiness: BusinessRef,
    val applicantUserId: String,
    val amount: Money,
    val status: DocumentStatus,
    val createdAt: Instant,
)

enum class TaskKind { OCR, FORM_FILL, EXPORT }
enum class TaskStatus { QUEUED, RUNNING, SUCCEEDED, FAILED, CANCELLED }

data class TaskCapabilities(val canCancel: Boolean, val canRetry: Boolean)

data class BusinessTask(
    val id: String,
    val ownerUserId: String,
    val kind: TaskKind,
    val status: TaskStatus,
    val relatedBusiness: BusinessRef?,
    val progressPercent: Int?, // null 表示未知进度，而不是 0%。
    val capabilities: TaskCapabilities,
    val errorMessage: String?,
    val resultAttachmentId: String?,
)
