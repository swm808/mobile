package com.citybond.mobile.ui

import android.net.Uri

object AppRoutes {
    const val HOME = "home"
    const val BUSINESS = "business"
    const val ASSISTANT = "assistant"
    const val TASKS = "tasks"
    const val PROFILE = "profile"
    const val CONNECTION = "connection"
    const val PROJECTS = "projects"
    const val PROJECT_CREATE = "projects/create"
    const val PROJECT_DETAIL = "projects/{projectId}"
    const val PROJECT_EDIT = "projects/{projectId}/edit"
    const val DEBTS = "debts"
    const val DEBT_CREATE = "debts/create"
    const val DEBT_INCOMPLETE = "debts/incomplete"
    const val DEBT_DETAIL = "debts/{debtId}"
    const val DEBT_EDIT = "debts/{debtId}/edit"

    fun projectDetail(projectId: String) = "projects/${Uri.encode(projectId)}"
    fun projectEdit(projectId: String) = "projects/${Uri.encode(projectId)}/edit"
    fun debtDetail(debtId: String) = "debts/${Uri.encode(debtId)}"
    fun debtEdit(debtId: String) = "debts/${Uri.encode(debtId)}/edit"
}
