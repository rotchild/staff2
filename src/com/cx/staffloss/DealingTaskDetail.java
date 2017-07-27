package com.cx.staffloss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cx.applytask.ChooseJiHeActivity;
import com.cx.applytask.TuidingApplyTask;
import com.cx.applytask.WaiXiuApplyTask;
import com.cx.myobject.MyTaskObject;
import com.cx.netset.MHttpParams;
import com.cx.utils.MUtil;
import com.cx.utils.MySeCustomeDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class DealingTaskDetail extends  Activity {
Button getPhotoBtn,waixiuBtn,tuidingBtn,jiHeBtn;
Button waiRecordBtn,tuiRecordBtn,jiRecordBtn;
Button finishButton;

TextView reportNo_tv,carNo_tv,carType_tv,isTheCar_tv,theCarNo_tv,reportDeport_tv,
reporterName_tv,theAboutMoney_tv,deportConnector_tv,theYardTime_tv;
RadioGroup isYidiCarRG;
int isYidiCar=0;//0非异地
//TextView dealingTaskBackTv;

MyTaskObject selectTaskObject;

ImageView callDeparterIv,callDeliverIv,dealingtaskback;

String theReporterPhonestr,deportConnectorphonestr;

public static String SPName="StaffLossSP";
SharedPreferences dealingTaskSP;
//String buttonSelect="";//1为false,2为true;
public String dingsunamount="";//定损金额

String caseidStr="-1";//案件id

ProgressDialog queryPd;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dealingtaskdetail_layout);
		Bundle bundle=this.getIntent().getExtras();
		//buttonSelect=bundle.getString("btnSelect");
		selectTaskObject=(MyTaskObject) this.getIntent().getSerializableExtra("selectTask");
		initView();
	}
	public void initView(){
		dealingtaskback=(ImageView)findViewById(R.id.dealingtaskdetailback_iv);
		dealingtaskback.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				int resultCode=3;
				DealingTaskDetail.this.setResult(resultCode);
				DealingTaskDetail.this.finish();
			}
			
		});

		dealingTaskSP=getSharedPreferences(SPName,0);
		
		
/*		dealingTaskBackTv=(TextView)findViewById(R.id.dealingtaskback_tv);
		dealingTaskBackTv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				int resultCode=3;
				DealingTaskDetail.this.setResult(resultCode);
				DealingTaskDetail.this.finish();
			}
			
		});*/
		
		isYidiCarRG=(RadioGroup)findViewById(R.id.isyidiCarRG);
		isYidiCarRG.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO 自动生成的方法存根
				switch(checkedId){
				case R.id.isyidiCar:
					isYidiCar=1;
					break;
				case R.id.notyidiCar:
					isYidiCar=0;
					break;
				default:
					break;
				}
			}
			
		});
		
		
		finishButton=(Button)findViewById(R.id.casefinish_btn);
		finishButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
				//canFinishByHttp();
				//先查询，再显示输入金额框
				queryStateByHttp();
				//showCaseNoDialog();
			}
			
		});
		
		
		theYardTime_tv=(TextView)findViewById(R.id.taskdeliverTime_tv);
		
		reportNo_tv=(TextView)findViewById(R.id.reportNo_tv);
		carNo_tv=(TextView)findViewById(R.id.carNo_tv);
		carType_tv=(TextView)findViewById(R.id.carType_tv);
		isTheCar_tv=(TextView)findViewById(R.id.isTheCar_tv);
		theCarNo_tv=(TextView)findViewById(R.id.theCarNo_tv);
		reportDeport_tv=(TextView)findViewById(R.id.reportDeport_tv);
		deportConnector_tv=(TextView)findViewById(R.id.deportConnector_tv);
		//deportConnectorphone_tv=(TextView)findViewById(R.id.deportConnectorphone_tv);
		reporterName_tv=(TextView)findViewById(R.id.reporterName_tv);
		//theReporterPhone_tv=(TextView)findViewById(R.id.theReporterPhone_tv);
		theAboutMoney_tv=(TextView)findViewById(R.id.theAboutMoney_tv);
		
		//点击跳转拨打
/*		deportConnectorphone_tv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				String telnumber=deportConnectorphone_tv.getText().toString();
				Intent toCall=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+telnumber));
				startActivity(toCall);
			}
			
		});*/
		
		callDeparterIv=(ImageView)findViewById(R.id.calldeparter_iv);
		callDeparterIv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
			//	String telnumber=deportConnectorphone_tv.getText().toString();
				Intent toCall=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+deportConnectorphonestr));
				startActivity(toCall);
			}
			
		});
		
		callDeliverIv=(ImageView)findViewById(R.id.callReporterDepart_iv);
		callDeliverIv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				//String telnumber=theReporterPhone_tv.getText().toString();
				Intent toCall=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+theReporterPhonestr));
				startActivity(toCall);
			}
			
		});
		
/*		theReporterPhone_tv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				String telnumber=theReporterPhone_tv.getText().toString();
				Intent toCall=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+telnumber));
				startActivity(toCall);
			}
			
		});*/
		
		String reportNostr=selectTaskObject.getCase_No();
		String carNostr=selectTaskObject.getCar_No();
		String carTypestr=selectTaskObject.getBrand_name();
		String isTheCarstr=selectTaskObject.getIs_target();
		String theCarNostr=selectTaskObject.getTarget_No();
		String reportDeportstr=selectTaskObject.getParters_name();
		String deportConnectorstr=selectTaskObject.getParter_manager();
		deportConnectorphonestr=selectTaskObject.getParter_mobile();
		String reporterNamestr=selectTaskObject.getDelivery_name();
		 theReporterPhonestr=selectTaskObject.getDelivery_mobile();
		String theAboutMoneystr=selectTaskObject.getLoss_price();
		
		
		String yardTimeStr=selectTaskObject.getYard_time();
		
		caseidStr=selectTaskObject.getCase_id();
	
		
		
		reportNo_tv.setText(reportNostr);
		carNo_tv.setText(carNostr);
		carType_tv.setText(carTypestr);
		isTheCar_tv.setText(isTheCarstr);
		theCarNo_tv.setText(theCarNostr);
		reportDeport_tv.setText(reportDeportstr);
		deportConnector_tv.setText(deportConnectorstr);
		//deportConnectorphone_tv.setText(deportConnectorphonestr);
		reporterName_tv.setText(reporterNamestr);
		//theReporterPhone_tv.setText(theReporterPhonestr);
		theAboutMoney_tv.setText(theAboutMoneystr);
		theYardTime_tv.setText(yardTimeStr);
		{
			waiRecordBtn=(Button)findViewById(R.id.waixiurecord_btn);
			tuiRecordBtn=(Button)findViewById(R.id.tuidingrecord_btn);
			jiRecordBtn=(Button)findViewById(R.id.jiherecord_btn);
			
			waiRecordBtn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					queryWaiRecordByHttp(caseidStr);
//					Intent toWaiRecord=new Intent(DealingTaskDetail.this,WaiRecordAct.class);
//					startActivity(toWaiRecord);
				}
				
			});
			tuiRecordBtn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					queryTuiRecordByHttp(caseidStr);
				}
				
			});
			jiRecordBtn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					queryJiRecordByHttp(caseidStr);
				}
				
			});
			
			
			
		}
		getPhotoBtn=(Button)findViewById(R.id.getPhoto_btn);
		waixiuBtn=(Button)findViewById(R.id.waixiu_btn);
		tuidingBtn=(Button)findViewById(R.id.tuiding_btn);
		jiHeBtn=(Button)findViewById(R.id.jihe_btn);
	
/*		if(buttonSelect.equals("1")){//false
			waixiuBtn.setEnabled(true);
			tuidingBtn.setEnabled(true);
			jiHeBtn.setEnabled(true);
			finishButton.setEnabled(true);
			
			waixiuBtn.setClickable(false);
			tuidingBtn.setClickable(false);
			jiHeBtn.setClickable(false);
			finishButton.setClickable(false);
		}else{
			waixiuBtn.setEnabled(true);
			tuidingBtn.setEnabled(true);
			jiHeBtn.setEnabled(true);
			finishButton.setEnabled(true);
			
			waixiuBtn.setClickable(true);
			tuidingBtn.setClickable(true);
			jiHeBtn.setClickable(true);
			finishButton.setClickable(true);
		}*/
		Map<String,String> stateMap=null;
		String clickState="0";
		int photoState=dealingTaskSP.getInt("photoState", 0);
		String mapObjStr=dealingTaskSP.getString("mMap", "");
		if(mapObjStr.equals("")){
			
		}else{
		stateMap=MUtil.getMap(DealingTaskDetail.this, mapObjStr);
		}
		
		if(stateMap!=null){
			if(stateMap.containsKey(caseidStr)){
				clickState=stateMap.get(caseidStr);
			}
		}
		if(clickState.equals("1")){
			waixiuBtn.setEnabled(true);
			tuidingBtn.setEnabled(true);
			jiHeBtn.setEnabled(true);
			finishButton.setEnabled(true);
			
			waixiuBtn.setClickable(true);
			tuidingBtn.setClickable(true);
			jiHeBtn.setClickable(true);
			finishButton.setClickable(true);

			
		}else{
			waixiuBtn.setEnabled(false);
			tuidingBtn.setEnabled(false);
			jiHeBtn.setEnabled(false);
			finishButton.setEnabled(false);
			
			waixiuBtn.setClickable(false);
			tuidingBtn.setClickable(false);
			jiHeBtn.setClickable(false);
			finishButton.setClickable(false);

		}
		
		getPhotoBtn.setOnClickListener(new OnClickListener(){

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if(Build.VERSION.SDK_INT>=23){//6.0及以上
					if(PermissionChecker.checkSelfPermission(DealingTaskDetail.this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
						 requestPermissions(new String[] {Manifest.permission.CAMERA},
					                1);
						Toast.makeText(DealingTaskDetail.this, "请在权限管理中开启相机权限", Toast.LENGTH_SHORT).show();
					}else{
						try{
							Intent toTakePhoto=new Intent(DealingTaskDetail.this,TakePhotoActivity.class);
							//startActivity(toTakePhoto);
							Bundle bundle=new Bundle();
							bundle.putString("case_id", caseidStr);
							toTakePhoto.putExtras(bundle);
							startActivity(toTakePhoto);	
						}catch(Exception e){
							e.printStackTrace();
						}	
					}
					
				}else{
					if(PermissionChecker.checkSelfPermission(DealingTaskDetail.this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
						Toast.makeText(DealingTaskDetail.this, "请开启照相权限", Toast.LENGTH_SHORT).show();
						 ActivityCompat.requestPermissions(DealingTaskDetail.this,  new String[]{Manifest.permission.CAMERA}, 1);
					}else{
						try{
							Intent toTakePhoto=new Intent(DealingTaskDetail.this,TakePhotoActivity.class);
							//startActivity(toTakePhoto);
							Bundle bundle=new Bundle();
							bundle.putString("case_id", caseidStr);
							toTakePhoto.putExtras(bundle);
							startActivity(toTakePhoto);	
						}catch(Exception e){
							e.printStackTrace();
						}
						
					}	
				}

			}
			
		});
		
		
		waixiuBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Bundle bundle=new Bundle();
				bundle.putSerializable("selectTaskObject", selectTaskObject);
				Intent toApplyWaixiu=new Intent(DealingTaskDetail.this,WaiXiuApplyTask.class);
				toApplyWaixiu.putExtras(bundle);
				startActivity(toApplyWaixiu);
			}
			
		});
		tuidingBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Bundle bundle=new Bundle();
				bundle.putSerializable("tuiselectTaskObject", selectTaskObject);
				Intent toApplyTuiding=new Intent(DealingTaskDetail.this,TuidingApplyTask.class);
				toApplyTuiding.putExtras(bundle);
				startActivity(toApplyTuiding);
			}
			
		});
		
		jiHeBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Bundle bundle=new Bundle();
				bundle.putSerializable("jiselectTaskObject", selectTaskObject);
				//Intent toApplyJiHe=new Intent(DealingTaskDetail.this,JiHeApplyTask.class);
				Intent toChooseJiHe=new Intent(DealingTaskDetail.this,ChooseJiHeActivity.class);
				toChooseJiHe.putExtras(bundle);
				startActivity(toChooseJiHe);
			}
			
		});
	}
	
	
	


	@SuppressLint("NewApi")
	@Override
	public void onRequestPermissionsResult(int requestCode,
			String[] permissions, int[] grantResults) {
		// TODO 自动生成的方法存根
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch(requestCode){
		case 1:
			if(grantResults.length>0){
				if(grantResults[0]==-1){//拒绝
					Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
					Uri uri=Uri.fromParts("package", getPackageName(), null);
					intent.setData(uri);
					startActivity(intent);
				}
			}
			
			break;
		default:
			break;
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case 1:
			/*waixiuBtn.setEnabled(true);
			tuidingBtn.setEnabled(true);
			jiHeBtn.setEnabled(true);
			finishButton.setEnabled(true);
			*/
			
			/*waixiuBtn.setClickable(true);
			tuidingBtn.setClickable(true);
			jiHeBtn.setClickable(true);
			finishButton.setClickable(true);*/
			break;
	   default:
		   break;
		}
		
	}
	
	
	
	@Override
	protected void onRestart() {
		// TODO 自动生成的方法存根
		super.onRestart();
		int photoState=dealingTaskSP.getInt("photoState", 0);
		Map<String,String> stateMap=null;
		String clickState="0";
		//int photoState=dealingTaskSP.getInt("photoState", 0);
		String mapObjStr=dealingTaskSP.getString("mMap", "");
		if(mapObjStr.equals("")){
			
		}else{
		stateMap=MUtil.getMap(DealingTaskDetail.this, mapObjStr);
		}
		
		if(stateMap!=null){
			if(stateMap.containsKey(caseidStr)){
				clickState=stateMap.get(caseidStr);
			}
		}
		
		if(clickState.equals("1")){
			waixiuBtn.setEnabled(true);
			tuidingBtn.setEnabled(true);
			jiHeBtn.setEnabled(true);
			finishButton.setEnabled(true);
			
			waixiuBtn.setClickable(true);
			tuidingBtn.setClickable(true);
			jiHeBtn.setClickable(true);
			finishButton.setClickable(true);

			
		}else{
			waixiuBtn.setEnabled(false);
			tuidingBtn.setEnabled(false);
			jiHeBtn.setEnabled(false);
			finishButton.setEnabled(false);
			
			waixiuBtn.setClickable(false);
			tuidingBtn.setClickable(false);
			jiHeBtn.setClickable(false);
			finishButton.setClickable(false);

		}
	}
	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		//dealingTaskSP
		/*SharedPreferences.Editor dealingTaskEditor=dealingTaskSP.edit();
		dealingTaskEditor.putInt("photoState", 0);
		dealingTaskEditor.commit();*/
		int resultCode=3;
		DealingTaskDetail.this.setResult(resultCode);
		super.onDestroy();
	}

/*	public void canFinishByHttp(){
		AsyncHttpClient canFinishClient=new AsyncHttpClient();
		canFinishClient.addHeader("Charset", MHttpParams.DEFAULT_CHARSET);
		canFinishClient.setTimeout(MHttpParams.DEFAULT_TIME_OUT);
		String dUrl=MHttpParams.IP;
		String mUrl=dealingTaskSP.getString("IP", dUrl);
		String dPort=MHttpParams.DEFAULT_PORT;
		String mPort=dealingTaskSP.getString("Port", dPort);
		String canFinishUrl="http://"+mUrl+":"+mPort+"/"+MHttpParams.isFinishUrl;
		RequestParams params=new RequestParams();
		int case_id=MyhttpStorage.case_id;//this is something wrong?
		//case_id=15;
		params.put("case_id", String.valueOf(case_id));
		canFinishClient.post(canFinishUrl, params, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				// TODO 自动生成的方法存根
				super.onSuccess(statusCode, response);
				boolean success=false;
				try{
					success=response.getBoolean("success");
					if(success){
						Log.d("dealingtask", "success is true");
						JSONArray data=response.getJSONArray("data");
						if(data.length()>0){
							
								JSONObject mdata=data.getJSONObject(0);
								int case_state=mdata.getInt("case_state");
								int repair_state=mdata.getInt("repair_state");//waixiu
								int audit_state=mdata.getInt("audit_state");
								int loss_state=mdata.getInt("loss_state");
								if(case_state==3){
									Toast.makeText(DealingTaskDetail.this, "定损完成", Toast.LENGTH_SHORT).show();
									DealingTaskDetail.this.finish();
								}else{
									Toast.makeText(DealingTaskDetail.this, "定损尚未完成", Toast.LENGTH_SHORT).show();
								}
							
						}
					}else{
						Log.d("dealingtask", "success is false");
					}
				}catch(JSONException e){
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
			}
			
			@Override
			public void onFailure(Throwable e, String errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
			}
			
		});

	}*/
	
	public void queryWaiRecordByHttp(final String case_id){//查询外修记录
		queryPd=ProgressDialog.show(DealingTaskDetail.this, "查询中", "请稍后");
		AsyncHttpClient queryClient=new AsyncHttpClient();
		queryClient.addHeader("Charset", MHttpParams.DEFAULT_CHARSET);
		queryClient.setTimeout(MHttpParams.DEFAULT_TIME_OUT);
		String dUrl=MHttpParams.IP;
		String mUrl=dealingTaskSP.getString("IP", dUrl);
		String dPort=MHttpParams.DEFAULT_PORT;
		String mPort=dealingTaskSP.getString("Port", dPort);
		String queryUrl="http://"+mUrl+":"+mPort+"/"+MHttpParams.queryWaiRecordUrl;
		RequestParams params=new RequestParams();
		//int case_id=caseidStr;
		params.put("case_id", case_id);
		queryClient.put(queryUrl, params,  new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				// TODO 自动生成的方法存根
				super.onSuccess(statusCode, response);
				if(queryPd!=null&&queryPd.isShowing()){
					queryPd.dismiss();
				}
				boolean success=false;
				try{
					success=response.getBoolean("success");
					if(success){
						ArrayList<HashMap> mapList=new ArrayList<HashMap>();
						
						
						JSONArray data=response.getJSONArray("data");
						for(int i=0;i<data.length();i++){
							HashMap recordStore=new HashMap();
							JSONObject mdata=(JSONObject) data.get(i);
							String repair_finishstate=mdata.getString("repair_finishstate");
							if(repair_finishstate.equals("1")){
								String repair_finishState="已完成";
								String addTimeStamp=mdata.getString("add_time");//时间戳
								String addTime=MUtil.getDetailTime(addTimeStamp);
								String repairTimeStamp=mdata.getString("repair_time");//时间戳
								String repairTime=MUtil.getStrTime(repairTimeStamp);
								String repair_endtimeStamp=mdata.getString("repair_endtime");//时间戳
								
								String repair_endtime="";
								if(repair_endtimeStamp.equals("null")){
									
								}else{
									repair_endtime=MUtil.getDetailTime(repair_endtimeStamp);
								}
								String repair_id=mdata.getString("id");//repair_taskid
								String repair_parts=mdata.getString("repair_parts");
								String repair_price=mdata.getString("repair_price");
								String parts_price=mdata.getString("parts_price");
								 
								String repair_remark=mdata.getString("repair_remark");
								if(repair_remark.equals("null")){
									repair_remark="";	
								}
								String outrepair_remark=mdata.getString("outrepair_remark");
								if(outrepair_remark.equals("null")){
									outrepair_remark="";
								}
								String factoryname=mdata.getString("factoryname");
								
								recordStore.put("repair_finishState", repair_finishState);
								recordStore.put("repairid", repair_id);
								recordStore.put("add_time", addTime);
								recordStore.put("repairTime", repairTime);
								recordStore.put("repair_endtime", repair_endtime);
								recordStore.put("repair_parts", repair_parts);
								recordStore.put("repair_price", repair_price);
								recordStore.put("parts_price", parts_price);
								recordStore.put("repair_remark", repair_remark);
								recordStore.put("outrepair_remark", outrepair_remark);
								recordStore.put("factoryname", factoryname);
								
								mapList.add(recordStore);
							}else{
								String repair_finishState="外修中";
								String addTimeStamp=mdata.getString("add_time");//时间戳
								String addTime=MUtil.getDetailTime(addTimeStamp);
								String repairTimeStamp=mdata.getString("repair_time");//时间戳
								String repairTime=MUtil.getStrTime(repairTimeStamp);
								String repair_endtimeStamp=mdata.getString("repair_endtime");//时间戳
								
								String repair_endtime="";
								if(repair_endtimeStamp.equals("null")){
									
								}else{
									repair_endtime=MUtil.getDetailTime(repair_endtimeStamp);
								}
								String repair_id=mdata.getString("id");//repair_taskid
								String repair_parts=mdata.getString("repair_parts");
								String repair_price=mdata.getString("repair_price");
								String parts_price=mdata.getString("parts_price");
								String repair_remark=mdata.getString("repair_remark");
								if(repair_remark.equals("null")){
									repair_remark="";	
								}
								String outrepair_remark=mdata.getString("outrepair_remark");
								if(outrepair_remark.equals("null")){
									outrepair_remark="";
								}
								String factoryname=mdata.getString("factoryname");
								
								recordStore.put("repair_finishState", repair_finishState);
								recordStore.put("repairid", repair_id);
								recordStore.put("add_time", addTime);
								recordStore.put("repairTime", repairTime);
								recordStore.put("repair_endtime", repair_endtime);
								recordStore.put("repair_parts", repair_parts);
								recordStore.put("repair_price", repair_price);
								recordStore.put("parts_price", parts_price);
								recordStore.put("repair_remark", repair_remark);
								recordStore.put("outrepair_remark", outrepair_remark);
								recordStore.put("factoryname", factoryname);
								
								mapList.add(recordStore);
							}
						}
						Intent toWaiRecord=new Intent(DealingTaskDetail.this,WaiRecordAct.class);
						toWaiRecord.putExtra("maplist",(Serializable) mapList);
						startActivity(toWaiRecord);

					}else{
						
					}
				}catch(JSONException e){
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
				if(queryPd!=null&&queryPd.isShowing()){
					queryPd.dismiss();
				}

			}
			
			@Override
			public void onFailure(Throwable e, String errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
				Log.d("dealingTask", "query on Failure");
				if(queryPd!=null&&queryPd.isShowing()){
					queryPd.dismiss();
				}

			}
			
		});
	}
	
	
	public void queryTuiRecordByHttp(final String case_id){//查询推定记录
		queryPd=ProgressDialog.show(DealingTaskDetail.this, "查询中", "请稍后");
		AsyncHttpClient queryClient=new AsyncHttpClient();
		queryClient.addHeader("Charset", MHttpParams.DEFAULT_CHARSET);
		queryClient.setTimeout(MHttpParams.DEFAULT_TIME_OUT);
		String dUrl=MHttpParams.IP;
		String mUrl=dealingTaskSP.getString("IP", dUrl);
		String dPort=MHttpParams.DEFAULT_PORT;
		String mPort=dealingTaskSP.getString("Port", dPort);
		String queryUrl="http://"+mUrl+":"+mPort+"/"+MHttpParams.queryTuiRecordUrl;
		RequestParams params=new RequestParams();
		//int case_id=caseidStr;
		params.put("case_id", case_id);
		queryClient.put(queryUrl, params,  new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				// TODO 自动生成的方法存根
				super.onSuccess(statusCode, response);
				if(queryPd!=null&&queryPd.isShowing()){
					queryPd.dismiss();
				}
				boolean success=false;
				try{
					success=response.getBoolean("success");
					if(success){
						ArrayList<HashMap> mapList=new ArrayList<HashMap>();
						
						
						JSONArray data=response.getJSONArray("data");
						for(int i=0;i<data.length();i++){
							HashMap recordStore=new HashMap();
							JSONObject mdata=(JSONObject) data.get(i);
							String loss_state=mdata.getString("loss_state");
							if(loss_state.equals("1")){
								String loss_finishstate="已完成";
								String addTimeStamp=mdata.getString("add_time");//时间戳
								String addTime=MUtil.getDetailTime(addTimeStamp);
								String loss_starttimeStamp=mdata.getString("loss_starttime");//时间戳
								String loss_starttime="";
								if(loss_starttimeStamp.equals("null")){
									
								}else{
									loss_starttime=MUtil.getStrTime(loss_starttimeStamp);
								}
								 
								String loss_endtimeStamp=mdata.getString("loss_endtime");//时间戳
								
								String loss_endtime="";
								if(loss_endtimeStamp.equals("null")){
									
								}else{
									loss_endtime=MUtil.getDetailTime(loss_endtimeStamp);
								}
								String loss_id=mdata.getString("id");//repair_taskid
								String loss_price=mdata.getString("loss_price");
								if(loss_price.equals("null")){
									loss_price="";
								}

								String factory_name=mdata.getString("factory_name");
								if(factory_name.equals("null")){
									factory_name="";
								}
								String loss_remark=mdata.getString("loss_remark");
								if(loss_remark.equals("null")){
									loss_remark="";
								}
								recordStore.put("loss_finishstate", loss_finishstate);
								recordStore.put("loss_id", loss_id);
								recordStore.put("add_time", addTime);
								recordStore.put("loss_starttime", loss_starttime);
								recordStore.put("loss_endtime", loss_endtime);
								recordStore.put("loss_price", loss_price);
								recordStore.put("factory_name", factory_name);
								recordStore.put("loss_remark", loss_remark);

								
								mapList.add(recordStore);
							}else{
								String loss_finishstate="推定中";
								String addTimeStamp=mdata.getString("add_time");//时间戳
								String addTime=MUtil.getDetailTime(addTimeStamp);
								String loss_starttimeStamp=mdata.getString("loss_starttime");//时间戳
								String loss_starttime="";
								if(loss_starttimeStamp.equals("null")){
									
								}else{
									loss_starttime=MUtil.getStrTime(loss_starttimeStamp);
								}
								 
								String loss_endtimeStamp=mdata.getString("loss_endtime");//时间戳
								
								String loss_endtime="";
								if(loss_endtimeStamp.equals("null")){
									
								}else{
									loss_endtime=MUtil.getDetailTime(loss_endtimeStamp);
								}
								String loss_id=mdata.getString("id");//repair_taskid
								String loss_price=mdata.getString("loss_price");
								if(loss_price.equals("null")){
									loss_price="";
								}

								String factory_name=mdata.getString("factory_name");
								if(factory_name.equals("null")){
									factory_name="";
								}
								String loss_remark=mdata.getString("loss_remark");
								if(loss_remark.equals("null")){
									loss_remark="";
								}
								
								recordStore.put("loss_finishstate", loss_finishstate);
								recordStore.put("loss_id", loss_id);
								recordStore.put("add_time", addTime);
								recordStore.put("loss_starttime", loss_starttime);
								recordStore.put("loss_endtime", loss_endtime);
								recordStore.put("loss_price", loss_price);
								recordStore.put("factory_name", factory_name);
								recordStore.put("loss_remark", loss_remark);

								
								mapList.add(recordStore);
							}
						}
						Intent toWaiRecord=new Intent(DealingTaskDetail.this,TuiRecordAct.class);
						toWaiRecord.putExtra("maplist",(Serializable) mapList);
						startActivity(toWaiRecord);

					}else{
						
					}
				}catch(JSONException e){
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
				if(queryPd!=null&&queryPd.isShowing()){
					queryPd.dismiss();
				}

			}
			
			@Override
			public void onFailure(Throwable e, String errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
				Log.d("dealingTask", "query on Failure");
				if(queryPd!=null&&queryPd.isShowing()){
					queryPd.dismiss();
				}

			}
			
		});
	}
	
	public void queryJiRecordByHttp(final String case_id){//查询稽核记录
		queryPd=ProgressDialog.show(DealingTaskDetail.this, "查询中", "请稍后");
		AsyncHttpClient queryClient=new AsyncHttpClient();
		queryClient.addHeader("Charset", MHttpParams.DEFAULT_CHARSET);
		queryClient.setTimeout(MHttpParams.DEFAULT_TIME_OUT);
		String dUrl=MHttpParams.IP;
		String mUrl=dealingTaskSP.getString("IP", dUrl);
		String dPort=MHttpParams.DEFAULT_PORT;
		String mPort=dealingTaskSP.getString("Port", dPort);
		String queryUrl="http://"+mUrl+":"+mPort+"/"+MHttpParams.queryJiRecordUrl;
		RequestParams params=new RequestParams();
		//int case_id=caseidStr;
		params.put("case_id", case_id);
		queryClient.put(queryUrl, params, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				// TODO 自动生成的方法存根
				super.onSuccess(statusCode, response);
				if(queryPd!=null&&queryPd.isShowing()){
					queryPd.dismiss();
				}
				boolean success=false;
				try{
					success=response.getBoolean("success");
					if(success){
						ArrayList<HashMap> mapList=new ArrayList<HashMap>();
						
						
						JSONArray data=response.getJSONArray("data");
						for(int i=0;i<data.length();i++){
							HashMap recordStore=new HashMap();
							JSONObject mdata=(JSONObject) data.get(i);
							String audit_state=mdata.getString("audit_state");
							if(audit_state.equals("1")){
								String audit_finishstate="已完成";
								String audit_starttimeStamp=mdata.getString("audit_starttime");//时间戳
								String audit_starttime="";
								if(audit_starttimeStamp.equals("null")){
									
								}else{
									audit_starttime=MUtil.getDetailTime(audit_starttimeStamp);
								}
								
								String jiId=mdata.getString("id");
								String audit_endtimeStamp=mdata.getString("audit_endtime");//时间戳
								String audit_endtime="";
								if(audit_endtimeStamp.equals("null")){
									
								}else{
									audit_endtime=MUtil.getStrTime(audit_endtimeStamp);
								}
								 
								String audit_remark=mdata.getString("audit_remark");
								
								recordStore.put("jiId", jiId);
								recordStore.put("audit_finishstate", audit_finishstate);
								recordStore.put("audit_starttime", audit_starttime);
								recordStore.put("audit_endtime", audit_endtime);
								recordStore.put("audit_remark", audit_remark);

								mapList.add(recordStore);
							}else{
								String audit_finishstate="稽核中";
								String audit_starttimeStamp=mdata.getString("audit_starttime");//时间戳
								String audit_starttime="";
								if(audit_starttimeStamp.equals("null")){
									
								}else{
									audit_starttime=MUtil.getDetailTime(audit_starttimeStamp);
								}
								
								String jiId=mdata.getString("id");
								String audit_endtimeStamp=mdata.getString("audit_endtime");//时间戳
								String audit_endtime="";
								if(audit_endtimeStamp.equals("null")){
									
								}else{
									audit_endtime=MUtil.getStrTime(audit_endtimeStamp);
								}
								 
								String audit_remark=mdata.getString("audit_remark");
								
								recordStore.put("jiId", jiId);
								recordStore.put("audit_finishstate", audit_finishstate);
								recordStore.put("audit_starttime", audit_starttime);
								recordStore.put("audit_endtime", audit_endtime);
								recordStore.put("audit_remark", audit_remark);

								mapList.add(recordStore);
							}
						}
						Intent toWaiRecord=new Intent(DealingTaskDetail.this,JiRecordAct.class);
						toWaiRecord.putExtra("maplist",(Serializable) mapList);
						startActivity(toWaiRecord);

					}else{
						
					}
				}catch(JSONException e){
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
				if(queryPd!=null&&queryPd.isShowing()){
					queryPd.dismiss();
				}

			}
			
			@Override
			public void onFailure(Throwable e, String errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
				Log.d("dealingTask", "query on Failure");
				if(queryPd!=null&&queryPd.isShowing()){
					queryPd.dismiss();
				}

			}
			
		});
	}
	
	public void queryStateByHttp(){//查询支线任务是否完成
		queryPd=ProgressDialog.show(DealingTaskDetail.this, "加载中", "请稍后");
		AsyncHttpClient queryClient=new AsyncHttpClient();
		queryClient.addHeader("Charset", MHttpParams.DEFAULT_CHARSET);
		queryClient.setTimeout(MHttpParams.DEFAULT_TIME_OUT);
		String dUrl=MHttpParams.IP;
		String mUrl=dealingTaskSP.getString("IP", dUrl);
		String dPort=MHttpParams.DEFAULT_PORT;
		String mPort=dealingTaskSP.getString("Port", dPort);
		String queryUrl="http://"+mUrl+":"+mPort+"/"+MHttpParams.queryUrl;
		RequestParams params=new RequestParams();
		//int case_id=caseidStr;
		params.put("case_id", caseidStr);
		queryClient.put(queryUrl, params,  new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				// TODO 自动生成的方法存根
				super.onSuccess(statusCode, response);
				if(queryPd!=null&&queryPd.isShowing()){
					queryPd.dismiss();
				}
				boolean success=false;
				try{
					success=response.getBoolean("success");
					if(success){
						JSONArray data=response.getJSONArray("data");
						JSONObject mData=(JSONObject) data.get(0);
						int repair_state=mData.getInt("repair_state");
						int audit_state=mData.getInt("audit_state");
						int loss_state=mData.getInt("loss_state");
						if(repair_state==0){
							Toast.makeText(DealingTaskDetail.this, "外修任务尚未完成", Toast.LENGTH_SHORT).show();
						}else if(audit_state==0){
							Toast.makeText(DealingTaskDetail.this, "稽核任务尚未完成", Toast.LENGTH_SHORT).show();	
						}else if(loss_state==0){
							Toast.makeText(DealingTaskDetail.this, "推定任务尚未完成", Toast.LENGTH_SHORT).show();
						}else{
							if(isYidiCar==1){//是异地车
								showCaseNoDialog();
							}else{
								CanfinishByHttp();//查询三代的步骤
								//showCaseNoDialog();
								////finishByHttp(dingsunAmount);
							}
							
						}
					}else{
						
					}
				}catch(JSONException e){
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
				if(queryPd!=null&&queryPd.isShowing()){
					queryPd.dismiss();
				}
			}
			
			@Override
			public void onFailure(Throwable e, String errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
				Log.d("dealingTask", "query on Failure");
				if(queryPd!=null&&queryPd.isShowing()){
					queryPd.dismiss();
				}
			}
			
		});
	}
	
	
	public void CanfinishByHttp(){
		AsyncHttpClient CanfinishClient=new AsyncHttpClient();
		CanfinishClient.addHeader("Charset", MHttpParams.DEFAULT_CHARSET);
		CanfinishClient.setTimeout(MHttpParams.DEFAULT_TIME_OUT);
		String dUrl=MHttpParams.IP;
		String mUrl=dealingTaskSP.getString("IP", dUrl);
		String dPort=MHttpParams.DEFAULT_PORT;
		String mPort=dealingTaskSP.getString("Port", dPort);
		String CanfinishUrl="http://"+mUrl+":"+mPort+"/"+MHttpParams.CanfinishUrl;
		RequestParams params=new RequestParams();
		String car_no=carNo_tv.getText().toString();//车牌号
		String case_no=reportNo_tv.getText().toString();//报案号
		params.put("licenseno", car_no);
		params.put("registno", case_no);
		CanfinishClient.post(CanfinishUrl, params, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				// TODO 自动生成的方法存根
				super.onSuccess(statusCode, response);
				boolean success=false;
				try{
					success=response.getBoolean("success");
					if(success){
						JSONObject data=response.getJSONObject("data");
						String enddeflossflag=data.getString("enddeflossflag");
						if(enddeflossflag.equals("1")){
							showCaseNoDialog();
							//finishByHttp(dingsunAmount);
						}else{
							Toast.makeText(DealingTaskDetail.this, "三代系统任务尚未完成", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(DealingTaskDetail.this, "系统错误", Toast.LENGTH_SHORT).show();
					}
				}catch(JSONException e){
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
			}
			@Override
			public void onFailure(Throwable e, String errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
			}
			
		});
	}
	

	public void finishByHttp(String dingsunAmount){
		AsyncHttpClient finishClient=new AsyncHttpClient();
		finishClient.addHeader("Charset", MHttpParams.DEFAULT_CHARSET);
		finishClient.setTimeout(MHttpParams.DEFAULT_TIME_OUT);
		String dUrl=MHttpParams.IP;
		String mUrl=dealingTaskSP.getString("IP", dUrl);
		String dPort=MHttpParams.DEFAULT_PORT;
		String mPort=dealingTaskSP.getString("Port", dPort);
		String finishUrl="http://"+mUrl+":"+mPort+"/"+MHttpParams.ToFinishTaskUrl;
		RequestParams params=new RequestParams();
		//int id=MyhttpStorage.id;
		int id=dealingTaskSP.getInt("id", -1);
		//int case_id=MyhttpStorage.case_id;
		String final_price=dingsunAmount;//定损金额
		params.put("id", String.valueOf(id));
		params.put("case_id", caseidStr);
		params.put("final_price", final_price);
		params.put("isYidiCar", String.valueOf(isYidiCar));
		finishClient.post(finishUrl, params,new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				// TODO 自动生成的方法存根
				super.onSuccess(statusCode, response);
				boolean success=false;
				try{
					success=response.getBoolean("success");
					if(success){
						Toast.makeText(DealingTaskDetail.this, "任务完成", Toast.LENGTH_SHORT).show();
						//刷新状态
						int resultCode=3;
						DealingTaskDetail.this.setResult(resultCode);
						DealingTaskDetail.this.finish();
					}else{
						String errormessage=response.getString("err");
						Toast.makeText(DealingTaskDetail.this, "errormessage", Toast.LENGTH_SHORT).show();	
					}
				}catch(JSONException e){
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
			}
			
			@Override
			public void onFailure(Throwable e, String errorResponse) {
				// TODO 自动生成的方法存根
				super.onFailure(e, errorResponse);
			}
			
		});
	}
	public void showCaseNoDialog(){
		final MySeCustomeDialog myDialog=new MySeCustomeDialog(this,R.style.Dialog);
		myDialog.setSeCustomDialog();
		final EditText editText=(EditText) myDialog.getEditText();
		myDialog.setTitle("定损金额");
		myDialog.setItemName("定损金额：");
		myDialog.setOnPositiveListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				String amountContent=editText.getText().toString();
				if(null!=amountContent&&amountContent.length()>0){
					dingsunamount=amountContent;
					//finishByHttp(dingsunamount);
					//CanfinishByHttp(dingsunamount);
					//queryStateByHttp(dingsunamount);
					finishByHttp(dingsunamount);

					if(null!=myDialog&&myDialog.isShowing()){
						InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						if(imm.isActive()){
							imm.hideSoftInputFromWindow(myDialog.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
						}

						myDialog.dismiss();
					}
				}else{
					Toast.makeText(DealingTaskDetail.this, "请输入定损金额", Toast.LENGTH_SHORT).show();
				}
				
				
			}
			
		});
		myDialog.setOnNegativeListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if(myDialog!=null&&myDialog.isShowing()){
					myDialog.dismiss();
				}
			}
			
		});
		myDialog.show();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO 自动生成的方法存根
		if(keyCode==KeyEvent.KEYCODE_BACK){
			//刷新任务状态
			int resultCode=3;
			DealingTaskDetail.this.setResult(resultCode);
			DealingTaskDetail.this.finish();
			return false;
		}else{
			return super.onKeyDown(keyCode, event);
		}
		
	}
	
	
}
