# develop 后端静态路由清单（移动端覆盖核对附录）

代码基线：本地 `origin/develop`，提交 `a7da695f46a542ebb5a3bfcab87f4238df89f2fa`。日期：2026-09-11。

共提取 **45 个 Python 文件中的 510 条 HTTP 路由装饰器声明**。该数字不是业务功能数，也不是运行时实际挂载接口数。

本清单通过读取固定 Git 快照和 Python AST 生成，没有进入 FastAPI lifespan、连接数据库或调用接口。直接路由路径只拼接同文件中可静态识别的 `APIRouter` 前缀；跨文件嵌套路由、动态路径与是否实际挂载以[运行时接口合同清单](develop后端接口清单.md)为准。

本清单用于防止遗漏导入、导出、历史、状态操作、任务资源和系统管理声明；与[移动端需求文档](移动端需求文档.md)中的 F01—F40 对照维护。

本提交唯一未进入 OpenAPI 的声明是 `app.modules.ocr.api.sheet_fill.patch_sheet_field`；移动端不得把它当作可调用合同。

## backend/app/application.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 610 | GET | `/` | [read_root](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/application.py#L610) | `app` |
| 615 | GET | `/health` | [health_check](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/application.py#L615) | `app` |

## backend/app/modules/ai_dispatch/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 27 | GET | `/system/ai-servers` | [get_ai_servers](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ai_dispatch/api.py#L27) | `router` |
| 33 | GET | `/system/ai-servers/status` | [get_ai_server_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ai_dispatch/api.py#L33) | `router` |
| 39 | POST | `/system/ai-servers` | [create_ai_server](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ai_dispatch/api.py#L39) | `router` |
| 48 | PUT | `/system/ai-servers/{server_id}` | [update_ai_server](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ai_dispatch/api.py#L48) | `router` |
| 60 | DELETE | `/system/ai-servers/{server_id}` | [delete_ai_server](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ai_dispatch/api.py#L60) | `router` |
| 72 | POST | `/system/ai-servers/{server_id}/test` | [test_ai_server](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ai_dispatch/api.py#L72) | `router` |
| 87 | GET | `/system/ai-stats` | [get_ai_stats](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ai_dispatch/api.py#L87) | `router` |

## backend/app/modules/announcements/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 21 | GET | `/announcements` | [read_announcements](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/announcements/api.py#L21) | `router` |
| 32 | GET | `/announcements/manage` | [read_announcements_for_management](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/announcements/api.py#L32) | `router` |
| 43 | POST | `/announcements` | [add_announcement](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/announcements/api.py#L43) | `router` |
| 52 | PUT | `/announcements/{announcement_id}` | [edit_announcement](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/announcements/api.py#L52) | `router` |
| 62 | POST | `/announcements/{announcement_id}/publish` | [publish_announcement_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/announcements/api.py#L62) | `router` |
| 71 | POST | `/announcements/{announcement_id}/withdraw` | [withdraw_announcement_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/announcements/api.py#L71) | `router` |
| 80 | DELETE | `/announcements/{announcement_id}` | [remove_announcement](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/announcements/api.py#L80) | `router` |

## backend/app/modules/assistant/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 133 | POST | `/assistant/turns` | [post_assistant_turn](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L133) | `router` |
| 154 | POST | `/assistant/actions` | [post_assistant_action](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L154) | `router` |
| 163 | POST | `/assistant/loan-contracts/summary` | [create_loan_contract_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L163) | `router` |
| 188 | POST | `/assistant/loan-contracts/debt-candidates` | [search_loan_contract_debt_candidates](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L188) | `router` |
| 208 | POST | `/assistant/loan-contracts/archive` | [archive_existing_loan_contract](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L208) | `router` |
| 222 | POST | `/assistant/route` | [route_dialogue](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L222) | `router` |
| 250 | POST | `/assistant/query-summary` | [summarize_query_result](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L250) | `router` |
| 267 | POST | `/assistant/conversations` | [create_free_chat_conversation](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L267) | `router` |
| 284 | GET | `/assistant/conversations` | [list_free_chat_conversations](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L284) | `router` |
| 295 | GET | `/assistant/conversations/{conversation_id}` | [read_free_chat_conversation](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L295) | `router` |
| 307 | DELETE | `/assistant/conversations/{conversation_id}` | [delete_free_chat_conversation](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L307) | `router` |
| 320 | PUT | `/assistant/conversations/{conversation_id}/transcript` | [sync_ai_conversation_transcript](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L320) | `router` |
| 339 | POST | `/assistant/conversations/{conversation_id}/messages` | [post_free_chat_message](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L339) | `router` |
| 375 | POST | `/assistant/sessions` | [create_assistant_session](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L375) | `router` |
| 435 | GET | `/assistant/sessions/{session_id}` | [read_assistant_session](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L435) | `router` |
| 445 | POST | `/assistant/sessions/{session_id}/form-handoff` | [issue_assistant_form_handoff](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L445) | `router` |
| 478 | POST | `/assistant/sessions/{session_id}/missing-master-data` | [start_assistant_missing_master_data](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L478) | `router` |
| 504 | POST | `/assistant/sessions/{session_id}/resume-parent` | [resume_assistant_parent_session](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L504) | `router` |
| 516 | POST | `/assistant/sessions/{session_id}/messages` | [post_assistant_message](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L516) | `router` |
| 577 | POST | `/assistant/sessions/{session_id}/fields` | [post_assistant_fields](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L577) | `router` |
| 597 | POST | `/assistant/sessions/{session_id}/form-state` | [post_assistant_form_state](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L597) | `router` |
| 633 | POST | `/assistant/sessions/{session_id}/selections` | [post_assistant_selection](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L633) | `router` |
| 660 | POST | `/assistant/sessions/{session_id}/confirm` | [confirm_assistant_session](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L660) | `router` |
| 679 | POST | `/assistant/sessions/{session_id}/debt-memo/form-state` | [post_assistant_debt_memo_form_state](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L679) | `router` |
| 698 | POST | `/assistant/sessions/{session_id}/debt-memo/form-saved` | [post_assistant_debt_memo_form_saved](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L698) | `router` |
| 717 | POST | `/assistant/sessions/{session_id}/project/form-state` | [post_assistant_project_form_state](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L717) | `router` |
| 735 | POST | `/assistant/sessions/{session_id}/project/form-saved` | [post_assistant_project_form_saved](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L735) | `router` |
| 753 | POST | `/assistant/sessions/{session_id}/debt-form-saved` | [post_assistant_debt_form_saved](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L753) | `router` |
| 788 | POST | `/assistant/sessions/{session_id}/debt-extraction/retry` | [post_assistant_debt_extraction_retry](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L788) | `router` |
| 823 | POST | `/assistant/sessions/{session_id}/cancel` | [cancel_assistant_session](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/api.py#L823) | `router` |

## backend/app/modules/assistant/debt_prefill_api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 42 | POST | `/ocr/root-debt-prefill` | [post_ocr_root_debt_prefill](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L42) | `router` |
| 88 | POST | `/ocr/modules/{module_id}/debt-prefill` | [post_ocr_module_debt_prefill](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L88) | `router` |
| 124 | POST | `/ocr/documents/{document_id}/analyses/{analysis_id}/controlled-company-prefill` | [post_ocr_controlled_company_prefill](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L124) | `router` |
| 150 | POST | `/ocr/documents/base-materials/entry` | [post_ocr_base_material_entry](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L150) | `router` |
| 171 | POST | `/ocr/documents/base-materials/entry-jobs` | [create_ocr_base_material_entry_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L171) | `router` |
| 194 | GET | `/ocr/documents/base-materials/entry-jobs/active` | [get_active_ocr_base_material_entry_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L194) | `router` |
| 208 | GET | `/ocr/documents/base-materials/entry-jobs/{job_id}` | [get_ocr_base_material_entry_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L208) | `router` |
| 227 | POST | `/ocr/documents/{document_id}/analyses/{analysis_id}/real-estate-prefill` | [post_ocr_real_estate_prefill](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/assistant/debt_prefill_api.py#L227) | `router` |

## backend/app/modules/audit/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 15 | GET | `/audit/logs` | [read_operation_logs](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/audit/api.py#L15) | `router` |
| 39 | GET | `/audit/logs/{log_id}` | [read_operation_log_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/audit/api.py#L39) | `router` |

## backend/app/modules/auth/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 65 | GET | `/auth/captcha` | [issue_captcha](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L65) | `router` |
| 71 | POST | `/auth/login` | [login](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L71) | `router` |
| 121 | POST | `/auth/logout` | [logout](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L121) | `router` |
| 133 | POST | `/auth/activity` | [refresh_activity](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L133) | `router` |
| 160 | GET | `/auth/me` | [get_me](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L160) | `router` |
| 177 | PUT | `/auth/me/password` | [change_my_password](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L177) | `router` |
| 187 | GET | `/auth/users` | [read_regular_users](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L187) | `router` |
| 198 | POST | `/auth/users` | [create_regular_user](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L198) | `router` |
| 208 | PUT | `/auth/users/{user_id}` | [update_regular_user](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L208) | `router` |
| 219 | POST | `/auth/users/{user_id}/deactivate` | [deactivate_user](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L219) | `router` |
| 233 | POST | `/auth/users/{user_id}/activate` | [activate_user](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L233) | `router` |
| 238 | DELETE | `/auth/users/{user_id}` | [delete_user](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L238) | `router` |
| 243 | POST | `/auth/user-admin/transfer` | [transfer_user_admin](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/api.py#L243) | `router` |

## backend/app/modules/auth/permission_api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 48 | GET | `/system/permissions` | [read_permission_configuration](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/permission_api.py#L48) | `router` |
| 61 | PUT | `/system/permissions/{role_code}` | [save_role_permissions](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/auth/permission_api.py#L61) | `router` |

## backend/app/modules/backup/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 91 | GET | `/system/backups` | [read_backups](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L91) | `router` |
| 103 | GET | `/system/backups/schedule` | [read_backup_schedule](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L103) | `router` |
| 112 | PUT | `/system/backups/schedule` | [update_backup_schedule](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L112) | `router` |
| 122 | GET | `/system/backups/storage` | [read_backup_storage](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L122) | `router` |
| 131 | PUT | `/system/backups/storage` | [update_backup_storage](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L131) | `router` |
| 142 | POST | `/system/backups/manual` | [start_manual_backup](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L142) | `router` |
| 154 | GET | `/system/backups/{backup_id}` | [read_backup](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L154) | `router` |
| 164 | POST | `/system/backups/{backup_id}/cancel` | [cancel_backup](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L164) | `router` |
| 174 | DELETE | `/system/backups/{backup_id}` | [delete_backup](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L174) | `router` |
| 185 | GET | `/system/backups/{backup_id}/download` | [download_backup](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L185) | `router` |
| 203 | POST | `/system/restore-uploads` | [start_restore_upload](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L203) | `router` |
| 215 | PUT | `/system/restore-uploads/{upload_id}/parts/{part}` | [upload_restore_part](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L215) | `router` |
| 258 | GET | `/system/restore-uploads/{upload_id}` | [read_restore_upload](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L258) | `router` |
| 268 | POST | `/system/restore-uploads/{upload_id}/complete` | [complete_restore_upload](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L268) | `router` |
| 291 | POST | `/system/restores/precheck` | [precheck_restore](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L291) | `router` |
| 352 | POST | `/system/restores` | [start_restore](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L352) | `router` |
| 414 | POST | `/system/restores/cancel` | [cancel_restore](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L414) | `router` |
| 435 | GET | `/system/restores/status` | [restore_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L435) | `router` |
| 436 | GET | `/system/maintenance-status` | [restore_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L436) | `router` |
| 442 | POST | `/system/restores/release-maintenance` | [release_restore_maintenance](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/backup/api.py#L442) | `router` |

## backend/app/modules/credit_statistics/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 48 | GET | `/guarantee/credit-statistics` | [read_credit_statistics](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L48) | `router` |
| 66 | GET | `/guarantee/credit-statistics/bank-options` | [read_credit_statistics_bank_options](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L66) | `router` |
| 78 | GET | `/guarantee/credit-statistics/export` | [export_credit_statistics](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L78) | `router` |
| 112 | GET | `/guarantee/credit-statistics/manual-credit-import-template` | [download_manual_credit_import_template](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L112) | `router` |
| 132 | POST | `/guarantee/credit-statistics/manual-credit-import/preview` | [preview_manual_credit_import_file](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L132) | `router` |
| 153 | POST | `/guarantee/credit-statistics/manual-credit-import/confirm` | [confirm_manual_credit_import](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L153) | `router` |
| 165 | PUT | `/guarantee/manual-credit-limits/{canonical_creditor_org_id}` | [put_manual_credit](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L165) | `router` |
| 183 | PUT | `/guarantee/credit-statistics/bank-mappings/{creditor_org_id}` | [put_credit_statistics_bank_mapping](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L183) | `router` |
| 201 | DELETE | `/guarantee/manual-credit-limits/{canonical_creditor_org_id}` | [remove_manual_credit](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/credit_statistics/api.py#L201) | `router` |

## backend/app/modules/dataentry/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 77 | GET | `/data-entry/finance/bank-accounts` | [get_bank_accounts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L77) | `router` |
| 169 | PUT | `/data-entry/finance/bank-accounts` | [save_bank_accounts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L169) | `router` |
| 199 | POST | `/data-entry/finance/bank-routing-resolutions` | [resolve_bank_routing_number](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L199) | `router` |
| 211 | POST | `/data-entry/finance/bank-accounts/own/company-resolutions` | [resolve_own_company_name](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L211) | `router` |
| 223 | PUT | `/data-entry/finance/bank-accounts/{record_id}` | [update_bank_accounts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L223) | `router` |
| 260 | POST | `/data-entry/finance/bank-accounts/own/check` | [check_own_company_bank_account_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L260) | `router` |
| 272 | POST | `/data-entry/finance/bank-accounts/own` | [create_own_company_bank_account_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L272) | `router` |
| 289 | POST | `/data-entry/finance/bank-accounts/repayment` | [create_repayment_bank_account_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L289) | `router` |
| 316 | GET | `/data-entry/finance/bank-accounts/{record_id}/repayment-history` | [get_repayment_account_history](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L316) | `router` |
| 328 | PUT | `/data-entry/finance/bank-accounts/{record_id}/repayment-profile` | [update_repayment_profile](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L328) | `router` |
| 346 | DELETE | `/data-entry/finance/bank-accounts/{record_id}/{account_type}` | [delete_bank_account_entry](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L346) | `router` |
| 388 | GET | `/data-entry/finance/invoice` | [get_invoice](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L388) | `router` |
| 483 | PUT | `/data-entry/finance/invoice` | [save_invoice](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L483) | `router` |
| 512 | PUT | `/data-entry/finance/invoice/{invoice_id}` | [update_invoice](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L512) | `router` |
| 544 | GET | `/data-entry/finance/invoice/{invoice_id}` | [get_invoice_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L544) | `router` |
| 556 | POST | `/data-entry/finance/invoice/{invoice_id}/usage-events` | [create_invoice_usage_event](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L556) | `router` |
| 574 | DELETE | `/data-entry/finance/invoice/{invoice_id}` | [delete_invoice](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/api.py#L574) | `router` |

## backend/app/modules/dataentry/asset_api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 76 | GET | `/data-entry/assets/real-estates` | [get_asset_real_estate_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L76) | `router` |
| 94 | GET | `/data-entry/assets/real-estates/options` | [get_asset_real_estate_options](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L94) | `router` |
| 110 | GET | `/data-entry/assets/real-estates/{real_estate_id}` | [get_asset_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L110) | `router` |
| 119 | POST | `/data-entry/assets/real-estates` | [create_asset_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L119) | `router` |
| 155 | PUT | `/data-entry/assets/real-estates/{real_estate_id}` | [update_asset_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L155) | `router` |
| 190 | PATCH | `/data-entry/assets/real-estates/{real_estate_id}/status` | [update_asset_real_estate_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L190) | `router` |
| 205 | DELETE | `/data-entry/assets/real-estates/{real_estate_id}` | [delete_asset_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L205) | `router` |
| 242 | POST | `/data-entry/assets/real-estates/{real_estate_id}/attachments` | [upload_asset_real_estate_attachment](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L242) | `router` |
| 275 | DELETE | `/data-entry/assets/real-estates/{real_estate_id}/attachments/{attachment_id}` | [delete_asset_real_estate_attachment_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L275) | `router` |
| 293 | GET | `/data-entry/assets/controlled-companies` | [get_asset_controlled_company_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L293) | `router` |
| 311 | POST | `/data-entry/assets/controlled-companies` | [create_asset_controlled_company_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L311) | `router` |
| 350 | GET | `/data-entry/assets/controlled-companies/{company_id}` | [get_asset_controlled_company_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L350) | `router` |
| 362 | PUT | `/data-entry/assets/controlled-companies/{company_id}` | [update_asset_controlled_company_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L362) | `router` |
| 400 | PATCH | `/data-entry/assets/controlled-companies/{company_id}/status` | [update_asset_controlled_company_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L400) | `router` |
| 418 | DELETE | `/data-entry/assets/controlled-companies/{company_id}` | [delete_asset_controlled_company_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L418) | `router` |
| 455 | GET | `/data-entry/assets/enterprise-groups` | [get_enterprise_group_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L455) | `router` |
| 469 | POST | `/data-entry/assets/enterprise-groups` | [create_enterprise_group_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L469) | `router` |
| 482 | PUT | `/data-entry/assets/enterprise-groups/{group_id}` | [update_enterprise_group_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L482) | `router` |
| 492 | PATCH | `/data-entry/assets/enterprise-groups/{group_id}/status` | [update_enterprise_group_status_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L492) | `router` |
| 505 | DELETE | `/data-entry/assets/enterprise-groups/{group_id}` | [delete_enterprise_group_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L505) | `router` |
| 523 | GET | `/data-entry/assets/scope-configurations` | [get_group_scope_configuration_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L523) | `router` |
| 550 | POST | `/data-entry/assets/scope-configurations/change` | [change_group_scope_configuration_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L550) | `router` |
| 562 | POST | `/data-entry/assets/scope-configurations/batch-change` | [batch_change_group_scope_configuration_records](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L562) | `router` |
| 574 | GET | `/data-entry/assets/scope-configurations/{company_id}/history` | [get_group_scope_configuration_history](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/asset_api.py#L574) | `router` |

## backend/app/modules/dataentry/engineering_api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 28 | GET | `/data-entry/engineering/projects` | [get_engineering_project_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/engineering_api.py#L28) | `router` |
| 46 | GET | `/data-entry/engineering/projects/{project_id}` | [get_engineering_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/engineering_api.py#L46) | `router` |
| 55 | POST | `/data-entry/engineering/projects` | [create_engineering_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/engineering_api.py#L55) | `router` |
| 68 | PUT | `/data-entry/engineering/projects/{project_id}` | [update_engineering_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/engineering_api.py#L68) | `router` |
| 78 | PATCH | `/data-entry/engineering/projects/{project_id}/status` | [update_engineering_project_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/engineering_api.py#L78) | `router` |
| 93 | DELETE | `/data-entry/engineering/projects/{project_id}` | [delete_engineering_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/engineering_api.py#L93) | `router` |

## backend/app/modules/dataentry/table_api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 14 | POST | `/data-entry/table-query/{surface_key}` | [query_cross_department_table](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/dataentry/table_api.py#L14) | `router` |

## backend/app/modules/debt/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 151 | GET | `/debts/supplementary-fee-names` | [get_supplementary_fee_names](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L151) | `router` |
| 177 | POST | `/debts/supplementary-fee-names` | [create_supplementary_fee_name](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L177) | `router` |
| 205 | GET | `/debts/pending` | [get_pending_debt_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L205) | `router` |
| 245 | PATCH | `/debts/pending/{pending_id}` | [update_pending_debt](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L245) | `router` |
| 279 | GET | `/debts/pending/{pending_id}` | [get_pending_debt_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L279) | `router` |
| 305 | DELETE | `/debts/pending/{pending_id}` | [delete_pending_debt](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L305) | `router` |
| 337 | POST | `/debts/pending/{pending_id}/convert` | [convert_pending_debt](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L337) | `router` |
| 448 | GET | `/debts` | [get_debt_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L448) | `router` |
| 507 | POST | `/debts/query` | [query_debt_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L507) | `router` |
| 516 | GET | `/debts/outstanding-summary` | [get_debt_outstanding_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L516) | `router` |
| 569 | GET | `/debts/fund-statistics` | [get_debt_fund_statistics](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L569) | `router` |
| 631 | GET | `/debts/disbursement-summary` | [get_debt_disbursement_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L631) | `router` |
| 657 | GET | `/debts/comprehensive-cost` | [get_comprehensive_cost_query](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L657) | `router` |
| 739 | GET | `/debts/remaining-fee-allowance` | [get_remaining_fee_allowance](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L739) | `router` |
| 761 | POST | `/debts/floating-rate-calculation` | [calculate_floating_rate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L761) | `router` |
| 775 | POST | `/debts/automatic-project/resolve` | [resolve_debt_automatic_project](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L775) | `router` |
| 793 | POST | `/debts/automatic-project/cost-approval` | [create_debt_automatic_project_cost_approval](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L793) | `router` |
| 812 | POST | `/debts` | [create_debt_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L812) | `router` |
| 838 | POST | `/debts/contract-attachment-uploads` | [upload_debt_contract_attachments](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L838) | `router` |
| 926 | GET | `/debts/contract-attachments/{attachment_file_id}/source-file` | [download_debt_contract_attachment](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L926) | `router` |
| 945 | POST | `/debts/interest-plan-preview` | [preview_debt_interest_plan](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L945) | `router` |
| 958 | POST | `/debts/bill-maturity-preview` | [preview_bill_maturity_amounts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L958) | `router` |
| 971 | POST | `/debts/repayment-plan-import` | [import_repayment_plan_workbook](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L971) | `router` |
| 993 | GET | `/debts/repayment-plan-template` | [download_repayment_plan_template](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L993) | `router` |
| 1011 | POST | `/debts/repayment-plan-export` | [export_repayment_plan](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1011) | `router` |
| 1035 | GET | `/debts/{debt_id}/irr-calculator-export` | [export_debt_irr_calculator](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1035) | `router` |
| 1101 | GET | `/debts/projects/{project_id}/irr-calculator-export` | [export_project_irr_calculator](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1101) | `router` |
| 1202 | POST | `/debts/repayment-plan-custom-export` | [export_custom_repayment_plan](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1202) | `router` |
| 1225 | GET | `/debts/{debt_id}` | [get_debt_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1225) | `router` |
| 1234 | GET | `/debts/{debt_id}/history` | [get_debt_history](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1234) | `router` |
| 1255 | GET | `/debts/{debt_id}/history/{history_id}` | [get_debt_history_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1255) | `router` |
| 1277 | PUT | `/debts/{debt_id}` | [update_debt_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1277) | `router` |
| 1303 | POST | `/debts/{debt_id}/principal-balance-at-date` | [get_principal_balance_at_date](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1303) | `router` |
| 1315 | DELETE | `/debts/{debt_id}` | [delete_debt_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1315) | `router` |
| 1331 | GET | `/cashflows/upcoming` | [list_upcoming_payments](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1331) | `cashflow_router` |
| 1340 | GET | `/cashflows/upcoming/summary` | [get_upcoming_payments_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1340) | `cashflow_router` |
| 1349 | GET | `/cashflows/{event_id}/splits` | [get_cashflow_splits](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1349) | `cashflow_router` |
| 1358 | PUT | `/cashflows/{event_id}/splits/{creditor_org_id}/status` | [update_cashflow_split_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/api.py#L1358) | `cashflow_router` |

## backend/app/modules/debt/project_api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 21 | GET | `/projects/{project_id}/debt-overview` | [get_project_debt_overview_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/project_api.py#L21) | `router` |
| 33 | GET | `/projects/{project_id}/tree` | [get_project_tree](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt/project_api.py#L33) | `router` |

## backend/app/modules/debt_memo/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 91 | POST | `/debt-memo/project-options/query` | [query_debt_memo_project_options](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L91) | `router` |
| 214 | GET | `/debt-memo/plans` | [get_plan_memo_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L214) | `router` |
| 263 | POST | `/debt-memo/plans/query` | [query_plan_memo_table](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L263) | `router` |
| 314 | GET | `/debt-memo/plans/{plan_id}` | [get_plan_memo_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L314) | `router` |
| 324 | POST | `/debt-memo/plans` | [add_plan_memo](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L324) | `router` |
| 359 | PUT | `/debt-memo/plans/{plan_id}` | [edit_plan_memo](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L359) | `router` |
| 369 | DELETE | `/debt-memo/plans/{plan_id}` | [remove_plan_memo](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L369) | `router` |
| 378 | POST | `/debt-memo/plans/{plan_id}/convert-to-actual` | [convert_plan_memo_to_actual](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L378) | `router` |
| 398 | GET | `/debt-memo/actuals` | [get_actual_memo_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L398) | `router` |
| 447 | POST | `/debt-memo/actuals/query` | [query_actual_memo_table](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L447) | `router` |
| 499 | GET | `/debt-memo/actuals/{actual_id}` | [get_actual_memo_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L499) | `router` |
| 508 | POST | `/debt-memo/actuals` | [add_actual_memo](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L508) | `router` |
| 544 | PUT | `/debt-memo/actuals/{actual_id}` | [edit_actual_memo](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L544) | `router` |
| 555 | DELETE | `/debt-memo/actuals/{actual_id}` | [remove_actual_memo](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L555) | `router` |
| 564 | POST | `/debt-memo/actuals/{actual_id}/link-debt` | [link_actual_memo_to_debt](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L564) | `router` |
| 576 | GET | `/debt-memo/actuals/{actual_id}/debt-prefill` | [get_actual_memo_debt_prefill](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L576) | `router` |
| 591 | GET | `/debt-memo/overview-disbursement-summary` | [get_overview_disbursement_summary_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L591) | `router` |
| 619 | GET | `/debt-memo/overview-daily-disbursements` | [get_overview_daily_disbursements_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L619) | `router` |
| 645 | GET | `/debt-memo/overview-daily-disbursements/{kind}` | [get_overview_daily_disbursement_list_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L645) | `router` |
| 680 | GET | `/debt-memo/summary` | [get_debt_memo_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L680) | `router` |
| 689 | GET | `/debt-memo/dashboard` | [get_debt_memo_dashboard](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L689) | `router` |
| 698 | GET | `/debt-memo/activity` | [get_debt_memo_activity](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/debt_memo/api.py#L698) | `router` |

## backend/app/modules/documents/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 109 | POST | `/documents/repayment-notices/resolve` | [resolve_notice](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L109) | `router` |
| 121 | POST | `/documents/repayment-payments` | [create_payment_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L121) | `router` |
| 138 | GET | `/documents/repayment-payments` | [list_payment_documents](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L138) | `router` |
| 167 | GET | `/documents/repayment-payments/counts` | [get_payment_document_counts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L167) | `router` |
| 181 | GET | `/documents/repayment-payments/workflow-counts` | [get_payment_document_workflow_counts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L181) | `router` |
| 192 | GET | `/documents/repayment-payments/auto-generation/status` | [get_payment_document_auto_generation_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L192) | `router` |
| 203 | POST | `/documents/repayment-payments/auto-generation/run-now` | [run_payment_document_auto_generation_now](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L203) | `router` |
| 214 | GET | `/documents/repayment-payments/duplicate-check` | [check_payment_document_duplicate](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L214) | `router` |
| 238 | POST | `/documents/repayment-payments/{document_id}/print` | [record_payment_document_print](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L238) | `router` |
| 256 | GET | `/documents/repayment-payments/{document_id}/excel` | [download_payment_document_excel](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L256) | `router` |
| 285 | POST | `/documents/repayment-payments/{document_id}/receipt-verifications` | [verify_payment_document_receipts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L285) | `router` |
| 323 | GET | `/documents/repayment-payments/{document_id}` | [get_payment_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L323) | `router` |
| 339 | PUT | `/documents/repayment-payments/{document_id}` | [update_payment_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L339) | `router` |
| 357 | POST | `/documents/repayment-payments/{document_id}/void` | [void_payment_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L357) | `router` |
| 375 | DELETE | `/documents/repayment-payments/{document_id}` | [delete_payment_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L375) | `router` |
| 396 | POST | `/documents/fee-applications/from-debts` | [create_fee_applications_from_debts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L396) | `router` |
| 413 | GET | `/documents/fee-applications` | [list_fee_applications](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L413) | `router` |
| 435 | GET | `/documents/fee-applications/counts` | [get_fee_application_counts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L435) | `router` |
| 446 | GET | `/documents/fee-applications/{document_id}` | [get_fee_application](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L446) | `router` |
| 462 | PUT | `/documents/fee-applications/{document_id}` | [update_fee_application](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L462) | `router` |
| 480 | GET | `/documents/fee-applications/{document_id}/excel` | [download_fee_application_excel](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L480) | `router` |
| 507 | POST | `/documents/fee-applications/{document_id}/void` | [void_fee_application](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L507) | `router` |
| 530 | GET | `/documents/cost-approvals/project-options` | [get_cost_approval_project_options](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L530) | `router` |
| 550 | GET | `/documents/cost-approvals/defaults` | [get_cost_approval_defaults_endpoint](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L550) | `router` |
| 568 | POST | `/documents/cost-approvals` | [create_cost_approval](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L568) | `router` |
| 585 | GET | `/documents/cost-approvals` | [list_cost_approvals](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L585) | `router` |
| 612 | GET | `/documents/cost-approvals/counts` | [get_cost_approval_counts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L612) | `router` |
| 628 | GET | `/documents/cost-approvals/{document_id}` | [get_cost_approval](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L628) | `router` |
| 644 | POST | `/documents/cost-approvals/{document_id}/print` | [record_cost_approval_document_print](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L644) | `router` |
| 662 | GET | `/documents/cost-approvals/{document_id}/excel` | [download_cost_approval_excel](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L662) | `router` |
| 690 | POST | `/documents/cost-approvals/{document_id}/regenerate` | [regenerate_cost_approval](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L690) | `router` |
| 708 | GET | `/documents/cost-approvals/{document_id}/history` | [get_cost_approval_history](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L708) | `router` |
| 728 | GET | `/documents/cost-approvals/{document_id}/history/{history_id}` | [get_cost_approval_history_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L728) | `router` |
| 746 | GET | `/documents/cost-approvals/{document_id}/versions/{version_no}` | [get_cost_approval_version](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L746) | `router` |
| 764 | PUT | `/documents/cost-approvals/{document_id}` | [update_cost_approval](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L764) | `router` |
| 782 | POST | `/documents/cost-approvals/{document_id}/void` | [void_cost_approval](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L782) | `router` |
| 800 | DELETE | `/documents/cost-approvals/{document_id}` | [delete_cost_approval](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/documents/api.py#L800) | `router` |

## backend/app/modules/export/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 47 | POST | `/exports/debts` | [export_debts](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L47) | `router` |
| 69 | POST | `/exports/cashflows` | [export_cashflows](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L69) | `router` |
| 91 | POST | `/exports/projects/summary` | [export_project_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L91) | `router` |
| 109 | POST | `/exports/projects/ledger` | [export_project_ledger](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L109) | `router` |
| 140 | POST | `/exports/projects/cost-process` | [export_project_cost_process](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L140) | `router` |
| 170 | POST | `/exports/jobs/debts` | [create_debt_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L170) | `router` |
| 190 | POST | `/exports/jobs/cashflows` | [create_cashflow_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L190) | `router` |
| 210 | POST | `/exports/jobs/projects/summary` | [create_project_summary_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L210) | `router` |
| 230 | POST | `/exports/jobs/projects/ledger` | [create_project_ledger_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L230) | `router` |
| 250 | POST | `/exports/jobs/projects/cost-process` | [create_project_cost_process_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L250) | `router` |
| 270 | GET | `/exports/jobs/{job_id}` | [get_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L270) | `router` |
| 279 | DELETE | `/exports/jobs/{job_id}` | [delete_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L279) | `router` |
| 288 | GET | `/exports/jobs/{job_id}/download` | [download_export_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/export/api.py#L288) | `router` |

## backend/app/modules/file_upload/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 22 | POST | `/file-uploads/approval-documents` | [upload_approval_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/file_upload/api.py#L22) | `router` |
| 39 | POST | `/file-uploads/repayment-subject-attachments` | [upload_repayment_subject_attachment](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/file_upload/api.py#L39) | `router` |
| 56 | GET | `/file-uploads/{category}/{filename}` | [download_uploaded_file](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/file_upload/api.py#L56) | `router` |

## backend/app/modules/guarantee/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 58 | GET | `/guarantee/navigation` | [get_guarantee_navigation](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L58) | `router` |
| 102 | GET | `/guarantee/records` | [list_guarantee_records](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L102) | `router` |
| 128 | GET | `/guarantee/statistics` | [get_statistics](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L128) | `router` |
| 136 | GET | `/guarantee/detail-statistics` | [get_detail_statistics](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L136) | `router` |
| 159 | GET | `/guarantee/records/{record_id}` | [get_guarantee_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L159) | `router` |
| 168 | POST | `/guarantee/records` | [create_guarantee_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L168) | `router` |
| 182 | PUT | `/guarantee/records/{record_id}` | [update_guarantee_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L182) | `router` |
| 203 | PATCH | `/guarantee/records/{record_id}/balance` | [patch_guarantee_balance](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L203) | `router` |
| 220 | PATCH | `/guarantee/records/{record_id}/status` | [patch_guarantee_status](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L220) | `router` |
| 237 | DELETE | `/guarantee/records/{record_id}` | [delete_guarantee_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L237) | `router` |
| 253 | GET | `/guarantee/attachments/{attachment_id}/source-file` | [download_guarantee_attachment](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/api.py#L253) | `router` |

## backend/app/modules/guarantee/ocr_prefill_api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 27 | POST | `/ocr/guarantee-prefill` | [prefill_guarantee_application](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/guarantee/ocr_prefill_api.py#L27) | `router` |

## backend/app/modules/master_data/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 84 | GET | `/master-data/debtors` | [get_debtor_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L84) | `router` |
| 94 | GET | `/master-data/debtors/tree` | [get_debtor_tree](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L94) | `router` |
| 103 | GET | `/master-data/debtors/search` | [search_debtor_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L103) | `router` |
| 121 | POST | `/master-data/debtors` | [create_debtor_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L121) | `router` |
| 130 | PUT | `/master-data/debtors/{debtor_id}` | [update_debtor_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L130) | `router` |
| 140 | GET | `/master-data/creditor-orgs` | [get_creditor_org_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L140) | `router` |
| 158 | GET | `/master-data/creditor-orgs/search` | [search_creditor_org_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L158) | `router` |
| 183 | POST | `/master-data/creditor-orgs/table-query` | [query_creditor_org_table_page](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L183) | `router` |
| 192 | POST | `/master-data/creditor-orgs/resolve` | [resolve_creditor_org_names](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L192) | `router` |
| 203 | GET | `/master-data/creditor-orgs/{org_id}` | [get_creditor_org_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L203) | `router` |
| 212 | POST | `/master-data/creditor-orgs` | [create_creditor_org_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L212) | `router` |
| 221 | PUT | `/master-data/creditor-orgs/{org_id}` | [update_creditor_org_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L221) | `router` |
| 231 | GET | `/master-data/creditor-org-seed-config` | [get_creditor_org_seed_config_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L231) | `router` |
| 239 | PUT | `/master-data/creditor-org-seed-config` | [update_creditor_org_seed_config_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L239) | `router` |
| 248 | GET | `/master-data/guarantee-companies` | [get_guarantee_company_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L248) | `router` |
| 266 | GET | `/master-data/guarantee-companies/{company_id}` | [get_guarantee_company_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L266) | `router` |
| 275 | POST | `/master-data/guarantee-companies` | [create_guarantee_company_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L275) | `router` |
| 284 | PUT | `/master-data/guarantee-companies/{company_id}` | [update_guarantee_company_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L284) | `router` |
| 294 | GET | `/master-data/financing-products/standard` | [get_standard_financing_product_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L294) | `router` |
| 304 | POST | `/master-data/financing-products/standard` | [create_standard_financing_product_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L304) | `router` |
| 317 | PUT | `/master-data/financing-products/standard/{product_id}` | [update_standard_financing_product_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L317) | `router` |
| 327 | GET | `/master-data/financing-products/custom` | [get_custom_financing_product_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L327) | `router` |
| 337 | POST | `/master-data/financing-products/custom` | [create_custom_financing_product_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L337) | `router` |
| 350 | PUT | `/master-data/financing-products/custom/{product_id}` | [update_custom_financing_product_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L350) | `router` |
| 360 | GET | `/master-data/real-estates` | [get_real_estate_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L360) | `router` |
| 370 | GET | `/master-data/real-estates/{real_estate_id}` | [get_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L370) | `router` |
| 379 | POST | `/master-data/real-estates` | [create_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L379) | `router` |
| 388 | PUT | `/master-data/real-estates/{real_estate_id}` | [update_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L388) | `router` |
| 398 | DELETE | `/master-data/real-estates/{real_estate_id}` | [delete_real_estate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L398) | `router` |
| 407 | GET | `/master-data/financing-types` | [get_financing_type_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L407) | `router` |
| 417 | POST | `/master-data/financing-types` | [create_financing_type_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L417) | `router` |
| 426 | PUT | `/master-data/financing-types/{type_id}` | [update_financing_type_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/master_data/api.py#L426) | `router` |

## backend/app/modules/ocr/api/automation.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 56 | POST | `/automation-jobs` | [start_automation_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/automation.py#L56) | `router` |
| 160 | POST | `/documents/{document_id}/reprocess-jobs` | [start_document_reprocess_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/automation.py#L160) | `router` |
| 213 | GET | `/automation-jobs/{job_id}` | [get_automation_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/automation.py#L213) | `router` |
| 242 | DELETE | `/automation-jobs/{job_id}` | [cancel_automation_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/automation.py#L242) | `router` |

## backend/app/modules/ocr/api/catalog.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 30 | GET | `/extraction-config` | [get_contract_extraction_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/catalog.py#L30) | `router` |
| 43 | GET | `/extraction-config/export` | [export_contract_extraction_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/catalog.py#L43) | `router` |
| 56 | GET | `/extraction-config/initial-seed` | [get_contract_extraction_initial_seed](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/catalog.py#L56) | `router` |
| 68 | PUT | `/extraction-config` | [update_contract_extraction_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/catalog.py#L68) | `router` |
| 82 | POST | `/extraction-config/import` | [import_contract_extraction_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/catalog.py#L82) | `router` |
| 96 | POST | `/extraction-config/import-initial-seed` | [import_contract_extraction_initial_seed](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/catalog.py#L96) | `router` |

## backend/app/modules/ocr/api/debt_init.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 68 | GET | `/debt-init/template` | [download_debt_init_template](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L68) | `router` |
| 90 | POST | `/debt-init/preview` | [preview_debt_init](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L90) | `router` |
| 108 | POST | `/debt-init/records` | [create_debt_init_records](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L108) | `router` |
| 119 | POST | `/debt-init/records/upload` | [create_debt_init_records_with_source](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L119) | `router` |
| 148 | GET | `/debt-init/batches` | [read_debt_init_batches](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L148) | `router` |
| 169 | POST | `/debt-init/batches/clear` | [clear_debt_init_batches](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L169) | `router` |
| 188 | GET | `/debt-init/batches/{batch_id}/source` | [download_debt_init_batch_source](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L188) | `router` |
| 209 | GET | `/debt-init/records` | [read_debt_init_records](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L209) | `router` |
| 230 | POST | `/debt-init/records/clear` | [clear_debt_init_records](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L230) | `router` |
| 243 | PATCH | `/debt-init/records/{record_id}` | [patch_debt_init_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L243) | `router` |
| 255 | POST | `/debt-init/records/{record_id}/transfer` | [transfer_debt_init_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L255) | `router` |
| 266 | POST | `/debt-init/records/transfer-pending` | [transfer_debt_init_records_to_pending](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L266) | `router` |
| 277 | POST | `/debt-init/records/transfer-jobs` | [create_debt_init_transfer_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L277) | `router` |
| 295 | GET | `/debt-init/records/transfer-jobs/active` | [read_active_debt_init_transfer_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L295) | `router` |
| 305 | GET | `/debt-init/records/transfer-jobs/{job_id}` | [read_debt_init_transfer_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L305) | `router` |
| 316 | DELETE | `/debt-init/records/transfer-jobs/{job_id}` | [cancel_debt_init_transfer_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/debt_init.py#L316) | `router` |

## backend/app/modules/ocr/api/docs_sortout.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 53 | POST | `/document-sortout-jobs` | [start_document_sortout_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/docs_sortout.py#L53) | `router` |
| 109 | GET | `/document-sortout-jobs/{job_id}` | [get_document_sortout_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/docs_sortout.py#L109) | `router` |
| 149 | GET | `/document-sortout-jobs/{job_id}/documents/{document_id}/diagnostics` | [get_document_sortout_debug_info](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/docs_sortout.py#L149) | `router` |
| 179 | DELETE | `/document-sortout-jobs/{job_id}` | [cancel_document_sortout_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/docs_sortout.py#L179) | `router` |

## backend/app/modules/ocr/api/documents.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 108 | GET | `/documents` | [get_ocr_documents](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L108) | `router` |
| 119 | GET | `/documents/base-materials/three-year-one-period` | [get_three_year_one_period_materials](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L119) | `router` |
| 148 | GET | `/documents/base-materials` | [get_company_base_materials](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L148) | `router` |
| 179 | GET | `/documents/base-material-candidates` | [get_base_material_candidates](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L179) | `router` |
| 249 | POST | `/documents/base-materials/association-preview` | [preview_base_material_associations](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L249) | `router` |
| 270 | POST | `/documents/base-materials/bind-batch` | [bind_base_material_groups](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L270) | `router` |
| 298 | POST | `/documents/base-materials/bind` | [bind_base_materials](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L298) | `router` |
| 359 | POST | `/documents/base-materials/unbind` | [unbind_base_materials](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L359) | `router` |
| 380 | POST | `/documents/word-upload` | [upload_word_document_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L380) | `router` |
| 429 | POST | `/documents/spreadsheet-upload` | [upload_spreadsheet_document_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L429) | `router` |
| 500 | GET | `/documents/{document_id}` | [get_ocr_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L500) | `router` |
| 524 | DELETE | `/documents/{document_id}` | [delete_ocr_document_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L524) | `router` |
| 535 | POST | `/documents/{document_id}/replace-with/{replacement_document_id}` | [replace_ocr_document_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L535) | `router` |
| 556 | PUT | `/documents/{document_id}` | [update_ocr_document](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L556) | `router` |
| 579 | GET | `/documents/{document_id}/analyses` | [get_ocr_document_analyses](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L579) | `router` |
| 593 | POST | `/documents/{document_id}/analyses` | [create_ocr_document_analysis](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L593) | `router` |
| 617 | GET | `/documents/{document_id}/analyses/{analysis_id}` | [get_ocr_document_analysis](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L617) | `router` |
| 637 | GET | `/documents/{document_id}/analyses/{analysis_id}/debug` | [get_ocr_document_analysis_debug](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L637) | `router` |
| 660 | DELETE | `/documents/{document_id}/analyses/{analysis_id}` | [cancel_ocr_document_analysis](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L660) | `router` |
| 680 | PATCH | `/documents/{document_id}/analyses/{analysis_id}/fields/{field_id}` | [patch_ocr_extracted_field](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L680) | `router` |
| 704 | PATCH | `/documents/{document_id}/analyses/{analysis_id}/invoice-lines/{line_item_id}` | [patch_ocr_invoice_line_item](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L704) | `router` |
| 710 | GET | `/documents/{document_id}/pages/{page_no}/image` | [get_ocr_document_page_image](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L710) | `router` |
| 728 | GET | `/documents/{document_id}/source-file` | [get_ocr_document_source_file](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L728) | `router` |
| 749 | POST | `/documents/review-job` | [save_ocr_review_job_document_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L749) | `router` |
| 803 | POST | `/documents` | [save_ocr_document_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/documents.py#L803) | `router` |

## backend/app/modules/ocr/api/health.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 42 | GET | `/capabilities` | [get_ocr_capabilities](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/health.py#L42) | `router` |
| 55 | GET | `/service-ready` | [get_ocr_service_ready](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/health.py#L55) | `router` |
| 103 | GET | `/health` | [ocr_health_check](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/health.py#L103) | `router` |

## backend/app/modules/ocr/api/material_list.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 63 | POST | `/material-list/analyze` | [analyze_material_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/material_list.py#L63) | `router` |
| 120 | POST | `/material-list/package` | [package_materials](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/material_list.py#L120) | `router` |

## backend/app/modules/ocr/api/modules.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 40 | POST | `/modules/root-debt-import-preview` | [post_ocr_root_debt_preview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L40) | `router` |
| 59 | GET | `/modules` | [get_ocr_modules](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L59) | `router` |
| 83 | GET | `/modules/owners` | [get_ocr_history_owners](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L83) | `router` |
| 96 | GET | `/modules/history-search` | [search_ocr_history](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L96) | `router` |
| 120 | GET | `/modules/{module_id}` | [get_ocr_module](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L120) | `router` |
| 138 | GET | `/modules/{module_id}/debt-import-preview` | [get_ocr_module_debt_preview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L138) | `router` |
| 157 | PATCH | `/modules/{module_id}` | [rename_ocr_module_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L157) | `router` |
| 169 | PATCH | `/modules/{module_id}/parent` | [move_ocr_module_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L169) | `router` |
| 186 | PATCH | `/documents/{document_id}/module` | [move_ocr_document_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L186) | `router` |
| 210 | DELETE | `/modules/{module_id}` | [delete_ocr_module_api](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/modules.py#L210) | `router` |

## backend/app/modules/ocr/api/review.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 60 | GET | `/performance-metrics` | [get_ocr_performance_metrics](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review.py#L60) | `router` |

## backend/app/modules/ocr/api/review_jobs.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 58 | POST | `/review-jobs` | [start_ocr_review_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_jobs.py#L58) | `router` |
| 149 | POST | `/review-jobs/image-bundles` | [start_ocr_image_bundle_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_jobs.py#L149) | `router` |
| 199 | POST | `/review-jobs/{job_id}/start` | [start_staged_ocr_review_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_jobs.py#L199) | `router` |

## backend/app/modules/ocr/api/review_resources.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 47 | DELETE | `/review-jobs/{job_id}` | [cancel_ocr_review_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L47) | `router` |
| 90 | DELETE | `/review-jobs/{job_id}/staged-resource` | [release_staged_ocr_review_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L90) | `router` |
| 111 | GET | `/review-jobs/{job_id}/source-file` | [get_ocr_review_job_source_file](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L111) | `router` |
| 163 | GET | `/review-jobs/{job_id}/source-pages/{page_index}` | [get_ocr_review_job_source_page](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L163) | `router` |
| 216 | GET | `/review-jobs/{job_id}/direct-preview` | [get_ocr_review_job_direct_preview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L216) | `router` |
| 263 | GET | `/review-jobs/{job_id}/source-images/{image_index}` | [get_ocr_review_job_source_image](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L263) | `router` |
| 334 | GET | `/review-jobs/{job_id}` | [get_ocr_review_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L334) | `router` |
| 381 | GET | `/review-jobs/{job_id}/pages/{page_no}` | [get_ocr_review_job_page](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L381) | `router` |
| 429 | GET | `/review-jobs/{job_id}/pages/{page_no}/image` | [get_ocr_review_job_page_image](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L429) | `router` |
| 456 | GET | `/review-jobs/{job_id}/diagnostics` | [get_ocr_review_job_debug_info](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_resources.py#L456) | `router` |

## backend/app/modules/ocr/api/review_sync.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 29 | POST | `/process` | [process_ocr_file](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_sync.py#L29) | `router` |
| 55 | POST | `/review-process` | [process_ocr_vlm_file](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/review_sync.py#L55) | `router` |

## backend/app/modules/ocr/api/sheet_fill.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 62 | POST | `/sheet-fill/jobs` | [start_sheet_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L62) | `router` |
| 86 | GET | `/sheet-fill/jobs` | [read_sheet_jobs](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L86) | `router` |
| 117 | GET | `/sheet-fill/fields` | [read_sheet_fields](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L117) | `router` |
| 128 | GET | `/sheet-fill/fields/export` | [export_sheet_fields](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L128) | `router` |
| 143 | POST | `/sheet-fill/fields/import` | [import_sheet_fields](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L143) | `router` |
| 163 | PATCH | `/sheet-fill/fields/{field_key:path}` | [patch_sheet_field](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L163) | `router` |
| 183 | GET | `/sheet-fill/jobs/{job_id}` | [read_sheet_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L183) | `router` |
| 194 | GET | `/sheet-fill/active-job` | [read_active_sheet_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L194) | `router` |
| 204 | GET | `/sheet-fill/jobs/{job_id}/mappings` | [read_sheet_mappings](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L204) | `router` |
| 215 | POST | `/sheet-fill/jobs/{job_id}/mappings/confirm` | [confirm_sheet_mappings](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L215) | `router` |
| 231 | POST | `/sheet-fill/jobs/{job_id}/mappings/analyze` | [analyze_sheet_map](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L231) | `router` |
| 243 | GET | `/sheet-fill/jobs/{job_id}/preview` | [read_sheet_preview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L243) | `router` |
| 254 | POST | `/sheet-fill/jobs/{job_id}/preview/confirm` | [confirm_sheet_preview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L254) | `router` |
| 270 | POST | `/sheet-fill/jobs/{job_id}/retry` | [retry_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L270) | `router` |
| 284 | POST | `/sheet-fill/jobs/{job_id}/rerun` | [rerun_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L284) | `router` |
| 298 | GET | `/sheet-fill/jobs/{job_id}/file` | [download_sheet_file](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L298) | `router` |
| 333 | GET | `/sheet-fill/jobs/{job_id}/debug` | [download_sheet_debug](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_fill.py#L333) | `router` |

## backend/app/modules/ocr/api/sheet_rules.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 46 | GET | `/rule-operators` | [read_rule_operators](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L46) | `router` |
| 104 | GET | `/org-relations` | [read_org_relations](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L104) | `router` |
| 115 | POST | `/org-relations` | [create_org_relation](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L115) | `router` |
| 134 | PATCH | `/org-relations/{relation_id}` | [patch_org_relation](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L134) | `router` |
| 160 | GET | `/rules` | [read_sheet_rules](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L160) | `router` |
| 179 | POST | `/rules` | [create_sheet_rule](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L179) | `router` |
| 193 | POST | `/rules/{rule_key}/versions` | [clone_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L193) | `router` |
| 207 | PATCH | `/rule-versions/{version_id}` | [patch_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L207) | `router` |
| 223 | POST | `/rule-versions/{version_id}/validate` | [validate_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L223) | `router` |
| 237 | POST | `/rule-versions/{version_id}/preview` | [preview_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L237) | `router` |
| 252 | POST | `/rule-versions/{version_id}/publish` | [publish_sheet_rule_version](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L252) | `router` |
| 266 | GET | `/rules/export` | [export_sheet_rules](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L266) | `router` |
| 280 | POST | `/rules/import` | [import_sheet_rules](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/ocr/api/sheet_rules.py#L280) | `router` |

## backend/app/modules/overview/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 37 | GET | `/overview/funding-gap-config` | [read_funding_gap_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/overview/api.py#L37) | `router` |
| 70 | PUT | `/overview/funding-gap-config` | [save_funding_gap_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/overview/api.py#L70) | `router` |
| 82 | DELETE | `/overview/funding-gap-config/{config_id}` | [clear_funding_gap_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/overview/api.py#L82) | `router` |
| 101 | GET | `/overview/planned-financing-config` | [read_planned_financing_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/overview/api.py#L101) | `router` |
| 132 | PUT | `/overview/planned-financing-config` | [save_planned_financing_config](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/overview/api.py#L132) | `router` |

## backend/app/modules/project/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 205 | GET | `/projects/import-template` | [get_project_import_template](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L205) | `router` |
| 227 | POST | `/projects/import` | [import_project_records](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L227) | `router` |
| 318 | GET | `/projects` | [get_project_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L318) | `router` |
| 367 | POST | `/projects/query` | [query_project_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L367) | `router` |
| 376 | POST | `/projects/query-selection` | [query_project_selection_ids](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L376) | `router` |
| 417 | POST | `/projects` | [create_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L417) | `router` |
| 445 | GET | `/projects/code-preview` | [get_project_code_preview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L445) | `router` |
| 454 | GET | `/projects/options` | [get_project_options](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L454) | `router` |
| 470 | GET | `/projects/financing-pipeline/summary` | [get_project_financing_pipeline_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L470) | `router` |
| 491 | GET | `/projects/financing-pipeline/details` | [get_project_financing_pipeline_details](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L491) | `router` |
| 518 | GET | `/projects/engagements` | [get_project_engagement_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L518) | `router` |
| 538 | POST | `/projects/engagements` | [create_project_engagement_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L538) | `router` |
| 551 | GET | `/projects/engagements/{engagement_id}` | [get_project_engagement_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L551) | `router` |
| 560 | PUT | `/projects/engagements/{engagement_id}` | [update_project_engagement_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L560) | `router` |
| 570 | POST | `/projects/engagements/{engagement_id}/status` | [change_project_engagement_status_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L570) | `router` |
| 583 | POST | `/projects/engagements/{engagement_id}/convert` | [convert_project_engagement_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L583) | `router` |
| 597 | GET | `/projects/{project_id}` | [get_project_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L597) | `router` |
| 606 | PUT | `/projects/{project_id}` | [update_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L606) | `router` |
| 618 | PUT | `/projects/{project_id}/refinance` | [update_project_refinance_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L618) | `router` |
| 630 | POST | `/projects/{project_id}/engagement-status` | [update_project_engagement_status_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L630) | `router` |
| 642 | POST | `/projects/{project_id}/complete-funding` | [complete_project_funding_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L642) | `router` |
| 657 | POST | `/projects/{project_id}/abandon` | [abandon_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L657) | `router` |
| 672 | POST | `/projects/{project_id}/restore-abandonment` | [restore_abandoned_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L672) | `router` |
| 687 | DELETE | `/projects/{project_id}` | [delete_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L687) | `router` |
| 703 | POST | `/projects/{project_id}/restore` | [restore_project_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L703) | `router` |
| 721 | GET | `/projects/{project_id}/history` | [get_project_history](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L721) | `router` |
| 742 | GET | `/projects/{project_id}/history/{history_id}` | [get_project_history_detail](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L742) | `router` |
| 764 | GET | `/projects/{project_id}/creditors` | [get_project_creditors](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L764) | `router` |
| 773 | PUT | `/projects/{project_id}/creditors` | [replace_project_creditor_records](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/project/api.py#L773) | `router` |

## backend/app/modules/query_center/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 78 | POST | `/query-center/reports/composed/preview` | [preview_composed_query_report](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L78) | `router` |
| 90 | POST | `/query-center/reports/composed/intelligence-jobs` | [create_composed_query_report_intelligence_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L90) | `router` |
| 107 | GET | `/query-center/reports/composed/intelligence-jobs/{job_id}` | [get_composed_query_report_intelligence_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L107) | `router` |
| 123 | POST | `/query-center/reports/composed/intelligence-jobs/{job_id}/retry` | [retry_composed_query_report_intelligence_job](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L123) | `router` |
| 140 | POST | `/query-center/reports/composed/intelligence` | [draft_composed_query_report_intelligence](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L140) | `router` |
| 161 | POST | `/query-center/reports/composed/export` | [export_composed_query_report](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L161) | `router` |
| 216 | POST | `/query-center/reports/financing-work/preview` | [preview_financing_work_report](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L216) | `router` |
| 228 | POST | `/query-center/reports/financing-work/export` | [export_financing_work_report](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L228) | `router` |
| 273 | POST | `/query-center/interpret` | [interpret_query_center_prompt](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L273) | `router` |
| 296 | POST | `/query-center/export` | [export_query_center_result](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L296) | `router` |
| 332 | GET | `/query-center/financing-structure` | [read_financing_structure](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L332) | `router` |
| 352 | GET | `/query-center/financing-total` | [read_financing_total](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L352) | `router` |
| 382 | GET | `/query-center/planned-financing-trial` | [read_planned_financing_trial](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L382) | `router` |
| 416 | GET | `/query-center/debt-structure` | [read_debt_structure](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L416) | `router` |
| 436 | GET | `/query-center/credit-statistics` | [read_enterprise_credit_statistics](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L436) | `router` |
| 456 | GET | `/query-center/group-scope` | [read_group_scope](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L456) | `router` |
| 470 | GET | `/query-center/guarantees/month-end` | [read_guarantees_at_month_end](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L470) | `router` |
| 490 | GET | `/query-center/debt-monthly` | [read_debt_monthly](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L490) | `router` |
| 522 | GET | `/query-center/group-overview` | [read_group_overview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L522) | `router` |
| 556 | GET | `/query-center/debt-refinance` | [read_debt_refinance](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_center/api.py#L556) | `router` |

## backend/app/modules/query_language/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 21 | POST | `/query/interpret` | [interpret_natural_language_query](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/query_language/api.py#L21) | `router` |

## backend/app/modules/statistics/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 49 | GET | `/statistics/pending-items` | [read_pending_items](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/statistics/api.py#L49) | `router` |
| 57 | GET | `/statistics/calendar` | [read_calendar_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/statistics/api.py#L57) | `router` |
| 90 | GET | `/statistics/calendar/{target_date}/items` | [read_calendar_items](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/statistics/api.py#L90) | `router` |
| 121 | GET | `/statistics/cashflows` | [read_cashflow_list](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/statistics/api.py#L121) | `router` |
| 162 | GET | `/statistics/projects/summary` | [read_project_cashflow_summary](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/statistics/api.py#L162) | `router` |

## backend/app/modules/sync/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 16 | GET | `/sync/changes` | [get_sync_changes](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/sync/api.py#L16) | `router` |
| 37 | GET | `/sync/snapshot` | [get_sync_snapshot](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/sync/api.py#L37) | `router` |

## backend/app/modules/system/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 65 | GET | `/meta/options` | [read_meta_options](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L65) | `router` |
| 73 | GET | `/system/parameters` | [read_system_parameters](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L73) | `router` |
| 83 | PUT | `/system/parameters/{param_key}` | [update_system_parameter_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L83) | `router` |
| 93 | POST | `/system/dev/drop-all-tables` | [drop_all_tables_for_development](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L93) | `router` |
| 108 | GET | `/system/dev/business-test-data` | [read_business_test_data](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L108) | `router` |
| 126 | POST | `/system/dev/business-test-data/import` | [import_business_test_data_for_development](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L126) | `router` |
| 144 | GET | `/system/holidays` | [read_holiday_calendar](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L144) | `router` |
| 153 | GET | `/system/holidays/official-preview` | [read_official_holiday_preview](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L153) | `router` |
| 165 | POST | `/system/holidays/import-official` | [import_official_holiday_schedule](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L165) | `router` |
| 177 | GET | `/system/holiday-schedules/{year}` | [read_holiday_schedule](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L177) | `router` |
| 191 | PUT | `/system/holiday-schedules/{year}` | [save_holiday_schedule](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L191) | `router` |
| 211 | POST | `/system/holiday-schedules/{year}/publish` | [publish_holiday_schedule_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L211) | `router` |
| 229 | PUT | `/system/holidays/{holiday_date}` | [upsert_holiday_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L229) | `router` |
| 239 | DELETE | `/system/holidays/{holiday_date}` | [delete_holiday_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L239) | `router` |
| 248 | GET | `/system/lpr-rates` | [read_lpr_rates](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L248) | `router` |
| 257 | PUT | `/system/lpr-rates` | [upsert_lpr_rate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L257) | `router` |
| 266 | POST | `/system/lpr-rates/import-official` | [import_official_lpr_rates](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L266) | `router` |
| 274 | DELETE | `/system/lpr-rates/{rate_id}` | [delete_lpr_rate_record](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/system/api.py#L274) | `router` |

## backend/app/modules/table_views/api.py

| 行号 | 方法 | 本文件直接路由路径 | 处理函数 | 路由对象 |
| ---: | --- | --- | --- | --- |
| 35 | GET | `/table-page-size-preferences/{surface_key}` | [get_table_page_size_preference](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/table_views/api.py#L35) | `router` |
| 58 | PUT | `/table-page-size-preferences/{surface_key}` | [put_table_page_size_preference](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/table_views/api.py#L58) | `router` |
| 96 | GET | `/table-views` | [get_table_views](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/table_views/api.py#L96) | `router` |
| 119 | PUT | `/table-preferences/{surface_key}` | [put_table_preference](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/table_views/api.py#L119) | `router` |
| 143 | POST | `/table-views` | [post_table_view](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/table_views/api.py#L143) | `router` |
| 163 | PATCH | `/table-views/{view_id}` | [patch_table_view](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/table_views/api.py#L163) | `router` |
| 181 | DELETE | `/table-views/{view_id}` | [remove_table_view](https://github.com/tongdeyu/citybond/blob/a7da695f46a542ebb5a3bfcab87f4238df89f2fa/backend/app/modules/table_views/api.py#L181) | `router` |
