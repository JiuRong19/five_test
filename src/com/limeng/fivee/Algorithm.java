package com.limeng.fivee;

import java.util.Date;
import java.util.Random;



public class Algorithm {
	int [][] chessborad=new int[16][16];//表示棋盘，-1无棋子 0黑子 1白子
	int [][] chessblack=new int[128][2];//表示黑子的每一步
	int [][] chesswhite=new int[128][2];//表示白子的每一步
	int blackcountplay;//下的黑棋数
	int whitecountplay;//下的白棋数
	int x=-1;//计算后棋子的X坐标，范围0-15
	int y=-1;//计算后棋子的Y坐标，范围0-15
	int chessnum=0;//棋子总数
	


	public static final int  	NONE = -1,//无子
								BLACK = 0, //黑子
								WHITE = 1,//白子
								HENG = 0,//横方向
								SHU = 1,//竖
								R_RAMP = 2,//右斜
								L_RAMP = 3;//左斜
	public static final int  FIVE = 10000,//五连的分数
								L_FOUR = 2000,//活四
								D_FOUR = 1200,//冲四
								L_THREE = 800,//活三
								D_THREE = 180,//冲三
								L_TWO = 80,//活二
								D_TWO = 20;//冲二
	//private int model;//先后手
	//private int aiSide,humSide;
	private int[][] tempB;//棋盘数组
	private int level;//级别
	//private int stepNum;//步数
	//private int winner;//赢家
	private int minx,maxx,miny,maxy;//控制搜索范围
	
	
	Algorithm()//构造函数
	{
		clear();
	}

	int getX()
	{
		if(x>=0&&x<16)
			return x;
		//else System.out.print("x is out of the line");
		return -1;
	}
	int getY()
	{
		if(y>=0&&y<16)
			return y;
		//else System.out.print("y is out of the line");
		return -1;
	}
	void clear()
	{
		for(int i=0;i<16;i++)
			for(int j=0;j<16;j++)
				chessborad[i][j]=-1;//-1 无棋子 0 黑子 1白子
		chessnum=0;
		for(int i=0;i<128;i++)
			for(int j=0;j<2;j++)
			{
				chessblack[i][j]=-1;//黑棋数组 0-15
				chesswhite[i][j]=-1;//棋数组 0-15
				}
		blackcountplay=0;
		whitecountplay=0;
		
		//model =0;
		level =0;
		//winner = NONE;
		//stepNum = 0;
		//rows = new int[16*16];
		//cols = new int[16*16];
		tempB = new int[16][16];
//		for(int r=0;r<16;r++)
//			for(int c=0;c<16;c++)
//				tempB[r][c]=chessborad[r][c];
		for(int r=0;r<16;r++)
			for(int c=0;c<16;c++)
				tempB[r][c]=chessborad[r][c];
	}
	int Add(int x,int y,int flag)
	{//falg=0为黑子 1为白子 添加以下的棋子
		if(chessborad[x][y]!=-1)//表示棋盘，-1无棋子 0黑子 1白子
			return 1;
		//chessnum++;
		if(flag==0)
		{
			chessblack[blackcountplay][0]=x;
			chessblack[blackcountplay][1]=y;
			blackcountplay++;
		}
		else
		{
			chesswhite[whitecountplay][0]=x;
			chesswhite[whitecountplay][1]=y;
			whitecountplay++;
		}
		UpdateChessBoard();
		return 0;
	}
	int BackStep(int flag,int flag21)// 悔棋一步 falg=1为黑子 2为白子
	{
		if(flag21==0&&chessnum>0)
		{
			if(flag==1)
			{
				if(blackcountplay>0)
					blackcountplay--;
				if(whitecountplay>0)
					whitecountplay--;
			}
			else 
			{
				if(whitecountplay>0)
					whitecountplay--;
				if(blackcountplay>0)
					blackcountplay--;
			}
			UpdateChessBoard();
			if(chessnum==0)
				return 0;//若无棋可悔，该黑子下棋
			else return flag;
		}
		else if(chessnum>0)
		{
			if(flag==1&&blackcountplay>0)
			{
				blackcountplay--;
			}
			else if(flag!=1&&whitecountplay>0)
			{
				whitecountplay--;
			}
			UpdateChessBoard();
			if(chessnum==0)
				return 0;//若无棋可悔，该黑子下棋
			else return 1-flag;
		}
		
			return 0;//若无棋可悔，该黑子下棋
		
	}
	void UpdateChessBoard()//根据棋子数组更新棋盘数组
	{
		for(int i=0;i<16;i++)
			for(int j=0;j<16;j++)
				chessborad[i][j]=-1;
		chessnum=0;
		for(int i=0;i<blackcountplay;i++)
			{
			chessborad[chessblack[i][0]][chessblack[i][1]]=0;
			chessnum++;
			}
		for(int i=0;i<whitecountplay;i++)
			{
			chessborad[chesswhite[i][0]][chesswhite[i][1]]=1;
			chessnum++;
			}
			
	}
	int Judge(int x,int y,int flag)//判断输赢 flag 黑子 0，为白子 1
	{
		//return 0;
		//获胜标志 0 黑棋胜1白棋胜 2 平局 3 棋局未完成
		//先判断最后一个棋子的8个方向能不能连成五子
		//在判断chessnum==256
		int x1=x;
		int y1=y;
		int[] direction=new int [4];
		for(int i=0;i<4;i++)
			direction[i]=1;//加入坐标为 x,y的棋子
		if(chessnum<=256)
		{
			/////////////////////////方向1//////////////////////////////
			x1=x+1;
			y1=y;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad表示棋盘，-1无棋子 0黑子 1白子
			{
				x1=x1+1;
				direction[0]=direction[0]+1;
			}
			x1=x-1;
			y1=y;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad表示棋盘，-1无棋子 0黑子 1白子
			{
				x1=x1-1;
				direction[0]=direction[0]+1;
			}
			if(direction[0]>=5)
				return flag;
			
			//////////////////////////////////////////方向2/////////////////////////////////
			x1=x+1;
			y1=y+1;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad表示棋盘，-1无棋子 0黑子 1白子
			{
				x1=x1+1;
				y1=y1+1;
				direction[1]=direction[1]+1;
			}
			x1=x-1;
			y1=y-1;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad表示棋盘，-1无棋子 0黑子 1白子
			{
				x1=x1-1;
				y1=y1-1;
				direction[1]=direction[1]+1;
			}
			if(direction[1]>=5)
				return flag;
			
			//////////////////////////////方向3////////////////////////////////////
			x1=x;
			y1=y+1;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad表示棋盘，-1无棋子 0黑子 1白子
			{
				y1=y1+1;
				direction[2]=direction[2]+1;
			}
			x1=x;
			y1=y-1;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad表示棋盘，-1无棋子 0黑子 1白子
			{
				y1=y1-1;
				direction[2]=direction[2]+1;
			}
			if(direction[2]>=5)
				return flag;
			
			//////////////////////方向4///////////////////////////////
			x1=x-1;
			y1=y+1;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad表示棋盘，-1无棋子 0黑子 1白子
			{
				x1=x1-1;
				y1=y1+1;
				direction[3]=direction[3]+1;
			}
			x1=x+1;
			y1=y-1;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad表示棋盘，-1无棋子 0黑子 1白子
			{
				x1=x1+1;
				y1=y1-1;
				direction[3]=direction[3]+1;
			}
			if(direction[3]>=5)
				return flag;
		}
		else return 2;
		return 3;
	}
	int Xylimit(int x,int y)//判断XY为坐标的点是否在棋盘内  1 在棋盘范围内 0 不再棋盘范围内
	
	{
		if(x>=0&&x<16&&y>=0&&y<16)
			return 1;
		return 0;
	}
	
	void Alg(int depth,int flag4)//1简单算法 2 中计算法 3 高级算法  flag4黑子 0，为白子 1
	{
		x=-1;
		y=-1;
		if(chessnum==0)
			{
			Add(8,8,0);
			return;
			}
		
//		for(int i=0;i<16;i++)///////////测试用
//			for(int j=0;j<16;j++)
//			{
//				if(chessborad[i][j]==-1)
//				{
//					x=i;
//					y=j;
//					return;
//				}
//			}
	
//		
		for(int i=0;i<16;i++)
			for(int j=0;j<16;j++)
				tempB[i][j]=chessborad[i][j];
		level=depth*2;
		AI a=new AI();
		a.play(level,flag4);
		x=a.aix;
		y=a.aiy;
		
	}
	

	final class AI //ai类
	{		
		private int aix,aiy;//记录电脑所下的位置
		public final int INFINITY = 10000000;
		int aiflag;
		
		private void play(int d,int flag)//flag为当前电脑要下其的颜色 0 黑 1 白
		{
			aiflag=flag;
			aix=0;
			aiy=0;
			for(int r = 0; r<16; r++)
				for(int c = 0; c<16; c++)
					tempB[r][c] = chessborad[r][c];
			if(chessnum ==1)
			{//第一步在人下周围随机选择
				Random r = new Random(new Date().getTime());
//				int x = 0,
				do{
				int humR = chessblack[0][0],
					humC = chessblack[0][1];
					switch(r.nextInt(8))
					{
						case 0: aix = humR+1; aiy = humC+1; break;
						case 1: aix = humR+1; aiy = humC;   break;
						case 2: aix = humR+1; aiy = humC-1; break;
						case 3: aix = humR  ; aiy = humC+1; break;
						case 4: aix = humR  ; aiy = humC-1; break;
						case 5: aix = humR-1; aiy = humC+1; break;
						case 6: aix = humR-1; aiy = humC  ; break;
						case 7: aix = humR-1; aiy = humC-1; break;
					}
				}while(!(aix>-1&&aix<16&&aiy>-1&&aiy<16&&chessborad[aix][aiy]==-1));
					step(aix,aiy);
					return ;
			}
			else if(!must()){//判断有没有必须应的着，若没，进行搜索
					search(d,flag,-INFINITY,INFINITY);
					step(aix,aiy);
//					add(aix,aiy,flag);
			}
			//oneRoundisEnd = true;
		}
		private int search(int deep,int side,int alpha, int beta)
		{//alpha beta搜索
			if(deep <= 0)
				return evlation();	
			for (int row = minx; row <= maxx; row++)
	    		for (int col = miny; col <= maxy; col++)
	    		{
	    			if(tempB[row][col] == NONE)
	    			{	
	    				tempB[row][col] = side;
	    				int v = -search(deep-1,-side,-beta,-alpha);
	    				tempB[row][col] = NONE;
	    				if (v >= beta) { 
	    					return beta; 
	    				}
	    				if (v > alpha) {
	    					if(deep == level){
	    						aix = row;
	    						aiy = col;
	    					}
	    					alpha = v; 
	    				} 

	    			}
	    		}
			return alpha;
		}
		
		private boolean must()
		{//判断有没有必须应的着，如五连双三之类
			int aiR = 0,aiC = 0,aiScore = 0,
				humR = 0,humC = 0,humScore = 0;
			for (int r = 0; r < 16; r++)
	    		for (int c = 0; c < 16; c++){
	    			if(tempB[r][c] == NONE){
	    				int ais =score(aiflag,r,c,HENG)+
							score(aiflag,r,c,SHU)+
							score(aiflag,r,c,R_RAMP)+
							score(aiflag,r,c,L_RAMP);
	    				int hums = score(1-aiflag,r,c,HENG)+
							score(1-aiflag,r,c,SHU)+
							score(1-aiflag,r,c,R_RAMP)+
							score(1-aiflag,r,c,L_RAMP);
	    				if(aiScore < ais){
	    					aiScore = ais;
	    					aiR = r;
	    					aiC = c;
	    				}
	    				if(humScore < hums){
	    					humScore = hums;
	    					humR = r;
	    					humC = c;
	    				}
	    			
	    			}
	    		}
			if(aiScore>=humScore&&aiScore>=L_THREE*2){
				aix=aiR;
				aiy=aiC;
				step(aix,aiy);
				return true;
			}
			if(aiScore<humScore&&humScore>=L_THREE*2){
				aix=humR;
				aiy=humC;
				step(aix,aiy);
				return true;
			}					
			return false;
		}
				
				
		private int evlation()//评价函数
		{
			int s1, s2, s3, s4,score = 0;
			for (int x1 = minx; x1 <= maxx; x1++)
	    		for (int y1 = miny; y1 <= maxx; y1++)
	    			if(tempB[x1][y1] == NONE)
	    			{
	    				s1=score(aiflag,x1,y1,HENG)-score(1-aiflag,x1,y1,HENG)/2;
	    				s2=score(aiflag,x1,y1,SHU)-score(1-aiflag,x1,y1,SHU)/2;
	    				s3=score(aiflag,x1,y1,R_RAMP)-score(1-aiflag,x1,y1,R_RAMP)/2;
	    				s4=score(aiflag,x1,y1,L_RAMP)-score(1-aiflag,x1,y1,L_RAMP)/2;
	    				score = score+s1+s2+s3+s4;
	    			}
			return score;
		}
		
		private int score(int side,int r,int c,int direction)//返回一个方向的棋形
		{
			int num = 1,//棋形
					m = 1,n = 1;
				final int ltag = 10,rtag = 100,//两个tag为两端死活标志，为方便switch使用（提高效率）为整型
							a,b;
				switch(direction)
				{
					case HENG   : a = 0; b = 1; break;
					case SHU  : a = 1; b = 0; break;
					case R_RAMP : a = 1; b = 1; break;
					case L_RAMP : a = 1; b = -1; break;
					default	: a = 0;  b = 0;
				}
				for(m=1;m<5;m++)//分析当前棋子左五个
				{
					int row=r-a*m,col=c-b*m;
					if(row<0||col>=16||col<0){
						num = num+ltag;//若为死num加上标志，下类同
						break;
					}
					int temp = tempB[row][col];
					if(temp == side)
						num++;
					else if(temp == NONE)
						break;
					else{
						num = num+ltag;
						break;
					}
				}
				for(n = 1; n < 5; n++)
				{
					int row = r+a*n, col = c+b*n;
					if(row>=16||col>=16||col<0){
						num = num+rtag;
						break;
					}
					int temp = tempB[row][col];
					if(temp == side)
						num++;
					else if(temp == NONE)
						break;
					else{
						num = num+rtag;
						break;
					}
				
				}
				int row,col;
				switch(num)
				{
					case 1	:return 0;
					case 11	:return 0;
					case 101:return 0;
					case 111:return 0;
					case 2	:
						m++;
						row = r-a*m;
						col = c-b*m;
						if(row>=0&&col>=0&&col<16)
							if(tempB[row][col]!=-side)
								return L_TWO;
						n++;
						row = r+a*n;
						col = c+b*n;
						if(row<16&&col>=0&&col<16)
							if(tempB[row][col]!=-side)
								return L_TWO;
						return 0;
					case 12	:
						n=n+2;
						row = r+a*n;
						col = c+b*n;
						if(row<16&&col>=0&&col<16)
							if(tempB[row][col]!=-side&&tempB[row-a][col-b]!=-side)
								return D_TWO;
						return 0;
					case 102:
						m=m+2;
						row = r-a*m;
						col = c-b*m;
						if(row>=0&&col>=0&&col<16)
							if(tempB[row][col]!=-side&&tempB[row+a][col+b]!=-side)
								return D_TWO;
						return 0;
					case 112:return 0;
					case 3	:return L_THREE;
					case 13:
						n++;
						row = r+a*n;
						col = c+b*n;
						if(row<16&&col>=0&&col<16)
							if(tempB[row][col]!=-side)
								return D_THREE;
						return 0;
					case 103:
						m++;
						row = r-a*m;
						col = c-b*m;
						if(row>=0&&col>=0&&col<16)
							if(tempB[row][col]!=-side)
								return D_THREE;
						return 0;
					case 113:return 0;
					case 4	:return L_FOUR;
					case 14	:return D_FOUR;
					case 104:return D_FOUR;
					case 114:return 0;
					default :return FIVE;
				}
		}

	private boolean step(int r,int c)//计算范围
		{
			if(tempB[r][c] == NONE)
			{
//				tempB[r][c] = side;
//				rows[chessnum] = r;
//				cols[chessnum] = c;
				//aix=r;
				//aiy=c;
//				frame.rep(r,c,tempB);
				//chessnum++;
				//win(r,c,side);
				if(chessnum == 0)//计算范围
				{
					minx=r-level-1;
					maxx=r+level+1;
					miny=c-level-1;
					maxy=c+level+1;
				}
				else
				{
					if(r-level-1<minx) 
						minx = Math.max(r-level-1,0);
					if(r+level+1>maxx) 
						maxx = Math.min(r+level+1,16-1); 
					if(c-level-1<miny) 
						miny = Math.max(c-level-1,0); 
					if(c+level+1>maxy) 
						maxy = Math.min(c+level+1,16-1);
				}
				return true;
			}
			else
				return false;
		}
	}
	
}
