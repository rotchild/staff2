package com.cx.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class DynamicActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
	}
	
	protected void addView(Context context,LinearLayout ll,String name,String value){
		RelativeLayout itemRl=new RelativeLayout(context);
		RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		itemRl.setLayoutParams(params);
		TextView itemName=new TextView(context);//名称
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
	    itemRl.addView(itemName);

	    TextView itemValue=new TextView(context);
	     RelativeLayout.LayoutParams itemValue_params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	     itemValue_params.addRule(RelativeLayout.CENTER_VERTICAL);
	     itemValue_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
	     itemValue_params.rightMargin=20;
	     itemValue.setText(value);
	     itemValue.setPadding(0, 0, 0, 12);
	     itemValue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
	     itemValue.setTextColor(Color.parseColor("#C4C4C4"));
	     itemValue.setLayoutParams(itemValue_params);
	     itemRl.addView(itemValue);
	     
	     ll.addView(itemRl);
	     
	     LinearLayout line=new LinearLayout(this);
	     LayoutParams lineParams=new LayoutParams(LayoutParams.MATCH_PARENT,2);
	     
	     line.setBackgroundColor(Color.parseColor("#F5F5F5"));
	     line.setLayoutParams(lineParams);
	     ll.addView(line);

	}
	
	protected void addLineView(Context context,LinearLayout ll,int height){//分割线
		LinearLayout linell=new LinearLayout(context);
		LayoutParams lineParams=new LayoutParams(LayoutParams.MATCH_PARENT,height);
		linell.setBackgroundColor(Color.parseColor("#F5F5F5"));
		linell.setLayoutParams(lineParams);
		ll.addView(linell);
		}
}
