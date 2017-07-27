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

public class JiRecordAct extends DynamicActivity {
	LinearLayout jiRecordLinear;
	ArrayList<HashMap> maplistGet;
	ImageView backImg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jirecord_layout);
		maplistGet=(ArrayList<HashMap>) getIntent().getSerializableExtra("maplist");
		initView();
	}
	private void initView() {
		// TODO �Զ����ɵķ������
		backImg=(ImageView)findViewById(R.id.jifinishdetailback_iv);
		backImg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				JiRecordAct.this.finish();
			}
			
		});
		
		jiRecordLinear=(LinearLayout)findViewById(R.id.jirecord_ll);
		if(maplistGet!=null&&maplistGet.size()>0){
			for(int i=0;i<maplistGet.size();i++){
				HashMap mHashMap=maplistGet.get(i);
				String jiId=(String) mHashMap.get("jiId");
				addView(this,jiRecordLinear,"����id",jiId);
				
				String audit_finishstate=(String) mHashMap.get("audit_finishstate");
				addView(this,jiRecordLinear,"����״̬",audit_finishstate);
				
				String audit_starttime=(String) mHashMap.get("audit_starttime");
				addView(this,jiRecordLinear,"���˿�ʼʱ��",audit_starttime);
	
				String audit_endtime=(String) mHashMap.get("audit_endtime");
				addView(this,jiRecordLinear,"���˽���ʱ��",audit_endtime);
				
				String audit_remark=(String) mHashMap.get("audit_remark");
				addView(this,jiRecordLinear,"���˱�ע",audit_remark);
				
				addLineView(this,jiRecordLinear,40);
				
			}
		}
	}
}
