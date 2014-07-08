package com.limeng.fivee;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ChessView extends SurfaceView implements SurfaceHolder.Callback{
//	int flag1=0;
//	int flag2=0;
//	int flag3=0;//悔棋，选项，重开局按钮的按下状态 0抬起，1 按下
	
	int flag21=0;//0人机对战  1人人对战
	int flag22=0;// 0 初级 1 中级2 高级
	int flag23=0;//0 先手 1 后手
	int flag24=3;//获胜标志 0  黑棋胜1白棋胜 2 平局 3棋局未完成
	int flag4=0;//当前下棋者为黑子 0，为白子 1
	int what=2;//给activity发送消息，调用option Dialog
	
	private Paint paint;

	Bitmap fivelogo;//五子棋标志
	Bitmap blackchesslogo;//黑子标志
	Bitmap whitechesslogo;//白子标志
	Bitmap back;//悔棋按钮
	Bitmap option;//选项按钮
	Bitmap reopen;//重开局按钮
	Bitmap back_down;
	Bitmap option_down;
	Bitmap reopen_down;
	Bitmap blackchess;//黑棋棋子
	Bitmap whitechess;//白棋棋子
	
	Bitmap background;//背景图片
	Bitmap chessback;//棋盘背景
	Bitmap black_win;
	Bitmap white_win;
	Bitmap chess_tie;
	
	FiveActivity activity;
	Algorithm alo;
	
	ChessViewDrawThrread chessviewdrawthread;
	public ChessView(FiveActivity activity,int flag1,int flag2,int flag3) {
		super(activity);
		// TODO Auto-generated constructor stub
    	alo=new Algorithm();
		getHolder().addCallback(this);//注册接口
		this.activity = activity;
		chessviewdrawthread=new ChessViewDrawThrread(this);
		changeOption(flag1,flag2,flag3);
		init();
	}
	void ClearFalg()
	{
//		flag1=0;
//		flag2=0;
//		flag3=0;//悔棋，选项，重开局按钮的按下状态 0抬起，1 按下
		
//		flag21=0;//0人机对战  1人人对战
//		flag22=0;// 0 初级 1 中级2 高级
//		flag23=0;//0 先手 1 后手
		flag24=3;//获胜标志 0  黑棋胜1白棋胜 2 平局 3棋局未完成
		flag4=0;//当前下棋者为黑子 0，为白子 1
		what=2;//给activity发送消息，调用option Dialog
//		if(flag23==1)//flag23=0 先手 1 后手
//		{
//			alo.Add(8,8,0);
//		}
		
	}
    void init(){
    	fivelogo=BitmapFactory.decodeResource(getResources(), R.drawable.logo);
    	
    	blackchesslogo=BitmapFactory.decodeResource(getResources(), R.drawable.big_black);
    	
    	whitechesslogo=BitmapFactory.decodeResource(getResources(), R.drawable.big_white);
    	
//    	back=BitmapFactory.decodeResource(getResources(), R.drawable.backstep);
    	
//    	option=BitmapFactory.decodeResource(getResources(), R.drawable.choose2);
    	
//    	reopen=BitmapFactory.decodeResource(getResources(), R.drawable.reopen);
    	
    	back=BitmapFactory.decodeResource(getResources(), R.drawable.backstep_up);
    	
    	option=BitmapFactory.decodeResource(getResources(), R.drawable.choose2_up);
    	
    	reopen=BitmapFactory.decodeResource(getResources(), R.drawable.reopen_up);
    	
    	back_down=BitmapFactory.decodeResource(getResources(), R.drawable.backstep_up);
    	
    	option_down=BitmapFactory.decodeResource(getResources(), R.drawable.choose2_up);
    	
    	reopen_down=BitmapFactory.decodeResource(getResources(), R.drawable.reopen_up);
    	
    	background=BitmapFactory.decodeResource(getResources(), R.drawable.back);
    	
    	chessback=BitmapFactory.decodeResource(getResources(), R.drawable.chessboard);
    	
    	blackchess=BitmapFactory.decodeResource(getResources(), R.drawable.black);
    	
    	whitechess=BitmapFactory.decodeResource(getResources(), R.drawable.white);
    	black_win=BitmapFactory.decodeResource(getResources(), R.drawable.black_win);
    	white_win=BitmapFactory.decodeResource(getResources(), R.drawable.white_win);
    	chess_tie=BitmapFactory.decodeResource(getResources(), R.drawable.chess_tie);
    	
    	paint = new Paint();
		paint.setAntiAlias(true);
		//ClearFalg();
    }
    
    void changeOption(int flag1,int flag2,int flag3)//改变设置
    {
    	if(this.flag21!=flag1||this.flag22!=flag2||this.flag23!=flag3){
    		alo.clear();
	    	this.flag21=flag1;
	    	this.flag22=flag2;
	    	this.flag23=flag3;	
	    	if(flag23==1&&flag21==0)//flag23=0 先手 1 后手 flag21=0;//0人机对战  1人人对战
			{
				alo.Add(8,8,0);
				flag4=1;
			}
	    	else flag4=0;//当前下棋者为黑子 0，为白子 1
    	}
    }
    
    public void doDraw(Canvas canvas) {
    	canvas.drawBitmap(background,0,0, null);
    	canvas.drawBitmap(fivelogo,66,20, null);
    	canvas.drawBitmap(chessback,0,160, null);
    	if(flag4==0)
    		canvas.drawBitmap(blackchesslogo,0,850, null);
    	else canvas.drawBitmap(whitechesslogo,0,850, null);
//    	if(flag1==0)
//    		canvas.drawBitmap(back,94,850, null);
    	canvas.drawBitmap(back_down,94,850, null);
//    	if(flag2==0)
//    		canvas.drawBitmap(option,268,850, null);
    	canvas.drawBitmap(option_down,268,850, null);
//    	if(flag3==0)
//    		canvas.drawBitmap(reopen,436,850, null);
    	canvas.drawBitmap(reopen_down,436,850, null);
    	for(int i=0;i<16;i++)
		{
			canvas.drawLine(20, i*40+180,620, i*40+180, paint);
			canvas.drawLine(i*40+20, 180,i*40+20, 780, paint);
		}
    	for(int i=0;i<16;i++)//画棋子
    		for(int j=0;j<16;j++)
			{
    			if(alo.chessborad[i][j]==0)
    				canvas.drawBitmap(blackchess,i*40+20-20,j*40+180-20,null);
    			else if(alo.chessborad[i][j]==1)
    				canvas.drawBitmap(whitechess,i*40+20-20,j*40+180-20,null);
			}
    	if(flag24==0)
    		canvas.drawBitmap(black_win,60,400, null);
    	else if(flag24==1)
    		canvas.drawBitmap(white_win,60,400, null);
    	else if(flag24==2)
    		canvas.drawBitmap(chess_tie,60,400, null);
    }
    
    public boolean onTouchEvent(MotionEvent event) {
//    	flag1=0;
//    	flag2=0;
//    	flag3=0;
		int x = (int) event.getX();	//获取被点击处的x坐标
		int y = (int) event.getY();	//获取被点击处的y坐标
		if(x>436&&x<636&&y>850&&y<916||flag24!=3){//按下重开局按钮
//			flag3=1;
			alo.clear();
			ClearFalg();
			if(flag23==1&&flag21==0)//flag23=0 先手 1 后手 flag21=0;//0人机对战  1人人对战
			{
				alo.Add(8,8,0);
				flag4=1;
			}
			
		}//点击到棋盘上 落子
		else if(x>0&&x<640&&y>160&&y<800&&flag24==3)
		{
			if(flag21==1)//判断人人对战还是人机对战
			{
				if(alo.Add((x)/40,(y-160)/40,flag4)==0)
					{
						if(alo.Judge((x)/40,(y-160)/40,flag4)==flag4)
							flag24=flag4;
						else if(alo.Judge((x)/40,(y-160)/40,flag4)==2)
							flag24=2;
						flag4=1-flag4;
					}
			}
			else 
			{
				if(flag23==0)//用户先手
				{
					flag4=0;//当前下棋者为黑子 0，为白子 1
					if(alo.Add((x)/40,(y-160)/40,0)==0)
					{
						if(alo.Judge((x)/40,(y-160)/40,0)==0)
							{
							flag24=0;
							return super.onTouchEvent(event);
							}	
						else if(alo.Judge((x)/40,(y-160)/40,0)==2)
							{
							flag24=2;
							return super.onTouchEvent(event);
							}
						
						flag4=1;
						
						alo.Alg(flag22+1,1);//int flag22=0;// 0 初级 1 中级2 高级  第二位是便是当前下的是 黑子 0，为白子 1
						
						//调用算法求出 计算机下的位置 存储在alo类的 x,y变量中
						
						if(alo.getX()!=-1&&alo.getY()!=-1)
							alo.Add(alo.getX(),alo.getY(),1);
						
						if(alo.Judge(alo.getX(),alo.getY(),1)==1)//每下一步棋都判断是否棋局结束
							flag24=1;
						else if(alo.Judge(alo.getX(),alo.getY(),1)==2)
							flag24=2;
						flag4=0;
					}
				}
				else //用户后手
				{
					flag4=1;//当前下棋者为黑子 0，为白子 1
					if(alo.Add((x)/40,(y-160)/40,1)==0)
					{
						if(alo.Judge((x)/40,(y-160)/40,1)==1)
							{
							flag24=1;
							return super.onTouchEvent(event);
							}
						else if(alo.Judge((x)/40,(y-160)/40,1)==2)
							{
							flag24=2;
							return super.onTouchEvent(event);
							}
						flag4=0;
						alo.Alg(flag22+1,0);
						if(alo.getX()!=-1&&alo.getY()!=-1)
							alo.Add(alo.getX(),alo.getY(),0);
						if(alo.Judge(alo.getX(),alo.getY(),0)==0)
							flag24=0;
						else if(alo.Judge(alo.getX(),alo.getY(),0)==2)
							flag24=2;
						flag4=1;
					}
				}
			}
		}//点击到棋盘上 落子
		if(x>94&&x<248&&y>850&&y<912){//按下悔棋按钮
//			flag1=1;
			flag4=alo.BackStep(flag4,flag21);
			if(flag4==0&&alo.chessnum==0&&flag23==1&&flag21==0)//flag23=0 先手 1 后手 flag21=0;//0人机对战  1人人对战
			{
				alo.Add(8,8,0);//人机对战中的后手，并且悔棋后棋盘上没有棋子则为计算机添加一个黑子
				flag4=1;
			}
		}//点击到棋盘上 落子
		if(x>268&&x<396&&y>850&&y<906){//按下选项按钮
//			flag2=1;
			activity.ActHandler.sendEmptyMessage(what);
		}//点击到棋盘上 落子
		return super.onTouchEvent(event);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		 this.chessviewdrawthread.flag = true;
		 chessviewdrawthread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		boolean retry = true;
        this.chessviewdrawthread.flag =  false;
        while (retry) {
            try {
            	chessviewdrawthread.join();
                retry = false;
            } 
            catch (InterruptedException e) {}//不断地循环，直到刷帧线程结束
        }
	}

}
