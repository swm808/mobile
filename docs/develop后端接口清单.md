# develop 后端接口清单

代码基线：`origin/develop`，提交 `56437f747980389f06d0574a2e5f25460280f3ef`。

静态扫描得到 508 个路由声明。此清单用于覆盖追踪，不代表已进行服务启动、接口调用或 Android 联调。路径依据 application.py 及 OCR 子路由挂载关系补齐；部署时核对实际 OpenAPI 与反向代理前缀。根路径与健康检查不加 API 前缀。

业务接口默认前缀 `/api/v1`。权限应结合 `auth/http_permission_rules.py`、`permission_catalog.py` 和具体服务数据范围校验；不能仅按 HTTP 方法判断授权。

## backend/app/application.py

共 2 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/` | [read_root](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/application.py#L609) |
| GET | `/health` | [health_check](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/application.py#L613) |

## backend/app/modules/ai_dispatch/api.py

共 7 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/system/ai-servers` | [get_ai_servers](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ai_dispatch/api.py#L28) |
| GET | `/api/v1/system/ai-servers/status` | [get_ai_server_status](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ai_dispatch/api.py#L34) |
| POST | `/api/v1/system/ai-servers` | [create_ai_server](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ai_dispatch/api.py#L40) |
| PUT | `/api/v1/system/ai-servers/{server_id}` | [update_ai_server](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ai_dispatch/api.py#L49) |
| DELETE | `/api/v1/system/ai-servers/{server_id}` | [delete_ai_server](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ai_dispatch/api.py#L61) |
| POST | `/api/v1/system/ai-servers/{server_id}/test` | [test_ai_server](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ai_dispatch/api.py#L73) |
| GET | `/api/v1/system/ai-stats` | [get_ai_stats](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ai_dispatch/api.py#L88) |

## backend/app/modules/announcements/api.py

共 7 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/announcements` | [read_announcements](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/announcements/api.py#L22) |
| GET | `/api/v1/announcements/manage` | [read_announcements_for_management](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/announcements/api.py#L33) |
| POST | `/api/v1/announcements` | [add_announcement](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/announcements/api.py#L44) |
| PUT | `/api/v1/announcements/{announcement_id}` | [edit_announcement](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/announcements/api.py#L53) |
| POST | `/api/v1/announcements/{announcement_id}/publish` | [publish_announcement_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/announcements/api.py#L63) |
| POST | `/api/v1/announcements/{announcement_id}/withdraw` | [withdraw_announcement_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/announcements/api.py#L72) |
| DELETE | `/api/v1/announcements/{announcement_id}` | [remove_announcement](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/announcements/api.py#L81) |

## backend/app/modules/assistant/api.py

共 30 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/assistant/turns` | [post_assistant_turn](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L129) |
| POST | `/api/v1/assistant/actions` | [post_assistant_action](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L150) |
| POST | `/api/v1/assistant/loan-contracts/summary` | [create_loan_contract_summary](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L163) |
| POST | `/api/v1/assistant/loan-contracts/debt-candidates` | [search_loan_contract_debt_candidates](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L188) |
| POST | `/api/v1/assistant/loan-contracts/archive` | [archive_existing_loan_contract](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L207) |
| POST | `/api/v1/assistant/route` | [route_dialogue](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L219) |
| POST | `/api/v1/assistant/query-summary` | [summarize_query_result](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L247) |
| POST | `/api/v1/assistant/conversations` | [create_free_chat_conversation](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L265) |
| GET | `/api/v1/assistant/conversations` | [list_free_chat_conversations](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L281) |
| GET | `/api/v1/assistant/conversations/{conversation_id}` | [read_free_chat_conversation](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L292) |
| DELETE | `/api/v1/assistant/conversations/{conversation_id}` | [delete_free_chat_conversation](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L304) |
| PUT | `/api/v1/assistant/conversations/{conversation_id}/transcript` | [sync_ai_conversation_transcript](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L317) |
| POST | `/api/v1/assistant/conversations/{conversation_id}/messages` | [post_free_chat_message](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L336) |
| POST | `/api/v1/assistant/sessions` | [create_assistant_session](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L373) |
| GET | `/api/v1/assistant/sessions/{session_id}` | [read_assistant_session](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L429) |
| POST | `/api/v1/assistant/sessions/{session_id}/form-handoff` | [issue_assistant_form_handoff](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L442) |
| POST | `/api/v1/assistant/sessions/{session_id}/missing-master-data` | [start_assistant_missing_master_data](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L475) |
| POST | `/api/v1/assistant/sessions/{session_id}/resume-parent` | [resume_assistant_parent_session](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L501) |
| POST | `/api/v1/assistant/sessions/{session_id}/messages` | [post_assistant_message](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L510) |
| POST | `/api/v1/assistant/sessions/{session_id}/fields` | [post_assistant_fields](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L571) |
| POST | `/api/v1/assistant/sessions/{session_id}/form-state` | [post_assistant_form_state](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L594) |
| POST | `/api/v1/assistant/sessions/{session_id}/selections` | [post_assistant_selection](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L627) |
| POST | `/api/v1/assistant/sessions/{session_id}/confirm` | [confirm_assistant_session](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L654) |
| POST | `/api/v1/assistant/sessions/{session_id}/debt-memo/form-state` | [post_assistant_debt_memo_form_state](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L676) |
| POST | `/api/v1/assistant/sessions/{session_id}/debt-memo/form-saved` | [post_assistant_debt_memo_form_saved](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L695) |
| POST | `/api/v1/assistant/sessions/{session_id}/project/form-state` | [post_assistant_project_form_state](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L714) |
| POST | `/api/v1/assistant/sessions/{session_id}/project/form-saved` | [post_assistant_project_form_saved](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L732) |
| POST | `/api/v1/assistant/sessions/{session_id}/debt-form-saved` | [post_assistant_debt_form_saved](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L750) |
| POST | `/api/v1/assistant/sessions/{session_id}/debt-extraction/retry` | [post_assistant_debt_extraction_retry](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L785) |
| POST | `/api/v1/assistant/sessions/{session_id}/cancel` | [cancel_assistant_session](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/api.py#L817) |

## backend/app/modules/assistant/debt_prefill_api.py

共 8 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/ocr/root-debt-prefill` | [post_ocr_root_debt_prefill](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/debt_prefill_api.py#L47) |
| POST | `/api/v1/ocr/modules/{module_id}/debt-prefill` | [post_ocr_module_debt_prefill](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/debt_prefill_api.py#L93) |
| POST | `/api/v1/ocr/documents/{document_id}/analyses/{analysis_id}/controlled-company-prefill` | [post_ocr_controlled_company_prefill](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/debt_prefill_api.py#L129) |
| POST | `/api/v1/ocr/documents/base-materials/entry` | [post_ocr_base_material_entry](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/debt_prefill_api.py#L155) |
| POST | `/api/v1/ocr/documents/base-materials/entry-jobs` | [create_ocr_base_material_entry_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/debt_prefill_api.py#L177) |
| GET | `/api/v1/ocr/documents/base-materials/entry-jobs/active` | [get_active_ocr_base_material_entry_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/debt_prefill_api.py#L199) |
| GET | `/api/v1/ocr/documents/base-materials/entry-jobs/{job_id}` | [get_ocr_base_material_entry_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/debt_prefill_api.py#L213) |
| POST | `/api/v1/ocr/documents/{document_id}/analyses/{analysis_id}/real-estate-prefill` | [post_ocr_real_estate_prefill](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/assistant/debt_prefill_api.py#L232) |

## backend/app/modules/audit/api.py

共 2 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/audit/logs` | [read_operation_logs](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/audit/api.py#L16) |
| GET | `/api/v1/audit/logs/{log_id}` | [read_operation_log_detail](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/audit/api.py#L40) |

## backend/app/modules/auth/api.py

共 13 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/auth/captcha` | [issue_captcha](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/api.py#L66) |
| POST | `/api/v1/auth/login` | [login](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/api.py#L72) |
| POST | `/api/v1/auth/logout` | [logout](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/api.py#L122) |
| POST | `/api/v1/auth/activity` | [refresh_activity](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/api.py#L134) |
| GET | `/api/v1/auth/me` | [get_me](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/api.py#L161) |
| PUT | `/api/v1/auth/me/password` | [change_my_password](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/api.py#L178) |
| GET | `/api/v1/auth/users` | [read_regular_users](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/api.py#L188) |
| POST | `/api/v1/auth/users` | [create_regular_user](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/api.py#L199) |
| PUT | `/api/v1/auth/users/{user_id}` | [update_regular_user](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/api.py#L209) |
| POST | `/api/v1/auth/users/{user_id}/deactivate` | [deactivate_user](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/api.py#L220) |
| POST | `/api/v1/auth/users/{user_id}/activate` | [activate_user](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/api.py#L234) |
| DELETE | `/api/v1/auth/users/{user_id}` | [delete_user](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/api.py#L239) |
| POST | `/api/v1/auth/user-admin/transfer` | [transfer_user_admin](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/api.py#L244) |

## backend/app/modules/auth/permission_api.py

共 2 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/system/permissions` | [read_permission_configuration](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/permission_api.py#L49) |
| PUT | `/api/v1/system/permissions/{role_code}` | [save_role_permissions](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/auth/permission_api.py#L62) |

## backend/app/modules/backup/api.py

共 20 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/system/backups` | [read_backups](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L92) |
| GET | `/api/v1/system/backups/schedule` | [read_backup_schedule](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L104) |
| PUT | `/api/v1/system/backups/schedule` | [update_backup_schedule](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L113) |
| GET | `/api/v1/system/backups/storage` | [read_backup_storage](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L123) |
| PUT | `/api/v1/system/backups/storage` | [update_backup_storage](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L132) |
| POST | `/api/v1/system/backups/manual` | [start_manual_backup](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L143) |
| GET | `/api/v1/system/backups/{backup_id}` | [read_backup](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L155) |
| POST | `/api/v1/system/backups/{backup_id}/cancel` | [cancel_backup](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L165) |
| DELETE | `/api/v1/system/backups/{backup_id}` | [delete_backup](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L175) |
| GET | `/api/v1/system/backups/{backup_id}/download` | [download_backup](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L186) |
| POST | `/api/v1/system/restore-uploads` | [start_restore_upload](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L204) |
| PUT | `/api/v1/system/restore-uploads/{upload_id}/parts/{part}` | [upload_restore_part](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L216) |
| GET | `/api/v1/system/restore-uploads/{upload_id}` | [read_restore_upload](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L259) |
| POST | `/api/v1/system/restore-uploads/{upload_id}/complete` | [complete_restore_upload](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L269) |
| POST | `/api/v1/system/restores/precheck` | [precheck_restore](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L292) |
| POST | `/api/v1/system/restores` | [start_restore](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L353) |
| POST | `/api/v1/system/restores/cancel` | [cancel_restore](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L415) |
| GET | `/api/v1/system/restores/status` | [restore_status](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L437) |
| GET | `/api/v1/system/maintenance-status` | [restore_status](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L437) |
| POST | `/api/v1/system/restores/release-maintenance` | [release_restore_maintenance](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/backup/api.py#L443) |

## backend/app/modules/credit_statistics/api.py

共 9 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/guarantee/credit-statistics` | [read_credit_statistics](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/credit_statistics/api.py#L49) |
| GET | `/api/v1/guarantee/credit-statistics/bank-options` | [read_credit_statistics_bank_options](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/credit_statistics/api.py#L70) |
| GET | `/api/v1/guarantee/credit-statistics/export` | [export_credit_statistics](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/credit_statistics/api.py#L79) |
| GET | `/api/v1/guarantee/credit-statistics/manual-credit-import-template` | [download_manual_credit_import_template](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/credit_statistics/api.py#L113) |
| POST | `/api/v1/guarantee/credit-statistics/manual-credit-import/preview` | [preview_manual_credit_import_file](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/credit_statistics/api.py#L136) |
| POST | `/api/v1/guarantee/credit-statistics/manual-credit-import/confirm` | [confirm_manual_credit_import](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/credit_statistics/api.py#L157) |
| PUT | `/api/v1/guarantee/manual-credit-limits/{canonical_creditor_org_id}` | [put_manual_credit](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/credit_statistics/api.py#L169) |
| PUT | `/api/v1/guarantee/credit-statistics/bank-mappings/{creditor_org_id}` | [put_credit_statistics_bank_mapping](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/credit_statistics/api.py#L187) |
| DELETE | `/api/v1/guarantee/manual-credit-limits/{canonical_creditor_org_id}` | [remove_manual_credit](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/credit_statistics/api.py#L205) |

## backend/app/modules/dataentry/api.py

共 17 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/data-entry/finance/bank-accounts` | [get_bank_accounts](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L78) |
| PUT | `/api/v1/data-entry/finance/bank-accounts` | [save_bank_accounts](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L170) |
| POST | `/api/v1/data-entry/finance/bank-routing-resolutions` | [resolve_bank_routing_number](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L203) |
| POST | `/api/v1/data-entry/finance/bank-accounts/own/company-resolutions` | [resolve_own_company_name](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L215) |
| PUT | `/api/v1/data-entry/finance/bank-accounts/{record_id}` | [update_bank_accounts](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L224) |
| POST | `/api/v1/data-entry/finance/bank-accounts/own/check` | [check_own_company_bank_account_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L264) |
| POST | `/api/v1/data-entry/finance/bank-accounts/own` | [create_own_company_bank_account_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L277) |
| POST | `/api/v1/data-entry/finance/bank-accounts/repayment` | [create_repayment_bank_account_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L294) |
| GET | `/api/v1/data-entry/finance/bank-accounts/{record_id}/repayment-history` | [get_repayment_account_history](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L320) |
| PUT | `/api/v1/data-entry/finance/bank-accounts/{record_id}/repayment-profile` | [update_repayment_profile](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L332) |
| DELETE | `/api/v1/data-entry/finance/bank-accounts/{record_id}/{account_type}` | [delete_bank_account_entry](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L350) |
| GET | `/api/v1/data-entry/finance/invoice` | [get_invoice](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L389) |
| PUT | `/api/v1/data-entry/finance/invoice` | [save_invoice](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L484) |
| PUT | `/api/v1/data-entry/finance/invoice/{invoice_id}` | [update_invoice](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L513) |
| GET | `/api/v1/data-entry/finance/invoice/{invoice_id}` | [get_invoice_detail](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L545) |
| POST | `/api/v1/data-entry/finance/invoice/{invoice_id}/usage-events` | [create_invoice_usage_event](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L560) |
| DELETE | `/api/v1/data-entry/finance/invoice/{invoice_id}` | [delete_invoice](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/api.py#L575) |

## backend/app/modules/dataentry/asset_api.py

共 24 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/data-entry/assets/real-estates` | [get_asset_real_estate_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L77) |
| GET | `/api/v1/data-entry/assets/real-estates/options` | [get_asset_real_estate_options](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L95) |
| GET | `/api/v1/data-entry/assets/real-estates/{real_estate_id}` | [get_asset_real_estate_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L107) |
| POST | `/api/v1/data-entry/assets/real-estates` | [create_asset_real_estate_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L120) |
| PUT | `/api/v1/data-entry/assets/real-estates/{real_estate_id}` | [update_asset_real_estate_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L152) |
| PATCH | `/api/v1/data-entry/assets/real-estates/{real_estate_id}/status` | [update_asset_real_estate_status](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L187) |
| DELETE | `/api/v1/data-entry/assets/real-estates/{real_estate_id}` | [delete_asset_real_estate_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L205) |
| POST | `/api/v1/data-entry/assets/real-estates/{real_estate_id}/attachments` | [upload_asset_real_estate_attachment](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L243) |
| DELETE | `/api/v1/data-entry/assets/real-estates/{real_estate_id}/attachments/{attachment_id}` | [delete_asset_real_estate_attachment_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L275) |
| GET | `/api/v1/data-entry/assets/controlled-companies` | [get_asset_controlled_company_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L290) |
| POST | `/api/v1/data-entry/assets/controlled-companies` | [create_asset_controlled_company_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L312) |
| GET | `/api/v1/data-entry/assets/controlled-companies/{company_id}` | [get_asset_controlled_company_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L350) |
| PUT | `/api/v1/data-entry/assets/controlled-companies/{company_id}` | [update_asset_controlled_company_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L362) |
| PATCH | `/api/v1/data-entry/assets/controlled-companies/{company_id}/status` | [update_asset_controlled_company_status](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L400) |
| DELETE | `/api/v1/data-entry/assets/controlled-companies/{company_id}` | [delete_asset_controlled_company_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L418) |
| GET | `/api/v1/data-entry/assets/enterprise-groups` | [get_enterprise_group_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L452) |
| POST | `/api/v1/data-entry/assets/enterprise-groups` | [create_enterprise_group_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L470) |
| PUT | `/api/v1/data-entry/assets/enterprise-groups/{group_id}` | [update_enterprise_group_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L479) |
| PATCH | `/api/v1/data-entry/assets/enterprise-groups/{group_id}/status` | [update_enterprise_group_status_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L492) |
| DELETE | `/api/v1/data-entry/assets/enterprise-groups/{group_id}` | [delete_enterprise_group_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L505) |
| GET | `/api/v1/data-entry/assets/scope-configurations` | [get_group_scope_configuration_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L523) |
| POST | `/api/v1/data-entry/assets/scope-configurations/change` | [change_group_scope_configuration_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L550) |
| POST | `/api/v1/data-entry/assets/scope-configurations/batch-change` | [batch_change_group_scope_configuration_records](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L562) |
| GET | `/api/v1/data-entry/assets/scope-configurations/{company_id}/history` | [get_group_scope_configuration_history](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/asset_api.py#L574) |

## backend/app/modules/dataentry/engineering_api.py

共 6 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/data-entry/engineering/projects` | [get_engineering_project_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/engineering_api.py#L29) |
| GET | `/api/v1/data-entry/engineering/projects/{project_id}` | [get_engineering_project_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/engineering_api.py#L47) |
| POST | `/api/v1/data-entry/engineering/projects` | [create_engineering_project_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/engineering_api.py#L60) |
| PUT | `/api/v1/data-entry/engineering/projects/{project_id}` | [update_engineering_project_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/engineering_api.py#L69) |
| PATCH | `/api/v1/data-entry/engineering/projects/{project_id}/status` | [update_engineering_project_status](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/engineering_api.py#L79) |
| DELETE | `/api/v1/data-entry/engineering/projects/{project_id}` | [delete_engineering_project_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/engineering_api.py#L94) |

## backend/app/modules/dataentry/table_api.py

共 1 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/data-entry/table-query/{surface_key}` | [query_cross_department_table](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/dataentry/table_api.py#L15) |

## backend/app/modules/debt/api.py

共 38 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/debts/supplementary-fee-names` | [get_supplementary_fee_names](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L152) |
| POST | `/api/v1/debts/supplementary-fee-names` | [create_supplementary_fee_name](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L178) |
| GET | `/api/v1/debts/pending` | [get_pending_debt_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L206) |
| PATCH | `/api/v1/debts/pending/{pending_id}` | [update_pending_debt](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L246) |
| GET | `/api/v1/debts/pending/{pending_id}` | [get_pending_debt_detail](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L280) |
| DELETE | `/api/v1/debts/pending/{pending_id}` | [delete_pending_debt](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L306) |
| POST | `/api/v1/debts/pending/{pending_id}/convert` | [convert_pending_debt](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L342) |
| GET | `/api/v1/debts` | [get_debt_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L449) |
| POST | `/api/v1/debts/query` | [query_debt_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L508) |
| GET | `/api/v1/debts/outstanding-summary` | [get_debt_outstanding_summary](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L517) |
| GET | `/api/v1/debts/fund-statistics` | [get_debt_fund_statistics](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L570) |
| GET | `/api/v1/debts/disbursement-summary` | [get_debt_disbursement_summary](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L632) |
| GET | `/api/v1/debts/comprehensive-cost` | [get_comprehensive_cost_query](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L658) |
| GET | `/api/v1/debts/remaining-fee-allowance` | [get_remaining_fee_allowance](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L743) |
| POST | `/api/v1/debts/floating-rate-calculation` | [calculate_floating_rate_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L765) |
| POST | `/api/v1/debts/automatic-project/resolve` | [resolve_debt_automatic_project](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L779) |
| POST | `/api/v1/debts/automatic-project/cost-approval` | [create_debt_automatic_project_cost_approval](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L798) |
| POST | `/api/v1/debts` | [create_debt_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L813) |
| POST | `/api/v1/debts/contract-attachment-uploads` | [upload_debt_contract_attachments](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L840) |
| GET | `/api/v1/debts/contract-attachments/{attachment_file_id}/source-file` | [download_debt_contract_attachment](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L925) |
| POST | `/api/v1/debts/interest-plan-preview` | [preview_debt_interest_plan](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L947) |
| POST | `/api/v1/debts/bill-maturity-preview` | [preview_bill_maturity_amounts](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L960) |
| POST | `/api/v1/debts/repayment-plan-import` | [import_repayment_plan_workbook](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L973) |
| GET | `/api/v1/debts/repayment-plan-template` | [download_repayment_plan_template](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L992) |
| POST | `/api/v1/debts/repayment-plan-export` | [export_repayment_plan](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L1010) |
| GET | `/api/v1/debts/{debt_id}/irr-calculator-export` | [export_debt_irr_calculator](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L1034) |
| GET | `/api/v1/debts/projects/{project_id}/irr-calculator-export` | [export_project_irr_calculator](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L1100) |
| POST | `/api/v1/debts/repayment-plan-custom-export` | [export_custom_repayment_plan](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L1201) |
| GET | `/api/v1/debts/{debt_id}` | [get_debt_detail](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L1224) |
| GET | `/api/v1/debts/{debt_id}/history` | [get_debt_history](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L1233) |
| GET | `/api/v1/debts/{debt_id}/history/{history_id}` | [get_debt_history_detail](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L1257) |
| PUT | `/api/v1/debts/{debt_id}` | [update_debt_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L1276) |
| POST | `/api/v1/debts/{debt_id}/principal-balance-at-date` | [get_principal_balance_at_date](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L1302) |
| DELETE | `/api/v1/debts/{debt_id}` | [delete_debt_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L1314) |
| GET | `/api/v1/cashflows/upcoming` | [list_upcoming_payments](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L1330) |
| GET | `/api/v1/cashflows/upcoming/summary` | [get_upcoming_payments_summary](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L1339) |
| GET | `/api/v1/cashflows/{event_id}/splits` | [get_cashflow_splits](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L1348) |
| PUT | `/api/v1/cashflows/{event_id}/splits/{creditor_org_id}/status` | [update_cashflow_split_status](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/api.py#L1360) |

## backend/app/modules/debt/project_api.py

共 2 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/projects/{project_id}/debt-overview` | [get_project_debt_overview_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/project_api.py#L22) |
| GET | `/api/v1/projects/{project_id}/tree` | [get_project_tree](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt/project_api.py#L34) |

## backend/app/modules/debt_memo/api.py

共 22 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/debt-memo/project-options/query` | [query_debt_memo_project_options](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L95) |
| GET | `/api/v1/debt-memo/plans` | [get_plan_memo_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L215) |
| POST | `/api/v1/debt-memo/plans/query` | [query_plan_memo_table](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L264) |
| GET | `/api/v1/debt-memo/plans/{plan_id}` | [get_plan_memo_detail](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L315) |
| POST | `/api/v1/debt-memo/plans` | [add_plan_memo](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L329) |
| PUT | `/api/v1/debt-memo/plans/{plan_id}` | [edit_plan_memo](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L360) |
| DELETE | `/api/v1/debt-memo/plans/{plan_id}` | [remove_plan_memo](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L370) |
| POST | `/api/v1/debt-memo/plans/{plan_id}/convert-to-actual` | [convert_plan_memo_to_actual](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L382) |
| GET | `/api/v1/debt-memo/actuals` | [get_actual_memo_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L399) |
| POST | `/api/v1/debt-memo/actuals/query` | [query_actual_memo_table](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L448) |
| GET | `/api/v1/debt-memo/actuals/{actual_id}` | [get_actual_memo_detail](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L500) |
| POST | `/api/v1/debt-memo/actuals` | [add_actual_memo](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L513) |
| PUT | `/api/v1/debt-memo/actuals/{actual_id}` | [edit_actual_memo](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L545) |
| DELETE | `/api/v1/debt-memo/actuals/{actual_id}` | [remove_actual_memo](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L556) |
| POST | `/api/v1/debt-memo/actuals/{actual_id}/link-debt` | [link_actual_memo_to_debt](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L565) |
| GET | `/api/v1/debt-memo/actuals/{actual_id}/debt-prefill` | [get_actual_memo_debt_prefill](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L577) |
| GET | `/api/v1/debt-memo/overview-disbursement-summary` | [get_overview_disbursement_summary_api](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L592) |
| GET | `/api/v1/debt-memo/overview-daily-disbursements` | [get_overview_daily_disbursements_api](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L620) |
| GET | `/api/v1/debt-memo/overview-daily-disbursements/{kind}` | [get_overview_daily_disbursement_list_api](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L649) |
| GET | `/api/v1/debt-memo/summary` | [get_debt_memo_summary](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L681) |
| GET | `/api/v1/debt-memo/dashboard` | [get_debt_memo_dashboard](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L690) |
| GET | `/api/v1/debt-memo/activity` | [get_debt_memo_activity](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/debt_memo/api.py#L699) |

## backend/app/modules/documents/api.py

共 35 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/documents/repayment-notices/resolve` | [resolve_notice](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L108) |
| POST | `/api/v1/documents/repayment-payments` | [create_payment_document](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L121) |
| GET | `/api/v1/documents/repayment-payments` | [list_payment_documents](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L137) |
| GET | `/api/v1/documents/repayment-payments/counts` | [get_payment_document_counts](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L166) |
| GET | `/api/v1/documents/repayment-payments/workflow-counts` | [get_payment_document_workflow_counts](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L180) |
| GET | `/api/v1/documents/repayment-payments/auto-generation/status` | [get_payment_document_auto_generation_status](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L191) |
| POST | `/api/v1/documents/repayment-payments/auto-generation/run-now` | [run_payment_document_auto_generation_now](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L202) |
| GET | `/api/v1/documents/repayment-payments/duplicate-check` | [check_payment_document_duplicate](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L213) |
| POST | `/api/v1/documents/repayment-payments/{document_id}/print` | [record_payment_document_print](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L237) |
| GET | `/api/v1/documents/repayment-payments/{document_id}/excel` | [download_payment_document_excel](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L252) |
| POST | `/api/v1/documents/repayment-payments/{document_id}/receipt-verifications` | [verify_payment_document_receipts](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L284) |
| GET | `/api/v1/documents/repayment-payments/{document_id}` | [get_payment_document](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L322) |
| POST | `/api/v1/documents/repayment-payments/{document_id}/void` | [void_payment_document](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L338) |
| DELETE | `/api/v1/documents/repayment-payments/{document_id}` | [delete_payment_document](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L356) |
| POST | `/api/v1/documents/fee-applications/from-debts` | [create_fee_applications_from_debts](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L378) |
| GET | `/api/v1/documents/fee-applications` | [list_fee_applications](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L394) |
| GET | `/api/v1/documents/fee-applications/counts` | [get_fee_application_counts](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L416) |
| GET | `/api/v1/documents/fee-applications/{document_id}` | [get_fee_application](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L427) |
| PUT | `/api/v1/documents/fee-applications/{document_id}` | [update_fee_application](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L443) |
| GET | `/api/v1/documents/fee-applications/{document_id}/excel` | [download_fee_application_excel](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L458) |
| POST | `/api/v1/documents/fee-applications/{document_id}/void` | [void_fee_application](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L488) |
| GET | `/api/v1/documents/cost-approvals/project-options` | [get_cost_approval_project_options](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L511) |
| GET | `/api/v1/documents/cost-approvals/defaults` | [get_cost_approval_defaults_endpoint](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L531) |
| POST | `/api/v1/documents/cost-approvals` | [create_cost_approval](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L550) |
| GET | `/api/v1/documents/cost-approvals` | [list_cost_approvals](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L566) |
| GET | `/api/v1/documents/cost-approvals/counts` | [get_cost_approval_counts](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L588) |
| GET | `/api/v1/documents/cost-approvals/{document_id}` | [get_cost_approval](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L602) |
| GET | `/api/v1/documents/cost-approvals/{document_id}/excel` | [download_cost_approval_excel](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L615) |
| POST | `/api/v1/documents/cost-approvals/{document_id}/regenerate` | [regenerate_cost_approval](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L646) |
| GET | `/api/v1/documents/cost-approvals/{document_id}/history` | [get_cost_approval_history](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L664) |
| GET | `/api/v1/documents/cost-approvals/{document_id}/history/{history_id}` | [get_cost_approval_history_detail](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L684) |
| GET | `/api/v1/documents/cost-approvals/{document_id}/versions/{version_no}` | [get_cost_approval_version](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L702) |
| PUT | `/api/v1/documents/cost-approvals/{document_id}` | [update_cost_approval](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L720) |
| POST | `/api/v1/documents/cost-approvals/{document_id}/void` | [void_cost_approval](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L738) |
| DELETE | `/api/v1/documents/cost-approvals/{document_id}` | [delete_cost_approval](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/documents/api.py#L756) |

## backend/app/modules/export/api.py

共 13 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/exports/debts` | [export_debts](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/export/api.py#L48) |
| POST | `/api/v1/exports/cashflows` | [export_cashflows](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/export/api.py#L70) |
| POST | `/api/v1/exports/projects/summary` | [export_project_summary](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/export/api.py#L92) |
| POST | `/api/v1/exports/projects/ledger` | [export_project_ledger](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/export/api.py#L110) |
| POST | `/api/v1/exports/projects/cost-process` | [export_project_cost_process](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/export/api.py#L141) |
| POST | `/api/v1/exports/jobs/debts` | [create_debt_export_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/export/api.py#L175) |
| POST | `/api/v1/exports/jobs/cashflows` | [create_cashflow_export_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/export/api.py#L195) |
| POST | `/api/v1/exports/jobs/projects/summary` | [create_project_summary_export_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/export/api.py#L215) |
| POST | `/api/v1/exports/jobs/projects/ledger` | [create_project_ledger_export_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/export/api.py#L235) |
| POST | `/api/v1/exports/jobs/projects/cost-process` | [create_project_cost_process_export_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/export/api.py#L255) |
| GET | `/api/v1/exports/jobs/{job_id}` | [get_export_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/export/api.py#L271) |
| DELETE | `/api/v1/exports/jobs/{job_id}` | [delete_export_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/export/api.py#L280) |
| GET | `/api/v1/exports/jobs/{job_id}/download` | [download_export_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/export/api.py#L289) |

## backend/app/modules/file_upload/api.py

共 3 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/file-uploads/approval-documents` | [upload_approval_document](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/file_upload/api.py#L23) |
| POST | `/api/v1/file-uploads/repayment-subject-attachments` | [upload_repayment_subject_attachment](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/file_upload/api.py#L40) |
| GET | `/api/v1/file-uploads/{category}/{filename}` | [download_uploaded_file](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/file_upload/api.py#L57) |

## backend/app/modules/guarantee/api.py

共 11 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/guarantee/navigation` | [get_guarantee_navigation](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/guarantee/api.py#L59) |
| GET | `/api/v1/guarantee/records` | [list_guarantee_records](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/guarantee/api.py#L103) |
| GET | `/api/v1/guarantee/statistics` | [get_statistics](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/guarantee/api.py#L129) |
| GET | `/api/v1/guarantee/detail-statistics` | [get_detail_statistics](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/guarantee/api.py#L137) |
| GET | `/api/v1/guarantee/records/{record_id}` | [get_guarantee_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/guarantee/api.py#L160) |
| POST | `/api/v1/guarantee/records` | [create_guarantee_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/guarantee/api.py#L169) |
| PUT | `/api/v1/guarantee/records/{record_id}` | [update_guarantee_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/guarantee/api.py#L183) |
| PATCH | `/api/v1/guarantee/records/{record_id}/balance` | [patch_guarantee_balance](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/guarantee/api.py#L204) |
| PATCH | `/api/v1/guarantee/records/{record_id}/status` | [patch_guarantee_status](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/guarantee/api.py#L221) |
| DELETE | `/api/v1/guarantee/records/{record_id}` | [delete_guarantee_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/guarantee/api.py#L238) |
| GET | `/api/v1/guarantee/attachments/{attachment_id}/source-file` | [download_guarantee_attachment](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/guarantee/api.py#L254) |

## backend/app/modules/guarantee/ocr_prefill_api.py

共 1 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/ocr/guarantee-prefill` | [prefill_guarantee_application](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/guarantee/ocr_prefill_api.py#L28) |

## backend/app/modules/master_data/api.py

共 32 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/master-data/debtors` | [get_debtor_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L85) |
| GET | `/api/v1/master-data/debtors/tree` | [get_debtor_tree](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L95) |
| GET | `/api/v1/master-data/debtors/search` | [search_debtor_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L104) |
| POST | `/api/v1/master-data/debtors` | [create_debtor_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L122) |
| PUT | `/api/v1/master-data/debtors/{debtor_id}` | [update_debtor_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L131) |
| GET | `/api/v1/master-data/creditor-orgs` | [get_creditor_org_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L141) |
| GET | `/api/v1/master-data/creditor-orgs/search` | [search_creditor_org_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L159) |
| POST | `/api/v1/master-data/creditor-orgs/table-query` | [query_creditor_org_table_page](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L184) |
| POST | `/api/v1/master-data/creditor-orgs/resolve` | [resolve_creditor_org_names](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L193) |
| GET | `/api/v1/master-data/creditor-orgs/{org_id}` | [get_creditor_org_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L204) |
| POST | `/api/v1/master-data/creditor-orgs` | [create_creditor_org_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L213) |
| PUT | `/api/v1/master-data/creditor-orgs/{org_id}` | [update_creditor_org_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L222) |
| GET | `/api/v1/master-data/creditor-org-seed-config` | [get_creditor_org_seed_config_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L232) |
| PUT | `/api/v1/master-data/creditor-org-seed-config` | [update_creditor_org_seed_config_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L240) |
| GET | `/api/v1/master-data/guarantee-companies` | [get_guarantee_company_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L249) |
| GET | `/api/v1/master-data/guarantee-companies/{company_id}` | [get_guarantee_company_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L267) |
| POST | `/api/v1/master-data/guarantee-companies` | [create_guarantee_company_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L276) |
| PUT | `/api/v1/master-data/guarantee-companies/{company_id}` | [update_guarantee_company_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L285) |
| GET | `/api/v1/master-data/financing-products/standard` | [get_standard_financing_product_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L295) |
| POST | `/api/v1/master-data/financing-products/standard` | [create_standard_financing_product_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L309) |
| PUT | `/api/v1/master-data/financing-products/standard/{product_id}` | [update_standard_financing_product_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L318) |
| GET | `/api/v1/master-data/financing-products/custom` | [get_custom_financing_product_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L328) |
| POST | `/api/v1/master-data/financing-products/custom` | [create_custom_financing_product_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L342) |
| PUT | `/api/v1/master-data/financing-products/custom/{product_id}` | [update_custom_financing_product_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L351) |
| GET | `/api/v1/master-data/real-estates` | [get_real_estate_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L361) |
| GET | `/api/v1/master-data/real-estates/{real_estate_id}` | [get_real_estate_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L371) |
| POST | `/api/v1/master-data/real-estates` | [create_real_estate_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L380) |
| PUT | `/api/v1/master-data/real-estates/{real_estate_id}` | [update_real_estate_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L389) |
| DELETE | `/api/v1/master-data/real-estates/{real_estate_id}` | [delete_real_estate_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L399) |
| GET | `/api/v1/master-data/financing-types` | [get_financing_type_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L408) |
| POST | `/api/v1/master-data/financing-types` | [create_financing_type_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L418) |
| PUT | `/api/v1/master-data/financing-types/{type_id}` | [update_financing_type_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/master_data/api.py#L427) |

## backend/app/modules/ocr/api/automation.py

共 4 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/ocr/automation-jobs` | [start_automation_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/automation.py#L62) |
| POST | `/api/v1/ocr/documents/{document_id}/reprocess-jobs` | [start_document_reprocess_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/automation.py#L166) |
| GET | `/api/v1/ocr/automation-jobs/{job_id}` | [get_automation_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/automation.py#L218) |
| DELETE | `/api/v1/ocr/automation-jobs/{job_id}` | [cancel_automation_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/automation.py#L243) |

## backend/app/modules/ocr/api/catalog.py

共 6 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/ocr/extraction-config` | [get_contract_extraction_config](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/catalog.py#L35) |
| GET | `/api/v1/ocr/extraction-config/export` | [export_contract_extraction_config](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/catalog.py#L48) |
| GET | `/api/v1/ocr/extraction-config/initial-seed` | [get_contract_extraction_initial_seed](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/catalog.py#L61) |
| PUT | `/api/v1/ocr/extraction-config` | [update_contract_extraction_config](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/catalog.py#L73) |
| POST | `/api/v1/ocr/extraction-config/import` | [import_contract_extraction_config](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/catalog.py#L87) |
| POST | `/api/v1/ocr/extraction-config/import-initial-seed` | [import_contract_extraction_initial_seed](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/catalog.py#L101) |

## backend/app/modules/ocr/api/debt_init.py

共 16 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/ocr/debt-init/template` | [download_debt_init_template](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L69) |
| POST | `/api/v1/ocr/debt-init/preview` | [preview_debt_init](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L91) |
| POST | `/api/v1/ocr/debt-init/records` | [create_debt_init_records](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L109) |
| POST | `/api/v1/ocr/debt-init/records/upload` | [create_debt_init_records_with_source](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L120) |
| GET | `/api/v1/ocr/debt-init/batches` | [read_debt_init_batches](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L149) |
| POST | `/api/v1/ocr/debt-init/batches/clear` | [clear_debt_init_batches](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L170) |
| GET | `/api/v1/ocr/debt-init/batches/{batch_id}/source` | [download_debt_init_batch_source](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L189) |
| GET | `/api/v1/ocr/debt-init/records` | [read_debt_init_records](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L210) |
| POST | `/api/v1/ocr/debt-init/records/clear` | [clear_debt_init_records](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L231) |
| PATCH | `/api/v1/ocr/debt-init/records/{record_id}` | [patch_debt_init_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L244) |
| POST | `/api/v1/ocr/debt-init/records/{record_id}/transfer` | [transfer_debt_init_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L256) |
| POST | `/api/v1/ocr/debt-init/records/transfer-pending` | [transfer_debt_init_records_to_pending](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L267) |
| POST | `/api/v1/ocr/debt-init/records/transfer-jobs` | [create_debt_init_transfer_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L282) |
| GET | `/api/v1/ocr/debt-init/records/transfer-jobs/active` | [read_active_debt_init_transfer_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L296) |
| GET | `/api/v1/ocr/debt-init/records/transfer-jobs/{job_id}` | [read_debt_init_transfer_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L306) |
| DELETE | `/api/v1/ocr/debt-init/records/transfer-jobs/{job_id}` | [cancel_debt_init_transfer_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/debt_init.py#L317) |

## backend/app/modules/ocr/api/docs_sortout.py

共 4 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/ocr/document-sortout-jobs` | [start_document_sortout_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/docs_sortout.py#L58) |
| GET | `/api/v1/ocr/document-sortout-jobs/{job_id}` | [get_document_sortout_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/docs_sortout.py#L114) |
| GET | `/api/v1/ocr/document-sortout-jobs/{job_id}/documents/{document_id}/diagnostics` | [get_document_sortout_debug_info](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/docs_sortout.py#L154) |
| DELETE | `/api/v1/ocr/document-sortout-jobs/{job_id}` | [cancel_document_sortout_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/docs_sortout.py#L180) |

## backend/app/modules/ocr/api/documents.py

共 25 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/ocr/documents` | [get_ocr_documents](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L109) |
| GET | `/api/v1/ocr/documents/base-materials/three-year-one-period` | [get_three_year_one_period_materials](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L120) |
| GET | `/api/v1/ocr/documents/base-materials` | [get_company_base_materials](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L149) |
| GET | `/api/v1/ocr/documents/base-material-candidates` | [get_base_material_candidates](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L180) |
| POST | `/api/v1/ocr/documents/base-materials/association-preview` | [preview_base_material_associations](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L254) |
| POST | `/api/v1/ocr/documents/base-materials/bind-batch` | [bind_base_material_groups](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L274) |
| POST | `/api/v1/ocr/documents/base-materials/bind` | [bind_base_materials](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L299) |
| POST | `/api/v1/ocr/documents/base-materials/unbind` | [unbind_base_materials](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L360) |
| POST | `/api/v1/ocr/documents/word-upload` | [upload_word_document_api](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L385) |
| POST | `/api/v1/ocr/documents/spreadsheet-upload` | [upload_spreadsheet_document_api](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L434) |
| GET | `/api/v1/ocr/documents/{document_id}` | [get_ocr_document](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L505) |
| DELETE | `/api/v1/ocr/documents/{document_id}` | [delete_ocr_document_api](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L525) |
| POST | `/api/v1/ocr/documents/{document_id}/replace-with/{replacement_document_id}` | [replace_ocr_document_api](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L539) |
| PUT | `/api/v1/ocr/documents/{document_id}` | [update_ocr_document](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L561) |
| GET | `/api/v1/ocr/documents/{document_id}/analyses` | [get_ocr_document_analyses](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L584) |
| POST | `/api/v1/ocr/documents/{document_id}/analyses` | [create_ocr_document_analysis](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L599) |
| GET | `/api/v1/ocr/documents/{document_id}/analyses/{analysis_id}` | [get_ocr_document_analysis](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L622) |
| GET | `/api/v1/ocr/documents/{document_id}/analyses/{analysis_id}/debug` | [get_ocr_document_analysis_debug](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L642) |
| DELETE | `/api/v1/ocr/documents/{document_id}/analyses/{analysis_id}` | [cancel_ocr_document_analysis](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L661) |
| PATCH | `/api/v1/ocr/documents/{document_id}/analyses/{analysis_id}/fields/{field_id}` | [patch_ocr_extracted_field](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L685) |
| PATCH | `/api/v1/ocr/documents/{document_id}/analyses/{analysis_id}/invoice-lines/{line_item_id}` | [patch_ocr_invoice_line_item](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L705) |
| GET | `/api/v1/ocr/documents/{document_id}/pages/{page_no}/image` | [get_ocr_document_page_image](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L711) |
| GET | `/api/v1/ocr/documents/{document_id}/source-file` | [get_ocr_document_source_file](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L729) |
| POST | `/api/v1/ocr/documents/review-job` | [save_ocr_review_job_document_api](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L754) |
| POST | `/api/v1/ocr/documents` | [save_ocr_document_api](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/documents.py#L808) |

## backend/app/modules/ocr/api/health.py

共 3 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/ocr/capabilities` | [get_ocr_capabilities](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/health.py#L43) |
| GET | `/api/v1/ocr/service-ready` | [get_ocr_service_ready](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/health.py#L56) |
| GET | `/api/v1/ocr/health` | [ocr_health_check](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/health.py#L104) |

## backend/app/modules/ocr/api/material_list.py

共 2 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/ocr/material-list/analyze` | [analyze_material_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/material_list.py#L64) |
| POST | `/api/v1/ocr/material-list/package` | [package_materials](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/material_list.py#L121) |

## backend/app/modules/ocr/api/modules.py

共 10 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/ocr/modules/root-debt-import-preview` | [post_ocr_root_debt_preview](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/modules.py#L45) |
| GET | `/api/v1/ocr/modules` | [get_ocr_modules](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/modules.py#L64) |
| GET | `/api/v1/ocr/modules/owners` | [get_ocr_history_owners](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/modules.py#L88) |
| GET | `/api/v1/ocr/modules/history-search` | [search_ocr_history](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/modules.py#L101) |
| GET | `/api/v1/ocr/modules/{module_id}` | [get_ocr_module](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/modules.py#L125) |
| GET | `/api/v1/ocr/modules/{module_id}/debt-import-preview` | [get_ocr_module_debt_preview](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/modules.py#L143) |
| PATCH | `/api/v1/ocr/modules/{module_id}` | [rename_ocr_module_api](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/modules.py#L158) |
| PATCH | `/api/v1/ocr/modules/{module_id}/parent` | [move_ocr_module_api](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/modules.py#L170) |
| PATCH | `/api/v1/ocr/documents/{document_id}/module` | [move_ocr_document_api](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/modules.py#L187) |
| DELETE | `/api/v1/ocr/modules/{module_id}` | [delete_ocr_module_api](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/modules.py#L211) |

## backend/app/modules/ocr/api/review.py

共 1 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/ocr/performance-metrics` | [get_ocr_performance_metrics](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review.py#L61) |

## backend/app/modules/ocr/api/review_jobs.py

共 3 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/ocr/review-jobs` | [start_ocr_review_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_jobs.py#L63) |
| POST | `/api/v1/ocr/review-jobs/image-bundles` | [start_ocr_image_bundle_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_jobs.py#L154) |
| POST | `/api/v1/ocr/review-jobs/{job_id}/start` | [start_staged_ocr_review_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_jobs.py#L204) |

## backend/app/modules/ocr/api/review_resources.py

共 10 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| DELETE | `/api/v1/ocr/review-jobs/{job_id}` | [cancel_ocr_review_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_resources.py#L48) |
| DELETE | `/api/v1/ocr/review-jobs/{job_id}/staged-resource` | [release_staged_ocr_review_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_resources.py#L91) |
| GET | `/api/v1/ocr/review-jobs/{job_id}/source-file` | [get_ocr_review_job_source_file](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_resources.py#L112) |
| GET | `/api/v1/ocr/review-jobs/{job_id}/source-pages/{page_index}` | [get_ocr_review_job_source_page](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_resources.py#L164) |
| GET | `/api/v1/ocr/review-jobs/{job_id}/direct-preview` | [get_ocr_review_job_direct_preview](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_resources.py#L217) |
| GET | `/api/v1/ocr/review-jobs/{job_id}/source-images/{image_index}` | [get_ocr_review_job_source_image](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_resources.py#L264) |
| GET | `/api/v1/ocr/review-jobs/{job_id}` | [get_ocr_review_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_resources.py#L339) |
| GET | `/api/v1/ocr/review-jobs/{job_id}/pages/{page_no}` | [get_ocr_review_job_page](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_resources.py#L386) |
| GET | `/api/v1/ocr/review-jobs/{job_id}/pages/{page_no}/image` | [get_ocr_review_job_page_image](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_resources.py#L430) |
| GET | `/api/v1/ocr/review-jobs/{job_id}/diagnostics` | [get_ocr_review_job_debug_info](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_resources.py#L461) |

## backend/app/modules/ocr/api/review_sync.py

共 2 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/ocr/process` | [process_ocr_file](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_sync.py#L30) |
| POST | `/api/v1/ocr/review-process` | [process_ocr_vlm_file](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/review_sync.py#L60) |

## backend/app/modules/ocr/api/sheet_fill.py

共 17 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/ocr/sheet-fill/jobs` | [start_sheet_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L63) |
| GET | `/api/v1/ocr/sheet-fill/jobs` | [read_sheet_jobs](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L87) |
| GET | `/api/v1/ocr/sheet-fill/fields` | [read_sheet_fields](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L118) |
| GET | `/api/v1/ocr/sheet-fill/fields/export` | [export_sheet_fields](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L129) |
| POST | `/api/v1/ocr/sheet-fill/fields/import` | [import_sheet_fields](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L144) |
| PATCH | `/api/v1/ocr/sheet-fill/fields/{field_key:path}` | [patch_sheet_field](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L164) |
| GET | `/api/v1/ocr/sheet-fill/jobs/{job_id}` | [read_sheet_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L184) |
| GET | `/api/v1/ocr/sheet-fill/active-job` | [read_active_sheet_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L195) |
| GET | `/api/v1/ocr/sheet-fill/jobs/{job_id}/mappings` | [read_sheet_mappings](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L205) |
| POST | `/api/v1/ocr/sheet-fill/jobs/{job_id}/mappings/confirm` | [confirm_sheet_mappings](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L216) |
| POST | `/api/v1/ocr/sheet-fill/jobs/{job_id}/mappings/analyze` | [analyze_sheet_map](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L232) |
| GET | `/api/v1/ocr/sheet-fill/jobs/{job_id}/preview` | [read_sheet_preview](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L244) |
| POST | `/api/v1/ocr/sheet-fill/jobs/{job_id}/preview/confirm` | [confirm_sheet_preview](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L255) |
| POST | `/api/v1/ocr/sheet-fill/jobs/{job_id}/retry` | [retry_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L271) |
| POST | `/api/v1/ocr/sheet-fill/jobs/{job_id}/rerun` | [rerun_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L285) |
| GET | `/api/v1/ocr/sheet-fill/jobs/{job_id}/file` | [download_sheet_file](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L299) |
| GET | `/api/v1/ocr/sheet-fill/jobs/{job_id}/debug` | [download_sheet_debug](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_fill.py#L334) |

## backend/app/modules/ocr/api/sheet_rules.py

共 13 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/ocr/sheet-fill/rule-operators` | [read_rule_operators](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_rules.py#L47) |
| GET | `/api/v1/ocr/sheet-fill/org-relations` | [read_org_relations](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_rules.py#L105) |
| POST | `/api/v1/ocr/sheet-fill/org-relations` | [create_org_relation](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_rules.py#L116) |
| PATCH | `/api/v1/ocr/sheet-fill/org-relations/{relation_id}` | [patch_org_relation](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_rules.py#L135) |
| GET | `/api/v1/ocr/sheet-fill/rules` | [read_sheet_rules](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_rules.py#L161) |
| POST | `/api/v1/ocr/sheet-fill/rules` | [create_sheet_rule](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_rules.py#L180) |
| POST | `/api/v1/ocr/sheet-fill/rules/{rule_key}/versions` | [clone_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_rules.py#L194) |
| PATCH | `/api/v1/ocr/sheet-fill/rule-versions/{version_id}` | [patch_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_rules.py#L208) |
| POST | `/api/v1/ocr/sheet-fill/rule-versions/{version_id}/validate` | [validate_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_rules.py#L224) |
| POST | `/api/v1/ocr/sheet-fill/rule-versions/{version_id}/preview` | [preview_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_rules.py#L238) |
| POST | `/api/v1/ocr/sheet-fill/rule-versions/{version_id}/publish` | [publish_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_rules.py#L253) |
| GET | `/api/v1/ocr/sheet-fill/rules/export` | [export_sheet_rules](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_rules.py#L267) |
| POST | `/api/v1/ocr/sheet-fill/rules/import` | [import_sheet_rules](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/ocr/api/sheet_rules.py#L281) |

## backend/app/modules/overview/api.py

共 5 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/overview/funding-gap-config` | [read_funding_gap_config](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/overview/api.py#L41) |
| PUT | `/api/v1/overview/funding-gap-config` | [save_funding_gap_config](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/overview/api.py#L74) |
| DELETE | `/api/v1/overview/funding-gap-config/{config_id}` | [clear_funding_gap_config](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/overview/api.py#L86) |
| GET | `/api/v1/overview/planned-financing-config` | [read_planned_financing_config](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/overview/api.py#L105) |
| PUT | `/api/v1/overview/planned-financing-config` | [save_planned_financing_config](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/overview/api.py#L136) |

## backend/app/modules/project/api.py

共 29 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/projects/import-template` | [get_project_import_template](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L206) |
| POST | `/api/v1/projects/import` | [import_project_records](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L228) |
| GET | `/api/v1/projects` | [get_project_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L319) |
| POST | `/api/v1/projects/query` | [query_project_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L368) |
| POST | `/api/v1/projects/query-selection` | [query_project_selection_ids](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L377) |
| POST | `/api/v1/projects` | [create_project_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L418) |
| GET | `/api/v1/projects/code-preview` | [get_project_code_preview](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L446) |
| GET | `/api/v1/projects/options` | [get_project_options](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L455) |
| GET | `/api/v1/projects/financing-pipeline/summary` | [get_project_financing_pipeline_summary](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L474) |
| GET | `/api/v1/projects/financing-pipeline/details` | [get_project_financing_pipeline_details](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L495) |
| GET | `/api/v1/projects/engagements` | [get_project_engagement_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L519) |
| POST | `/api/v1/projects/engagements` | [create_project_engagement_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L543) |
| GET | `/api/v1/projects/engagements/{engagement_id}` | [get_project_engagement_detail](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L552) |
| PUT | `/api/v1/projects/engagements/{engagement_id}` | [update_project_engagement_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L561) |
| POST | `/api/v1/projects/engagements/{engagement_id}/status` | [change_project_engagement_status_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L574) |
| POST | `/api/v1/projects/engagements/{engagement_id}/convert` | [convert_project_engagement_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L588) |
| GET | `/api/v1/projects/{project_id}` | [get_project_detail](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L598) |
| PUT | `/api/v1/projects/{project_id}` | [update_project_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L607) |
| PUT | `/api/v1/projects/{project_id}/refinance` | [update_project_refinance_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L619) |
| POST | `/api/v1/projects/{project_id}/engagement-status` | [update_project_engagement_status_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L631) |
| POST | `/api/v1/projects/{project_id}/complete-funding` | [complete_project_funding_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L643) |
| POST | `/api/v1/projects/{project_id}/abandon` | [abandon_project_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L658) |
| POST | `/api/v1/projects/{project_id}/restore-abandonment` | [restore_abandoned_project_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L673) |
| DELETE | `/api/v1/projects/{project_id}` | [delete_project_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L688) |
| POST | `/api/v1/projects/{project_id}/restore` | [restore_project_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L704) |
| GET | `/api/v1/projects/{project_id}/history` | [get_project_history](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L722) |
| GET | `/api/v1/projects/{project_id}/history/{history_id}` | [get_project_history_detail](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L746) |
| GET | `/api/v1/projects/{project_id}/creditors` | [get_project_creditors](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L765) |
| PUT | `/api/v1/projects/{project_id}/creditors` | [replace_project_creditor_records](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/project/api.py#L774) |

## backend/app/modules/query_center/api.py

共 20 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/query-center/reports/composed/preview` | [preview_composed_query_report](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L82) |
| POST | `/api/v1/query-center/reports/composed/intelligence-jobs` | [create_composed_query_report_intelligence_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L95) |
| GET | `/api/v1/query-center/reports/composed/intelligence-jobs/{job_id}` | [get_composed_query_report_intelligence_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L111) |
| POST | `/api/v1/query-center/reports/composed/intelligence-jobs/{job_id}/retry` | [retry_composed_query_report_intelligence_job](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L128) |
| POST | `/api/v1/query-center/reports/composed/intelligence` | [draft_composed_query_report_intelligence](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L144) |
| POST | `/api/v1/query-center/reports/composed/export` | [export_composed_query_report](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L162) |
| POST | `/api/v1/query-center/reports/financing-work/preview` | [preview_financing_work_report](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L220) |
| POST | `/api/v1/query-center/reports/financing-work/export` | [export_financing_work_report](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L229) |
| POST | `/api/v1/query-center/interpret` | [interpret_query_center_prompt](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L274) |
| POST | `/api/v1/query-center/export` | [export_query_center_result](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L297) |
| GET | `/api/v1/query-center/financing-structure` | [read_financing_structure](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L333) |
| GET | `/api/v1/query-center/financing-total` | [read_financing_total](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L353) |
| GET | `/api/v1/query-center/planned-financing-trial` | [read_planned_financing_trial](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L383) |
| GET | `/api/v1/query-center/debt-structure` | [read_debt_structure](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L417) |
| GET | `/api/v1/query-center/credit-statistics` | [read_enterprise_credit_statistics](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L437) |
| GET | `/api/v1/query-center/group-scope` | [read_group_scope](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L457) |
| GET | `/api/v1/query-center/guarantees/month-end` | [read_guarantees_at_month_end](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L471) |
| GET | `/api/v1/query-center/debt-monthly` | [read_debt_monthly](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L491) |
| GET | `/api/v1/query-center/group-overview` | [read_group_overview](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L523) |
| GET | `/api/v1/query-center/debt-refinance` | [read_debt_refinance](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_center/api.py#L557) |

## backend/app/modules/query_language/api.py

共 1 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| POST | `/api/v1/query/interpret` | [interpret_natural_language_query](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/query_language/api.py#L22) |

## backend/app/modules/statistics/api.py

共 5 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/statistics/pending-items` | [read_pending_items](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/statistics/api.py#L50) |
| GET | `/api/v1/statistics/calendar` | [read_calendar_summary](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/statistics/api.py#L58) |
| GET | `/api/v1/statistics/calendar/{target_date}/items` | [read_calendar_items](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/statistics/api.py#L91) |
| GET | `/api/v1/statistics/cashflows` | [read_cashflow_list](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/statistics/api.py#L122) |
| GET | `/api/v1/statistics/projects/summary` | [read_project_cashflow_summary](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/statistics/api.py#L163) |

## backend/app/modules/sync/api.py

共 2 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/sync/changes` | [get_sync_changes](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/sync/api.py#L17) |
| GET | `/api/v1/sync/snapshot` | [get_sync_snapshot](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/sync/api.py#L38) |

## backend/app/modules/system/api.py

共 18 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/meta/options` | [read_meta_options](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L66) |
| GET | `/api/v1/system/parameters` | [read_system_parameters](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L74) |
| PUT | `/api/v1/system/parameters/{param_key}` | [update_system_parameter_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L84) |
| POST | `/api/v1/system/dev/drop-all-tables` | [drop_all_tables_for_development](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L94) |
| GET | `/api/v1/system/dev/business-test-data` | [read_business_test_data](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L112) |
| POST | `/api/v1/system/dev/business-test-data/import` | [import_business_test_data_for_development](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L130) |
| GET | `/api/v1/system/holidays` | [read_holiday_calendar](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L145) |
| GET | `/api/v1/system/holidays/official-preview` | [read_official_holiday_preview](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L157) |
| POST | `/api/v1/system/holidays/import-official` | [import_official_holiday_schedule](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L169) |
| GET | `/api/v1/system/holiday-schedules/{year}` | [read_holiday_schedule](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L181) |
| PUT | `/api/v1/system/holiday-schedules/{year}` | [save_holiday_schedule](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L195) |
| POST | `/api/v1/system/holiday-schedules/{year}/publish` | [publish_holiday_schedule_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L215) |
| PUT | `/api/v1/system/holidays/{holiday_date}` | [upsert_holiday_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L230) |
| DELETE | `/api/v1/system/holidays/{holiday_date}` | [delete_holiday_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L240) |
| GET | `/api/v1/system/lpr-rates` | [read_lpr_rates](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L249) |
| PUT | `/api/v1/system/lpr-rates` | [upsert_lpr_rate_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L258) |
| POST | `/api/v1/system/lpr-rates/import-official` | [import_official_lpr_rates](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L267) |
| DELETE | `/api/v1/system/lpr-rates/{rate_id}` | [delete_lpr_rate_record](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/system/api.py#L275) |

## backend/app/modules/table_views/api.py

共 7 项。

| 方法 | 完整路径 | 处理函数及代码依据 |
| --- | --- | --- |
| GET | `/api/v1/table-page-size-preferences/{surface_key}` | [get_table_page_size_preference](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/table_views/api.py#L39) |
| PUT | `/api/v1/table-page-size-preferences/{surface_key}` | [put_table_page_size_preference](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/table_views/api.py#L62) |
| GET | `/api/v1/table-views` | [get_table_views](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/table_views/api.py#L97) |
| PUT | `/api/v1/table-preferences/{surface_key}` | [put_table_preference](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/table_views/api.py#L123) |
| POST | `/api/v1/table-views` | [post_table_view](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/table_views/api.py#L148) |
| PATCH | `/api/v1/table-views/{view_id}` | [patch_table_view](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/table_views/api.py#L164) |
| DELETE | `/api/v1/table-views/{view_id}` | [remove_table_view](https://github.com/tongdeyu/citybond/blob/56437f747980389f06d0574a2e5f25460280f3ef/backend/app/modules/table_views/api.py#L182) |

