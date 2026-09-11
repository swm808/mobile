# 核心模型与 Mock 数据

模型位于 [main/model](../../../../../../main/java/com/citybond/mobile/model)，假数据统一放在本目录 [MockData.kt](MockData.kt)。这是移动端业务模型草案，不是 API 合同或完整数据库设计；需求没有电商订单，业务单据使用 `BusinessDocument`。

| 需求范围 | 核心模型 | 示例 |
| --- | --- | --- |
| F29、F31—F32 基础资料与账号 | Organization、User | 3 个单位，3 个用户（含停用账号） |
| F03—F10 项目融资 | Project、Debt、Money | 3 个项目、3 笔债务，覆盖待补充、存续、结清 |
| F11—F12 现金流与速记 | Repayment、CashEntry | 3 条还款、2 条资金记录 |
| F16—F18 单据 | BusinessDocument、BusinessRef | 还本付息通知单、费用申请单 |
| F21、F24、F30 文件与任务 | Attachment、BusinessTask | 文件元数据及运行、失败、排队任务 |
| F28 本地多轮对话 | Conversation、ChatMessage、DebtDraft | 2 个会话、4 条消息及待补充草稿 |

调试代码中可直接读取，暂不需要服务器或新增依赖：

```kotlin
import com.citybond.mobile.mock.MockData

val projects = MockData.projects
val debt = MockData.debts.first()
val repayments = MockData.repayments.filter { it.debtId == debt.id }
val myTasks = MockData.tasks.filter { it.ownerUserId == MockData.currentUser.id }
val emptyList = MockData.emptyProjects
```

- 数据仅在 `debug` 源集中，正式版不可引用；`main` 页面接入前需另加数据提供接口，不能直接 import MockData。
- 全部为虚构数据，固定参考日期为 2026-09-09。测试即将到期场景时使用 `referenceDate`，避免随当天日期漂移。
- 金额单位为人民币元，使用字符串构造 BigDecimal；利率为比例值；业务日期为 LocalDate，事件时间为 UTC Instant。
- ID 为本地示例字符串；枚举为首版展示状态，不等于后端枚举全集。角色名称不能用于真实授权。
- 覆盖空列表、长名称、空字段、零余额、停用账号、任务失败和空会话；尚未模拟 HTTP 错误、分页或请求延迟。
- 附件没有真实文件；任务能力为示例配置。还款记录不是完整计划，示例利息不用于计算验证。
- 对话和草稿仅在内存中；尚未实现 Room、加密、持久化、多轮推理及正式提交。
- 本批不展开全部融资品种专用字段、担保措施、发票/资产、报表及系统配置模型，后续按对应业务批次补充。

验证：在项目根目录运行 `./mobilew :app:testDebugUnitTest`，包含 Mock 引用关系、账号归属、消息顺序及单据金额一致性检查。
