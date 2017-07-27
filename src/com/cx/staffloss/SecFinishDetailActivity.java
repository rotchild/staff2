package com.cx.staffloss;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cx.applytask.JiHeApplyTask;
import com.cx.applytask.TuidingApplyTask;
import com.cx.myobject.MyFinishObject;
import com.cx.myobject.MyNoteShowDialog;
import com.cx.netset.MHttpParams;
import com.cx.utils.MUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SecFinishDetailActivity extends Activity {
	MyFinishObject selectTaskObject;
	public static String SPName="StaffLossSP";
	String TAG="SecFinish";
	String case_id="";//������case_id
	
	LinearLayout zhixian;//֧�߲���
	
	TextView reportNo_tv,carNo_tv,carType_tv,isTheCar_tv,theCarNo_tv,reportDeport_tv,
	deportConnector_tv,deportConnectorphone_tv,reporterName_tv,theReporterPhone_tv,thefenpeiTime_tv,theAboutMoney_tv,dingsun_money,
	finishDetailBack_tv;
	
	TextView repairDate_tv,repairFactory_tv,repairParts_tv,partsPrice_tv,repairPrice_tv;
	//TextView repairRemark_tv;
	
	TextView lossStartTime_tv;
	//TextView lossRemark_tv;
	TextView auditStartTime_tv;
	//TextView auditRemark_tv;
	
	TextView isYidiCar_tv;
	ImageView finishDetailBack_iv;
	
	Button waixiuLookBtn,tuidingLookBtn,jiheLookBtn;//�鿴��ע
	MyNoteShowDialog mNoteShowDialog;
	//RelativeLayout itemRL;
	ProgressDialog zhixianPd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.secfinishdetail_layout);
		selectTaskObject=(MyFinishObject) this.getIntent().getSerializableExtra("selectFtask");
		initView();
	}
public void addView(LinearLayout ll,String name,String value){
	
	RelativeLayout itemRL=new RelativeLayout(this);
	RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
	
	itemRL.setLayoutParams(params);
	TextView itemName=new TextView(this);//����
	RelativeLayout.LayoutParams itemName_params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	itemName_params.addRule(RelativeLayout.CENTER_VERTICAL);
	itemName_params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
	itemName_params.leftMargin=20;
	itemName_params.topMargin=30;
	itemName.setText(name);
	itemName.setPadding(0, 0, 0, 12);
	itemName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
	itemName.setTextColor(Color.parseColor("#7b7b7b"));
	itemName.setLayoutParams(itemName_params);
	itemRL.addView(itemName);
	
	TextView itemValue=new TextView(this);
	RelativeLayout.LayoutParams itemValue_params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	itemValue_params.addRule(RelativeLayout.CENTER_VERTICAL);
	itemValue_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
	itemValue_params.rightMargin=20;
	itemValue.setText(value);
	itemValue.setPadding(0, 0, 0, 12);
	itemValue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
	itemValue.setTextColor(Color.parseColor("#C4C4C4"));
	itemValue.setLayoutParams(itemValue_params);
	itemRL.addView(itemValue);
	
	ll.addView(itemRL);	
	
	LinearLayout line=new LinearLayout(this);
	LayoutParams lineParams=new LayoutParams(LayoutParams.MATCH_PARENT,2);
	
	line.setBackgroundColor(Color.parseColor("#F5F5F5"));
	line.setLayoutParams(lineParams);
	ll.addView(line);
 
}

public void addLineView(LinearLayout ll,int height){//�ָ���
LinearLayout linell=new LinearLayout(this);
LayoutParams lineParams=new LayoutParams(LayoutParams.MATCH_PARENT,height);
linell.setBackgroundColor(Color.parseColor("#F5F5F5"));
linell.setLayoutParams(lineParams);
ll.addView(linell);
}

public void addButtonView(LinearLayout ll,String name,final String remark){
	RelativeLayout itemRL=new RelativeLayout(this);
	LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
	itemRL.setLayoutParams(params);
	TextView itemName=new TextView(this);
	RelativeLayout.LayoutParams itemName_params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	itemName_params.addRule(RelativeLayout.CENTER_VERTICAL);
	itemName_params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
	itemName_params.leftMargin=20;
	itemName_params.topMargin=30;
	itemName.setText(name);
	itemName.setPadding(0, 0, 0, 12);
	itemName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
	itemName.setTextColor(Color.parseColor("#7b7b7b"));
	itemName.setLayoutParams(itemName_params);
	itemRL.addView(itemName);
	
	Button itemBtn=new Button(this);
	RelativeLayout.LayoutParams itemValue_params=new RelativeLayout.LayoutParams(120,60);
	itemValue_params.addRule(RelativeLayout.CENTER_VERTICAL);
	itemValue_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
	itemValue_params.rightMargin=20;
	itemBtn.setText("�鿴");
	itemBtn.setPadding(0, 0, 0, 12);
	itemBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
	itemBtn.setTextColor(Color.parseColor("#C4C4C4"));
	Drawable dialogred=this.getResources().getDrawable(R.drawable.dialogredbtn_shape);
	itemBtn.setBackgroundDrawable(dialogred);
	itemBtn.setLayoutParams(itemValue_params);
	itemBtn.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			showNoteDialog(remark);
		}
		
	});
	
	itemRL.addView(itemBtn);
	
	ll.addView(itemRL);	
	
	LinearLayout line=new LinearLayout(this);
	LayoutParams lineParams=new LayoutParams(LayoutParams.MATCH_PARENT,2);
	
	line.setBackgroundColor(Color.parseColor("#F5F5F5"));
	line.setLayoutParams(lineParams);
	ll.addView(line);
}
	
public void initView(){
zhixian=(LinearLayout)findViewById(R.id.zhixian_ll);
//addView(zhixian,"����","ֵ");
	finishDetailBack_iv=(ImageView)findViewById(R.id.secfinishdetailback_iv);
	finishDetailBack_iv.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			SecFinishDetailActivity.this.finish();
		}
		
	});
	
	/*finishDetailBack_tv=(TextView)findViewById(R.id.finishdetailback_tv);
	finishDetailBack_tv.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			FinishDetailActivity.this.finish();
		}
		
	});*/
	
	
	reportNo_tv=(TextView)findViewById(R.id.secfinishdetail_reportNo_tv);
	carNo_tv=(TextView)findViewById(R.id.secfinishdetail_carNo_tv);
	carType_tv=(TextView)findViewById(R.id.secfinishdetail_carType_tv);
	isTheCar_tv=(TextView)findViewById(R.id.secfinishdetail_isTheCar_tv);
	theCarNo_tv=(TextView)findViewById(R.id.secfinishdetail_theCarNo_tv);
	reportDeport_tv=(TextView)findViewById(R.id.secfinishdetail_reportDeport_tv);
	deportConnector_tv=(TextView)findViewById(R.id.secfinishdetail_deportConnector_tv);
	deportConnectorphone_tv=(TextView)findViewById(R.id.secfinishdetail_deportConnectorphone_tv);
	reporterName_tv=(TextView)findViewById(R.id.secfinishdetail_reporterName_tv);
	theReporterPhone_tv=(TextView)findViewById(R.id.secfinishdetail_theReporterPhone_tv);
	thefenpeiTime_tv=(TextView)findViewById(R.id.secfinishdetail_thefenpeitime_tv);
	theAboutMoney_tv=(TextView)findViewById(R.id.secfinishdetail_theAboutMoney_tv);
	dingsun_money=(TextView)findViewById(R.id.secfinishdetail_dingsun_money_tv);
	
	isYidiCar_tv=(TextView)findViewById(R.id.secfinishdetail_isYidiCar_tv);
	
	case_id=selectTaskObject.getCase_id();//get case_id
	String reportStr=selectTaskObject.getCase_No();
	String carNoStr=selectTaskObject.getCar_No();
	String carTypeStr=selectTaskObject.getBrand_name();
	String isTheCarStr=selectTaskObject.getIs_target();
	String theCarNoStr=selectTaskObject.getTarget_No();
	String reportDeportStr=selectTaskObject.getParters_name();
	String deportConnectorStr=selectTaskObject.getParter_manager();
	String deportConnectorPhoneStr=selectTaskObject.getParter_mobile();
	String reporterNameStr=selectTaskObject.getDelivery_name();
	String reporterPhone=selectTaskObject.getDelivery_mobile();
	String theFenpeiStr=selectTaskObject.getYard_time();
	String theAboutMoneyStr=selectTaskObject.getLoss_price();
	String dingsunMoneyStr=selectTaskObject.getDingsun_price();
	String isYidiCar=selectTaskObject.getIsYidiCar();
	String isYidiCarStr=isYidiCar.equals("1")?"��":"��";
	
	reportNo_tv.setText(reportStr);
	carNo_tv.setText(carNoStr);
	carType_tv.setText(carTypeStr);
	isTheCar_tv.setText(isTheCarStr);
	theCarNo_tv.setText(theCarNoStr);
	reportDeport_tv.setText(reportDeportStr);
	deportConnector_tv.setText(deportConnectorStr);
	deportConnectorphone_tv.setText(deportConnectorPhoneStr);
	reporterName_tv.setText(reporterNameStr);
	theReporterPhone_tv.setText(reporterPhone);
	String theFenpeiSDFStr=MUtil.getStrTime(theFenpeiStr);
	thefenpeiTime_tv.setText(theFenpeiSDFStr);
	theAboutMoney_tv.setText(theAboutMoneyStr);
	dingsun_money.setText(dingsunMoneyStr);
	isYidiCar_tv.setText(isYidiCarStr);
	
	getAllZhiXian(case_id);//��ȡ֧����Ϣ
	
	//--------------------isVisible?-----------------
/*	String repairState=selectTaskObject.getWaixiu_state();
	String lossState=selectTaskObject.getLoss_state();
	String auditState=selectTaskObject.getAudit_state();


	//repairDate_tv,repairFactory_tv,repairParts_tv,partsPrice_tv,repairPrice_tv,repairRemark_tv
	if(repairState.equals("1")){
		repairDate_tv=(TextView)findViewById(R.id.secfinish_waixiu_date_tv);
		repairFactory_tv=(TextView)findViewById(R.id.waixiu_danwei_tv);
		repairParts_tv=(TextView)findViewById(R.id.waixiu_peijian_tv);
		partsPrice_tv=(TextView)findViewById(R.id.waixiu_peijianprice_tv);
		repairPrice_tv=(TextView)findViewById(R.id.waixiu_repairprice_tv);
		//repairRemark_tv=(TextView)findViewById(R.id.waixiu_note_tv);
		waixiuLookBtn=(Button)findViewById(R.id.waixiu_look_btn);

		String repairDateStamp=selectTaskObject.getRepair_time();
		String repairTime=MUtil.getStrTime(repairDateStamp);
		String repairFactory=selectTaskObject.getRepair_factory();
		String repairParts=selectTaskObject.getRepair_parts();
		String partsPrice=selectTaskObject.getParts_price();
		String repairPrice=selectTaskObject.getRepair_price();
		final String repairRemark=selectTaskObject.getRepair_remark();
		
		repairDate_tv.setText(repairTime);
		repairFactory_tv.setText(repairFactory);
		repairParts_tv.setText(repairParts);
		partsPrice_tv.setText(partsPrice);
		repairPrice_tv.setText(repairPrice);
		//repairRemark_tv.setText(repairRemark);
		waixiuLookBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				showNoteDialog(repairRemark);
			}
			
		});
		
		LinearLayout waixiupartLinear=(LinearLayout)findViewById(R.id.waixiu_part_linear);
		RelativeLayout repairTimeLayout=(RelativeLayout)findViewById(R.id.finish_repair_date_rl);
		RelativeLayout repairFactoryLayout=(RelativeLayout)findViewById(R.id.repair_factory_rl);
		RelativeLayout repairPartsLayout=(RelativeLayout)findViewById(R.id.repair_parts_rl);
		RelativeLayout repairPartsPriceLayout=(RelativeLayout)findViewById(R.id.repair_partsprice_rl);
		RelativeLayout repairPriceLayout=(RelativeLayout)findViewById(R.id.repair_price_rl);
		RelativeLayout repairRemarkLayout=(RelativeLayout)findViewById(R.id.repair_remark_rl);
		
		LinearLayout linear1=(LinearLayout)findViewById(R.id.linear1);
		LinearLayout linear2=(LinearLayout)findViewById(R.id.linear2);
		LinearLayout linear3=(LinearLayout)findViewById(R.id.linear3);
		LinearLayout linear4=(LinearLayout)findViewById(R.id.linear4);
		LinearLayout linear5=(LinearLayout)findViewById(R.id.linear5);
		
		LinearLayout waixiuDividerLayout=(LinearLayout)findViewById(R.id.waixiudivider_linear);
		
		waixiupartLinear.setVisibility(View.VISIBLE);//ȫ���ķ�ʽ��Ч
		waixiuDividerLayout.setVisibility(View.VISIBLE);
		repairTimeLayout.setVisibility(View.VISIBLE);
		repairFactoryLayout.setVisibility(View.VISIBLE);
		repairPartsLayout.setVisibility(View.VISIBLE);
		repairPartsPriceLayout.setVisibility(View.VISIBLE);
		repairPriceLayout.setVisibility(View.VISIBLE);
		repairRemarkLayout.setVisibility(View.VISIBLE);
		
		linear1.setVisibility(View.VISIBLE);
		linear2.setVisibility(View.VISIBLE);
		linear3.setVisibility(View.VISIBLE);
		linear4.setVisibility(View.VISIBLE);
		linear5.setVisibility(View.VISIBLE);
	}
	
	if(lossState.equals("1")){
		lossStartTime_tv=(TextView)findViewById(R.id.loss_startTime_tv);
		//lossRemark_tv=(TextView)findViewById(R.id.loss_remark_tv);
		tuidingLookBtn=(Button)findViewById(R.id.loss_look_btn);
		String lossStartTimeStamp=selectTaskObject.getLossStartTimeStamp();
		final String lossRemark=selectTaskObject.getLossRemark();
		String lossStartTime=MUtil.getStrTime(lossStartTimeStamp);
		lossStartTime_tv.setText(lossStartTime);
		//lossRemark_tv.setText(lossRemark);
		tuidingLookBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				showNoteDialog(lossRemark);
			}
			
		});
		RelativeLayout loss_starttimeRelative=(RelativeLayout)findViewById(R.id.loss_starttime_rl);
		RelativeLayout loss_remarkRelative=(RelativeLayout)findViewById(R.id.loss_remark_rl);
		
		LinearLayout linear6=(LinearLayout)findViewById(R.id.linear6);
		LinearLayout linear7=(LinearLayout)findViewById(R.id.linear7);
		
		
		loss_starttimeRelative.setVisibility(View.VISIBLE);
		loss_remarkRelative.setVisibility(View.VISIBLE);
		
		linear6.setVisibility(View.VISIBLE);
		linear7.setVisibility(View.VISIBLE);
	}
	
	if(auditState.equals("1")){
		auditStartTime_tv=(TextView)findViewById(R.id.audit_starttime_tv);
		//auditRemark_tv=(TextView)findViewById(R.id.audit_remark_tv);
		jiheLookBtn=(Button)findViewById(R.id.audit_look_btn);
		String auditStartTimeStamp=selectTaskObject.getAuditStartTimeStamp();
		final String auditRemark=selectTaskObject.getAuditRemark();
		String auditStartTime=MUtil.getStrTime(auditStartTimeStamp);
		auditStartTime_tv.setText(auditStartTime);
		//auditRemark_tv.setText(auditRemark);
		jiheLookBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				showNoteDialog(auditRemark);
			}
			
		});
		
		RelativeLayout audit_starttimeRelative=(RelativeLayout)findViewById(R.id.audit_starttime_rl);
		RelativeLayout audit_RemarkRelative=(RelativeLayout)findViewById(R.id.audit_remark_rl);
		
		LinearLayout linear8=(LinearLayout)findViewById(R.id.linear8);
		LinearLayout linear9=(LinearLayout)findViewById(R.id.linear9);
		
		audit_starttimeRelative.setVisibility(View.VISIBLE);
		audit_RemarkRelative.setVisibility(View.VISIBLE);
		
		linear8.setVisibility(View.VISIBLE);
		linear9.setVisibility(View.VISIBLE);
	}*/
	
}

public void getAllZhiXian(String case_id){
	zhixianPd=ProgressDialog.show(SecFinishDetailActivity.this, "���Ժ�", "��ѯ��...");
	AsyncHttpClient zhixianqueryClient=new AsyncHttpClient();
	zhixianqueryClient.addHeader("Charset", MHttpParams.DEFAULT_CHARSET);
	zhixianqueryClient.setTimeout(MHttpParams.DEFAULT_TIME_OUT);
	String dUrl=MHttpParams.IP;
	SharedPreferences SecFinishSP=getSharedPreferences(SPName,0);
	String mUrl=SecFinishSP.getString("IP", dUrl);
	String dPort=MHttpParams.DEFAULT_PORT;
	String mPort=SecFinishSP.getString("Port", dPort);
	String zhixianqueryUrl="http://"+mUrl+":"+mPort+"/"+MHttpParams.ZhiXianQueryTaskUrl;
	RequestParams requestparams=new RequestParams();
	requestparams.put("case_id", case_id);
	zhixianqueryClient.post(zhixianqueryUrl, requestparams, new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, JSONObject response) {
			// TODO �Զ����ɵķ������
			super.onSuccess(statusCode, response);
			if(zhixianPd!=null&&zhixianPd.isShowing()){
				zhixianPd.dismiss();
				zhixianPd=null;
			}
			boolean success=false;
			try{
				success=response.getBoolean("success");
				if(success){
					JSONObject data=response.getJSONObject("data");
					JSONArray wx=data.getJSONArray("wx");
					if(wx.length()>0){
						for(int i=0;i<wx.length();i++){
							JSONObject wxData=wx.getJSONObject(i);
							String wxid=wxData.getString("repairtask_id");
							String addTimeStamp=wxData.getString("add_time");//����ʱ���
							String addTimeStr=MUtil.getDetailTime(addTimeStamp);//����ʱ�䣨��ϸ��
							
							String repairTimeStamp=wxData.getString("repair_time");//��������ʱ���
							String repairTime=MUtil.getStrTime(repairTimeStamp);
							String repairFactroy=wxData.getString("repair_factoryname");//���޵�λ
							String repairParts=wxData.getString("repair_parts");
							String partsPrice=wxData.getString("parts_price");
							String repairPrice=wxData.getString("repair_price");
							
							String repairRemark="";//���뱸ע
							Object RemarkObj=wxData.get("repair_remark");
							if(RemarkObj instanceof String){
								repairRemark=wxData.getString("repair_remark");
							}
							
							String repairOutRemark="";//��ɱ�ע
							Object outRemarkObj=wxData.get("outrepair_remark");
							if(outRemarkObj instanceof String){
								repairOutRemark=wxData.getString("outrepair_remark");
							}
							addLineView(zhixian,40);//��ӷָ���
							addView(zhixian,"���ޱ��",wxid);
							//Log.d(TAG, "���ޱ��"+wxid);
							addView(zhixian,"��������ʱ��:",addTimeStr);
							addView(zhixian,"��������:",repairTime);
							addView(zhixian,"���޵�λ:",repairFactroy);
							addView(zhixian,"�������:",repairParts);
							addView(zhixian,"����۸�:",partsPrice);
							addView(zhixian,"�޸����:",repairPrice);
							addButtonView(zhixian,"�������뱸ע",repairRemark);
							addButtonView(zhixian,"������ɱ�ע",repairOutRemark);
						}
					}
					
					JSONArray td=data.getJSONArray("td");
					if(td.length()>0){
						for(int i=0;i<td.length();i++){
							JSONObject tdData=td.getJSONObject(i);
							String tdid=tdData.getString("id");//�ƶ�����id
							String tdAddTimeStamp=tdData.getString("add_time");//����ʱ���
							String tdAddTime=MUtil.getDetailTime(tdAddTimeStamp);
							String tdEndTimeStamp=tdData.getString("loss_endtime");//�ƶ����ʱ���
							String tdEndTime=MUtil.getDetailTime(tdEndTimeStamp);
							String lossRemark="";//��ɱ�ע
							Object lossRemarkObj=tdData.get("loss_remark");
							if(lossRemarkObj instanceof String){
								lossRemark=tdData.getString("outrepair_remark");
							}
							addLineView(zhixian,40);//��ӷָ���
							addView(zhixian,"�ƶ����",tdid);
							addView(zhixian,"�ƶ�����ʱ��",tdAddTime);
							addView(zhixian,"�ƶ�����ʱ��",tdEndTime);
							addButtonView(zhixian,"�ƶ���ɱ�ע",lossRemark);
						}
					}
				
				JSONArray jh=data.getJSONArray("jh");
				if(jh.length()>0){
					for(int i=0;i<jh.length();i++){
						JSONObject jhData=jh.getJSONObject(i);
						String jhid=jhData.getString("id");//����task id
						String jhAddTimeStamp=jhData.getString("audit_starttime");//��������ʱ��
						String jhAddTime=MUtil.getDetailTime(jhAddTimeStamp);
						String jhEndTimeStamp=jhData.getString("audit_endtime");//���˽���ʱ��
						String jhEndTime=MUtil.getDetailTime(jhEndTimeStamp);
						String auditRemark="";//���뱸ע
						Object auditRemarkObj=jhData.get("audit_remark");
						if(auditRemarkObj instanceof String){
							auditRemark=jhData.getString("audit_remark");
						}
						
						String cutRemark="";//��ɱ�ע
						Object cutRemarkObj=jhData.get("cut_remark");
						if(cutRemarkObj instanceof String){
							cutRemark=jhData.getString("cut_remark");
						}
						
						addLineView(zhixian,40);//��ӷָ���
						addView(zhixian,"���˱��",jhid);
						addView(zhixian,"��������ʱ��",jhAddTime);
						addView(zhixian,"���˽���ʱ��",jhEndTime);
						addButtonView(zhixian,"�������뱸ע",auditRemark);
						addButtonView(zhixian,"������ɱ�ע",cutRemark);
					}
				}
					
				}else{
					Toast.makeText(SecFinishDetailActivity.this, "��������", Toast.LENGTH_SHORT).show();
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(Throwable e, JSONObject errorResponse) {
			// TODO �Զ����ɵķ������
			super.onFailure(e, errorResponse);
			if(zhixianPd!=null&&zhixianPd.isShowing()){
				zhixianPd.dismiss();
				zhixianPd=null;
			}
			Toast.makeText(SecFinishDetailActivity.this, "֧����Ϣ��ѯʧ��,������", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onFailure(Throwable e, String errorResponse) {
			// TODO �Զ����ɵķ������
			super.onFailure(e, errorResponse);
			if(zhixianPd!=null&&zhixianPd.isShowing()){
				zhixianPd.dismiss();
				zhixianPd=null;
			}
			Toast.makeText(SecFinishDetailActivity.this, "֧����Ϣ��ѯʧ��,������", Toast.LENGTH_SHORT).show();
		}
		
	});

}

public void showNoteDialog(String remark){
	TextView noteTv;
	mNoteShowDialog=new MyNoteShowDialog(SecFinishDetailActivity.this,R.style.Dialog);
	mNoteShowDialog.setMyNoteShowDialog();
	String noteTitleStr="��ע";
	mNoteShowDialog.setTitle(noteTitleStr);
	noteTv=(TextView)mNoteShowDialog.getNoteText();
	noteTv.setText(remark);
	mNoteShowDialog.setCloseBtn(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			if(mNoteShowDialog!=null&&mNoteShowDialog.isShowing()){
				mNoteShowDialog.dismiss();
				mNoteShowDialog=null;
			}
		}
		
	});
	mNoteShowDialog.show();
}
}
