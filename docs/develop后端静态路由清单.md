**develop 后端静态路由清单（移动端覆盖核对附录）**

代码基线：本地 `origin/develop`，提交 `56437f74`。日期：2026-09-09。

共提取 45 个 Python 文件中的 508 条 HTTP 路由装饰器声明。该数字不是业务功能数，也不是运行时实际挂载接口数。

本清单通过读取 Git 快照和 Python AST 生成，没有导入或启动后端，没有连接数据库。本文件不含移动端实现代码。

“本文件路由路径”仅拼接本文件直接声明的 APIRouter prefix，未拼接 application 的全局 API 前缀或跨文件嵌套路由前缀。例如 OCR 子模块仍需要沿 include_router 树补全路径。嵌套工厂中的路由也列在内；是否实际挂载、角色依赖、输入输出以及动态注册接口，最终以应用装配和运行环境 OpenAPI 验证为准。服务辅助接口不必各自对应独立页面。

本清单用于防止遗漏导入、导出、历史、状态操作、任务资源和系统管理接口；与[业务重难点与全功能展示方案](Android业务重难点与全功能展示方案.md)中的 F01—F40 对照维护。

**backend/app/application.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 608 | GET | `/` | `read_root` | `app` |
| 612 | GET | `/health` | `health_check` | `app` |

**backend/app/modules/ai_dispatch/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 27 | GET | `/system/ai-servers` | `get_ai_servers` | `router` |
| 33 | GET | `/system/ai-servers/status` | `get_ai_server_status` | `router` |
| 39 | POST | `/system/ai-servers` | `create_ai_server` | `router` |
| 48 | PUT | `/system/ai-servers/{server_id}` | `update_ai_server` | `router` |
| 60 | DELETE | `/system/ai-servers/{server_id}` | `delete_ai_server` | `router` |
| 72 | POST | `/system/ai-servers/{server_id}/test` | `test_ai_server` | `router` |
| 87 | GET | `/system/ai-stats` | `get_ai_stats` | `router` |

**backend/app/modules/announcements/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 21 | GET | `/announcements` | `read_announcements` | `router` |
| 32 | GET | `/announcements/manage` | `read_announcements_for_management` | `router` |
| 43 | POST | `/announcements` | `add_announcement` | `router` |
| 52 | PUT | `/announcements/{announcement_id}` | `edit_announcement` | `router` |
| 62 | POST | `/announcements/{announcement_id}/publish` | `publish_announcement_record` | `router` |
| 71 | POST | `/announcements/{announcement_id}/withdraw` | `withdraw_announcement_record` | `router` |
| 80 | DELETE | `/announcements/{announcement_id}` | `remove_announcement` | `router` |

**backend/app/modules/assistant/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 128 | POST | `/assistant/turns` | `post_assistant_turn` | `router` |
| 149 | POST | `/assistant/actions` | `post_assistant_action` | `router` |
| 158 | POST | `/assistant/loan-contracts/summary` | `create_loan_contract_summary` | `router` |
| 183 | POST | `/assistant/loan-contracts/debt-candidates` | `search_loan_contract_debt_candidates` | `router` |
| 202 | POST | `/assistant/loan-contracts/archive` | `archive_existing_loan_contract` | `router` |
| 215 | POST | `/assistant/route` | `route_dialogue` | `router` |
| 243 | POST | `/assistant/query-summary` | `summarize_query_result` | `router` |
| 260 | POST | `/assistant/conversations` | `create_free_chat_conversation` | `router` |
| 277 | GET | `/assistant/conversations` | `list_free_chat_conversations` | `router` |
| 288 | GET | `/assistant/conversations/{conversation_id}` | `read_free_chat_conversation` | `router` |
| 300 | DELETE | `/assistant/conversations/{conversation_id}` | `delete_free_chat_conversation` | `router` |
| 313 | PUT | `/assistant/conversations/{conversation_id}/transcript` | `sync_ai_conversation_transcript` | `router` |
| 332 | POST | `/assistant/conversations/{conversation_id}/messages` | `post_free_chat_message` | `router` |
| 368 | POST | `/assistant/sessions` | `create_assistant_session` | `router` |
| 428 | GET | `/assistant/sessions/{session_id}` | `read_assistant_session` | `router` |
| 438 | POST | `/assistant/sessions/{session_id}/form-handoff` | `issue_assistant_form_handoff` | `router` |
| 471 | POST | `/assistant/sessions/{session_id}/missing-master-data` | `start_assistant_missing_master_data` | `router` |
| 497 | POST | `/assistant/sessions/{session_id}/resume-parent` | `resume_assistant_parent_session` | `router` |
| 509 | POST | `/assistant/sessions/{session_id}/messages` | `post_assistant_message` | `router` |
| 570 | POST | `/assistant/sessions/{session_id}/fields` | `post_assistant_fields` | `router` |
| 590 | POST | `/assistant/sessions/{session_id}/form-state` | `post_assistant_form_state` | `router` |
| 626 | POST | `/assistant/sessions/{session_id}/selections` | `post_assistant_selection` | `router` |
| 653 | POST | `/assistant/sessions/{session_id}/confirm` | `confirm_assistant_session` | `router` |
| 672 | POST | `/assistant/sessions/{session_id}/debt-memo/form-state` | `post_assistant_debt_memo_form_state` | `router` |
| 691 | POST | `/assistant/sessions/{session_id}/debt-memo/form-saved` | `post_assistant_debt_memo_form_saved` | `router` |
| 710 | POST | `/assistant/sessions/{session_id}/project/form-state` | `post_assistant_project_form_state` | `router` |
| 728 | POST | `/assistant/sessions/{session_id}/project/form-saved` | `post_assistant_project_form_saved` | `router` |
| 746 | POST | `/assistant/sessions/{session_id}/debt-form-saved` | `post_assistant_debt_form_saved` | `router` |
| 781 | POST | `/assistant/sessions/{session_id}/debt-extraction/retry` | `post_assistant_debt_extraction_retry` | `router` |
| 816 | POST | `/assistant/sessions/{session_id}/cancel` | `cancel_assistant_session` | `router` |

**backend/app/modules/assistant/debt_prefill_api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 42 | POST | `/ocr/root-debt-prefill` | `post_ocr_root_debt_prefill` | `router` |
| 88 | POST | `/ocr/modules/{module_id}/debt-prefill` | `post_ocr_module_debt_prefill` | `router` |
| 124 | POST | `/ocr/documents/{document_id}/analyses/{analysis_id}/controlled-company-prefill` | `post_ocr_controlled_company_prefill` | `router` |
| 150 | POST | `/ocr/documents/base-materials/entry` | `post_ocr_base_material_entry` | `router` |
| 171 | POST | `/ocr/documents/base-materials/entry-jobs` | `create_ocr_base_material_entry_job` | `router` |
| 194 | GET | `/ocr/documents/base-materials/entry-jobs/active` | `get_active_ocr_base_material_entry_job` | `router` |
| 208 | GET | `/ocr/documents/base-materials/entry-jobs/{job_id}` | `get_ocr_base_material_entry_job` | `router` |
| 227 | POST | `/ocr/documents/{document_id}/analyses/{analysis_id}/real-estate-prefill` | `post_ocr_real_estate_prefill` | `router` |

**backend/app/modules/audit/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 15 | GET | `/audit/logs` | `read_operation_logs` | `router` |
| 39 | GET | `/audit/logs/{log_id}` | `read_operation_log_detail` | `router` |

**backend/app/modules/auth/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 65 | GET | `/auth/captcha` | `issue_captcha` | `router` |
| 71 | POST | `/auth/login` | `login` | `router` |
| 121 | POST | `/auth/logout` | `logout` | `router` |
| 133 | POST | `/auth/activity` | `refresh_activity` | `router` |
| 160 | GET | `/auth/me` | `get_me` | `router` |
| 177 | PUT | `/auth/me/password` | `change_my_password` | `router` |
| 187 | GET | `/auth/users` | `read_regular_users` | `router` |
| 198 | POST | `/auth/users` | `create_regular_user` | `router` |
| 208 | PUT | `/auth/users/{user_id}` | `update_regular_user` | `router` |
| 219 | POST | `/auth/users/{user_id}/deactivate` | `deactivate_user` | `router` |
| 233 | POST | `/auth/users/{user_id}/activate` | `activate_user` | `router` |
| 238 | DELETE | `/auth/users/{user_id}` | `delete_user` | `router` |
| 243 | POST | `/auth/user-admin/transfer` | `transfer_user_admin` | `router` |

**backend/app/modules/auth/permission_api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 48 | GET | `/system/permissions` | `read_permission_configuration` | `router` |
| 61 | PUT | `/system/permissions/{role_code}` | `save_role_permissions` | `router` |

**backend/app/modules/backup/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 91 | GET | `/system/backups` | `read_backups` | `router` |
| 103 | GET | `/system/backups/schedule` | `read_backup_schedule` | `router` |
| 112 | PUT | `/system/backups/schedule` | `update_backup_schedule` | `router` |
| 122 | GET | `/system/backups/storage` | `read_backup_storage` | `router` |
| 131 | PUT | `/system/backups/storage` | `update_backup_storage` | `router` |
| 142 | POST | `/system/backups/manual` | `start_manual_backup` | `router` |
| 154 | GET | `/system/backups/{backup_id}` | `read_backup` | `router` |
| 164 | POST | `/system/backups/{backup_id}/cancel` | `cancel_backup` | `router` |
| 174 | DELETE | `/system/backups/{backup_id}` | `delete_backup` | `router` |
| 185 | GET | `/system/backups/{backup_id}/download` | `download_backup` | `router` |
| 203 | POST | `/system/restore-uploads` | `start_restore_upload` | `router` |
| 215 | PUT | `/system/restore-uploads/{upload_id}/parts/{part}` | `upload_restore_part` | `router` |
| 258 | GET | `/system/restore-uploads/{upload_id}` | `read_restore_upload` | `router` |
| 268 | POST | `/system/restore-uploads/{upload_id}/complete` | `complete_restore_upload` | `router` |
| 291 | POST | `/system/restores/precheck` | `precheck_restore` | `router` |
| 352 | POST | `/system/restores` | `start_restore` | `router` |
| 414 | POST | `/system/restores/cancel` | `cancel_restore` | `router` |
| 435 | GET | `/system/restores/status` | `restore_status` | `router` |
| 436 | GET | `/system/maintenance-status` | `restore_status` | `router` |
| 442 | POST | `/system/restores/release-maintenance` | `release_restore_maintenance` | `router` |

**backend/app/modules/credit_statistics/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 48 | GET | `/guarantee/credit-statistics` | `read_credit_statistics` | `router` |
| 66 | GET | `/guarantee/credit-statistics/bank-options` | `read_credit_statistics_bank_options` | `router` |
| 78 | GET | `/guarantee/credit-statistics/export` | `export_credit_statistics` | `router` |
| 112 | GET | `/guarantee/credit-statistics/manual-credit-import-template` | `download_manual_credit_import_template` | `router` |
| 132 | POST | `/guarantee/credit-statistics/manual-credit-import/preview` | `preview_manual_credit_import_file` | `router` |
| 153 | POST | `/guarantee/credit-statistics/manual-credit-import/confirm` | `confirm_manual_credit_import` | `router` |
| 165 | PUT | `/guarantee/manual-credit-limits/{canonical_creditor_org_id}` | `put_manual_credit` | `router` |
| 183 | PUT | `/guarantee/credit-statistics/bank-mappings/{creditor_org_id}` | `put_credit_statistics_bank_mapping` | `router` |
| 201 | DELETE | `/guarantee/manual-credit-limits/{canonical_creditor_org_id}` | `remove_manual_credit` | `router` |

**backend/app/modules/dataentry/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 77 | GET | `/data-entry/finance/bank-accounts` | `get_bank_accounts` | `router` |
| 169 | PUT | `/data-entry/finance/bank-accounts` | `save_bank_accounts` | `router` |
| 199 | POST | `/data-entry/finance/bank-routing-resolutions` | `resolve_bank_routing_number` | `router` |
| 211 | POST | `/data-entry/finance/bank-accounts/own/company-resolutions` | `resolve_own_company_name` | `router` |
| 223 | PUT | `/data-entry/finance/bank-accounts/{record_id}` | `update_bank_accounts` | `router` |
| 260 | POST | `/data-entry/finance/bank-accounts/own/check` | `check_own_company_bank_account_record` | `router` |
| 272 | POST | `/data-entry/finance/bank-accounts/own` | `create_own_company_bank_account_record` | `router` |
| 289 | POST | `/data-entry/finance/bank-accounts/repayment` | `create_repayment_bank_account_record` | `router` |
| 316 | GET | `/data-entry/finance/bank-accounts/{record_id}/repayment-history` | `get_repayment_account_history` | `router` |
| 328 | PUT | `/data-entry/finance/bank-accounts/{record_id}/repayment-profile` | `update_repayment_profile` | `router` |
| 346 | DELETE | `/data-entry/finance/bank-accounts/{record_id}/{account_type}` | `delete_bank_account_entry` | `router` |
| 388 | GET | `/data-entry/finance/invoice` | `get_invoice` | `router` |
| 483 | PUT | `/data-entry/finance/invoice` | `save_invoice` | `router` |
| 512 | PUT | `/data-entry/finance/invoice/{invoice_id}` | `update_invoice` | `router` |
| 544 | GET | `/data-entry/finance/invoice/{invoice_id}` | `get_invoice_detail` | `router` |
| 556 | POST | `/data-entry/finance/invoice/{invoice_id}/usage-events` | `create_invoice_usage_event` | `router` |
| 574 | DELETE | `/data-entry/finance/invoice/{invoice_id}` | `delete_invoice` | `router` |

**backend/app/modules/dataentry/asset_api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 76 | GET | `/data-entry/assets/real-estates` | `get_asset_real_estate_list` | `router` |
| 94 | GET | `/data-entry/assets/real-estates/options` | `get_asset_real_estate_options` | `router` |
| 106 | GET | `/data-entry/assets/real-estates/{real_estate_id}` | `get_asset_real_estate_record` | `router` |
| 115 | POST | `/data-entry/assets/real-estates` | `create_asset_real_estate_record` | `router` |
| 151 | PUT | `/data-entry/assets/real-estates/{real_estate_id}` | `update_asset_real_estate_record` | `router` |
| 186 | PATCH | `/data-entry/assets/real-estates/{real_estate_id}/status` | `update_asset_real_estate_status` | `router` |
| 201 | DELETE | `/data-entry/assets/real-estates/{real_estate_id}` | `delete_asset_real_estate_record` | `router` |
| 238 | POST | `/data-entry/assets/real-estates/{real_estate_id}/attachments` | `upload_asset_real_estate_attachment` | `router` |
| 271 | DELETE | `/data-entry/assets/real-estates/{real_estate_id}/attachments/{attachment_id}` | `delete_asset_real_estate_attachment_record` | `router` |
| 289 | GET | `/data-entry/assets/controlled-companies` | `get_asset_controlled_company_list` | `router` |
| 307 | POST | `/data-entry/assets/controlled-companies` | `create_asset_controlled_company_record` | `router` |
| 346 | GET | `/data-entry/assets/controlled-companies/{company_id}` | `get_asset_controlled_company_record` | `router` |
| 358 | PUT | `/data-entry/assets/controlled-companies/{company_id}` | `update_asset_controlled_company_record` | `router` |
| 396 | PATCH | `/data-entry/assets/controlled-companies/{company_id}/status` | `update_asset_controlled_company_status` | `router` |
| 414 | DELETE | `/data-entry/assets/controlled-companies/{company_id}` | `delete_asset_controlled_company_record` | `router` |
| 451 | GET | `/data-entry/assets/enterprise-groups` | `get_enterprise_group_list` | `router` |
| 465 | POST | `/data-entry/assets/enterprise-groups` | `create_enterprise_group_record` | `router` |
| 478 | PUT | `/data-entry/assets/enterprise-groups/{group_id}` | `update_enterprise_group_record` | `router` |
| 488 | PATCH | `/data-entry/assets/enterprise-groups/{group_id}/status` | `update_enterprise_group_status_record` | `router` |
| 501 | DELETE | `/data-entry/assets/enterprise-groups/{group_id}` | `delete_enterprise_group_record` | `router` |
| 519 | GET | `/data-entry/assets/scope-configurations` | `get_group_scope_configuration_list` | `router` |
| 546 | POST | `/data-entry/assets/scope-configurations/change` | `change_group_scope_configuration_record` | `router` |
| 558 | POST | `/data-entry/assets/scope-configurations/batch-change` | `batch_change_group_scope_configuration_records` | `router` |
| 570 | GET | `/data-entry/assets/scope-configurations/{company_id}/history` | `get_group_scope_configuration_history` | `router` |

**backend/app/modules/dataentry/engineering_api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 28 | GET | `/data-entry/engineering/projects` | `get_engineering_project_list` | `router` |
| 46 | GET | `/data-entry/engineering/projects/{project_id}` | `get_engineering_project_record` | `router` |
| 55 | POST | `/data-entry/engineering/projects` | `create_engineering_project_record` | `router` |
| 68 | PUT | `/data-entry/engineering/projects/{project_id}` | `update_engineering_project_record` | `router` |
| 78 | PATCH | `/data-entry/engineering/projects/{project_id}/status` | `update_engineering_project_status` | `router` |
| 93 | DELETE | `/data-entry/engineering/projects/{project_id}` | `delete_engineering_project_record` | `router` |

**backend/app/modules/dataentry/table_api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 14 | POST | `/data-entry/table-query/{surface_key}` | `query_cross_department_table` | `router` |

**backend/app/modules/debt/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 151 | GET | `/debts/supplementary-fee-names` | `get_supplementary_fee_names` | `router` |
| 177 | POST | `/debts/supplementary-fee-names` | `create_supplementary_fee_name` | `router` |
| 205 | GET | `/debts/pending` | `get_pending_debt_list` | `router` |
| 245 | PATCH | `/debts/pending/{pending_id}` | `update_pending_debt` | `router` |
| 279 | GET | `/debts/pending/{pending_id}` | `get_pending_debt_detail` | `router` |
| 305 | DELETE | `/debts/pending/{pending_id}` | `delete_pending_debt` | `router` |
| 337 | POST | `/debts/pending/{pending_id}/convert` | `convert_pending_debt` | `router` |
| 448 | GET | `/debts` | `get_debt_list` | `router` |
| 507 | POST | `/debts/query` | `query_debt_list` | `router` |
| 516 | GET | `/debts/outstanding-summary` | `get_debt_outstanding_summary` | `router` |
| 569 | GET | `/debts/fund-statistics` | `get_debt_fund_statistics` | `router` |
| 631 | GET | `/debts/disbursement-summary` | `get_debt_disbursement_summary` | `router` |
| 657 | GET | `/debts/comprehensive-cost` | `get_comprehensive_cost_query` | `router` |
| 739 | GET | `/debts/remaining-fee-allowance` | `get_remaining_fee_allowance` | `router` |
| 761 | POST | `/debts/floating-rate-calculation` | `calculate_floating_rate_record` | `router` |
| 775 | POST | `/debts/automatic-project/resolve` | `resolve_debt_automatic_project` | `router` |
| 793 | POST | `/debts/automatic-project/cost-approval` | `create_debt_automatic_project_cost_approval` | `router` |
| 812 | POST | `/debts` | `create_debt_record` | `router` |
| 836 | POST | `/debts/contract-attachment-uploads` | `upload_debt_contract_attachments` | `router` |
| 924 | GET | `/debts/contract-attachments/{attachment_file_id}/source-file` | `download_debt_contract_attachment` | `router` |
| 943 | POST | `/debts/interest-plan-preview` | `preview_debt_interest_plan` | `router` |
| 956 | POST | `/debts/bill-maturity-preview` | `preview_bill_maturity_amounts` | `router` |
| 969 | POST | `/debts/repayment-plan-import` | `import_repayment_plan_workbook` | `router` |
| 991 | GET | `/debts/repayment-plan-template` | `download_repayment_plan_template` | `router` |
| 1009 | POST | `/debts/repayment-plan-export` | `export_repayment_plan` | `router` |
| 1033 | GET | `/debts/{debt_id}/irr-calculator-export` | `export_debt_irr_calculator` | `router` |
| 1099 | GET | `/debts/projects/{project_id}/irr-calculator-export` | `export_project_irr_calculator` | `router` |
| 1200 | POST | `/debts/repayment-plan-custom-export` | `export_custom_repayment_plan` | `router` |
| 1223 | GET | `/debts/{debt_id}` | `get_debt_detail` | `router` |
| 1232 | GET | `/debts/{debt_id}/history` | `get_debt_history` | `router` |
| 1253 | GET | `/debts/{debt_id}/history/{history_id}` | `get_debt_history_detail` | `router` |
| 1275 | PUT | `/debts/{debt_id}` | `update_debt_record` | `router` |
| 1301 | POST | `/debts/{debt_id}/principal-balance-at-date` | `get_principal_balance_at_date` | `router` |
| 1313 | DELETE | `/debts/{debt_id}` | `delete_debt_record` | `router` |
| 1329 | GET | `/cashflows/upcoming` | `list_upcoming_payments` | `cashflow_router` |
| 1338 | GET | `/cashflows/upcoming/summary` | `get_upcoming_payments_summary` | `cashflow_router` |
| 1347 | GET | `/cashflows/{event_id}/splits` | `get_cashflow_splits` | `cashflow_router` |
| 1356 | PUT | `/cashflows/{event_id}/splits/{creditor_org_id}/status` | `update_cashflow_split_status` | `cashflow_router` |

**backend/app/modules/debt/project_api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 21 | GET | `/projects/{project_id}/debt-overview` | `get_project_debt_overview_record` | `router` |
| 33 | GET | `/projects/{project_id}/tree` | `get_project_tree` | `router` |

**backend/app/modules/debt_memo/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 91 | POST | `/debt-memo/project-options/query` | `query_debt_memo_project_options` | `router` |
| 214 | GET | `/debt-memo/plans` | `get_plan_memo_list` | `router` |
| 263 | POST | `/debt-memo/plans/query` | `query_plan_memo_table` | `router` |
| 314 | GET | `/debt-memo/plans/{plan_id}` | `get_plan_memo_detail` | `router` |
| 324 | POST | `/debt-memo/plans` | `add_plan_memo` | `router` |
| 359 | PUT | `/debt-memo/plans/{plan_id}` | `edit_plan_memo` | `router` |
| 369 | DELETE | `/debt-memo/plans/{plan_id}` | `remove_plan_memo` | `router` |
| 378 | POST | `/debt-memo/plans/{plan_id}/convert-to-actual` | `convert_plan_memo_to_actual` | `router` |
| 398 | GET | `/debt-memo/actuals` | `get_actual_memo_list` | `router` |
| 447 | POST | `/debt-memo/actuals/query` | `query_actual_memo_table` | `router` |
| 499 | GET | `/debt-memo/actuals/{actual_id}` | `get_actual_memo_detail` | `router` |
| 508 | POST | `/debt-memo/actuals` | `add_actual_memo` | `router` |
| 544 | PUT | `/debt-memo/actuals/{actual_id}` | `edit_actual_memo` | `router` |
| 555 | DELETE | `/debt-memo/actuals/{actual_id}` | `remove_actual_memo` | `router` |
| 564 | POST | `/debt-memo/actuals/{actual_id}/link-debt` | `link_actual_memo_to_debt` | `router` |
| 576 | GET | `/debt-memo/actuals/{actual_id}/debt-prefill` | `get_actual_memo_debt_prefill` | `router` |
| 591 | GET | `/debt-memo/overview-disbursement-summary` | `get_overview_disbursement_summary_api` | `router` |
| 619 | GET | `/debt-memo/overview-daily-disbursements` | `get_overview_daily_disbursements_api` | `router` |
| 645 | GET | `/debt-memo/overview-daily-disbursements/{kind}` | `get_overview_daily_disbursement_list_api` | `router` |
| 680 | GET | `/debt-memo/summary` | `get_debt_memo_summary` | `router` |
| 689 | GET | `/debt-memo/dashboard` | `get_debt_memo_dashboard` | `router` |
| 698 | GET | `/debt-memo/activity` | `get_debt_memo_activity` | `router` |

**backend/app/modules/documents/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 104 | POST | `/documents/repayment-notices/resolve` | `resolve_notice` | `router` |
| 116 | POST | `/documents/repayment-payments` | `create_payment_document` | `router` |
| 133 | GET | `/documents/repayment-payments` | `list_payment_documents` | `router` |
| 162 | GET | `/documents/repayment-payments/counts` | `get_payment_document_counts` | `router` |
| 176 | GET | `/documents/repayment-payments/workflow-counts` | `get_payment_document_workflow_counts` | `router` |
| 187 | GET | `/documents/repayment-payments/auto-generation/status` | `get_payment_document_auto_generation_status` | `router` |
| 198 | POST | `/documents/repayment-payments/auto-generation/run-now` | `run_payment_document_auto_generation_now` | `router` |
| 209 | GET | `/documents/repayment-payments/duplicate-check` | `check_payment_document_duplicate` | `router` |
| 233 | POST | `/documents/repayment-payments/{document_id}/print` | `record_payment_document_print` | `router` |
| 251 | GET | `/documents/repayment-payments/{document_id}/excel` | `download_payment_document_excel` | `router` |
| 280 | POST | `/documents/repayment-payments/{document_id}/receipt-verifications` | `verify_payment_document_receipts` | `router` |
| 318 | GET | `/documents/repayment-payments/{document_id}` | `get_payment_document` | `router` |
| 334 | POST | `/documents/repayment-payments/{document_id}/void` | `void_payment_document` | `router` |
| 352 | DELETE | `/documents/repayment-payments/{document_id}` | `delete_payment_document` | `router` |
| 373 | POST | `/documents/fee-applications/from-debts` | `create_fee_applications_from_debts` | `router` |
| 390 | GET | `/documents/fee-applications` | `list_fee_applications` | `router` |
| 412 | GET | `/documents/fee-applications/counts` | `get_fee_application_counts` | `router` |
| 423 | GET | `/documents/fee-applications/{document_id}` | `get_fee_application` | `router` |
| 439 | PUT | `/documents/fee-applications/{document_id}` | `update_fee_application` | `router` |
| 457 | GET | `/documents/fee-applications/{document_id}/excel` | `download_fee_application_excel` | `router` |
| 484 | POST | `/documents/fee-applications/{document_id}/void` | `void_fee_application` | `router` |
| 507 | GET | `/documents/cost-approvals/project-options` | `get_cost_approval_project_options` | `router` |
| 527 | GET | `/documents/cost-approvals/defaults` | `get_cost_approval_defaults_endpoint` | `router` |
| 545 | POST | `/documents/cost-approvals` | `create_cost_approval` | `router` |
| 562 | GET | `/documents/cost-approvals` | `list_cost_approvals` | `router` |
| 584 | GET | `/documents/cost-approvals/counts` | `get_cost_approval_counts` | `router` |
| 598 | GET | `/documents/cost-approvals/{document_id}` | `get_cost_approval` | `router` |
| 614 | GET | `/documents/cost-approvals/{document_id}/excel` | `download_cost_approval_excel` | `router` |
| 642 | POST | `/documents/cost-approvals/{document_id}/regenerate` | `regenerate_cost_approval` | `router` |
| 660 | GET | `/documents/cost-approvals/{document_id}/history` | `get_cost_approval_history` | `router` |
| 680 | GET | `/documents/cost-approvals/{document_id}/history/{history_id}` | `get_cost_approval_history_detail` | `router` |
| 698 | GET | `/documents/cost-approvals/{document_id}/versions/{version_no}` | `get_cost_approval_version` | `router` |
| 716 | PUT | `/documents/cost-approvals/{document_id}` | `update_cost_approval` | `router` |
| 734 | POST | `/documents/cost-approvals/{document_id}/void` | `void_cost_approval` | `router` |
| 752 | DELETE | `/documents/cost-approvals/{document_id}` | `delete_cost_approval` | `router` |

**backend/app/modules/export/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 47 | POST | `/exports/debts` | `export_debts` | `router` |
| 69 | POST | `/exports/cashflows` | `export_cashflows` | `router` |
| 91 | POST | `/exports/projects/summary` | `export_project_summary` | `router` |
| 109 | POST | `/exports/projects/ledger` | `export_project_ledger` | `router` |
| 140 | POST | `/exports/projects/cost-process` | `export_project_cost_process` | `router` |
| 170 | POST | `/exports/jobs/debts` | `create_debt_export_job` | `router` |
| 190 | POST | `/exports/jobs/cashflows` | `create_cashflow_export_job` | `router` |
| 210 | POST | `/exports/jobs/projects/summary` | `create_project_summary_export_job` | `router` |
| 230 | POST | `/exports/jobs/projects/ledger` | `create_project_ledger_export_job` | `router` |
| 250 | POST | `/exports/jobs/projects/cost-process` | `create_project_cost_process_export_job` | `router` |
| 270 | GET | `/exports/jobs/{job_id}` | `get_export_job` | `router` |
| 279 | DELETE | `/exports/jobs/{job_id}` | `delete_export_job` | `router` |
| 288 | GET | `/exports/jobs/{job_id}/download` | `download_export_job` | `router` |

**backend/app/modules/file_upload/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 22 | POST | `/file-uploads/approval-documents` | `upload_approval_document` | `router` |
| 39 | POST | `/file-uploads/repayment-subject-attachments` | `upload_repayment_subject_attachment` | `router` |
| 56 | GET | `/file-uploads/{category}/{filename}` | `download_uploaded_file` | `router` |

**backend/app/modules/guarantee/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 58 | GET | `/guarantee/navigation` | `get_guarantee_navigation` | `router` |
| 102 | GET | `/guarantee/records` | `list_guarantee_records` | `router` |
| 128 | GET | `/guarantee/statistics` | `get_statistics` | `router` |
| 136 | GET | `/guarantee/detail-statistics` | `get_detail_statistics` | `router` |
| 159 | GET | `/guarantee/records/{record_id}` | `get_guarantee_record` | `router` |
| 168 | POST | `/guarantee/records` | `create_guarantee_record` | `router` |
| 182 | PUT | `/guarantee/records/{record_id}` | `update_guarantee_record` | `router` |
| 203 | PATCH | `/guarantee/records/{record_id}/balance` | `patch_guarantee_balance` | `router` |
| 220 | PATCH | `/guarantee/records/{record_id}/status` | `patch_guarantee_status` | `router` |
| 237 | DELETE | `/guarantee/records/{record_id}` | `delete_guarantee_record` | `router` |
| 253 | GET | `/guarantee/attachments/{attachment_id}/source-file` | `download_guarantee_attachment` | `router` |

**backend/app/modules/guarantee/ocr_prefill_api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 27 | POST | `/ocr/guarantee-prefill` | `prefill_guarantee_application` | `router` |

**backend/app/modules/master_data/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 84 | GET | `/master-data/debtors` | `get_debtor_list` | `router` |
| 94 | GET | `/master-data/debtors/tree` | `get_debtor_tree` | `router` |
| 103 | GET | `/master-data/debtors/search` | `search_debtor_list` | `router` |
| 121 | POST | `/master-data/debtors` | `create_debtor_record` | `router` |
| 130 | PUT | `/master-data/debtors/{debtor_id}` | `update_debtor_record` | `router` |
| 140 | GET | `/master-data/creditor-orgs` | `get_creditor_org_list` | `router` |
| 158 | GET | `/master-data/creditor-orgs/search` | `search_creditor_org_list` | `router` |
| 183 | POST | `/master-data/creditor-orgs/table-query` | `query_creditor_org_table_page` | `router` |
| 192 | POST | `/master-data/creditor-orgs/resolve` | `resolve_creditor_org_names` | `router` |
| 203 | GET | `/master-data/creditor-orgs/{org_id}` | `get_creditor_org_record` | `router` |
| 212 | POST | `/master-data/creditor-orgs` | `create_creditor_org_record` | `router` |
| 221 | PUT | `/master-data/creditor-orgs/{org_id}` | `update_creditor_org_record` | `router` |
| 231 | GET | `/master-data/creditor-org-seed-config` | `get_creditor_org_seed_config_record` | `router` |
| 239 | PUT | `/master-data/creditor-org-seed-config` | `update_creditor_org_seed_config_record` | `router` |
| 248 | GET | `/master-data/guarantee-companies` | `get_guarantee_company_list` | `router` |
| 266 | GET | `/master-data/guarantee-companies/{company_id}` | `get_guarantee_company_record` | `router` |
| 275 | POST | `/master-data/guarantee-companies` | `create_guarantee_company_record` | `router` |
| 284 | PUT | `/master-data/guarantee-companies/{company_id}` | `update_guarantee_company_record` | `router` |
| 294 | GET | `/master-data/financing-products/standard` | `get_standard_financing_product_list` | `router` |
| 304 | POST | `/master-data/financing-products/standard` | `create_standard_financing_product_record` | `router` |
| 317 | PUT | `/master-data/financing-products/standard/{product_id}` | `update_standard_financing_product_record` | `router` |
| 327 | GET | `/master-data/financing-products/custom` | `get_custom_financing_product_list` | `router` |
| 337 | POST | `/master-data/financing-products/custom` | `create_custom_financing_product_record` | `router` |
| 350 | PUT | `/master-data/financing-products/custom/{product_id}` | `update_custom_financing_product_record` | `router` |
| 360 | GET | `/master-data/real-estates` | `get_real_estate_list` | `router` |
| 370 | GET | `/master-data/real-estates/{real_estate_id}` | `get_real_estate_record` | `router` |
| 379 | POST | `/master-data/real-estates` | `create_real_estate_record` | `router` |
| 388 | PUT | `/master-data/real-estates/{real_estate_id}` | `update_real_estate_record` | `router` |
| 398 | DELETE | `/master-data/real-estates/{real_estate_id}` | `delete_real_estate_record` | `router` |
| 407 | GET | `/master-data/financing-types` | `get_financing_type_list` | `router` |
| 417 | POST | `/master-data/financing-types` | `create_financing_type_record` | `router` |
| 426 | PUT | `/master-data/financing-types/{type_id}` | `update_financing_type_record` | `router` |

**backend/app/modules/ocr/api/automation.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 56 | POST | `/automation-jobs` | `start_automation_job` | `router` |
| 160 | POST | `/documents/{document_id}/reprocess-jobs` | `start_document_reprocess_job` | `router` |
| 213 | GET | `/automation-jobs/{job_id}` | `get_automation_job` | `router` |
| 242 | DELETE | `/automation-jobs/{job_id}` | `cancel_automation_job` | `router` |

**backend/app/modules/ocr/api/catalog.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 30 | GET | `/extraction-config` | `get_contract_extraction_config` | `router` |
| 43 | GET | `/extraction-config/export` | `export_contract_extraction_config` | `router` |
| 56 | GET | `/extraction-config/initial-seed` | `get_contract_extraction_initial_seed` | `router` |
| 68 | PUT | `/extraction-config` | `update_contract_extraction_config` | `router` |
| 82 | POST | `/extraction-config/import` | `import_contract_extraction_config` | `router` |
| 96 | POST | `/extraction-config/import-initial-seed` | `import_contract_extraction_initial_seed` | `router` |

**backend/app/modules/ocr/api/debt_init.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 68 | GET | `/debt-init/template` | `download_debt_init_template` | `router` |
| 90 | POST | `/debt-init/preview` | `preview_debt_init` | `router` |
| 108 | POST | `/debt-init/records` | `create_debt_init_records` | `router` |
| 119 | POST | `/debt-init/records/upload` | `create_debt_init_records_with_source` | `router` |
| 148 | GET | `/debt-init/batches` | `read_debt_init_batches` | `router` |
| 169 | POST | `/debt-init/batches/clear` | `clear_debt_init_batches` | `router` |
| 188 | GET | `/debt-init/batches/{batch_id}/source` | `download_debt_init_batch_source` | `router` |
| 209 | GET | `/debt-init/records` | `read_debt_init_records` | `router` |
| 230 | POST | `/debt-init/records/clear` | `clear_debt_init_records` | `router` |
| 243 | PATCH | `/debt-init/records/{record_id}` | `patch_debt_init_record` | `router` |
| 255 | POST | `/debt-init/records/{record_id}/transfer` | `transfer_debt_init_record` | `router` |
| 266 | POST | `/debt-init/records/transfer-pending` | `transfer_debt_init_records_to_pending` | `router` |
| 277 | POST | `/debt-init/records/transfer-jobs` | `create_debt_init_transfer_job` | `router` |
| 295 | GET | `/debt-init/records/transfer-jobs/active` | `read_active_debt_init_transfer_job` | `router` |
| 305 | GET | `/debt-init/records/transfer-jobs/{job_id}` | `read_debt_init_transfer_job` | `router` |
| 316 | DELETE | `/debt-init/records/transfer-jobs/{job_id}` | `cancel_debt_init_transfer_job` | `router` |

**backend/app/modules/ocr/api/docs_sortout.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 53 | POST | `/document-sortout-jobs` | `start_document_sortout_job` | `router` |
| 109 | GET | `/document-sortout-jobs/{job_id}` | `get_document_sortout_job` | `router` |
| 149 | GET | `/document-sortout-jobs/{job_id}/documents/{document_id}/diagnostics` | `get_document_sortout_debug_info` | `router` |
| 179 | DELETE | `/document-sortout-jobs/{job_id}` | `cancel_document_sortout_job` | `router` |

**backend/app/modules/ocr/api/documents.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 108 | GET | `/documents` | `get_ocr_documents` | `router` |
| 119 | GET | `/documents/base-materials/three-year-one-period` | `get_three_year_one_period_materials` | `router` |
| 148 | GET | `/documents/base-materials` | `get_company_base_materials` | `router` |
| 179 | GET | `/documents/base-material-candidates` | `get_base_material_candidates` | `router` |
| 249 | POST | `/documents/base-materials/association-preview` | `preview_base_material_associations` | `router` |
| 270 | POST | `/documents/base-materials/bind-batch` | `bind_base_material_groups` | `router` |
| 298 | POST | `/documents/base-materials/bind` | `bind_base_materials` | `router` |
| 359 | POST | `/documents/base-materials/unbind` | `unbind_base_materials` | `router` |
| 380 | POST | `/documents/word-upload` | `upload_word_document_api` | `router` |
| 429 | POST | `/documents/spreadsheet-upload` | `upload_spreadsheet_document_api` | `router` |
| 500 | GET | `/documents/{document_id}` | `get_ocr_document` | `router` |
| 524 | DELETE | `/documents/{document_id}` | `delete_ocr_document_api` | `router` |
| 535 | POST | `/documents/{document_id}/replace-with/{replacement_document_id}` | `replace_ocr_document_api` | `router` |
| 556 | PUT | `/documents/{document_id}` | `update_ocr_document` | `router` |
| 579 | GET | `/documents/{document_id}/analyses` | `get_ocr_document_analyses` | `router` |
| 593 | POST | `/documents/{document_id}/analyses` | `create_ocr_document_analysis` | `router` |
| 617 | GET | `/documents/{document_id}/analyses/{analysis_id}` | `get_ocr_document_analysis` | `router` |
| 637 | GET | `/documents/{document_id}/analyses/{analysis_id}/debug` | `get_ocr_document_analysis_debug` | `router` |
| 660 | DELETE | `/documents/{document_id}/analyses/{analysis_id}` | `cancel_ocr_document_analysis` | `router` |
| 680 | PATCH | `/documents/{document_id}/analyses/{analysis_id}/fields/{field_id}` | `patch_ocr_extracted_field` | `router` |
| 704 | PATCH | `/documents/{document_id}/analyses/{analysis_id}/invoice-lines/{line_item_id}` | `patch_ocr_invoice_line_item` | `router` |
| 710 | GET | `/documents/{document_id}/pages/{page_no}/image` | `get_ocr_document_page_image` | `router` |
| 728 | GET | `/documents/{document_id}/source-file` | `get_ocr_document_source_file` | `router` |
| 749 | POST | `/documents/review-job` | `save_ocr_review_job_document_api` | `router` |
| 803 | POST | `/documents` | `save_ocr_document_api` | `router` |

**backend/app/modules/ocr/api/health.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 42 | GET | `/capabilities` | `get_ocr_capabilities` | `router` |
| 55 | GET | `/service-ready` | `get_ocr_service_ready` | `router` |
| 103 | GET | `/health` | `ocr_health_check` | `router` |

**backend/app/modules/ocr/api/material_list.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 63 | POST | `/material-list/analyze` | `analyze_material_list` | `router` |
| 120 | POST | `/material-list/package` | `package_materials` | `router` |

**backend/app/modules/ocr/api/modules.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 40 | POST | `/modules/root-debt-import-preview` | `post_ocr_root_debt_preview` | `router` |
| 59 | GET | `/modules` | `get_ocr_modules` | `router` |
| 83 | GET | `/modules/owners` | `get_ocr_history_owners` | `router` |
| 96 | GET | `/modules/history-search` | `search_ocr_history` | `router` |
| 120 | GET | `/modules/{module_id}` | `get_ocr_module` | `router` |
| 138 | GET | `/modules/{module_id}/debt-import-preview` | `get_ocr_module_debt_preview` | `router` |
| 157 | PATCH | `/modules/{module_id}` | `rename_ocr_module_api` | `router` |
| 169 | PATCH | `/modules/{module_id}/parent` | `move_ocr_module_api` | `router` |
| 186 | PATCH | `/documents/{document_id}/module` | `move_ocr_document_api` | `router` |
| 210 | DELETE | `/modules/{module_id}` | `delete_ocr_module_api` | `router` |

**backend/app/modules/ocr/api/review.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 60 | GET | `/performance-metrics` | `get_ocr_performance_metrics` | `router` |

**backend/app/modules/ocr/api/review_jobs.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 58 | POST | `/review-jobs` | `start_ocr_review_job` | `router` |
| 149 | POST | `/review-jobs/image-bundles` | `start_ocr_image_bundle_job` | `router` |
| 199 | POST | `/review-jobs/{job_id}/start` | `start_staged_ocr_review_job` | `router` |

**backend/app/modules/ocr/api/review_resources.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 47 | DELETE | `/review-jobs/{job_id}` | `cancel_ocr_review_job` | `router` |
| 90 | DELETE | `/review-jobs/{job_id}/staged-resource` | `release_staged_ocr_review_job` | `router` |
| 111 | GET | `/review-jobs/{job_id}/source-file` | `get_ocr_review_job_source_file` | `router` |
| 163 | GET | `/review-jobs/{job_id}/source-pages/{page_index}` | `get_ocr_review_job_source_page` | `router` |
| 216 | GET | `/review-jobs/{job_id}/direct-preview` | `get_ocr_review_job_direct_preview` | `router` |
| 263 | GET | `/review-jobs/{job_id}/source-images/{image_index}` | `get_ocr_review_job_source_image` | `router` |
| 334 | GET | `/review-jobs/{job_id}` | `get_ocr_review_job` | `router` |
| 381 | GET | `/review-jobs/{job_id}/pages/{page_no}` | `get_ocr_review_job_page` | `router` |
| 429 | GET | `/review-jobs/{job_id}/pages/{page_no}/image` | `get_ocr_review_job_page_image` | `router` |
| 456 | GET | `/review-jobs/{job_id}/diagnostics` | `get_ocr_review_job_debug_info` | `router` |

**backend/app/modules/ocr/api/review_sync.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 29 | POST | `/process` | `process_ocr_file` | `router` |
| 55 | POST | `/review-process` | `process_ocr_vlm_file` | `router` |

**backend/app/modules/ocr/api/sheet_fill.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 62 | POST | `/sheet-fill/jobs` | `start_sheet_job` | `router` |
| 86 | GET | `/sheet-fill/jobs` | `read_sheet_jobs` | `router` |
| 117 | GET | `/sheet-fill/fields` | `read_sheet_fields` | `router` |
| 128 | GET | `/sheet-fill/fields/export` | `export_sheet_fields` | `router` |
| 143 | POST | `/sheet-fill/fields/import` | `import_sheet_fields` | `router` |
| 163 | PATCH | `/sheet-fill/fields/{field_key:path}` | `patch_sheet_field` | `router` |
| 183 | GET | `/sheet-fill/jobs/{job_id}` | `read_sheet_job` | `router` |
| 194 | GET | `/sheet-fill/active-job` | `read_active_sheet_job` | `router` |
| 204 | GET | `/sheet-fill/jobs/{job_id}/mappings` | `read_sheet_mappings` | `router` |
| 215 | POST | `/sheet-fill/jobs/{job_id}/mappings/confirm` | `confirm_sheet_mappings` | `router` |
| 231 | POST | `/sheet-fill/jobs/{job_id}/mappings/analyze` | `analyze_sheet_map` | `router` |
| 243 | GET | `/sheet-fill/jobs/{job_id}/preview` | `read_sheet_preview` | `router` |
| 254 | POST | `/sheet-fill/jobs/{job_id}/preview/confirm` | `confirm_sheet_preview` | `router` |
| 270 | POST | `/sheet-fill/jobs/{job_id}/retry` | `retry_job` | `router` |
| 284 | POST | `/sheet-fill/jobs/{job_id}/rerun` | `rerun_job` | `router` |
| 298 | GET | `/sheet-fill/jobs/{job_id}/file` | `download_sheet_file` | `router` |
| 333 | GET | `/sheet-fill/jobs/{job_id}/debug` | `download_sheet_debug` | `router` |

**backend/app/modules/ocr/api/sheet_rules.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 46 | GET | `/rule-operators` | `read_rule_operators` | `router` |
| 104 | GET | `/org-relations` | `read_org_relations` | `router` |
| 115 | POST | `/org-relations` | `create_org_relation` | `router` |
| 134 | PATCH | `/org-relations/{relation_id}` | `patch_org_relation` | `router` |
| 160 | GET | `/rules` | `read_sheet_rules` | `router` |
| 179 | POST | `/rules` | `create_sheet_rule` | `router` |
| 193 | POST | `/rules/{rule_key}/versions` | `clone_sheet_rule_version` | `router` |
| 207 | PATCH | `/rule-versions/{version_id}` | `patch_sheet_rule_version` | `router` |
| 223 | POST | `/rule-versions/{version_id}/validate` | `validate_sheet_rule_version` | `router` |
| 237 | POST | `/rule-versions/{version_id}/preview` | `preview_sheet_rule_version` | `router` |
| 252 | POST | `/rule-versions/{version_id}/publish` | `publish_sheet_rule_version` | `router` |
| 266 | GET | `/rules/export` | `export_sheet_rules` | `router` |
| 280 | POST | `/rules/import` | `import_sheet_rules` | `router` |

**backend/app/modules/overview/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 37 | GET | `/overview/funding-gap-config` | `read_funding_gap_config` | `router` |
| 70 | PUT | `/overview/funding-gap-config` | `save_funding_gap_config` | `router` |
| 82 | DELETE | `/overview/funding-gap-config/{config_id}` | `clear_funding_gap_config` | `router` |
| 101 | GET | `/overview/planned-financing-config` | `read_planned_financing_config` | `router` |
| 132 | PUT | `/overview/planned-financing-config` | `save_planned_financing_config` | `router` |

**backend/app/modules/project/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 205 | GET | `/projects/import-template` | `get_project_import_template` | `router` |
| 227 | POST | `/projects/import` | `import_project_records` | `router` |
| 318 | GET | `/projects` | `get_project_list` | `router` |
| 367 | POST | `/projects/query` | `query_project_list` | `router` |
| 376 | POST | `/projects/query-selection` | `query_project_selection_ids` | `router` |
| 417 | POST | `/projects` | `create_project_record` | `router` |
| 445 | GET | `/projects/code-preview` | `get_project_code_preview` | `router` |
| 454 | GET | `/projects/options` | `get_project_options` | `router` |
| 470 | GET | `/projects/financing-pipeline/summary` | `get_project_financing_pipeline_summary` | `router` |
| 491 | GET | `/projects/financing-pipeline/details` | `get_project_financing_pipeline_details` | `router` |
| 518 | GET | `/projects/engagements` | `get_project_engagement_list` | `router` |
| 538 | POST | `/projects/engagements` | `create_project_engagement_record` | `router` |
| 551 | GET | `/projects/engagements/{engagement_id}` | `get_project_engagement_detail` | `router` |
| 560 | PUT | `/projects/engagements/{engagement_id}` | `update_project_engagement_record` | `router` |
| 570 | POST | `/projects/engagements/{engagement_id}/status` | `change_project_engagement_status_record` | `router` |
| 583 | POST | `/projects/engagements/{engagement_id}/convert` | `convert_project_engagement_record` | `router` |
| 597 | GET | `/projects/{project_id}` | `get_project_detail` | `router` |
| 606 | PUT | `/projects/{project_id}` | `update_project_record` | `router` |
| 618 | PUT | `/projects/{project_id}/refinance` | `update_project_refinance_record` | `router` |
| 630 | POST | `/projects/{project_id}/engagement-status` | `update_project_engagement_status_record` | `router` |
| 642 | POST | `/projects/{project_id}/complete-funding` | `complete_project_funding_record` | `router` |
| 657 | POST | `/projects/{project_id}/abandon` | `abandon_project_record` | `router` |
| 672 | POST | `/projects/{project_id}/restore-abandonment` | `restore_abandoned_project_record` | `router` |
| 687 | DELETE | `/projects/{project_id}` | `delete_project_record` | `router` |
| 703 | POST | `/projects/{project_id}/restore` | `restore_project_record` | `router` |
| 721 | GET | `/projects/{project_id}/history` | `get_project_history` | `router` |
| 742 | GET | `/projects/{project_id}/history/{history_id}` | `get_project_history_detail` | `router` |
| 764 | GET | `/projects/{project_id}/creditors` | `get_project_creditors` | `router` |
| 773 | PUT | `/projects/{project_id}/creditors` | `replace_project_creditor_records` | `router` |

**backend/app/modules/query_center/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 78 | POST | `/query-center/reports/composed/preview` | `preview_composed_query_report` | `router` |
| 90 | POST | `/query-center/reports/composed/intelligence-jobs` | `create_composed_query_report_intelligence_job` | `router` |
| 107 | GET | `/query-center/reports/composed/intelligence-jobs/{job_id}` | `get_composed_query_report_intelligence_job` | `router` |
| 123 | POST | `/query-center/reports/composed/intelligence-jobs/{job_id}/retry` | `retry_composed_query_report_intelligence_job` | `router` |
| 140 | POST | `/query-center/reports/composed/intelligence` | `draft_composed_query_report_intelligence` | `router` |
| 161 | POST | `/query-center/reports/composed/export` | `export_composed_query_report` | `router` |
| 216 | POST | `/query-center/reports/financing-work/preview` | `preview_financing_work_report` | `router` |
| 228 | POST | `/query-center/reports/financing-work/export` | `export_financing_work_report` | `router` |
| 273 | POST | `/query-center/interpret` | `interpret_query_center_prompt` | `router` |
| 296 | POST | `/query-center/export` | `export_query_center_result` | `router` |
| 332 | GET | `/query-center/financing-structure` | `read_financing_structure` | `router` |
| 352 | GET | `/query-center/financing-total` | `read_financing_total` | `router` |
| 382 | GET | `/query-center/planned-financing-trial` | `read_planned_financing_trial` | `router` |
| 416 | GET | `/query-center/debt-structure` | `read_debt_structure` | `router` |
| 436 | GET | `/query-center/credit-statistics` | `read_enterprise_credit_statistics` | `router` |
| 456 | GET | `/query-center/group-scope` | `read_group_scope` | `router` |
| 470 | GET | `/query-center/guarantees/month-end` | `read_guarantees_at_month_end` | `router` |
| 490 | GET | `/query-center/debt-monthly` | `read_debt_monthly` | `router` |
| 522 | GET | `/query-center/group-overview` | `read_group_overview` | `router` |
| 556 | GET | `/query-center/debt-refinance` | `read_debt_refinance` | `router` |

**backend/app/modules/query_language/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 21 | POST | `/query/interpret` | `interpret_natural_language_query` | `router` |

**backend/app/modules/statistics/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 49 | GET | `/statistics/pending-items` | `read_pending_items` | `router` |
| 57 | GET | `/statistics/calendar` | `read_calendar_summary` | `router` |
| 90 | GET | `/statistics/calendar/{target_date}/items` | `read_calendar_items` | `router` |
| 121 | GET | `/statistics/cashflows` | `read_cashflow_list` | `router` |
| 162 | GET | `/statistics/projects/summary` | `read_project_cashflow_summary` | `router` |

**backend/app/modules/sync/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 16 | GET | `/sync/changes` | `get_sync_changes` | `router` |
| 37 | GET | `/sync/snapshot` | `get_sync_snapshot` | `router` |

**backend/app/modules/system/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 65 | GET | `/meta/options` | `read_meta_options` | `router` |
| 73 | GET | `/system/parameters` | `read_system_parameters` | `router` |
| 83 | PUT | `/system/parameters/{param_key}` | `update_system_parameter_record` | `router` |
| 93 | POST | `/system/dev/drop-all-tables` | `drop_all_tables_for_development` | `router` |
| 108 | GET | `/system/dev/business-test-data` | `read_business_test_data` | `router` |
| 126 | POST | `/system/dev/business-test-data/import` | `import_business_test_data_for_development` | `router` |
| 144 | GET | `/system/holidays` | `read_holiday_calendar` | `router` |
| 153 | GET | `/system/holidays/official-preview` | `read_official_holiday_preview` | `router` |
| 165 | POST | `/system/holidays/import-official` | `import_official_holiday_schedule` | `router` |
| 177 | GET | `/system/holiday-schedules/{year}` | `read_holiday_schedule` | `router` |
| 191 | PUT | `/system/holiday-schedules/{year}` | `save_holiday_schedule` | `router` |
| 211 | POST | `/system/holiday-schedules/{year}/publish` | `publish_holiday_schedule_record` | `router` |
| 229 | PUT | `/system/holidays/{holiday_date}` | `upsert_holiday_record` | `router` |
| 239 | DELETE | `/system/holidays/{holiday_date}` | `delete_holiday_record` | `router` |
| 248 | GET | `/system/lpr-rates` | `read_lpr_rates` | `router` |
| 257 | PUT | `/system/lpr-rates` | `upsert_lpr_rate_record` | `router` |
| 266 | POST | `/system/lpr-rates/import-official` | `import_official_lpr_rates` | `router` |
| 274 | DELETE | `/system/lpr-rates/{rate_id}` | `delete_lpr_rate_record` | `router` |

**backend/app/modules/table_views/api.py**

| 行号 | 方法 | 本文件路由路径 | 处理函数 | 路由对象 |
| --- | --- | --- | --- | --- |
| 35 | GET | `/table-page-size-preferences/{surface_key}` | `get_table_page_size_preference` | `router` |
| 58 | PUT | `/table-page-size-preferences/{surface_key}` | `put_table_page_size_preference` | `router` |
| 96 | GET | `/table-views` | `get_table_views` | `router` |
| 119 | PUT | `/table-preferences/{surface_key}` | `put_table_preference` | `router` |
| 143 | POST | `/table-views` | `post_table_view` | `router` |
| 163 | PATCH | `/table-views/{view_id}` | `patch_table_view` | `router` |
| 181 | DELETE | `/table-views/{view_id}` | `remove_table_view` | `router` |
