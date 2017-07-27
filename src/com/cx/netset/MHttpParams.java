package com.cx.netset;

public class MHttpParams {
	public static String DEFAULT_CHARSET="UTF-8";
	public static int DEFAULT_TIME_OUT=15*1000;
	public static String DEFAULT_URL="http://27.17.25.158";
	public static String DEFAULT_PORT="3003";
	public static String LoginBUrl="api/Public/LoginCheck";
	public static String CurrentTaskUrl="api/Public/getCurrentTask";
	public static String DealingTaskUrl="api/Public/getDoItTaskRec";
	public static String UpdateCaseNoUrl="api/Public/updateCaseNo";
	public static String getAllFactoryNoUrl="api/Public/getAllFactory";
	public static String applyTuidingUrl="api/Public/applyLossTask";
	public static String applyJiheUrl="api/Public/applyAuditTask";
	public static String applyWaixiuUrl="api/Public/applyRepairTask";
	public static String isFinishUrl="api/Public/getCaseState";
	public static String getJiheUrl="api/Public/getAuditUser";
	public static String IP="27.17.25.158";
	public static String CanfinishUrl="api/Public/ENDDEFLOSSFLAG";
	public static String queryUrl="api/Public/getCaseById";
	public static String queryWaiRecordUrl="api/Public/queryCXRepairTaskbyCaseId";//查询外修记录
	public static String queryTuiRecordUrl="api/Public/queryCXLossTaskbyCaseId";//查询推定记录
	public static String queryJiRecordUrl="api/Public/queryCXAuditTaskbyCaseId";//查询稽核记录
	public static String FinishTaskUrl="api/Public/getHisTaskRec";
	public static String FinishCXTaskUrl="api/Public/getCxHisTaskRec";//查询v_cxcase
	public static String ZhiXianQueryTaskUrl="api/Public/GetAllByCaseId";//查询支线任务
	public static String ToFinishTaskUrl="api/Public/finishCase";
	public static String uploadImg="api/Public/uploadImg";
	public static String checkUpdateUrl="api/Versions/GetVersionInfo";//检查更新
	public static String SystemOS="Android";
	public static String ProjectName="JidongApp";
	public static String APKName="机动APP";
}
