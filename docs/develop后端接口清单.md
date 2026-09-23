# develop 后端运行时接口合同清单

代码基线：`origin/develop`，提交 `a7da695f46a542ebb5a3bfcab87f4238df89f2fa`。生成日期：2026-09-11。

从固定提交构造 FastAPI 应用并读取 OpenAPI，得到 **418 个路径、509 个 HTTP 操作、850 个 Schema**。生成过程未进入 lifespan、未连接数据库，也未调用业务接口；实际部署仍需核对环境配置、反向代理前缀和数据权限。

业务接口默认前缀 `/api/v1`；`/` 与 `/health` 为公开根路径。权限列来自同一提交的路由权限解析器：`公开` 表示显式无需登录，`@...` 表示运行时按参数或任务类型继续解析，其他权限码仍会叠加对象数据范围校验。

请求列只摘要 OpenAPI 中的 path/query 参数和请求体 Schema，`*` 表示必填；响应列列出首个 2xx 响应。完整字段约束以该提交的 OpenAPI Schema 和 Pydantic 源码为准。

## backend/app/application.py

共 2 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/` | 公开 | — | 200 — | [read_root](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/application.py#L610) |
| GET | `/health` | 公开 | — | 200 — | [health_check](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/application.py#L615) |

## backend/app/modules/ai_dispatch/api.py

共 7 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/system/ai-servers` | `ai_settings.view` | — | 200 `AiServerListResponse` | [get_ai_servers](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ai_dispatch/api.py#L27) |
| POST | `/api/v1/system/ai-servers` | `ai_settings.edit` | body `AiServerPayload` | 200 `AiServerResponse` | [create_ai_server](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ai_dispatch/api.py#L39) |
| GET | `/api/v1/system/ai-servers/status` | `ai_settings.view` | — | 200 `AiServerListResponse` | [get_ai_server_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ai_dispatch/api.py#L33) |
| DELETE | `/api/v1/system/ai-servers/{server_id}` | `ai_settings.delete` | `server_id`(path*) | 204 — | [delete_ai_server](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ai_dispatch/api.py#L60) |
| PUT | `/api/v1/system/ai-servers/{server_id}` | `ai_settings.edit` | `server_id`(path*)；body `AiServerPayload` | 200 `AiServerResponse` | [update_ai_server](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ai_dispatch/api.py#L48) |
| POST | `/api/v1/system/ai-servers/{server_id}/test` | `ai_settings.test` | `server_id`(path*) | 200 `AiTestResponse` | [test_ai_server](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ai_dispatch/api.py#L72) |
| GET | `/api/v1/system/ai-stats` | `ai_settings.view` | `start_hour`(query), `end_hour`(query) | 200 `AiStatsResponse` | [get_ai_stats](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ai_dispatch/api.py#L87) |

## backend/app/modules/announcements/api.py

共 7 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/announcements` | `announcements.view` | `page`(query), `page_size`(query) | 200 `AnnouncementListResponse` | [read_announcements](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/announcements/api.py#L21) |
| POST | `/api/v1/announcements` | `announcements.edit` | body `AnnouncementPayload` | 201 `AnnouncementResponse` | [add_announcement](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/announcements/api.py#L43) |
| GET | `/api/v1/announcements/manage` | `announcements.edit` | `page`(query), `page_size`(query) | 200 `AnnouncementListResponse` | [read_announcements_for_management](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/announcements/api.py#L32) |
| DELETE | `/api/v1/announcements/{announcement_id}` | `announcements.edit` | `announcement_id`(path*) | 204 — | [remove_announcement](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/announcements/api.py#L80) |
| PUT | `/api/v1/announcements/{announcement_id}` | `announcements.edit` | `announcement_id`(path*)；body `AnnouncementPayload` | 200 `AnnouncementResponse` | [edit_announcement](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/announcements/api.py#L52) |
| POST | `/api/v1/announcements/{announcement_id}/publish` | `announcements.edit` | `announcement_id`(path*) | 200 `AnnouncementResponse` | [publish_announcement_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/announcements/api.py#L62) |
| POST | `/api/v1/announcements/{announcement_id}/withdraw` | `announcements.edit` | `announcement_id`(path*) | 200 `AnnouncementResponse` | [withdraw_announcement_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/announcements/api.py#L71) |

## backend/app/modules/assistant/api.py

共 30 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| POST | `/api/v1/assistant/actions` | `assistant.view` | body `AssistantActionRequest` | 200 `AssistantActionResponse` | [post_assistant_action](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L154) |
| GET | `/api/v1/assistant/conversations` | `assistant.view` | — | 200 列表[`AssistantConversationSummaryResponse`] | [list_free_chat_conversations](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L284) |
| POST | `/api/v1/assistant/conversations` | `assistant.view` | body `AssistantConversationCreateRequest` / `null` | 201 `AssistantConversationResponse` | [create_free_chat_conversation](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L267) |
| DELETE | `/api/v1/assistant/conversations/{conversation_id}` | `assistant.view` | `conversation_id`(path*) | 204 — | [delete_free_chat_conversation](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L307) |
| GET | `/api/v1/assistant/conversations/{conversation_id}` | `assistant.view` | `conversation_id`(path*) | 200 `AssistantConversationResponse` | [read_free_chat_conversation](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L295) |
| POST | `/api/v1/assistant/conversations/{conversation_id}/messages` | `assistant.view` | `conversation_id`(path*)；body `AssistantConversationMessageRequest` | 200 `AssistantConversationResponse` | [post_free_chat_message](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L339) |
| PUT | `/api/v1/assistant/conversations/{conversation_id}/transcript` | `assistant.view` | `conversation_id`(path*)；body `AssistantConversationTranscriptRequest` | 200 `AssistantConversationResponse` | [sync_ai_conversation_transcript](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L320) |
| POST | `/api/v1/assistant/loan-contracts/archive` | `debt.edit` | body `LoanContractArchiveRequest` | 200 `LoanContractArchiveResponse` | [archive_existing_loan_contract](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L208) |
| POST | `/api/v1/assistant/loan-contracts/debt-candidates` | `debt.view` | body `LoanContractDebtSearchRequest` | 200 `LoanContractDebtSearchResponse` | [search_loan_contract_debt_candidates](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L188) |
| POST | `/api/v1/assistant/loan-contracts/summary` | `assistant.view` | body `LoanContractDocumentRequest` | 200 `LoanContractSummaryResponse` | [create_loan_contract_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L163) |
| POST | `/api/v1/assistant/query-summary` | `assistant.view` | body `AssistantQuerySummaryRequest` | 200 `AssistantQuerySummaryResponse` | [summarize_query_result](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L250) |
| POST | `/api/v1/assistant/route` | `assistant.view` | body `AssistantDialogueRouteRequest` | 200 `AssistantDialogueRouteResponse` | [route_dialogue](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L222) |
| POST | `/api/v1/assistant/sessions` | `assistant.view` | body `AssistantSessionCreateRequest` / `null` | 201 `AssistantSessionResponse` | [create_assistant_session](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L375) |
| GET | `/api/v1/assistant/sessions/{session_id}` | `assistant.view` | `session_id`(path*) | 200 `AssistantSessionResponse` | [read_assistant_session](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L435) |
| POST | `/api/v1/assistant/sessions/{session_id}/cancel` | `assistant.view` | `session_id`(path*)；body `AssistantConfirmRequest` | 200 `AssistantSessionResponse` | [cancel_assistant_session](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L823) |
| POST | `/api/v1/assistant/sessions/{session_id}/confirm` | `assistant.view` | `session_id`(path*)；body `AssistantConfirmRequest` | 200 `AssistantSessionResponse` | [confirm_assistant_session](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L660) |
| POST | `/api/v1/assistant/sessions/{session_id}/debt-extraction/retry` | `assistant.view` | `session_id`(path*)；body `AssistantDebtExtractionRetryRequest` | 200 `AssistantSessionResponse` | [post_assistant_debt_extraction_retry](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L788) |
| POST | `/api/v1/assistant/sessions/{session_id}/debt-form-saved` | `assistant.view` | `session_id`(path*)；body `AssistantDebtFormSavedRequest` | 200 `AssistantSessionResponse` | [post_assistant_debt_form_saved](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L753) |
| POST | `/api/v1/assistant/sessions/{session_id}/debt-memo/form-saved` | `assistant.view` | `session_id`(path*)；body `AssistantDebtMemoFormSavedRequest` | 200 `AssistantSessionResponse` | [post_assistant_debt_memo_form_saved](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L698) |
| POST | `/api/v1/assistant/sessions/{session_id}/debt-memo/form-state` | `assistant.view` | `session_id`(path*)；body `AssistantDebtMemoFormStateRequest` | 200 `AssistantSessionResponse` | [post_assistant_debt_memo_form_state](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L679) |
| POST | `/api/v1/assistant/sessions/{session_id}/fields` | `assistant.view` | `session_id`(path*)；body `AssistantFieldsRequest` | 200 `AssistantSessionResponse` | [post_assistant_fields](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L577) |
| POST | `/api/v1/assistant/sessions/{session_id}/form-handoff` | `assistant.view` | `session_id`(path*) | 200 `AssistantFormHandoffResponse` | [issue_assistant_form_handoff](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L445) |
| POST | `/api/v1/assistant/sessions/{session_id}/form-state` | `assistant.view` | `session_id`(path*)；body `AssistantFormStateRequest` | 200 `AssistantSessionResponse` | [post_assistant_form_state](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L597) |
| POST | `/api/v1/assistant/sessions/{session_id}/messages` | `assistant.view` | `session_id`(path*)；body `AssistantMessageRequest` | 200 `AssistantSessionResponse` | [post_assistant_message](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L516) |
| POST | `/api/v1/assistant/sessions/{session_id}/missing-master-data` | `assistant.view` | `session_id`(path*)；body `AssistantMissingMasterDataStartRequest` | 200 `AssistantSessionResponse` | [start_assistant_missing_master_data](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L478) |
| POST | `/api/v1/assistant/sessions/{session_id}/project/form-saved` | `assistant.view` | `session_id`(path*)；body `AssistantProjectFormSavedRequest` | 200 `AssistantSessionResponse` | [post_assistant_project_form_saved](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L735) |
| POST | `/api/v1/assistant/sessions/{session_id}/project/form-state` | `assistant.view` | `session_id`(path*)；body `AssistantProjectFormStateRequest` | 200 `AssistantSessionResponse` | [post_assistant_project_form_state](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L717) |
| POST | `/api/v1/assistant/sessions/{session_id}/resume-parent` | `assistant.view` | `session_id`(path*) | 200 `AssistantSessionResponse` | [resume_assistant_parent_session](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L504) |
| POST | `/api/v1/assistant/sessions/{session_id}/selections` | `assistant.view` | `session_id`(path*)；body `AssistantSelectionRequest` | 200 `AssistantSessionResponse` | [post_assistant_selection](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L633) |
| POST | `/api/v1/assistant/turns` | `assistant.view` | body `AssistantTurnRequest` | 200 `AssistantTurnResponse` | [post_assistant_turn](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L133) |

## backend/app/modules/assistant/debt_prefill_api.py

共 8 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| POST | `/api/v1/ocr/documents/base-materials/entry` | `ocr.create` | body `OcrBaseMaterialEntryData` | 200 `OcrBaseMaterialEntryResponse` | [post_ocr_base_material_entry](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L150) |
| POST | `/api/v1/ocr/documents/base-materials/entry-jobs` | `ocr.create` | body `OcrBaseMaterialEntryData` | 202 `OcrBaseMaterialEntryJobItem` | [create_ocr_base_material_entry_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L171) |
| GET | `/api/v1/ocr/documents/base-materials/entry-jobs/active` | `ocr.create` | — | 200 `OcrBaseMaterialEntryJobItem` / `null` | [get_active_ocr_base_material_entry_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L194) |
| GET | `/api/v1/ocr/documents/base-materials/entry-jobs/{job_id}` | `ocr.create` | `job_id`(path*) | 200 `OcrBaseMaterialEntryJobItem` | [get_ocr_base_material_entry_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L208) |
| POST | `/api/v1/ocr/documents/{document_id}/analyses/{analysis_id}/controlled-company-prefill` | `ocr.create` | `document_id`(path*), `analysis_id`(path*) | 200 `OcrControlledCompanyPrefillResponse` | [post_ocr_controlled_company_prefill](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L124) |
| POST | `/api/v1/ocr/documents/{document_id}/analyses/{analysis_id}/real-estate-prefill` | `ocr.create` | `document_id`(path*), `analysis_id`(path*) | 200 `OcrRealEstatePrefillResponse` | [post_ocr_real_estate_prefill](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L227) |
| POST | `/api/v1/ocr/modules/{module_id}/debt-prefill` | `ocr.create` | `module_id`(path*)；body `OcrRootDebtImportData` / `null` | 200 `OcrDebtPrefillResponse` | [post_ocr_module_debt_prefill](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L88) |
| POST | `/api/v1/ocr/root-debt-prefill` | `ocr.create` | body `OcrRootDebtImportData` | 200 `OcrDebtPrefillResponse` | [post_ocr_root_debt_prefill](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L42) |

## backend/app/modules/audit/api.py

共 2 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/audit/logs` | `audit.view` | `module`(query), `action`(query), `operator_username`(query), `created_from`(query), `created_to`(query), `page`(query), `page_size`(query) | 200 `OperationLogListResponse` | [read_operation_logs](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/audit/api.py#L15) |
| GET | `/api/v1/audit/logs/{log_id}` | `audit.view` | `log_id`(path*) | 200 `OperationLogDetailResponse` | [read_operation_log_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/audit/api.py#L39) |

## backend/app/modules/auth/api.py

共 13 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| POST | `/api/v1/auth/activity` | 公开 | — | 204 — | [refresh_activity](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L133) |
| GET | `/api/v1/auth/captcha` | 公开 | — | 200 `CaptchaChallengeResponse` | [issue_captcha](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L65) |
| POST | `/api/v1/auth/login` | 公开 | body `LoginRequest` | 200 `CurrentUserResponse` | [login](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L71) |
| POST | `/api/v1/auth/logout` | 公开 | — | 204 — | [logout](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L121) |
| GET | `/api/v1/auth/me` | 公开 | — | 200 `CurrentUserResponse` | [get_me](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L160) |
| PUT | `/api/v1/auth/me/password` | 公开 | body `PasswordChangePayload` | 204 — | [change_my_password](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L177) |
| POST | `/api/v1/auth/user-admin/transfer` | `users.assign` | body `UserAdminTransferPayload` | 200 `UserAccountResponse` | [transfer_user_admin](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L243) |
| GET | `/api/v1/auth/users` | `users.view` | `keyword`(query), `page`(query), `page_size`(query) | 200 `UserAccountListResponse` | [read_regular_users](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L187) |
| POST | `/api/v1/auth/users` | `users.create` | body `UserAccountCreatePayload` | 201 `UserAccountResponse` | [create_regular_user](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L198) |
| DELETE | `/api/v1/auth/users/{user_id}` | `users.delete` | `user_id`(path*) | 204 — | [delete_user](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L238) |
| PUT | `/api/v1/auth/users/{user_id}` | `users.edit` | `user_id`(path*)；body `UserAccountUpdatePayload` | 200 `UserAccountResponse` | [update_regular_user](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L208) |
| POST | `/api/v1/auth/users/{user_id}/activate` | `users.deactivate` | `user_id`(path*) | 204 — | [activate_user](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L233) |
| POST | `/api/v1/auth/users/{user_id}/deactivate` | `users.deactivate` | `user_id`(path*) | 204 — | [deactivate_user](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L219) |

## backend/app/modules/auth/permission_api.py

共 2 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/system/permissions` | `permissions.view` | — | 200 — | [read_permission_configuration](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/permission_api.py#L48) |
| PUT | `/api/v1/system/permissions/{role_code}` | `permissions.manage` | `role_code`(path*)；body `RoleGrantPayload` | 200 — | [save_role_permissions](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/permission_api.py#L61) |

## backend/app/modules/backup/api.py

共 20 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/system/backups` | `backup.view` | `page`(query), `page_size`(query) | 200 `BackupListResponse` | [read_backups](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L91) |
| POST | `/api/v1/system/backups/manual` | `backup.create` | body `ManualBackupRequest` | 200 `BackupItem` | [start_manual_backup](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L142) |
| GET | `/api/v1/system/backups/schedule` | `backup.view` | — | 200 `BackupSchedule` | [read_backup_schedule](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L103) |
| PUT | `/api/v1/system/backups/schedule` | `backup.schedule` | body `BackupSchedule` | 200 `BackupSchedule` | [update_backup_schedule](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L112) |
| GET | `/api/v1/system/backups/storage` | `backup.storage` | — | 200 `BackupStorage` | [read_backup_storage](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L122) |
| PUT | `/api/v1/system/backups/storage` | `backup.storage` | body `BackupStorage` | 200 `BackupStorage` | [update_backup_storage](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L131) |
| DELETE | `/api/v1/system/backups/{backup_id}` | `backup.delete` | `backup_id`(path*) | 204 — | [delete_backup](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L174) |
| GET | `/api/v1/system/backups/{backup_id}` | `backup.view` | `backup_id`(path*) | 200 `BackupItem` | [read_backup](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L154) |
| POST | `/api/v1/system/backups/{backup_id}/cancel` | `backup.create` | `backup_id`(path*) | 200 `BackupItem` | [cancel_backup](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L164) |
| GET | `/api/v1/system/backups/{backup_id}/download` | `backup.download` | `backup_id`(path*) | 200 — | [download_backup](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L185) |
| GET | `/api/v1/system/maintenance-status` | 公开 | — | 200 `MaintenanceResponse` | [restore_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L435) |
| POST | `/api/v1/system/restore-uploads` | `backup.restore` | body `UploadCreateRequest` | 200 `UploadResponse` | [start_restore_upload](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L203) |
| GET | `/api/v1/system/restore-uploads/{upload_id}` | `backup.restore` | `upload_id`(path*) | 200 `UploadResponse` | [read_restore_upload](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L258) |
| POST | `/api/v1/system/restore-uploads/{upload_id}/complete` | `backup.restore` | `upload_id`(path*) | 200 `UploadResponse` | [complete_restore_upload](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L268) |
| PUT | `/api/v1/system/restore-uploads/{upload_id}/parts/{part}` | `backup.restore` | `upload_id`(path*), `part`(path*) | 200 `UploadResponse` | [upload_restore_part](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L215) |
| POST | `/api/v1/system/restores` | `backup.restore` | body `RestoreRequest` | 200 `RestoreStartResponse` | [start_restore](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L352) |
| POST | `/api/v1/system/restores/cancel` | `backup.restore` | — | 200 `MaintenanceResponse` | [cancel_restore](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L414) |
| POST | `/api/v1/system/restores/precheck` | `backup.restore` | body `RestorePrecheckRequest` | 200 `RestorePrecheckResponse` | [precheck_restore](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L291) |
| POST | `/api/v1/system/restores/release-maintenance` | `backup.release` | body `MaintenanceReleaseRequest` | 200 `MaintenanceResponse` | [release_restore_maintenance](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L442) |
| GET | `/api/v1/system/restores/status` | 公开 | — | 200 `MaintenanceResponse` | [restore_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L435) |

## backend/app/modules/credit_statistics/api.py

共 9 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/guarantee/credit-statistics` | `credit.view` | `keyword`(query), `category`(query), `page`(query), `page_size`(query) | 200 `CreditStatisticsResponse` | [read_credit_statistics](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L48) |
| PUT | `/api/v1/guarantee/credit-statistics/bank-mappings/{creditor_org_id}` | `credit.edit` | `creditor_org_id`(path*)；body `CreditStatisticsBankMappingUpsert` | 200 `CreditStatisticsBankMappingResponse` | [put_credit_statistics_bank_mapping](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L183) |
| GET | `/api/v1/guarantee/credit-statistics/bank-options` | `credit.view` | `keyword`(query) | 200 `CreditStatisticsBankOptionsResponse` | [read_credit_statistics_bank_options](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L66) |
| GET | `/api/v1/guarantee/credit-statistics/export` | `credit.export` | `keyword`(query), `category`(query) | 200 — | [export_credit_statistics](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L78) |
| GET | `/api/v1/guarantee/credit-statistics/manual-credit-import-template` | `credit.import` | — | 200 — | [download_manual_credit_import_template](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L112) |
| POST | `/api/v1/guarantee/credit-statistics/manual-credit-import/confirm` | `credit.import` | body `ManualCreditImportConfirmRequest` | 200 `ManualCreditImportConfirmResponse` | [confirm_manual_credit_import](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L153) |
| POST | `/api/v1/guarantee/credit-statistics/manual-credit-import/preview` | `credit.import` | body `Body_preview_manual_credit_import_file_api_v1_guarantee_credit_statistics_manual_credit_import_preview_post` | 200 `ManualCreditImportPreviewResponse` | [preview_manual_credit_import_file](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L132) |
| DELETE | `/api/v1/guarantee/manual-credit-limits/{canonical_creditor_org_id}` | `credit.delete` | `canonical_creditor_org_id`(path*), `version_no`(query*) | 204 — | [remove_manual_credit](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L201) |
| PUT | `/api/v1/guarantee/manual-credit-limits/{canonical_creditor_org_id}` | `credit.edit` | `canonical_creditor_org_id`(path*)；body `ManualCreditUpsert` | 200 `ManualCreditResponse` | [put_manual_credit](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L165) |

## backend/app/modules/dataentry/api.py

共 17 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/data-entry/finance/bank-accounts` | `finance.view` | `keyword`(query), `page`(query), `page_size`(query) | 200 `FinanceBankAccountListResponse` | [get_bank_accounts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L77) |
| PUT | `/api/v1/data-entry/finance/bank-accounts` | `finance.create` | body `FinanceBankAccountPayload` | 200 `FinanceBankAccountResponse` | [save_bank_accounts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L169) |
| POST | `/api/v1/data-entry/finance/bank-accounts/own` | `finance.create` | body `OwnCompanyBankAccountCreatePayload` | 201 `FinanceBankAccountResponse` | [create_own_company_bank_account_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L272) |
| POST | `/api/v1/data-entry/finance/bank-accounts/own/check` | `finance.view` | body `OwnCompanyBankAccountCheckPayload` | 200 `OwnCompanyBankAccountCheckResponse` | [check_own_company_bank_account_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L260) |
| POST | `/api/v1/data-entry/finance/bank-accounts/own/company-resolutions` | `finance.view` | body `FinanceOwnCompanyResolutionPayload` | 200 `FinanceOwnCompanyResolutionResponse` | [resolve_own_company_name](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L211) |
| POST | `/api/v1/data-entry/finance/bank-accounts/repayment` | `finance.create` | body `RepaymentBankAccountCreatePayload` | 201 `FinanceBankAccountResponse` | [create_repayment_bank_account_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L289) |
| PUT | `/api/v1/data-entry/finance/bank-accounts/{record_id}` | `finance.edit` | `record_id`(path*)；body `FinanceBankAccountPayload` | 200 `FinanceBankAccountResponse` | [update_bank_accounts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L223) |
| GET | `/api/v1/data-entry/finance/bank-accounts/{record_id}/repayment-history` | `finance.view` | `record_id`(path*) | 200 列表[`FinanceRepaymentAccountHistoryResponse`] | [get_repayment_account_history](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L316) |
| PUT | `/api/v1/data-entry/finance/bank-accounts/{record_id}/repayment-profile` | `finance.edit` | `record_id`(path*)；body `FinanceRepaymentProfileUpdatePayload` | 200 `FinanceBankAccountResponse` | [update_repayment_profile](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L328) |
| DELETE | `/api/v1/data-entry/finance/bank-accounts/{record_id}/{account_type}` | `finance.delete` | `record_id`(path*), `account_type`(path*), `assistant_action_id`(query) | 204 — | [delete_bank_account_entry](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L346) |
| POST | `/api/v1/data-entry/finance/bank-routing-resolutions` | `finance.view` | body `FinanceBankRoutingResolvePayload` | 200 `FinanceBankRoutingResolveResponse` | [resolve_bank_routing_number](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L199) |
| GET | `/api/v1/data-entry/finance/invoice` | `finance.view` | `keyword`(query), `page`(query), `page_size`(query) | 200 `FinanceInvoiceListResponse` | [get_invoice](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L388) |
| PUT | `/api/v1/data-entry/finance/invoice` | `finance.create` | body `FinanceInvoicePayload` | 200 `FinanceInvoiceResponse` | [save_invoice](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L483) |
| DELETE | `/api/v1/data-entry/finance/invoice/{invoice_id}` | `finance.delete` | `invoice_id`(path*), `assistant_action_id`(query) | 204 — | [delete_invoice](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L574) |
| GET | `/api/v1/data-entry/finance/invoice/{invoice_id}` | `finance.view` | `invoice_id`(path*) | 200 `FinanceInvoiceResponse` | [get_invoice_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L544) |
| PUT | `/api/v1/data-entry/finance/invoice/{invoice_id}` | `finance.edit` | `invoice_id`(path*)；body `FinanceInvoicePayload` | 200 `FinanceInvoiceResponse` | [update_invoice](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L512) |
| POST | `/api/v1/data-entry/finance/invoice/{invoice_id}/usage-events` | `finance.edit` | `invoice_id`(path*)；body `FinanceInvoiceUsageActionPayload` | 200 `FinanceInvoiceUsageActionResponse` | [create_invoice_usage_event](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L556) |

## backend/app/modules/dataentry/asset_api.py

共 24 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/data-entry/assets/controlled-companies` | `asset.view` | `keyword`(query), `include_inactive`(query), `page`(query), `page_size`(query) | 200 `AssetControlledCompanyListResponse` | [get_asset_controlled_company_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L293) |
| POST | `/api/v1/data-entry/assets/controlled-companies` | `asset.create` | `assistant_action_id`(query)；body `AssetControlledCompanyCreatePayload` | 201 `AssetControlledCompanyCreateResponse` | [create_asset_controlled_company_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L311) |
| DELETE | `/api/v1/data-entry/assets/controlled-companies/{company_id}` | `asset.delete` | `company_id`(path*), `assistant_action_id`(query) | 204 — | [delete_asset_controlled_company_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L418) |
| GET | `/api/v1/data-entry/assets/controlled-companies/{company_id}` | `asset.view` | `company_id`(path*) | 200 `AssetControlledCompanyResponse` | [get_asset_controlled_company_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L350) |
| PUT | `/api/v1/data-entry/assets/controlled-companies/{company_id}` | `asset.edit` | `company_id`(path*), `assistant_action_id`(query)；body `AssetControlledCompanyPayload` | 200 `AssetControlledCompanyResponse` | [update_asset_controlled_company_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L362) |
| PATCH | `/api/v1/data-entry/assets/controlled-companies/{company_id}/status` | `asset.edit` | `company_id`(path*)；body `DataEntryStatusPayload` | 200 `AssetControlledCompanyResponse` | [update_asset_controlled_company_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L400) |
| GET | `/api/v1/data-entry/assets/enterprise-groups` | `asset.view` | `include_inactive`(query), `as_of_date`(query) | 200 `EnterpriseGroupListResponse` | [get_enterprise_group_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L455) |
| POST | `/api/v1/data-entry/assets/enterprise-groups` | `asset.group_manage` | body `EnterpriseGroupCreate` | 201 `EnterpriseGroupResponse` | [create_enterprise_group_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L469) |
| DELETE | `/api/v1/data-entry/assets/enterprise-groups/{group_id}` | `asset.group_manage` | `group_id`(path*), `version_no`(query*) | 204 — | [delete_enterprise_group_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L505) |
| PUT | `/api/v1/data-entry/assets/enterprise-groups/{group_id}` | `asset.group_manage` | `group_id`(path*)；body `EnterpriseGroupUpdate` | 200 `EnterpriseGroupResponse` | [update_enterprise_group_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L482) |
| PATCH | `/api/v1/data-entry/assets/enterprise-groups/{group_id}/status` | `asset.group_manage` | `group_id`(path*)；body `EnterpriseGroupStatusUpdate` | 200 `EnterpriseGroupResponse` | [update_enterprise_group_status_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L492) |
| GET | `/api/v1/data-entry/assets/real-estates` | `asset.view` | `keyword`(query), `include_inactive`(query), `page`(query), `page_size`(query) | 200 `AssetRealEstateListResponse` | [get_asset_real_estate_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L76) |
| POST | `/api/v1/data-entry/assets/real-estates` | `asset.create` | `assistant_action_id`(query)；body `AssetRealEstatePayload` | 201 `AssetRealEstateResponse` | [create_asset_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L119) |
| GET | `/api/v1/data-entry/assets/real-estates/options` | `asset.view` | `include_inactive`(query), `project_id`(query), `exclude_debt_id`(query) | 200 `AssetRealEstateOptionListResponse` | [get_asset_real_estate_options](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L94) |
| DELETE | `/api/v1/data-entry/assets/real-estates/{real_estate_id}` | `asset.delete` | `real_estate_id`(path*), `assistant_action_id`(query) | 204 — | [delete_asset_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L205) |
| GET | `/api/v1/data-entry/assets/real-estates/{real_estate_id}` | `asset.view` | `real_estate_id`(path*) | 200 `AssetRealEstateResponse` | [get_asset_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L110) |
| PUT | `/api/v1/data-entry/assets/real-estates/{real_estate_id}` | `asset.edit` | `real_estate_id`(path*), `assistant_action_id`(query)；body `AssetRealEstatePayload` | 200 `AssetRealEstateResponse` | [update_asset_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L155) |
| POST | `/api/v1/data-entry/assets/real-estates/{real_estate_id}/attachments` | `asset.edit` | `real_estate_id`(path*)；body `Body_upload_asset_real_estate_attachment_api_v1_data_entry_assets_real_estates__real_estate_id__attachments_post` | 201 `AssetRealEstateAttachmentResponse` | [upload_asset_real_estate_attachment](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L242) |
| DELETE | `/api/v1/data-entry/assets/real-estates/{real_estate_id}/attachments/{attachment_id}` | `asset.edit` | `real_estate_id`(path*), `attachment_id`(path*) | 204 — | [delete_asset_real_estate_attachment_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L275) |
| PATCH | `/api/v1/data-entry/assets/real-estates/{real_estate_id}/status` | `asset.edit` | `real_estate_id`(path*)；body `DataEntryStatusPayload` | 200 `AssetRealEstateResponse` | [update_asset_real_estate_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L190) |
| GET | `/api/v1/data-entry/assets/scope-configurations` | `asset.view` | `as_of_date`(query), `group_id`(query), `keyword`(query), `issue_only`(query), `include_inactive`(query), `page`(query), `page_size`(query) | 200 `GroupScopeConfigurationListResponse` | [get_group_scope_configuration_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L523) |
| POST | `/api/v1/data-entry/assets/scope-configurations/batch-change` | `asset.group_manage` | body `GroupScopeBatchChangePayload` | 200 列表[`GroupScopeConfigurationItem`] | [batch_change_group_scope_configuration_records](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L562) |
| POST | `/api/v1/data-entry/assets/scope-configurations/change` | `asset.group_manage` | body `GroupScopeChangePayload` | 200 `GroupScopeConfigurationItem` | [change_group_scope_configuration_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L550) |
| GET | `/api/v1/data-entry/assets/scope-configurations/{company_id}/history` | `asset.view` | `company_id`(path*) | 200 `GroupScopeHistoryResponse` | [get_group_scope_configuration_history](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L574) |

## backend/app/modules/dataentry/engineering_api.py

共 6 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/data-entry/engineering/projects` | `engineering.view` | `keyword`(query), `include_inactive`(query), `page`(query), `page_size`(query) | 200 `EngineeringProjectListResponse` | [get_engineering_project_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/engineering_api.py#L28) |
| POST | `/api/v1/data-entry/engineering/projects` | `engineering.create` | body `EngineeringProjectPayload` | 201 `EngineeringProjectResponse` | [create_engineering_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/engineering_api.py#L55) |
| DELETE | `/api/v1/data-entry/engineering/projects/{project_id}` | `engineering.delete` | `project_id`(path*) | 204 — | [delete_engineering_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/engineering_api.py#L93) |
| GET | `/api/v1/data-entry/engineering/projects/{project_id}` | `engineering.view` | `project_id`(path*) | 200 `EngineeringProjectResponse` | [get_engineering_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/engineering_api.py#L46) |
| PUT | `/api/v1/data-entry/engineering/projects/{project_id}` | `engineering.edit` | `project_id`(path*)；body `EngineeringProjectPayload` | 200 `EngineeringProjectResponse` | [update_engineering_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/engineering_api.py#L68) |
| PATCH | `/api/v1/data-entry/engineering/projects/{project_id}/status` | `engineering.edit` | `project_id`(path*)；body `DataEntryStatusPayload` | 200 `EngineeringProjectResponse` | [update_engineering_project_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/engineering_api.py#L78) |

## backend/app/modules/dataentry/table_api.py

共 1 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| POST | `/api/v1/data-entry/table-query/{surface_key}` | `@dataentry` | `surface_key`(path*)；body `DataEntryTableQueryRequest` | 200 `DataEntryTableQueryResponse` | [query_cross_department_table](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/table_api.py#L14) |

## backend/app/modules/debt/api.py

共 38 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/cashflows/upcoming` | `debt.view` | `days`(query) | 200 `UpcomingPaymentsResponse` | [list_upcoming_payments](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1331) |
| GET | `/api/v1/cashflows/upcoming/summary` | `debt.view` | `days`(query) | 200 `UpcomingPaymentsSummaryResponse` | [get_upcoming_payments_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1340) |
| GET | `/api/v1/cashflows/{event_id}/splits` | `debt.view` | `event_id`(path*) | 200 `CashflowEventWithSplitsResponse` | [get_cashflow_splits](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1349) |
| PUT | `/api/v1/cashflows/{event_id}/splits/{creditor_org_id}/status` | `debt.confirm` | `event_id`(path*), `creditor_org_id`(path*)；body `CashflowSplitStatusPayload` | 200 `CashflowEventWithSplitsResponse` | [update_cashflow_split_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1358) |
| GET | `/api/v1/debts` | `debt.view` | `keyword`(query), `project_id`(query), `financing_type`(query), `debtor_id`(query), `debtor_include_descendants`(query), `creditor_org_id`(query), `borrowing_date_start`(query), `borrowing_date_end`(query), `repayment_date_start`(query), `repayment_date_end`(query), `source_type`(query), `page`(query), `page_size`(query) | 200 `DebtListResponse` | [get_debt_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L448) |
| POST | `/api/v1/debts` | `debt.create` | body `DebtCreatePayload` | 201 `DebtRecordResponse` | [create_debt_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L812) |
| POST | `/api/v1/debts/automatic-project/cost-approval` | `@debt_entry` | body `DebtAutomaticProjectCostApprovalPayload` | 201 `DebtAutomaticProjectCostApprovalResponse` | [create_debt_automatic_project_cost_approval](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L793) |
| POST | `/api/v1/debts/automatic-project/resolve` | `@debt_entry` | body `DebtAutomaticProjectResolvePayload` | 200 `DebtAutomaticProjectResolveResponse` | [resolve_debt_automatic_project](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L775) |
| POST | `/api/v1/debts/bill-maturity-preview` | `debt.view` | `debt_id`(query)；body `DebtBillMaturityPreviewPayload` | 200 `DebtInterestPlanMismatchPreviewResponse` | [preview_bill_maturity_amounts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L958) |
| GET | `/api/v1/debts/comprehensive-cost` | `debt.view` | `bank_name`(query*), `company_name`(query*), `fiscal_year`(query), `financing_type`(query), `all_years`(query), `as_of_date`(query), `attached_loan_amount`(query), `attached_term_months`(query), `attached_loan_rate`(query), `attached_deposit_amount`(query), `attached_deposit_term_months`(query), `attached_deposit_rate`(query), `attached_fee_rate`(query), `attached_project_debt_type`(query) | 200 `ComprehensiveCostQueryResponse` | [get_comprehensive_cost_query](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L657) |
| POST | `/api/v1/debts/contract-attachment-uploads` | `@debt_entry` | body `Body_upload_debt_contract_attachments_api_v1_debts_contract_attachment_uploads_post` | 200 列表[`DebtContractAttachmentItem`] | [upload_debt_contract_attachments](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L838) |
| GET | `/api/v1/debts/contract-attachments/{attachment_file_id}/source-file` | `debt.export` | `attachment_file_id`(path*) | 200 — | [download_debt_contract_attachment](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L926) |
| GET | `/api/v1/debts/disbursement-summary` | `debt.view` | `creditor_org_id`(query*), `creditor_scope`(query), `debtor_id`(query), `debtor_scope`(query), `borrowing_date_start`(query), `borrowing_date_end`(query), `page`(query), `page_size`(query) | 200 `DisbursementSummaryResponse` | [get_debt_disbursement_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L631) |
| POST | `/api/v1/debts/floating-rate-calculation` | `debt.view` | body `FloatingRateCalculationPayload` | 200 `FloatingRateCalculationResponse` | [calculate_floating_rate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L761) |
| GET | `/api/v1/debts/fund-statistics` | `debt.view` | `category`(query), `view`(query), `as_of_date`(query), `keyword`(query), `debt_id`(query), `financing_type`(query), `debtor_id`(query), `debtor_include_descendants`(query), `creditor_org_id`(query), `borrowing_date_start`(query), `borrowing_date_end`(query), `repayment_date_start`(query), `repayment_date_end`(query), `source_type`(query) | 200 `FundStatisticsResponse` | [get_debt_fund_statistics](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L569) |
| POST | `/api/v1/debts/interest-plan-preview` | `debt.view` | `debt_id`(query)；body `DebtInterestPlanPreviewPayload` | 200 `DebtInterestPlanMismatchPreviewResponse` | [preview_debt_interest_plan](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L945) |
| GET | `/api/v1/debts/outstanding-summary` | `debt.view` | `as_of_date`(query), `period_start_date`(query), `keyword`(query), `project_id`(query), `financing_type`(query), `debtor_id`(query), `debtor_include_descendants`(query), `creditor_org_id`(query), `borrowing_date_start`(query), `borrowing_date_end`(query), `repayment_date_start`(query), `repayment_date_end`(query), `page`(query), `page_size`(query) | 200 `OutstandingDebtSummaryResponse` | [get_debt_outstanding_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L516) |
| GET | `/api/v1/debts/pending` | `pending_debt.view` | `page`(query), `page_size`(query) | 200 — | [get_pending_debt_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L205) |
| DELETE | `/api/v1/debts/pending/{pending_id}` | `pending_debt.delete` | `pending_id`(path*) | 204 — | [delete_pending_debt](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L305) |
| GET | `/api/v1/debts/pending/{pending_id}` | `pending_debt.view` | `pending_id`(path*) | 200 — | [get_pending_debt_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L279) |
| PATCH | `/api/v1/debts/pending/{pending_id}` | `pending_debt.edit` | `pending_id`(path*)；body `PendingDebtUpdatePayload` | 200 — | [update_pending_debt](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L245) |
| POST | `/api/v1/debts/pending/{pending_id}/convert` | `pending_debt.transfer` | `pending_id`(path*)；body `DebtCreatePayload` | 201 `DebtRecordResponse` | [convert_pending_debt](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L337) |
| GET | `/api/v1/debts/projects/{project_id}/irr-calculator-export` | `debt.export` | `project_id`(path*), `format`(query) | 200 — | [export_project_irr_calculator](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1101) |
| POST | `/api/v1/debts/query` | `debt.view` | body `DebtTableQueryRequest` | 200 `DebtListResponse` | [query_debt_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L507) |
| GET | `/api/v1/debts/remaining-fee-allowance` | `debt.view` | `project_id`(query*), `exclude_debt_id`(query) | 200 `RemainingFeeAllowanceResponse` | [get_remaining_fee_allowance](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L739) |
| POST | `/api/v1/debts/repayment-plan-custom-export` | `debt.export` | body `RepaymentPlanCustomExportPayload` | 200 — | [export_custom_repayment_plan](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1202) |
| POST | `/api/v1/debts/repayment-plan-export` | `debt.export` | body `DebtPayload` | 200 — | [export_repayment_plan](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1011) |
| POST | `/api/v1/debts/repayment-plan-import` | `debt.import` | body `Body_import_repayment_plan_workbook_api_v1_debts_repayment_plan_import_post` | 200 `RepaymentPlanWorkbookResponse` | [import_repayment_plan_workbook](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L971) |
| GET | `/api/v1/debts/repayment-plan-template` | `debt.export` | — | 200 — | [download_repayment_plan_template](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L993) |
| GET | `/api/v1/debts/supplementary-fee-names` | 公开 | — | 200 — | [get_supplementary_fee_names](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L151) |
| POST | `/api/v1/debts/supplementary-fee-names` | `@debt_entry` | body `SupplementaryFeeNamePayload` | 201 — | [create_supplementary_fee_name](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L177) |
| DELETE | `/api/v1/debts/{debt_id}` | `debt.delete` | `debt_id`(path*), `version_no`(query*) | 204 — | [delete_debt_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1315) |
| GET | `/api/v1/debts/{debt_id}` | `debt.view` | `debt_id`(path*) | 200 `DebtRecordResponse` | [get_debt_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1225) |
| PUT | `/api/v1/debts/{debt_id}` | `debt.edit` | `debt_id`(path*), `force_full_regen`(query)；body `DebtPayload` | 200 `DebtRecordResponse` | [update_debt_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1277) |
| GET | `/api/v1/debts/{debt_id}/history` | `debt.view` | `debt_id`(path*), `page`(query), `page_size`(query) | 200 `BusinessHistoryListResponse` | [get_debt_history](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1234) |
| GET | `/api/v1/debts/{debt_id}/history/{history_id}` | `debt.view` | `debt_id`(path*), `history_id`(path*) | 200 `BusinessHistoryDetailResponse` | [get_debt_history_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1255) |
| GET | `/api/v1/debts/{debt_id}/irr-calculator-export` | `debt.export` | `debt_id`(path*), `format`(query) | 200 — | [export_debt_irr_calculator](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1035) |
| POST | `/api/v1/debts/{debt_id}/principal-balance-at-date` | `debt.view` | `debt_id`(path*)；body `PrincipalBalanceAtDateRequest` | 200 `PrincipalBalanceAtDateResponse` | [get_principal_balance_at_date](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1303) |

## backend/app/modules/debt/project_api.py

共 2 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/projects/{project_id}/debt-overview` | `debt.view` | `project_id`(path*) | 200 `DebtProjectOverviewResponse` | [get_project_debt_overview_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/project_api.py#L21) |
| GET | `/api/v1/projects/{project_id}/tree` | `debt.view` | `project_id`(path*) | 200 `ProjectTreeResponse` | [get_project_tree](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/project_api.py#L33) |

## backend/app/modules/debt_memo/api.py

共 22 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/debt-memo/activity` | `memo.view` | `page`(query), `page_size`(query) | 200 `DebtMemoActivityListResponse` | [get_debt_memo_activity](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L698) |
| GET | `/api/v1/debt-memo/actuals` | `memo.view` | `keyword`(query), `project_id`(query), `debtor_id`(query), `include_descendants`(query), `project_stage`(query), `creditor_org_id`(query), `actual_disbursement_date_start`(query), `actual_disbursement_date_end`(query), `actual_amount_min`(query), `actual_amount_max`(query), `status`(query), `sort_by`(query), `sort_order`(query), `page`(query), `page_size`(query) | 200 `DebtMemoActualListResponse` | [get_actual_memo_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L398) |
| POST | `/api/v1/debt-memo/actuals` | `memo.create` | body `DebtMemoActualPayload` | 201 `DebtMemoActualResponse` | [add_actual_memo](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L508) |
| POST | `/api/v1/debt-memo/actuals/query` | `memo.view` | body `DebtMemoActualTableQueryRequest` | 200 `DebtMemoActualListResponse` | [query_actual_memo_table](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L447) |
| DELETE | `/api/v1/debt-memo/actuals/{actual_id}` | `memo.delete` | `actual_id`(path*) | 204 — | [remove_actual_memo](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L555) |
| GET | `/api/v1/debt-memo/actuals/{actual_id}` | `memo.view` | `actual_id`(path*) | 200 `DebtMemoActualResponse` | [get_actual_memo_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L499) |
| PUT | `/api/v1/debt-memo/actuals/{actual_id}` | `memo.edit` | `actual_id`(path*)；body `DebtMemoActualPayload` | 200 `DebtMemoActualResponse` | [edit_actual_memo](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L544) |
| GET | `/api/v1/debt-memo/actuals/{actual_id}/debt-prefill` | `memo.view` | `actual_id`(path*) | 200 `DebtMemoPrefillResponse` | [get_actual_memo_debt_prefill](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L576) |
| POST | `/api/v1/debt-memo/actuals/{actual_id}/link-debt` | `memo.link` | `actual_id`(path*)；body `DebtMemoLinkDebtPayload` | 200 `DebtMemoActualResponse` | [link_actual_memo_to_debt](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L564) |
| GET | `/api/v1/debt-memo/dashboard` | `memo.view` | — | 200 `DebtMemoDashboardResponse` | [get_debt_memo_dashboard](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L689) |
| GET | `/api/v1/debt-memo/overview-daily-disbursements` | `memo.view` | `target_date`(query*), `debtor_id`(query), `debtor_ids`(query), `subject_mode`(query), `include_descendants`(query), `project_stage`(query) | 200 `DebtMemoOverviewDailyResponse` | [get_overview_daily_disbursements_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L619) |
| GET | `/api/v1/debt-memo/overview-daily-disbursements/{kind}` | `memo.view` | `kind`(path*), `target_date`(query*), `debtor_id`(query), `debtor_ids`(query), `subject_mode`(query), `include_descendants`(query), `project_stage`(query), `page`(query), `page_size`(query) | 200 `DebtMemoOverviewDailyListResponse` | [get_overview_daily_disbursement_list_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L645) |
| GET | `/api/v1/debt-memo/overview-disbursement-summary` | `memo.view` | `date_start`(query*), `date_end`(query*), `debtor_id`(query), `debtor_ids`(query), `subject_mode`(query), `include_descendants`(query), `project_stage`(query) | 200 `DebtMemoOverviewDisbursementResponse` | [get_overview_disbursement_summary_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L591) |
| GET | `/api/v1/debt-memo/plans` | `memo.view` | `keyword`(query), `project_id`(query), `plan_id`(query), `debtor_id`(query), `include_descendants`(query), `project_stage`(query), `status`(query), `plan_date_start`(query), `plan_date_end`(query), `plan_amount_min`(query), `plan_amount_max`(query), `sort_by`(query), `sort_order`(query), `page`(query), `page_size`(query) | 200 `DebtMemoPlanListResponse` | [get_plan_memo_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L214) |
| POST | `/api/v1/debt-memo/plans` | `memo.create` | body `DebtMemoPlanPayload` | 201 `DebtMemoPlanResponse` | [add_plan_memo](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L324) |
| POST | `/api/v1/debt-memo/plans/query` | `memo.view` | body `DebtMemoPlanTableQueryRequest` | 200 `DebtMemoPlanListResponse` | [query_plan_memo_table](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L263) |
| DELETE | `/api/v1/debt-memo/plans/{plan_id}` | `memo.delete` | `plan_id`(path*) | 204 — | [remove_plan_memo](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L369) |
| GET | `/api/v1/debt-memo/plans/{plan_id}` | `memo.view` | `plan_id`(path*) | 200 `DebtMemoPlanResponse` | [get_plan_memo_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L314) |
| PUT | `/api/v1/debt-memo/plans/{plan_id}` | `memo.edit` | `plan_id`(path*)；body `DebtMemoPlanPayload` | 200 `DebtMemoPlanResponse` | [edit_plan_memo](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L359) |
| POST | `/api/v1/debt-memo/plans/{plan_id}/convert-to-actual` | `memo.convert` | `plan_id`(path*)；body `DebtMemoConvertToActualPayload` | 200 `DebtMemoActualResponse` | [convert_plan_memo_to_actual](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L378) |
| POST | `/api/v1/debt-memo/project-options/query` | `memo.view` | body `DebtMemoProjectOptionQueryRequest` | 200 `DebtMemoProjectOptionListResponse` | [query_debt_memo_project_options](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L91) |
| GET | `/api/v1/debt-memo/summary` | `memo.view` | — | 200 `DebtMemoSummaryResponse` | [get_debt_memo_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L680) |

## backend/app/modules/documents/api.py

共 37 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/documents/cost-approvals` | `documents.view` | `status`(query), `document_status`(query), `keyword`(query), `page`(query), `page_size`(query) | 200 `CostApprovalDocumentListResponse` | [list_cost_approvals](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L585) |
| POST | `/api/v1/documents/cost-approvals` | `documents.create` | body `CostApprovalDocumentCreate` | 201 `CostApprovalDocumentDetail` | [create_cost_approval](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L568) |
| GET | `/api/v1/documents/cost-approvals/counts` | `documents.view` | `keyword`(query) | 200 `CostApprovalDocumentCountsResponse` | [get_cost_approval_counts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L612) |
| GET | `/api/v1/documents/cost-approvals/defaults` | `documents.view` | `project_id`(query*), `duplicate_generation_confirmed`(query) | 200 `CostApprovalDefaultsResponse` | [get_cost_approval_defaults_endpoint](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L550) |
| GET | `/api/v1/documents/cost-approvals/project-options` | `documents.view` | `keyword`(query), `limit`(query), `offset`(query) | 200 `CostApprovalProjectOptionListResponse` | [get_cost_approval_project_options](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L530) |
| DELETE | `/api/v1/documents/cost-approvals/{document_id}` | `documents.delete` | `document_id`(path*) | 204 — | [delete_cost_approval](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L800) |
| GET | `/api/v1/documents/cost-approvals/{document_id}` | `documents.view` | `document_id`(path*) | 200 `CostApprovalDocumentDetail` | [get_cost_approval](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L628) |
| PUT | `/api/v1/documents/cost-approvals/{document_id}` | `documents.edit` | `document_id`(path*)；body `CostApprovalDocumentUpdate` | 200 `CostApprovalDocumentDetail` | [update_cost_approval](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L764) |
| GET | `/api/v1/documents/cost-approvals/{document_id}/excel` | `documents.export` | `document_id`(path*) | 200 — | [download_cost_approval_excel](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L662) |
| GET | `/api/v1/documents/cost-approvals/{document_id}/history` | `documents.view` | `document_id`(path*), `page`(query), `page_size`(query) | 200 `BusinessHistoryListResponse` | [get_cost_approval_history](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L708) |
| GET | `/api/v1/documents/cost-approvals/{document_id}/history/{history_id}` | `documents.view` | `document_id`(path*), `history_id`(path*) | 200 `BusinessHistoryDetailResponse` | [get_cost_approval_history_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L728) |
| POST | `/api/v1/documents/cost-approvals/{document_id}/print` | `documents.export` | `document_id`(path*)；body `CostApprovalPrintRequest` | 200 `CostApprovalDocumentDetail` | [record_cost_approval_document_print](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L644) |
| POST | `/api/v1/documents/cost-approvals/{document_id}/regenerate` | `documents.edit` | `document_id`(path*)；body `CostApprovalDocumentRegenerate` | 200 `CostApprovalDocumentDetail` | [regenerate_cost_approval](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L690) |
| GET | `/api/v1/documents/cost-approvals/{document_id}/versions/{version_no}` | `documents.view` | `document_id`(path*), `version_no`(path*) | 200 `BusinessHistoryDetailResponse` | [get_cost_approval_version](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L746) |
| POST | `/api/v1/documents/cost-approvals/{document_id}/void` | `documents.void` | `document_id`(path*)；body `CostApprovalDocumentVoidRequest` | 200 `CostApprovalDocumentDetail` | [void_cost_approval](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L782) |
| GET | `/api/v1/documents/fee-applications` | `documents.view` | `keyword`(query), `document_status`(query), `page`(query), `page_size`(query) | 200 `FeeApplicationDocumentListResponse` | [list_fee_applications](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L413) |
| GET | `/api/v1/documents/fee-applications/counts` | `documents.view` | — | 200 `FeeApplicationDocumentCountsResponse` | [get_fee_application_counts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L435) |
| POST | `/api/v1/documents/fee-applications/from-debts` | `documents.create` | body `FeeApplicationCreateRequest` | 201 `FeeApplicationCreateResponse` | [create_fee_applications_from_debts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L396) |
| GET | `/api/v1/documents/fee-applications/{document_id}` | `documents.view` | `document_id`(path*) | 200 `FeeApplicationDocumentDetail` | [get_fee_application](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L446) |
| PUT | `/api/v1/documents/fee-applications/{document_id}` | `documents.edit` | `document_id`(path*)；body `FeeApplicationDocumentUpdate` | 200 `FeeApplicationDocumentDetail` | [update_fee_application](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L462) |
| GET | `/api/v1/documents/fee-applications/{document_id}/excel` | `documents.export` | `document_id`(path*) | 200 — | [download_fee_application_excel](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L480) |
| POST | `/api/v1/documents/fee-applications/{document_id}/void` | `documents.void` | `document_id`(path*)；body `FeeApplicationDocumentVoidRequest` | 200 `FeeApplicationDocumentDetail` | [void_fee_application](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L507) |
| POST | `/api/v1/documents/repayment-notices/resolve` | `documents.view` | body `RepaymentNoticeResolveRequest` | 200 `RepaymentNoticeResolveResponse` | [resolve_notice](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L109) |
| GET | `/api/v1/documents/repayment-payments` | `documents.view` | `payment_status`(query), `workflow_status`(query), `document_status`(query), `keyword`(query), `page`(query), `page_size`(query) | 200 `RepaymentPaymentDocumentListResponse` | [list_payment_documents](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L138) |
| POST | `/api/v1/documents/repayment-payments` | `documents.create` | body `RepaymentPaymentDocumentCreate` | 201 `RepaymentPaymentDocumentDetail` | [create_payment_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L121) |
| POST | `/api/v1/documents/repayment-payments/auto-generation/run-now` | `documents.automate` | — | 200 `RepaymentAutoGenerationStatusResponse` | [run_payment_document_auto_generation_now](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L203) |
| GET | `/api/v1/documents/repayment-payments/auto-generation/status` | `documents.view` | — | 200 `RepaymentAutoGenerationStatusResponse` | [get_payment_document_auto_generation_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L192) |
| GET | `/api/v1/documents/repayment-payments/counts` | `documents.view` | — | 200 `RepaymentPaymentDocumentCountsResponse` | [get_payment_document_counts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L167) |
| GET | `/api/v1/documents/repayment-payments/duplicate-check` | `documents.view` | `debt_id`(query*), `payment_date`(query*), `creditor_org_id`(query), `business_period_no`(query), `payment_type`(query) | 200 `RepaymentPaymentDuplicateCheckResponse` | [check_payment_document_duplicate](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L214) |
| GET | `/api/v1/documents/repayment-payments/workflow-counts` | `documents.view` | — | 200 `RepaymentPaymentWorkflowCountsResponse` | [get_payment_document_workflow_counts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L181) |
| DELETE | `/api/v1/documents/repayment-payments/{document_id}` | `documents.delete` | `document_id`(path*) | 204 — | [delete_payment_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L375) |
| GET | `/api/v1/documents/repayment-payments/{document_id}` | `documents.view` | `document_id`(path*) | 200 `RepaymentPaymentDocumentDetail` | [get_payment_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L323) |
| PUT | `/api/v1/documents/repayment-payments/{document_id}` | `documents.edit` | `document_id`(path*)；body `RepaymentPaymentDocumentUpdate` | 200 `RepaymentPaymentDocumentDetail` | [update_payment_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L339) |
| GET | `/api/v1/documents/repayment-payments/{document_id}/excel` | `documents.export` | `document_id`(path*) | 200 — | [download_payment_document_excel](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L256) |
| POST | `/api/v1/documents/repayment-payments/{document_id}/print` | `documents.export` | `document_id`(path*)；body `RepaymentPaymentPrintRequest` | 200 `RepaymentPaymentDocumentDetail` | [record_payment_document_print](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L238) |
| POST | `/api/v1/documents/repayment-payments/{document_id}/receipt-verifications` | `documents.verify` | `document_id`(path*)；body `RepaymentPaymentReceiptVerifyRequest` | 200 `RepaymentPaymentReceiptVerifyResponse` | [verify_payment_document_receipts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L285) |
| POST | `/api/v1/documents/repayment-payments/{document_id}/void` | `documents.void` | `document_id`(path*)；body `RepaymentPaymentDocumentVoidRequest` | 200 `RepaymentPaymentDocumentDetail` | [void_payment_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L357) |

## backend/app/modules/export/api.py

共 13 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| POST | `/api/v1/exports/cashflows` | `debt.export` | body `CashflowExportRequest` | 200 — | [export_cashflows](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L69) |
| POST | `/api/v1/exports/debts` | `debt.export` | body `DebtExportRequest` | 200 — | [export_debts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L47) |
| POST | `/api/v1/exports/jobs/cashflows` | `debt.export` | body `CashflowExportRequest` | 202 `ExportJobResponse` | [create_cashflow_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L190) |
| POST | `/api/v1/exports/jobs/debts` | `debt.export` | body `DebtExportRequest` | 202 `ExportJobResponse` | [create_debt_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L170) |
| POST | `/api/v1/exports/jobs/projects/cost-process` | `project.export` | body `ProjectCostProcessExportRequest` | 202 `ExportJobResponse` | [create_project_cost_process_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L250) |
| POST | `/api/v1/exports/jobs/projects/ledger` | `project.export` | body `ProjectLedgerExportRequest` | 202 `ExportJobResponse` | [create_project_ledger_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L230) |
| POST | `/api/v1/exports/jobs/projects/summary` | `project.export` | body `ProjectSummaryExportRequest` | 202 `ExportJobResponse` | [create_project_summary_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L210) |
| DELETE | `/api/v1/exports/jobs/{job_id}` | `@export_job` | `job_id`(path*) | 200 `ExportJobResponse` | [delete_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L279) |
| GET | `/api/v1/exports/jobs/{job_id}` | `@export_job` | `job_id`(path*) | 200 `ExportJobResponse` | [get_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L270) |
| GET | `/api/v1/exports/jobs/{job_id}/download` | `@export_job` | `job_id`(path*) | 200 — | [download_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L288) |
| POST | `/api/v1/exports/projects/cost-process` | `project.export` | body `ProjectCostProcessExportRequest` | 200 — | [export_project_cost_process](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L140) |
| POST | `/api/v1/exports/projects/ledger` | `project.export` | body `ProjectLedgerExportRequest` | 200 — | [export_project_ledger](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L109) |
| POST | `/api/v1/exports/projects/summary` | `project.export` | body `ProjectSummaryExportRequest` | 200 — | [export_project_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L91) |

## backend/app/modules/file_upload/api.py

共 3 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| POST | `/api/v1/file-uploads/approval-documents` | 公开 | body `Body_upload_approval_document_api_v1_file_uploads_approval_documents_post` | 200 `UploadedFileResponse` | [upload_approval_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/file_upload/api.py#L22) |
| POST | `/api/v1/file-uploads/repayment-subject-attachments` | 公开 | body `Body_upload_repayment_subject_attachment_api_v1_file_uploads_repayment_subject_attachments_post` | 200 `UploadedFileResponse` | [upload_repayment_subject_attachment](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/file_upload/api.py#L39) |
| GET | `/api/v1/file-uploads/{category}/{filename}` | 公开 | `category`(path*), `filename`(path*) | 200 — | [download_uploaded_file](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/file_upload/api.py#L56) |

## backend/app/modules/guarantee/api.py

共 11 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/guarantee/attachments/{attachment_id}/source-file` | `guarantee.export` | `attachment_id`(path*) | 200 — | [download_guarantee_attachment](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L253) |
| GET | `/api/v1/guarantee/detail-statistics` | `guarantee.view` | `group_id`(query*), `as_of_date`(query), `scope`(query), `keyword`(query), `page`(query), `page_size`(query) | 200 `GuaranteeDetailStatisticsResponse` | [get_detail_statistics](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L136) |
| GET | `/api/v1/guarantee/navigation` | `guarantee.view` | — | 200 `GuaranteeNavigationResponse` | [get_guarantee_navigation](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L58) |
| GET | `/api/v1/guarantee/records` | `guarantee.view` | `keyword`(query), `reported_to_credit`(query), `guarantee_date_from`(query), `guarantee_date_to`(query), `balance_missing`(query), `record_status`(query), `page`(query), `page_size`(query) | 200 `ExternalGuaranteeListResponse` | [list_guarantee_records](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L102) |
| POST | `/api/v1/guarantee/records` | `guarantee.create` | body `Body_create_guarantee_record_api_v1_guarantee_records_post` | 201 `ExternalGuaranteeRecord` | [create_guarantee_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L168) |
| DELETE | `/api/v1/guarantee/records/{record_id}` | `guarantee.delete` | `record_id`(path*), `version_no`(query*) | 204 — | [delete_guarantee_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L237) |
| GET | `/api/v1/guarantee/records/{record_id}` | `guarantee.view` | `record_id`(path*) | 200 `ExternalGuaranteeRecord` | [get_guarantee_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L159) |
| PUT | `/api/v1/guarantee/records/{record_id}` | `guarantee.edit` | `record_id`(path*)；body `Body_update_guarantee_record_api_v1_guarantee_records__record_id__put` | 200 `ExternalGuaranteeRecord` | [update_guarantee_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L182) |
| PATCH | `/api/v1/guarantee/records/{record_id}/balance` | `guarantee.edit` | `record_id`(path*)；body `ExternalGuaranteeBalanceUpdate` | 200 `ExternalGuaranteeRecord` | [patch_guarantee_balance](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L203) |
| PATCH | `/api/v1/guarantee/records/{record_id}/status` | `guarantee.edit` | `record_id`(path*)；body `ExternalGuaranteeStatusUpdate` | 200 `ExternalGuaranteeRecord` | [patch_guarantee_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L220) |
| GET | `/api/v1/guarantee/statistics` | `guarantee.view` | — | 200 `GuaranteeStatisticsResponse` | [get_statistics](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L128) |

## backend/app/modules/guarantee/ocr_prefill_api.py

共 1 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| POST | `/api/v1/ocr/guarantee-prefill` | `guarantee.create` | body `Body_prefill_guarantee_application_api_v1_ocr_guarantee_prefill_post` | 200 `GuaranteePrefillResponse` | [prefill_guarantee_application](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/ocr_prefill_api.py#L27) |

## backend/app/modules/master_data/api.py

共 32 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/master-data/creditor-org-seed-config` | `master_data.view` | — | 200 `MasterDataSeedConfigResponse` | [get_creditor_org_seed_config_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L231) |
| PUT | `/api/v1/master-data/creditor-org-seed-config` | `master_data.edit` | body `MasterDataSeedConfigPayload` | 200 `MasterDataSeedConfigResponse` | [update_creditor_org_seed_config_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L239) |
| GET | `/api/v1/master-data/creditor-orgs` | 公开 | `keyword`(query), `include_inactive`(query), `with_existing_debts`(query), `limit`(query) | 200 `CreditorOrgListResponse` | [get_creditor_org_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L140) |
| POST | `/api/v1/master-data/creditor-orgs` | `master_data.create` | body `CreditorOrgPayload` | 201 `CreditorOrgResponse` | [create_creditor_org_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L212) |
| POST | `/api/v1/master-data/creditor-orgs/resolve` | 公开 | body `CreditorOrgResolvePayload` | 200 `CreditorOrgResolveResponse` | [resolve_creditor_org_names](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L192) |
| GET | `/api/v1/master-data/creditor-orgs/search` | 公开 | `keyword`(query), `include_inactive`(query), `with_existing_debts`(query), `category_code`(query), `institution_type_code`(query), `page`(query), `page_size`(query) | 200 `CreditorOrgPageResponse` | [search_creditor_org_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L158) |
| POST | `/api/v1/master-data/creditor-orgs/table-query` | 公开 | body `CreditorOrgTableQueryRequest` | 200 `CreditorOrgPageResponse` | [query_creditor_org_table_page](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L183) |
| GET | `/api/v1/master-data/creditor-orgs/{org_id}` | 公开 | `org_id`(path*) | 200 `CreditorOrgResponse` | [get_creditor_org_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L203) |
| PUT | `/api/v1/master-data/creditor-orgs/{org_id}` | `master_data.edit` | `org_id`(path*)；body `CreditorOrgPayload` | 200 `CreditorOrgResponse` | [update_creditor_org_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L221) |
| GET | `/api/v1/master-data/debtors` | 公开 | `keyword`(query), `include_inactive`(query) | 200 `DebtorListResponse` | [get_debtor_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L84) |
| POST | `/api/v1/master-data/debtors` | `master_data.create` | body `DebtorPayload` | 201 `DebtorResponse` | [create_debtor_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L121) |
| GET | `/api/v1/master-data/debtors/search` | 公开 | `keyword`(query), `include_inactive`(query), `page`(query), `page_size`(query) | 200 `DebtorPageResponse` | [search_debtor_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L103) |
| GET | `/api/v1/master-data/debtors/tree` | 公开 | `include_inactive`(query) | 200 `DebtorTreeResponse` | [get_debtor_tree](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L94) |
| PUT | `/api/v1/master-data/debtors/{debtor_id}` | `master_data.edit` | `debtor_id`(path*)；body `DebtorPayload` | 200 `DebtorResponse` | [update_debtor_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L130) |
| GET | `/api/v1/master-data/financing-products/custom` | 公开 | `keyword`(query), `include_deleted`(query) | 200 `CustomFinancingProductListResponse` | [get_custom_financing_product_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L327) |
| POST | `/api/v1/master-data/financing-products/custom` | `master_data.create` | body `CustomFinancingProductPayload` | 201 `CustomFinancingProductResponse` | [create_custom_financing_product_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L337) |
| PUT | `/api/v1/master-data/financing-products/custom/{product_id}` | `master_data.edit` | `product_id`(path*)；body `CustomFinancingProductPayload` | 200 `CustomFinancingProductResponse` | [update_custom_financing_product_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L350) |
| GET | `/api/v1/master-data/financing-products/standard` | 公开 | `keyword`(query), `include_deleted`(query) | 200 `StandardFinancingProductListResponse` | [get_standard_financing_product_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L294) |
| POST | `/api/v1/master-data/financing-products/standard` | `master_data.create` | body `FinancingProductPayload` | 201 `FinancingProductResponse` | [create_standard_financing_product_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L304) |
| PUT | `/api/v1/master-data/financing-products/standard/{product_id}` | `master_data.edit` | `product_id`(path*)；body `FinancingProductPayload` | 200 `FinancingProductResponse` | [update_standard_financing_product_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L317) |
| GET | `/api/v1/master-data/financing-types` | 公开 | `keyword`(query), `include_deleted`(query) | 200 `FinancingTypeListResponse` | [get_financing_type_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L407) |
| POST | `/api/v1/master-data/financing-types` | `master_data.create` | body `FinancingTypePayload` | 201 `FinancingTypeResponse` | [create_financing_type_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L417) |
| PUT | `/api/v1/master-data/financing-types/{type_id}` | `master_data.edit` | `type_id`(path*)；body `FinancingTypePayload` | 200 `FinancingTypeResponse` | [update_financing_type_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L426) |
| GET | `/api/v1/master-data/guarantee-companies` | 公开 | `keyword`(query), `include_inactive`(query), `page`(query), `page_size`(query) | 200 `GuaranteeCompanyListResponse` | [get_guarantee_company_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L248) |
| POST | `/api/v1/master-data/guarantee-companies` | `master_data.create` | body `GuaranteeCompanyPayload` | 201 `GuaranteeCompanyResponse` | [create_guarantee_company_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L275) |
| GET | `/api/v1/master-data/guarantee-companies/{company_id}` | 公开 | `company_id`(path*) | 200 `GuaranteeCompanyResponse` | [get_guarantee_company_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L266) |
| PUT | `/api/v1/master-data/guarantee-companies/{company_id}` | `master_data.edit` | `company_id`(path*)；body `GuaranteeCompanyPayload` | 200 `GuaranteeCompanyResponse` | [update_guarantee_company_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L284) |
| GET | `/api/v1/master-data/real-estates` | 公开 | `keyword`(query), `include_inactive`(query) | 200 `RealEstateListResponse` | [get_real_estate_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L360) |
| POST | `/api/v1/master-data/real-estates` | `master_data.create` | body `RealEstatePayload` | 201 `RealEstateResponse` | [create_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L379) |
| DELETE | `/api/v1/master-data/real-estates/{real_estate_id}` | `master_data.delete` | `real_estate_id`(path*) | 204 — | [delete_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L398) |
| GET | `/api/v1/master-data/real-estates/{real_estate_id}` | 公开 | `real_estate_id`(path*) | 200 `RealEstateResponse` | [get_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L370) |
| PUT | `/api/v1/master-data/real-estates/{real_estate_id}` | `master_data.edit` | `real_estate_id`(path*)；body `RealEstatePayload` | 200 `RealEstateResponse` | [update_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L388) |

## backend/app/modules/ocr/api/automation.py

共 4 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| POST | `/api/v1/ocr/automation-jobs` | `ocr.create` | body `Body_start_automation_job_api_v1_ocr_automation_jobs_post` | 202 `AutomationJobStartResponse` | [start_automation_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/automation.py#L56) |
| DELETE | `/api/v1/ocr/automation-jobs/{job_id}` | `ocr.delete` | `job_id`(path*) | 204 — | [cancel_automation_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/automation.py#L242) |
| GET | `/api/v1/ocr/automation-jobs/{job_id}` | `ocr.view` | `job_id`(path*), `afterSequence`(query) | 200 `AutomationJobStatusResponse` | [get_automation_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/automation.py#L213) |
| POST | `/api/v1/ocr/documents/{document_id}/reprocess-jobs` | `ocr.create` | `document_id`(path*) | 202 `AutomationJobStartResponse` | [start_document_reprocess_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/automation.py#L160) |

## backend/app/modules/ocr/api/catalog.py

共 6 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/ocr/extraction-config` | `extraction.view` | — | 200 `ExtractionCatalogResponse` | [get_contract_extraction_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/catalog.py#L30) |
| PUT | `/api/v1/ocr/extraction-config` | `extraction.edit` | body `ExtractionCatalogUpdate` | 200 `ExtractionCatalogResponse` | [update_contract_extraction_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/catalog.py#L68) |
| GET | `/api/v1/ocr/extraction-config/export` | `extraction.view` | — | 200 `ExtractionSeed` | [export_contract_extraction_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/catalog.py#L43) |
| POST | `/api/v1/ocr/extraction-config/import` | `extraction.import` | body `ExtractionSeed` | 200 `ExtractionCatalogResponse` | [import_contract_extraction_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/catalog.py#L82) |
| POST | `/api/v1/ocr/extraction-config/import-initial-seed` | `extraction.import` | body `InitialSeedImport` | 200 `ExtractionCatalogResponse` | [import_contract_extraction_initial_seed](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/catalog.py#L96) |
| GET | `/api/v1/ocr/extraction-config/initial-seed` | `extraction.view` | — | 200 `ExtractionSeed` | [get_contract_extraction_initial_seed](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/catalog.py#L56) |

## backend/app/modules/ocr/api/debt_init.py

共 16 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/ocr/debt-init/batches` | `debt_init.view` | `page`(query), `pageSize`(query), `scope`(query), `keyword`(query) | 200 `DebtInitBatchPage` | [read_debt_init_batches](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L148) |
| POST | `/api/v1/ocr/debt-init/batches/clear` | `debt_init.delete` | body `DebtInitBatchClear` | 200 `DebtInitBatchClearResult` | [clear_debt_init_batches](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L169) |
| GET | `/api/v1/ocr/debt-init/batches/{batch_id}/source` | `debt_init.view` | `batch_id`(path*) | 200 — | [download_debt_init_batch_source](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L188) |
| POST | `/api/v1/ocr/debt-init/preview` | `debt_init.import` | body `Body_preview_debt_init_api_v1_ocr_debt_init_preview_post` | 200 `DebtInitPreview` | [preview_debt_init](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L90) |
| GET | `/api/v1/ocr/debt-init/records` | `debt_init.view` | `page`(query), `pageSize`(query), `batchId`(query), `scope`(query) | 200 `DebtInitRecordPage` | [read_debt_init_records](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L209) |
| POST | `/api/v1/ocr/debt-init/records` | `debt_init.import` | body `DebtInitSave` | 200 `DebtInitSaveResult` | [create_debt_init_records](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L108) |
| POST | `/api/v1/ocr/debt-init/records/clear` | `debt_init.delete` | body `DebtInitRecordClear` | 200 `DebtInitRecordClearResult` | [clear_debt_init_records](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L230) |
| POST | `/api/v1/ocr/debt-init/records/transfer-jobs` | `debt_init.transfer` | body `DebtInitTransferStart` | 202 `DebtInitTransferJobItem` | [create_debt_init_transfer_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L277) |
| GET | `/api/v1/ocr/debt-init/records/transfer-jobs/active` | `debt_init.view` | — | 200 `DebtInitTransferJobItem` / `null` | [read_active_debt_init_transfer_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L295) |
| DELETE | `/api/v1/ocr/debt-init/records/transfer-jobs/{job_id}` | `debt_init.transfer` | `job_id`(path*) | 200 `DebtInitTransferJobItem` | [cancel_debt_init_transfer_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L316) |
| GET | `/api/v1/ocr/debt-init/records/transfer-jobs/{job_id}` | `debt_init.view` | `job_id`(path*) | 200 `DebtInitTransferJobItem` | [read_debt_init_transfer_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L305) |
| POST | `/api/v1/ocr/debt-init/records/transfer-pending` | `debt_init.transfer` | body `DebtInitTransferStart` | 200 `DebtInitPendingTransferResult` | [transfer_debt_init_records_to_pending](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L266) |
| POST | `/api/v1/ocr/debt-init/records/upload` | `debt_init.import` | body `Body_create_debt_init_records_with_source_api_v1_ocr_debt_init_records_upload_post` | 200 `DebtInitSaveResult` | [create_debt_init_records_with_source](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L119) |
| PATCH | `/api/v1/ocr/debt-init/records/{record_id}` | `debt_init.edit` | `record_id`(path*)；body `DebtInitUpdate` | 200 `DebtInitRecordItem` | [patch_debt_init_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L243) |
| POST | `/api/v1/ocr/debt-init/records/{record_id}/transfer` | `debt_init.transfer` | `record_id`(path*) | 200 `DebtInitRecordItem` | [transfer_debt_init_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L255) |
| GET | `/api/v1/ocr/debt-init/template` | `debt_init.view` | — | 200 — | [download_debt_init_template](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L68) |

## backend/app/modules/ocr/api/docs_sortout.py

共 4 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| POST | `/api/v1/ocr/document-sortout-jobs` | `ocr.create` | body `DocumentSortoutStartRequest` | 200 `DocumentSortoutStartResponse` | [start_document_sortout_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/docs_sortout.py#L53) |
| DELETE | `/api/v1/ocr/document-sortout-jobs/{job_id}` | `ocr.delete` | `job_id`(path*) | 204 — | [cancel_document_sortout_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/docs_sortout.py#L179) |
| GET | `/api/v1/ocr/document-sortout-jobs/{job_id}` | `ocr.view` | `job_id`(path*) | 200 `DocumentSortoutStatusResponse` | [get_document_sortout_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/docs_sortout.py#L109) |
| GET | `/api/v1/ocr/document-sortout-jobs/{job_id}/documents/{document_id}/diagnostics` | `ocr.debug` | `job_id`(path*), `document_id`(path*) | 200 `DocumentSortoutDebugInfoResponse` | [get_document_sortout_debug_info](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/docs_sortout.py#L149) |

## backend/app/modules/ocr/api/documents.py

共 25 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/ocr/documents` | `ocr.view` | `page`(query), `page_size`(query) | 200 `OcrDocumentListResponse` | [get_ocr_documents](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L108) |
| POST | `/api/v1/ocr/documents` | `ocr.create` | body `Body_save_ocr_document_api_api_v1_ocr_documents_post` | 200 `OcrDocumentSaveResponse` | [save_ocr_document_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L803) |
| GET | `/api/v1/ocr/documents/base-material-candidates` | `materials.view` | `keyword`(query), `page`(query), `page_size`(query) | 200 — | [get_base_material_candidates](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L179) |
| GET | `/api/v1/ocr/documents/base-materials` | `materials.view` | `companyName`(query*), `debtorId`(query) | 200 — | [get_company_base_materials](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L148) |
| POST | `/api/v1/ocr/documents/base-materials/association-preview` | `materials.create` | body `OcrBaseMaterialAssociationPreviewData` | 200 `OcrBaseMaterialAssociationPreviewResponse` | [preview_base_material_associations](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L249) |
| POST | `/api/v1/ocr/documents/base-materials/bind` | `materials.create` | body `OcrBaseMaterialBindData` | 204 — | [bind_base_materials](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L298) |
| POST | `/api/v1/ocr/documents/base-materials/bind-batch` | `materials.create` | body `OcrBaseMaterialBatchBindData` | 204 — | [bind_base_material_groups](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L270) |
| GET | `/api/v1/ocr/documents/base-materials/three-year-one-period` | `materials.view` | `companyName`(query*), `debtorId`(query) | 200 — | [get_three_year_one_period_materials](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L119) |
| POST | `/api/v1/ocr/documents/base-materials/unbind` | `materials.create` | body `OcrBaseMaterialBindData` | 204 — | [unbind_base_materials](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L359) |
| POST | `/api/v1/ocr/documents/review-job` | `ocr.create` | body `OcrReviewJobSaveData` | 200 `OcrDocumentSaveResponse` | [save_ocr_review_job_document_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L749) |
| POST | `/api/v1/ocr/documents/spreadsheet-upload` | `ocr.create` | body `Body_upload_spreadsheet_document_api_api_v1_ocr_documents_spreadsheet_upload_post` | 200 `OcrDocumentSaveResponse` | [upload_spreadsheet_document_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L429) |
| POST | `/api/v1/ocr/documents/word-upload` | `ocr.create` | body `Body_upload_word_document_api_api_v1_ocr_documents_word_upload_post` | 200 `OcrDocumentSaveResponse` | [upload_word_document_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L380) |
| DELETE | `/api/v1/ocr/documents/{document_id}` | `ocr.delete` | `document_id`(path*) | 204 — | [delete_ocr_document_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L524) |
| GET | `/api/v1/ocr/documents/{document_id}` | `ocr.view` | `document_id`(path*) | 200 `OcrDocumentDetailResponse` | [get_ocr_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L500) |
| PUT | `/api/v1/ocr/documents/{document_id}` | `ocr.edit` | `document_id`(path*)；body `OcrDocumentUpdateData` | 200 `OcrDocumentSaveResponse` | [update_ocr_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L556) |
| GET | `/api/v1/ocr/documents/{document_id}/analyses` | `ocr.view` | `document_id`(path*) | 200 `AnalysisRunListResponse` | [get_ocr_document_analyses](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L579) |
| POST | `/api/v1/ocr/documents/{document_id}/analyses` | `ocr.analyze` | `document_id`(path*)；body `AnalysisCreateData` | 201 `AnalysisRunDetail` | [create_ocr_document_analysis](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L593) |
| DELETE | `/api/v1/ocr/documents/{document_id}/analyses/{analysis_id}` | `ocr.analyze` | `document_id`(path*), `analysis_id`(path*) | 204 — | [cancel_ocr_document_analysis](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L660) |
| GET | `/api/v1/ocr/documents/{document_id}/analyses/{analysis_id}` | `ocr.view` | `document_id`(path*), `analysis_id`(path*) | 200 `AnalysisRunDetail` | [get_ocr_document_analysis](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L617) |
| GET | `/api/v1/ocr/documents/{document_id}/analyses/{analysis_id}/debug` | `ocr.debug` | `document_id`(path*), `analysis_id`(path*) | 200 `AnalysisDebugResponse` | [get_ocr_document_analysis_debug](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L637) |
| PATCH | `/api/v1/ocr/documents/{document_id}/analyses/{analysis_id}/fields/{field_id}` | `ocr.analyze` | `document_id`(path*), `analysis_id`(path*), `field_id`(path*)；body `ExtractedFieldUpdate` | 200 `ExtractedFieldResponse` | [patch_ocr_extracted_field](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L680) |
| PATCH | `/api/v1/ocr/documents/{document_id}/analyses/{analysis_id}/invoice-lines/{line_item_id}` | `ocr.analyze` | `document_id`(path*), `analysis_id`(path*), `line_item_id`(path*)；body `InvoiceLineItemUpdate` | 200 `InvoiceLineItemResponse` | [patch_ocr_invoice_line_item](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L704) |
| GET | `/api/v1/ocr/documents/{document_id}/pages/{page_no}/image` | `ocr.view` | `document_id`(path*), `page_no`(path*) | 200 — | [get_ocr_document_page_image](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L710) |
| POST | `/api/v1/ocr/documents/{document_id}/replace-with/{replacement_document_id}` | `ocr.edit` | `document_id`(path*), `replacement_document_id`(path*) | 204 — | [replace_ocr_document_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L535) |
| GET | `/api/v1/ocr/documents/{document_id}/source-file` | `ocr.view` | `document_id`(path*) | 200 — | [get_ocr_document_source_file](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L728) |

## backend/app/modules/ocr/api/health.py

共 3 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/ocr/capabilities` | 公开 | — | 200 `OcrCapabilitiesResponse` | [get_ocr_capabilities](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/health.py#L42) |
| GET | `/api/v1/ocr/health` | `ocr_settings.view` | — | 200 `object` | [ocr_health_check](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/health.py#L103) |
| GET | `/api/v1/ocr/service-ready` | 公开 | — | 200 `object` | [get_ocr_service_ready](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/health.py#L55) |

## backend/app/modules/ocr/api/material_list.py

共 2 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| POST | `/api/v1/ocr/material-list/analyze` | `materials.create` | body `Body_analyze_material_list_api_v1_ocr_material_list_analyze_post` | 200 — | [analyze_material_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/material_list.py#L63) |
| POST | `/api/v1/ocr/material-list/package` | `materials.export` | body `MaterialPackageRequest` | 200 — | [package_materials](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/material_list.py#L120) |

## backend/app/modules/ocr/api/modules.py

共 10 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| PATCH | `/api/v1/ocr/documents/{document_id}/module` | `ocr.edit` | `document_id`(path*)；body `OcrEntryMoveData` | 204 — | [move_ocr_document_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L186) |
| GET | `/api/v1/ocr/modules` | `ocr.view` | `page`(query), `page_size`(query), `start`(query), `owner_id`(query) | 200 `OcrModuleListResponse` | [get_ocr_modules](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L59) |
| GET | `/api/v1/ocr/modules/history-search` | `ocr.view` | `keyword`(query*), `page`(query), `page_size`(query), `start`(query) | 200 `OcrHistorySearchResponse` | [search_ocr_history](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L96) |
| GET | `/api/v1/ocr/modules/owners` | `ocr.view` | — | 200 列表[`OcrHistoryOwnerItem`] | [get_ocr_history_owners](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L83) |
| POST | `/api/v1/ocr/modules/root-debt-import-preview` | `ocr.create` | body `OcrRootDebtImportData` | 200 `OcrDebtImportPreviewResponse` | [post_ocr_root_debt_preview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L40) |
| DELETE | `/api/v1/ocr/modules/{module_id}` | `ocr.delete` | `module_id`(path*) | 204 — | [delete_ocr_module_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L210) |
| GET | `/api/v1/ocr/modules/{module_id}` | `ocr.view` | `module_id`(path*) | 200 `OcrModuleDetailResponse` | [get_ocr_module](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L120) |
| PATCH | `/api/v1/ocr/modules/{module_id}` | `ocr.edit` | `module_id`(path*)；body `OcrModuleRenameData` | 200 `OcrModuleDetailResponse` | [rename_ocr_module_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L157) |
| GET | `/api/v1/ocr/modules/{module_id}/debt-import-preview` | `ocr.view` | `module_id`(path*) | 200 `OcrDebtImportPreviewResponse` | [get_ocr_module_debt_preview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L138) |
| PATCH | `/api/v1/ocr/modules/{module_id}/parent` | `ocr.edit` | `module_id`(path*)；body `OcrEntryMoveData` | 200 `OcrModuleDetailResponse` | [move_ocr_module_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L169) |

## backend/app/modules/ocr/api/review.py

共 1 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/ocr/performance-metrics` | `ocr.metrics` | — | 200 — | [get_ocr_performance_metrics](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review.py#L60) |

## backend/app/modules/ocr/api/review_jobs.py

共 3 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| POST | `/api/v1/ocr/review-jobs` | `ocr.create` | `defer`(query)；body `Body_start_ocr_review_job_api_v1_ocr_review_jobs_post` | 200 `OcrReviewJobStartResponse` | [start_ocr_review_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_jobs.py#L58) |
| POST | `/api/v1/ocr/review-jobs/image-bundles` | `ocr.create` | `defer`(query), `archive_only`(query)；body `Body_start_ocr_image_bundle_job_api_v1_ocr_review_jobs_image_bundles_post` | 200 `OcrReviewJobStartResponse` | [start_ocr_image_bundle_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_jobs.py#L149) |
| POST | `/api/v1/ocr/review-jobs/{job_id}/start` | `ocr.create` | `job_id`(path*) | 200 `OcrReviewJobStartResponse` | [start_staged_ocr_review_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_jobs.py#L199) |

## backend/app/modules/ocr/api/review_resources.py

共 10 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| DELETE | `/api/v1/ocr/review-jobs/{job_id}` | `ocr.delete` | `job_id`(path*) | 204 — | [cancel_ocr_review_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L47) |
| GET | `/api/v1/ocr/review-jobs/{job_id}` | `ocr.view` | `job_id`(path*) | 200 `OcrReviewJobStatusResponse` | [get_ocr_review_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L334) |
| GET | `/api/v1/ocr/review-jobs/{job_id}/diagnostics` | `ocr.debug` | `job_id`(path*) | 200 `OcrReviewJobDebugInfoResponse` | [get_ocr_review_job_debug_info](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L456) |
| GET | `/api/v1/ocr/review-jobs/{job_id}/direct-preview` | `ocr.view` | `job_id`(path*) | 200 — | [get_ocr_review_job_direct_preview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L216) |
| GET | `/api/v1/ocr/review-jobs/{job_id}/pages/{page_no}` | `ocr.view` | `job_id`(path*), `page_no`(path*) | 200 `OcrReviewJobResultPageResponse` | [get_ocr_review_job_page](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L381) |
| GET | `/api/v1/ocr/review-jobs/{job_id}/pages/{page_no}/image` | `ocr.view` | `job_id`(path*), `page_no`(path*) | 200 — | [get_ocr_review_job_page_image](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L429) |
| GET | `/api/v1/ocr/review-jobs/{job_id}/source-file` | `ocr.view` | `job_id`(path*) | 200 — | [get_ocr_review_job_source_file](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L111) |
| GET | `/api/v1/ocr/review-jobs/{job_id}/source-images/{image_index}` | `ocr.view` | `job_id`(path*), `image_index`(path*), `preview`(query) | 200 — | [get_ocr_review_job_source_image](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L263) |
| GET | `/api/v1/ocr/review-jobs/{job_id}/source-pages/{page_index}` | `ocr.view` | `job_id`(path*), `page_index`(path*) | 200 — | [get_ocr_review_job_source_page](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L163) |
| DELETE | `/api/v1/ocr/review-jobs/{job_id}/staged-resource` | `ocr.delete` | `job_id`(path*) | 204 — | [release_staged_ocr_review_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L90) |

## backend/app/modules/ocr/api/review_sync.py

共 2 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| POST | `/api/v1/ocr/process` | `ocr.create` | body `Body_process_ocr_file_api_v1_ocr_process_post` | 200 `OcrReviewResponse` | [process_ocr_file](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_sync.py#L29) |
| POST | `/api/v1/ocr/review-process` | `ocr.create` | body `Body_process_ocr_vlm_file_api_v1_ocr_review_process_post` | 200 `OcrCombinedReviewResponse` | [process_ocr_vlm_file](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_sync.py#L55) |

## backend/app/modules/ocr/api/sheet_fill.py

共 16 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/ocr/sheet-fill/active-job` | `sheet_fill.view` | — | 200 `SheetJobResponse` / `null` | [read_active_sheet_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L194) |
| GET | `/api/v1/ocr/sheet-fill/fields` | 公开 | — | 200 `SheetFieldList` | [read_sheet_fields](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L117) |
| GET | `/api/v1/ocr/sheet-fill/fields/export` | 公开 | — | 200 — | [export_sheet_fields](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L128) |
| POST | `/api/v1/ocr/sheet-fill/fields/import` | `sheet_rules.import` | body `Body_import_sheet_fields_api_v1_ocr_sheet_fill_fields_import_post` | 200 `SheetFieldImportResult` | [import_sheet_fields](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L143) |
| GET | `/api/v1/ocr/sheet-fill/jobs` | `sheet_fill.view` | `page`(query), `pageSize`(query), `keyword`(query) | 200 `SheetJobPageResponse` | [read_sheet_jobs](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L86) |
| POST | `/api/v1/ocr/sheet-fill/jobs` | `sheet_fill.create` | body `Body_start_sheet_job_api_v1_ocr_sheet_fill_jobs_post` | 200 `SheetJobStartResponse` | [start_sheet_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L62) |
| GET | `/api/v1/ocr/sheet-fill/jobs/{job_id}` | `sheet_fill.view` | `job_id`(path*) | 200 `SheetJobResponse` | [read_sheet_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L183) |
| GET | `/api/v1/ocr/sheet-fill/jobs/{job_id}/debug` | `sheet_fill.debug` | `job_id`(path*) | 200 — | [download_sheet_debug](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L333) |
| GET | `/api/v1/ocr/sheet-fill/jobs/{job_id}/file` | `sheet_fill.view` | `job_id`(path*), `outputMode`(query) | 200 — | [download_sheet_file](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L298) |
| GET | `/api/v1/ocr/sheet-fill/jobs/{job_id}/mappings` | `sheet_fill.view` | `job_id`(path*) | 200 `SheetMapReview` | [read_sheet_mappings](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L204) |
| POST | `/api/v1/ocr/sheet-fill/jobs/{job_id}/mappings/analyze` | `sheet_fill.create` | `job_id`(path*)；body `SheetMapAnalyze` | 200 `SheetMapAnalyzeResult` | [analyze_sheet_map](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L231) |
| POST | `/api/v1/ocr/sheet-fill/jobs/{job_id}/mappings/confirm` | `sheet_fill.create` | `job_id`(path*)；body `SheetMapConfirm` | 200 `SheetJobStartResponse` | [confirm_sheet_mappings](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L215) |
| GET | `/api/v1/ocr/sheet-fill/jobs/{job_id}/preview` | `sheet_fill.view` | `job_id`(path*) | 200 `SheetPreviewResponse` | [read_sheet_preview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L243) |
| POST | `/api/v1/ocr/sheet-fill/jobs/{job_id}/preview/confirm` | `sheet_fill.create` | `job_id`(path*)；body `SheetPreviewConfirm` | 200 `SheetJobStartResponse` | [confirm_sheet_preview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L254) |
| POST | `/api/v1/ocr/sheet-fill/jobs/{job_id}/rerun` | `sheet_fill.create` | `job_id`(path*) | 200 `SheetJobStartResponse` | [rerun_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L284) |
| POST | `/api/v1/ocr/sheet-fill/jobs/{job_id}/retry` | `sheet_fill.create` | `job_id`(path*) | 200 `SheetJobStartResponse` | [retry_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L270) |

## backend/app/modules/ocr/api/sheet_rules.py

共 13 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/ocr/sheet-fill/org-relations` | `sheet_rules.view` | — | 200 列表[`SheetOrgRelationItem`] | [read_org_relations](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L104) |
| POST | `/api/v1/ocr/sheet-fill/org-relations` | `sheet_rules.edit` | body `SheetOrgRelationItem` | 200 `SheetOrgRelationItem` | [create_org_relation](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L115) |
| PATCH | `/api/v1/ocr/sheet-fill/org-relations/{relation_id}` | `sheet_rules.edit` | `relation_id`(path*)；body `SheetOrgRelationItem` | 200 `SheetOrgRelationItem` | [patch_org_relation](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L134) |
| GET | `/api/v1/ocr/sheet-fill/rule-operators` | `sheet_rules.view` | — | 200 `object` | [read_rule_operators](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L46) |
| PATCH | `/api/v1/ocr/sheet-fill/rule-versions/{version_id}` | `sheet_rules.edit` | `version_id`(path*)；body `SheetRuleDraft` | 200 `SheetRuleVersionItem` | [patch_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L207) |
| POST | `/api/v1/ocr/sheet-fill/rule-versions/{version_id}/preview` | `sheet_rules.edit` | `version_id`(path*)；body `SheetRulePreviewRequest` | 200 `object` | [preview_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L237) |
| POST | `/api/v1/ocr/sheet-fill/rule-versions/{version_id}/publish` | `sheet_rules.publish` | `version_id`(path*) | 200 `SheetRuleVersionItem` | [publish_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L252) |
| POST | `/api/v1/ocr/sheet-fill/rule-versions/{version_id}/validate` | `sheet_rules.edit` | `version_id`(path*) | 200 `object` | [validate_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L223) |
| GET | `/api/v1/ocr/sheet-fill/rules` | `sheet_rules.view` | — | 200 `SheetRuleVersionList` | [read_sheet_rules](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L160) |
| POST | `/api/v1/ocr/sheet-fill/rules` | `sheet_rules.edit` | body `SheetRuleDraft` | 200 `SheetRuleVersionItem` | [create_sheet_rule](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L179) |
| GET | `/api/v1/ocr/sheet-fill/rules/export` | `sheet_rules.view` | — | 200 — | [export_sheet_rules](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L266) |
| POST | `/api/v1/ocr/sheet-fill/rules/import` | `sheet_rules.import` | body `Body_import_sheet_rules_api_v1_ocr_sheet_fill_rules_import_post` | 200 `SheetRuleImportResult` | [import_sheet_rules](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L280) |
| POST | `/api/v1/ocr/sheet-fill/rules/{rule_key}/versions` | `sheet_rules.edit` | `rule_key`(path*) | 200 `SheetRuleVersionItem` | [clone_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L193) |

## backend/app/modules/overview/api.py

共 5 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/overview/funding-gap-config` | `overview.view` | `config_type`(query*), `debtor_id`(query), `debtor_ids`(query), `subject_mode`(query), `scope`(query*), `project_stage`(query*), `date_start`(query*), `date_end`(query*) | 200 `FundingGapConfigResponse` / `null` | [read_funding_gap_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/overview/api.py#L37) |
| PUT | `/api/v1/overview/funding-gap-config` | `overview.edit` | body `FundingGapConfigUpsert` | 200 `FundingGapConfigResponse` | [save_funding_gap_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/overview/api.py#L70) |
| DELETE | `/api/v1/overview/funding-gap-config/{config_id}` | `overview.edit` | `config_id`(path*), `version_no`(query*) | 204 — | [clear_funding_gap_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/overview/api.py#L82) |
| GET | `/api/v1/overview/planned-financing-config` | `overview.view` | `calendar_year`(query*), `project_stage`(query), `project_debt_type`(query), `debtor_id`(query), `debtor_ids`(query), `subject_mode`(query), `include_descendants`(query) | 200 `PlannedFinancingConfigListResponse` | [read_planned_financing_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/overview/api.py#L101) |
| PUT | `/api/v1/overview/planned-financing-config` | `overview.edit` | body `PlannedFinancingConfigBatchUpsert` | 200 `PlannedFinancingConfigListResponse` | [save_planned_financing_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/overview/api.py#L132) |

## backend/app/modules/project/api.py

共 29 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/projects` | `project.view` | `keyword`(query), `project_id`(query), `fiscal_year`(query), `debtor_id`(query), `debtor_ids`(query), `subject_mode`(query), `include_descendants`(query), `creditor_org_id`(query), `creditor_keyword`(query), `financing_type`(query), `stage`(query), `planned_min`(query), `planned_max`(query), `funding_status`(query), `page`(query), `page_size`(query) | 200 `ProjectListResponse` | [get_project_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L318) |
| POST | `/api/v1/projects` | `project.create` | body `ProjectPayload` | 201 `ProjectResponse` | [create_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L417) |
| GET | `/api/v1/projects/code-preview` | `project.view` | `fiscal_year`(query*) | 200 `ProjectCodePreviewResponse` | [get_project_code_preview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L445) |
| GET | `/api/v1/projects/engagements` | `project.view` | `keyword`(query), `status`(query), `include_converted`(query), `page`(query), `page_size`(query) | 200 `ProjectEngagementListResponse` | [get_project_engagement_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L518) |
| POST | `/api/v1/projects/engagements` | `project.create` | body `ProjectEngagementCreatePayload` | 201 `ProjectEngagementResponse` | [create_project_engagement_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L538) |
| GET | `/api/v1/projects/engagements/{engagement_id}` | `project.view` | `engagement_id`(path*) | 200 `ProjectEngagementResponse` | [get_project_engagement_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L551) |
| PUT | `/api/v1/projects/engagements/{engagement_id}` | `project.edit` | `engagement_id`(path*)；body `ProjectEngagementUpdatePayload` | 200 `ProjectEngagementResponse` | [update_project_engagement_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L560) |
| POST | `/api/v1/projects/engagements/{engagement_id}/convert` | `project.edit` | `engagement_id`(path*)；body `ProjectEngagementConvertPayload` | 201 `ProjectResponse` | [convert_project_engagement_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L583) |
| POST | `/api/v1/projects/engagements/{engagement_id}/status` | `project.edit` | `engagement_id`(path*)；body `ProjectEngagementStatusPayload` | 200 `ProjectEngagementResponse` | [change_project_engagement_status_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L570) |
| GET | `/api/v1/projects/financing-pipeline/details` | `project.view` | `category`(query*), `as_of_date`(query*), `subject_mode`(query), `debtor_ids`(query), `include_descendants`(query), `page`(query), `page_size`(query) | 200 `ProjectFinancingPipelineDetailResponse` | [get_project_financing_pipeline_details](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L491) |
| GET | `/api/v1/projects/financing-pipeline/summary` | `project.view` | `as_of_date`(query*), `subject_mode`(query), `debtor_ids`(query), `include_descendants`(query) | 200 `ProjectFinancingPipelineSummaryResponse` | [get_project_financing_pipeline_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L470) |
| POST | `/api/v1/projects/import` | `project.import` | body `Body_import_project_records_api_v1_projects_import_post` | 200 `ProjectImportResponse` | [import_project_records](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L227) |
| GET | `/api/v1/projects/import-template` | `project.import` | — | 200 — | [get_project_import_template](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L205) |
| GET | `/api/v1/projects/options` | 公开 | `keyword`(query), `limit`(query), `offset`(query), `with_has_more`(query) | 200 — | [get_project_options](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L454) |
| POST | `/api/v1/projects/query` | `project.view` | body `ProjectTableQueryRequest` | 200 `ProjectListResponse` | [query_project_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L367) |
| POST | `/api/v1/projects/query-selection` | `project.view` | body `ProjectTableQueryRequest` | 200 `ProjectSelectionIdsResponse` | [query_project_selection_ids](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L376) |
| DELETE | `/api/v1/projects/{project_id}` | `project.delete` | `project_id`(path*), `version_no`(query*) | 200 `ProjectDeleteResponse` | [delete_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L687) |
| GET | `/api/v1/projects/{project_id}` | `project.view` | `project_id`(path*) | 200 `ProjectResponse` | [get_project_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L597) |
| PUT | `/api/v1/projects/{project_id}` | `project.edit` | `project_id`(path*)；body `ProjectUpdatePayload` | 200 `ProjectResponse` | [update_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L606) |
| POST | `/api/v1/projects/{project_id}/abandon` | `project.stage` | `project_id`(path*)；body `ProjectAbandonPayload` | 200 `ProjectResponse` | [abandon_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L657) |
| POST | `/api/v1/projects/{project_id}/complete-funding` | `project.stage` | `project_id`(path*)；body `ProjectFundingCompletePayload` | 200 `ProjectResponse` | [complete_project_funding_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L642) |
| GET | `/api/v1/projects/{project_id}/creditors` | `project.view` | `project_id`(path*) | 200 `ProjectCreditorListResponse` | [get_project_creditors](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L764) |
| PUT | `/api/v1/projects/{project_id}/creditors` | `project.edit` | `project_id`(path*)；body `ProjectCreditorUpdatePayload` | 200 `ProjectResponse` | [replace_project_creditor_records](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L773) |
| POST | `/api/v1/projects/{project_id}/engagement-status` | `project.edit` | `project_id`(path*)；body `ProjectEngagementStatusPayload` | 200 `ProjectResponse` | [update_project_engagement_status_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L630) |
| GET | `/api/v1/projects/{project_id}/history` | `project.view` | `project_id`(path*), `page`(query), `page_size`(query) | 200 `BusinessHistoryListResponse` | [get_project_history](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L721) |
| GET | `/api/v1/projects/{project_id}/history/{history_id}` | `project.view` | `project_id`(path*), `history_id`(path*) | 200 `BusinessHistoryDetailResponse` | [get_project_history_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L742) |
| PUT | `/api/v1/projects/{project_id}/refinance` | `project.edit` | `project_id`(path*)；body `ProjectRefinanceUpdatePayload` | 200 `ProjectResponse` | [update_project_refinance_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L618) |
| POST | `/api/v1/projects/{project_id}/restore` | `project.restore` | `project_id`(path*), `version_no`(query) | 200 `ProjectResponse` | [restore_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L703) |
| POST | `/api/v1/projects/{project_id}/restore-abandonment` | `project.restore` | `project_id`(path*)；body `ProjectRestoreAbandonPayload` | 200 `ProjectResponse` | [restore_abandoned_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L672) |

## backend/app/modules/query_center/api.py

共 20 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/query-center/credit-statistics` | `query.view` | `keyword`(query), `category`(query), `page`(query), `page_size`(query) | 200 `CreditStatisticsResponse` | [read_enterprise_credit_statistics](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L436) |
| GET | `/api/v1/query-center/debt-monthly` | `query.view` | `debtor_id`(query), `debtor_ids`(query), `subject_mode`(query), `include_descendants`(query), `date_start`(query*), `date_end`(query*), `project_stage`(query), `project_debt_type`(query), `project_debt_types`(query) | 200 `DebtMonthlyResponse` | [read_debt_monthly](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L490) |
| GET | `/api/v1/query-center/debt-refinance` | `query.view` | `debtor_id`(query*), `include_descendants`(query), `date_start`(query), `date_end`(query), `page`(query), `page_size`(query) | 200 `DebtRefinanceResponse` | [read_debt_refinance](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L556) |
| GET | `/api/v1/query-center/debt-structure` | `query.view` | `debtor_id`(query*), `include_descendants`(query), `as_of_date`(query), `date_start`(query), `date_end`(query) | 200 `DebtStructureResponse` | [read_debt_structure](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L416) |
| POST | `/api/v1/query-center/export` | `query.export` | body `QueryCenterExportRequest` | 200 — | [export_query_center_result](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L296) |
| GET | `/api/v1/query-center/financing-structure` | `query.view` | `debtor_id`(query*), `include_descendants`(query), `as_of_date`(query), `date_start`(query), `date_end`(query) | 200 `FinancingStructureResponse` | [read_financing_structure](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L332) |
| GET | `/api/v1/query-center/financing-total` | `query.view` | `debtor_id`(query), `debtor_ids`(query), `subject_mode`(query), `include_descendants`(query), `as_of_date`(query), `date_start`(query), `date_end`(query), `project_stage`(query), `project_debt_types`(query) | 200 `FinancingTotalResponse` | [read_financing_total](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L352) |
| GET | `/api/v1/query-center/group-overview` | `query.view` | `debtor_id`(query), `debtor_ids`(query), `subject_mode`(query), `include_descendants`(query), `as_of_date`(query), `date_start`(query), `date_end`(query), `project_stage`(query), `project_debt_types`(query), `include_average_comprehensive_cost`(query), `include_comprehensive_cost_change`(query) | 200 `GroupOverviewResponse` | [read_group_overview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L522) |
| GET | `/api/v1/query-center/group-scope` | `query.view` | `debtor_id`(query*), `include_inactive`(query) | 200 `GroupScopeResponse` | [read_group_scope](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L456) |
| GET | `/api/v1/query-center/guarantees/month-end` | `query.view` | `debtor_id`(query*), `include_descendants`(query), `as_of_date`(query*), `page`(query), `page_size`(query) | 200 `GuaranteeMonthEndResponse` | [read_guarantees_at_month_end](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L470) |
| POST | `/api/v1/query-center/interpret` | `query.view` | body `QueryIntentRequest` | 200 `QueryIntentResponse` | [interpret_query_center_prompt](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L273) |
| GET | `/api/v1/query-center/planned-financing-trial` | `query.view` | `debtor_id`(query), `debtor_ids`(query), `subject_mode`(query), `include_descendants`(query), `date_start`(query), `date_end`(query), `project_stage`(query), `project_debt_types`(query) | 200 `PlannedFinancingTrialResponse` | [read_planned_financing_trial](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L382) |
| POST | `/api/v1/query-center/reports/composed/export` | `query.export` | body `ComposedQueryReportRequest` | 200 — | [export_composed_query_report](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L161) |
| POST | `/api/v1/query-center/reports/composed/intelligence` | `query.compose` | body `ComposedReportIntelligenceRequest` | 200 `ComposedReportIntelligenceResponse` | [draft_composed_query_report_intelligence](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L140) |
| POST | `/api/v1/query-center/reports/composed/intelligence-jobs` | `query.compose` | body `ComposedReportIntelligenceRequest` | 202 `ComposedReportIntelligenceJobResponse` | [create_composed_query_report_intelligence_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L90) |
| GET | `/api/v1/query-center/reports/composed/intelligence-jobs/{job_id}` | `query.compose` | `job_id`(path*) | 200 `ComposedReportIntelligenceJobResponse` | [get_composed_query_report_intelligence_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L107) |
| POST | `/api/v1/query-center/reports/composed/intelligence-jobs/{job_id}/retry` | `query.compose` | `job_id`(path*) | 202 `ComposedReportIntelligenceJobResponse` | [retry_composed_query_report_intelligence_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L123) |
| POST | `/api/v1/query-center/reports/composed/preview` | `query.compose` | body `ComposedQueryReportRequest` | 200 `ComposedQueryReportResponse` | [preview_composed_query_report](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L78) |
| POST | `/api/v1/query-center/reports/financing-work/export` | `query.export` | body `FinancingWorkReportRequest` | 200 — | [export_financing_work_report](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L228) |
| POST | `/api/v1/query-center/reports/financing-work/preview` | `query.view` | body `FinancingWorkReportRequest` | 200 `FinancingWorkReportResponse` | [preview_financing_work_report](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L216) |

## backend/app/modules/query_language/api.py

共 1 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| POST | `/api/v1/query/interpret` | `query.view` | body `QueryInterpretRequest` | 200 `QueryInterpretResponse` | [interpret_natural_language_query](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_language/api.py#L21) |

## backend/app/modules/statistics/api.py

共 5 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/statistics/calendar` | `debt.view` | `month`(query*), `project_id`(query), `keyword`(query), `flow_type`(query), `debtor_id`(query), `debtor_ids`(query), `subject_mode`(query), `include_descendants`(query), `project_stage`(query), `project_debt_types`(query) | 200 `CalendarMonthlyResponse` | [read_calendar_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/statistics/api.py#L57) |
| GET | `/api/v1/statistics/calendar/{target_date}/items` | `debt.view` | `target_date`(path*), `project_id`(query), `keyword`(query), `flow_type`(query), `debtor_id`(query), `debtor_ids`(query), `subject_mode`(query), `include_descendants`(query), `project_stage`(query) | 200 `CalendarDailyItemsResponse` | [read_calendar_items](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/statistics/api.py#L90) |
| GET | `/api/v1/statistics/cashflows` | `debt.view` | `project_id`(query), `keyword`(query), `flow_type`(query), `flow_types`(query), `plan_date_start`(query), `plan_date_end`(query), `debtor_id`(query), `debtor_ids`(query), `subject_mode`(query), `include_descendants`(query), `project_stage`(query), `project_debt_types`(query), `page`(query), `page_size`(query) | 200 `CashflowListResponse` | [read_cashflow_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/statistics/api.py#L121) |
| GET | `/api/v1/statistics/pending-items` | `debt.view` | — | 200 `PendingItemsResponse` | [read_pending_items](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/statistics/api.py#L49) |
| GET | `/api/v1/statistics/projects/summary` | `debt.view` | `project_id`(query), `keyword`(query), `flow_type`(query), `plan_date_start`(query), `plan_date_end`(query), `debtor_id`(query), `debtor_ids`(query), `subject_mode`(query), `include_descendants`(query), `project_stage`(query) | 200 `ProjectCashflowSummaryResponse` | [read_project_cashflow_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/statistics/api.py#L162) |

## backend/app/modules/sync/api.py

共 2 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/sync/changes` | 公开 | `after`(query), `scopes`(query), `limit`(query) | 200 `SyncChangesResponse` | [get_sync_changes](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/sync/api.py#L16) |
| GET | `/api/v1/sync/snapshot` | 公开 | — | 200 `SyncSnapshotResponse` | [get_sync_snapshot](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/sync/api.py#L37) |

## backend/app/modules/system/api.py

共 18 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/meta/options` | 公开 | — | 200 `MetaOptionsResponse` | [read_meta_options](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L65) |
| GET | `/api/v1/system/dev/business-test-data` | `development.view` | — | 200 `BusinessTestDataSummary` | [read_business_test_data](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L108) |
| POST | `/api/v1/system/dev/business-test-data/import` | `development.import` | — | 200 `BusinessTestDataImportResponse` | [import_business_test_data_for_development](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L126) |
| POST | `/api/v1/system/dev/drop-all-tables` | `development.clear` | body `DropAllTablesPayload` | 200 `DropAllTablesResponse` | [drop_all_tables_for_development](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L93) |
| GET | `/api/v1/system/holiday-schedules/{year}` | `holidays.view` | `year`(path*) | 200 `HolidayScheduleResponse` | [read_holiday_schedule](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L177) |
| PUT | `/api/v1/system/holiday-schedules/{year}` | `holidays.edit` | `year`(path*)；body `HolidayScheduleUpsertPayload` | 200 `HolidayScheduleResponse` | [save_holiday_schedule](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L191) |
| POST | `/api/v1/system/holiday-schedules/{year}/publish` | `holidays.publish` | `year`(path*) | 200 `HolidaySchedulePublishResponse` | [publish_holiday_schedule_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L211) |
| GET | `/api/v1/system/holidays` | `holidays.view` | `year`(query*) | 200 `HolidayCalendarListResponse` | [read_holiday_calendar](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L144) |
| POST | `/api/v1/system/holidays/import-official` | `holidays.import` | `year`(query*) | 200 `OfficialHolidayImportResponse` | [import_official_holiday_schedule](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L165) |
| GET | `/api/v1/system/holidays/official-preview` | `holidays.view` | `year`(query*) | 200 `OfficialHolidayPreviewResponse` | [read_official_holiday_preview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L153) |
| DELETE | `/api/v1/system/holidays/{holiday_date}` | `holidays.edit` | `holiday_date`(path*) | 200 `CashflowRefreshResult` | [delete_holiday_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L239) |
| PUT | `/api/v1/system/holidays/{holiday_date}` | `holidays.edit` | `holiday_date`(path*)；body `HolidayCalendarUpsertPayload` | 200 `HolidayUpsertResponse` | [upsert_holiday_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L229) |
| GET | `/api/v1/system/lpr-rates` | `lpr.view` | `year`(query*) | 200 `LprRateListResponse` | [read_lpr_rates](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L248) |
| PUT | `/api/v1/system/lpr-rates` | `lpr.edit` | body `LprRateUpsertPayload` | 200 `LprRateUpsertResponse` | [upsert_lpr_rate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L257) |
| POST | `/api/v1/system/lpr-rates/import-official` | `lpr.import` | — | 200 `LprRateImportResponse` | [import_official_lpr_rates](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L266) |
| DELETE | `/api/v1/system/lpr-rates/{rate_id}` | `lpr.delete` | `rate_id`(path*) | 204 — | [delete_lpr_rate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L274) |
| GET | `/api/v1/system/parameters` | `@parameters` | — | 200 `SystemParameterListResponse` | [read_system_parameters](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L73) |
| PUT | `/api/v1/system/parameters/{param_key}` | `@parameters` | `param_key`(path*)；body `SystemParameterUpdatePayload` | 200 `SystemParameterResponse` | [update_system_parameter_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L83) |

## backend/app/modules/table_views/api.py

共 7 项。

| 方法 | 完整路径 | 权限 | 请求摘要 | 成功响应 | 处理函数及代码依据 |
| --- | --- | --- | --- | --- | --- |
| GET | `/api/v1/table-page-size-preferences/{surface_key}` | 公开 | `surface_key`(path*) | 200 `TablePageSizePreferenceResponse` | [get_table_page_size_preference](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/table_views/api.py#L35) |
| PUT | `/api/v1/table-page-size-preferences/{surface_key}` | 公开 | `surface_key`(path*)；body `TablePageSizePreferenceRequest` | 200 `TablePageSizePreferenceResponse` | [put_table_page_size_preference](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/table_views/api.py#L58) |
| PUT | `/api/v1/table-preferences/{surface_key}` | 公开 | `surface_key`(path*)；body `TablePreferenceRequest` | 200 `TableViewResponse` | [put_table_preference](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/table_views/api.py#L119) |
| GET | `/api/v1/table-views` | 公开 | `surface_key`(query*) | 200 `TableViewCollectionResponse` | [get_table_views](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/table_views/api.py#L96) |
| POST | `/api/v1/table-views` | 公开 | body `TableViewCreateRequest` | 201 `TableViewResponse` | [post_table_view](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/table_views/api.py#L143) |
| DELETE | `/api/v1/table-views/{view_id}` | 公开 | `view_id`(path*), `expected_version`(query) | 204 — | [remove_table_view](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/table_views/api.py#L181) |
| PATCH | `/api/v1/table-views/{view_id}` | 公开 | `view_id`(path*)；body `TableViewUpdateRequest` | 200 `TableViewResponse` | [patch_table_view](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/table_views/api.py#L163) |
