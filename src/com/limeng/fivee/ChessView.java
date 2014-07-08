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
//	int flag3=0;//���壬ѡ��ؿ��ְ�ť�İ���״̬ 0̧��1 ����
	
	int flag21=0;//0�˻���ս  1���˶�ս
	int flag22=0;// 0 ���� 1 �м�2 �߼�
	int flag23=0;//0 ���� 1 ����
	int flag24=3;//��ʤ��־ 0  ����ʤ1����ʤ 2 ƽ�� 3���δ���
	int flag4=0;//��ǰ������Ϊ���� 0��Ϊ���� 1
	int what=2;//��activity������Ϣ������option Dialog
	
	private Paint paint;

	Bitmap fivelogo;//�������־
	Bitmap blackchesslogo;//���ӱ�־
	Bitmap whitechesslogo;//���ӱ�־
	Bitmap back;//���尴ť
	Bitmap option;//ѡ�ť
	Bitmap reopen;//�ؿ��ְ�ť
	Bitmap back_down;
	Bitmap option_down;
	Bitmap reopen_down;
	Bitmap blackchess;//��������
	Bitmap whitechess;//��������
	
	Bitmap background;//����ͼƬ
	Bitmap chessback;//���̱���
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
		getHolder().addCallback(this);//ע��ӿ�
		this.activity = activity;
		chessviewdrawthread=new ChessViewDrawThrread(this);
		changeOption(flag1,flag2,flag3);
		init();
	}
	void ClearFalg()
	{
//		flag1=0;
//		flag2=0;
//		flag3=0;//���壬ѡ��ؿ��ְ�ť�İ���״̬ 0̧��1 ����
		
//		flag21=0;//0�˻���ս  1���˶�ս
//		flag22=0;// 0 ���� 1 �м�2 �߼�
//		flag23=0;//0 ���� 1 ����
		flag24=3;//��ʤ��־ 0  ����ʤ1����ʤ 2 ƽ�� 3���δ���
		flag4=0;//��ǰ������Ϊ���� 0��Ϊ���� 1
		what=2;//��activity������Ϣ������option Dialog
//		if(flag23==1)//flag23=0 ���� 1 ����
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
    
    void changeOption(int flag1,int flag2,int flag3)//�ı�����
    {
    	if(this.flag21!=flag1||this.flag22!=flag2||this.flag23!=flag3){
    		alo.clear();
	    	this.flag21=flag1;
	    	this.flag22=flag2;
	    	this.flag23=flag3;	
	    	if(flag23==1&&flag21==0)//flag23=0 ���� 1 ���� flag21=0;//0�˻���ս  1���˶�ս
			{
				alo.Add(8,8,0);
				flag4=1;
			}
	    	else flag4=0;//��ǰ������Ϊ���� 0��Ϊ���� 1
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
    	for(int i=0;i<16;i++)//������
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
		int x = (int) event.getX();	//��ȡ���������x����
		int y = (int) event.getY();	//��ȡ���������y����
		if(x>436&&x<636&&y>850&&y<916||flag24!=3){//�����ؿ��ְ�ť
//			flag3=1;
			alo.clear();
			ClearFalg();
			if(flag23==1&&flag21==0)//flag23=0 ���� 1 ���� flag21=0;//0�˻���ս  1���˶�ս
			{
				alo.Add(8,8,0);
				flag4=1;
			}
			
		}//����������� ����
		else if(x>0&&x<640&&y>160&&y<800&&flag24==3)
		{
			if(flag21==1)//�ж����˶�ս�����˻���ս
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
				if(flag23==0)//�û�����
				{
					flag4=0;//��ǰ������Ϊ���� 0��Ϊ���� 1
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
						
						alo.Alg(flag22+1,1);//int flag22=0;// 0 ���� 1 �м�2 �߼�  �ڶ�λ�Ǳ��ǵ�ǰ�µ��� ���� 0��Ϊ���� 1
						
						//�����㷨��� ������µ�λ�� �洢��alo��� x,y������
						
						if(alo.getX()!=-1&&alo.getY()!=-1)
							alo.Add(alo.getX(),alo.getY(),1);
						
						if(alo.Judge(alo.getX(),alo.getY(),1)==1)//ÿ��һ���嶼�ж��Ƿ���ֽ���
							flag24=1;
						else if(alo.Judge(alo.getX(),alo.getY(),1)==2)
							flag24=2;
						flag4=0;
					}
				}
				else //�û�����
				{
					flag4=1;//��ǰ������Ϊ���� 0��Ϊ���� 1
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
		}//����������� ����
		if(x>94&&x<248&&y>850&&y<912){//���»��尴ť
//			flag1=1;
			flag4=alo.BackStep(flag4,flag21);
			if(flag4==0&&alo.chessnum==0&&flag23==1&&flag21==0)//flag23=0 ���� 1 ���� flag21=0;//0�˻���ս  1���˶�ս
			{
				alo.Add(8,8,0);//�˻���ս�еĺ��֣����һ����������û��������Ϊ��������һ������
				flag4=1;
			}
		}//����������� ����
		if(x>268&&x<396&&y>850&&y<906){//����ѡ�ť
//			flag2=1;
			activity.ActHandler.sendEmptyMessage(what);
		}//����������� ����
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
            catch (InterruptedException e) {}//���ϵ�ѭ����ֱ��ˢ֡�߳̽���
        }
	}

}
