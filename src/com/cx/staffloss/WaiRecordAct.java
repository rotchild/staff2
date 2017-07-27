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

public class WaiRecordAct extends DynamicActivity {
LinearLayout waixiuRecordLinear;
ArrayList<HashMap> maplistGet;
ImageView backImg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wairecord_layout);
		maplistGet=(ArrayList<HashMap>) getIntent().getSerializableExtra("maplist");
		initView();
	}
	private void initView() {
		// TODO �Զ����ɵķ������
		backImg=(ImageView)findViewById(R.id.waifinishdetailback_iv);
		backImg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				WaiRecordAct.this.finish();
			}
			
		});
		
		waixiuRecordLinear=(LinearLayout)findViewById(R.id.wairecord_ll);
		//addView(this,waixiuRecordLinear,"��������","2017-5-5");	
		if(maplistGet!=null&&maplistGet.size()>0){
			for(int i=0;i<maplistGet.size();i++){
				HashMap mHashMap=maplistGet.get(i);
				String repairid=(String) mHashMap.get("repairid");
				addView(this,waixiuRecordLinear,"����id",repairid);
				
				String repairfinish_state=(String) mHashMap.get("repair_finishState");
				addView(this,waixiuRecordLinear,"����״̬",repairfinish_state);
				
				String add_time=(String) mHashMap.get("add_time");
				addView(this,waixiuRecordLinear,"����ʱ��",add_time);
	
				String repairTime=(String) mHashMap.get("repairTime");
				addView(this,waixiuRecordLinear,"��������",repairTime);
				
				String repair_endtime=(String) mHashMap.get("repair_endtime");
				addView(this,waixiuRecordLinear,"�������ʱ��",repair_endtime);
				
				String repair_parts=(String) mHashMap.get("repair_parts");
				addView(this,waixiuRecordLinear,"�������",repair_parts);
				
				String repair_price=(String) mHashMap.get("repair_price");
				addView(this,waixiuRecordLinear,"�����޸����",repair_price);
				
				String parts_price=(String) mHashMap.get("parts_price");
				addView(this,waixiuRecordLinear,"��������۸�",parts_price);
				
				String repair_remark=(String) mHashMap.get("repair_remark");
				addView(this,waixiuRecordLinear,"�������뱸ע",repair_remark);
				
				String outrepair_remark=(String) mHashMap.get("outrepair_remark");
				addView(this,waixiuRecordLinear,"������ɱ�ע",outrepair_remark);
				
				String factoryname=(String) mHashMap.get("factoryname");
				addView(this,waixiuRecordLinear,"��������",factoryname);
				
				addLineView(this,waixiuRecordLinear,40);
				
			}
		}
	}
	

}
