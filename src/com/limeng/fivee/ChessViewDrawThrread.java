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
            	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
                c = this.surfaceHolder.lockCanvas(null);
                synchronized (this.surfaceHolder) {
                	chessView.doDraw(c);//绘制
                }
            } finally {
                if (c != null) {
                	//更新屏幕显示内容
                    this.surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            try{
            	//Thread.sleep(sleepSpan);//睡眠指定毫秒数
            }
            catch(Exception e){
            	e.printStackTrace();//打印堆栈信息
            }
        }
	}
}