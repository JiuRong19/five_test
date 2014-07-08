package com.limeng.fivee;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ChessViewDrawThrread extends Thread{
	boolean flag = true;
	int sleepSpan = 100;
	ChessView chessView;
	SurfaceHolder surfaceHolder;
	public ChessViewDrawThrread(ChessView chessView){
		this.chessView = chessView;
		this.surfaceHolder = chessView.getHolder();
	}
	public void run(){
		Canvas c;
        while (this.flag) {
            c = null;
            try {
            	// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
                c = this.surfaceHolder.lockCanvas(null);
                synchronized (this.surfaceHolder) {
                	chessView.doDraw(c);//����
                }
            } finally {
                if (c != null) {
                	//������Ļ��ʾ����
                    this.surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            try{
            	//Thread.sleep(sleepSpan);//˯��ָ��������
            }
            catch(Exception e){
            	e.printStackTrace();//��ӡ��ջ��Ϣ
            }
        }
	}
}