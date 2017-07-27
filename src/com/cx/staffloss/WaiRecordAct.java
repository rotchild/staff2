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
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wairecord_layout);
		maplistGet=(ArrayList<HashMap>) getIntent().getSerializableExtra("maplist");
		initView();
	}
	private void initView() {
		// TODO 自动生成的方法存根
		backImg=(ImageView)findViewById(R.id.waifinishdetailback_iv);
		backImg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				WaiRecordAct.this.finish();
			}
			
		});
		
		waixiuRecordLinear=(LinearLayout)findViewById(R.id.wairecord_ll);
		//addView(this,waixiuRecordLinear,"外修日期","2017-5-5");	
		if(maplistGet!=null&&maplistGet.size()>0){
			for(int i=0;i<maplistGet.size();i++){
				HashMap mHashMap=maplistGet.get(i);
				String repairid=(String) mHashMap.get("repairid");
				addView(this,waixiuRecordLinear,"外修id",repairid);
				
				String repairfinish_state=(String) mHashMap.get("repair_finishState");
				addView(this,waixiuRecordLinear,"任务状态",repairfinish_state);
				
				String add_time=(String) mHashMap.get("add_time");
				addView(this,waixiuRecordLinear,"分配时间",add_time);
	
				String repairTime=(String) mHashMap.get("repairTime");
				addView(this,waixiuRecordLinear,"外修日期",repairTime);
				
				String repair_endtime=(String) mHashMap.get("repair_endtime");
				addView(this,waixiuRecordLinear,"外修完成时间",repair_endtime);
				
				String repair_parts=(String) mHashMap.get("repair_parts");
				addView(this,waixiuRecordLinear,"外修配件",repair_parts);
				
				String repair_price=(String) mHashMap.get("repair_price");
				addView(this,waixiuRecordLinear,"外修修复金额",repair_price);
				
				String parts_price=(String) mHashMap.get("parts_price");
				addView(this,waixiuRecordLinear,"外修配件价格",parts_price);
				
				String repair_remark=(String) mHashMap.get("repair_remark");
				addView(this,waixiuRecordLinear,"外修申请备注",repair_remark);
				
				String outrepair_remark=(String) mHashMap.get("outrepair_remark");
				addView(this,waixiuRecordLinear,"外修完成备注",outrepair_remark);
				
				String factoryname=(String) mHashMap.get("factoryname");
				addView(this,waixiuRecordLinear,"修理厂名称",factoryname);
				
				addLineView(this,waixiuRecordLinear,40);
				
			}
		}
	}
	

}
