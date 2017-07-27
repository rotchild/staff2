package com.cx.staffloss;

import java.util.ArrayList;
import java.util.HashMap;

import com.cx.utils.DynamicActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TuiRecordAct extends DynamicActivity {
	LinearLayout tuiRecordLinear;
	ArrayList<HashMap> maplistGet;
	ImageView backImg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tuirecord_layout);
		maplistGet=(ArrayList<HashMap>) getIntent().getSerializableExtra("maplist");
		initView();
	}
	private void initView() {
		// TODO �Զ����ɵķ������
		backImg=(ImageView)findViewById(R.id.tuifinishdetailback_iv);
		backImg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				TuiRecordAct.this.finish();
			}
			
		});
		
		tuiRecordLinear=(LinearLayout)findViewById(R.id.tuirecord_ll);
		if(maplistGet!=null&&maplistGet.size()>0){
			for(int i=0;i<maplistGet.size();i++){
				HashMap mHashMap=maplistGet.get(i);
				String loss_id=(String) mHashMap.get("loss_id");
				addView(this,tuiRecordLinear,"�ƶ�id",loss_id);
				
				String loss_finishstate=(String) mHashMap.get("loss_finishstate");
				addView(this,tuiRecordLinear,"����״̬",loss_finishstate);
				
				String add_time=(String) mHashMap.get("add_time");
				addView(this,tuiRecordLinear,"����ʱ��",add_time);
	
				String loss_starttime=(String) mHashMap.get("loss_starttime");
				addView(this,tuiRecordLinear,"�ƶ���ʼ",loss_starttime);
				
				String loss_endtime=(String) mHashMap.get("loss_endtime");
				addView(this,tuiRecordLinear,"�ƶ����ʱ��",loss_endtime);
				
				String loss_price=(String) mHashMap.get("loss_price");
				addView(this,tuiRecordLinear,"������",loss_price);
				
				String factory_name=(String) mHashMap.get("factory_name");
				addView(this,tuiRecordLinear,"��������",factory_name);
				
				String loss_remark=(String) mHashMap.get("loss_remark");
				addView(this,tuiRecordLinear,"�ƶ���ע",loss_remark);
				
				addLineView(this,tuiRecordLinear,40);
				
			}
		}
	}
}
