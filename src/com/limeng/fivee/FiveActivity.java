package com.limeng.fivee;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.SurfaceView;

public class FiveActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	public int flag1=0; //0人机对战  1人人对战
	public int flag2=0;// 0 初级 1 中级2 高级
	public int flag4=0;//0 先手 1 后手
	int flag=0;//0:开始界面调用dialog 1 游戏界面调用dialog
	ImageButton[] buttons=new ImageButton[3];
	private int[] mode={R.drawable.computer,R.drawable.people};
	private int[] level={R.drawable.level1,R.drawable.level2,R.drawable.level3};
	private int[] first_behind={R.drawable.first,R.drawable.behind};
	
	ChessView chessview;
	
	Handler ActHandler=new Handler(){
		public void handleMessage(Message msg){
			if(msg.what==2){//显示选择选项对话框
				flag=1;
				showDialog(1);
			}
		}
	};
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置全屏
        setContentView(R.layout.welcome);
        chessview = new ChessView(this,flag1,flag2,flag4);			//创建GameView
        buttons[0]=(ImageButton)findViewById(R.id.begin);
        buttons[1]=(ImageButton)findViewById(R.id.choose);
        buttons[2]=(ImageButton)findViewById(R.id.exit);
        for(int i=0;i<3;i++)
        	buttons[i].setOnClickListener(FiveActivity.this);
    }
    
    
    public void onClick(View v){
    	switch(v.getId()){
    	case R.id.begin :{
            setContentView(chessview);		
    		//this.setContentView(R.layout.game_main); //要先显示然后再对其组件取出、处理操作
		}break;
    	case R.id.choose :{flag=0;showDialog(1);}break;
    	case R.id.exit :{System.exit(0);}break;
    	}
	}
    
    protected Dialog onCreateDialog(int id){
    	Dialog dialog=null;
    	View option=LayoutInflater.from(this).inflate(R.layout.option, null);
    	Builder builder=new AlertDialog.Builder(FiveActivity.this);
    	builder.setView(option);
    	builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {//设置确定按钮点击的事件Listener
    				@Override
    				public void onClick(DialogInterface dialog, int which) {
    					dialog.dismiss();//添加音乐的change函数
    					//Toast.makeText(FiveActivity.this,FiveActivity.this.flag1+" "+FiveActivity.this.flag2+" "+FiveActivity.this.flag4 , Toast.LENGTH_LONG).show();
    					//if(flag==1)
    					{
    						//chessview=null;
    						chessview.changeOption(flag1,flag2,flag4);		//创建GameView
    			            //setContentView(chessview);	
    					}
    				}
    			});
//     	builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {//设置确定按钮点击的事件Listener
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();//添加音乐的change函数
//			}
//		});
    	dialog=builder.show();
    	setspinner(option);
    	return dialog;
   }
    
    
    
    void setspinner(View v){
    	 Spinner spinner1=(Spinner)v.findViewById(R.id.Spinner01);
	        BaseAdapter ba1=new BaseAdapter(){//为Spinner准备内容适配器
				@Override
				public int getCount() {return 2;}//总共两个选项
				@Override
				public Object getItem(int arg0) { return null; }
				@Override
				public long getItemId(int arg0) { return 0; }
				@Override
				public View getView(int arg0, View arg1, ViewGroup arg2) {
					ImageView  ii=new ImageView(FiveActivity.this);
					ii.setId(arg0);
					ii.setImageDrawable(getResources().getDrawable(mode[arg0]));//设置图片
					return ii;
				}        	
	        };
	        spinner1.setAdapter(ba1);
	        spinner1.setOnItemSelectedListener(new OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {//重写选项被选中事件的处理方法
					FiveActivity.this.flag1=arg2;
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) { }        	   
	           }
	        );
	        
	        //////////////////////////////////////////////////////////////
	        Spinner spinner2=(Spinner)v.findViewById(R.id.Spinner02);
	        BaseAdapter ba2=new BaseAdapter(){//为Spinner准备内容适配器
				@Override
				public int getCount() {return 3;}//总共三个选项
				@Override
				public Object getItem(int arg0) { return null; }
				@Override
				public long getItemId(int arg0) { return 0; }
				@Override
				public View getView(int arg0, View arg1, ViewGroup arg2) {
					ImageView  ii=new ImageView(FiveActivity.this);
					ii.setId(arg0);
					ii.setImageDrawable(getResources().getDrawable(level[arg0]));//设置图片
					return ii;
				}        	
	        };
	        spinner2.setAdapter(ba2);
	        spinner2.setOnItemSelectedListener(new OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {//重写选项被选中事件的处理方法
					FiveActivity.this.flag2=arg2;
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) { }        	   
	           }
	        );
	        Spinner spinner4=(Spinner)v.findViewById(R.id.Spinner04);
	        BaseAdapter ba4=new BaseAdapter(){//为Spinner准备内容适配器
				@Override
				public int getCount() {return 2;}//总共三个选项
				@Override
				public Object getItem(int arg0) { return null; }
				@Override
				public long getItemId(int arg0) { return 0; }
				@Override
				public View getView(int arg0, View arg1, ViewGroup arg2) {
					ImageView  ii=new ImageView(FiveActivity.this);
					ii.setId(arg0);
					ii.setImageDrawable(getResources().getDrawable(first_behind[arg0]));//设置图片
					return ii;
				}        	
	        };
	        spinner4.setAdapter(ba4);
	        spinner4.setOnItemSelectedListener(new OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {//重写选项被选中事件的处理方法
					FiveActivity.this.flag4=arg2;
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) { }        	   
	           }
	        );
    }
}
