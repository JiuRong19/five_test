package com.limeng.fivee;

import java.util.Date;
import java.util.Random;



public class Algorithm {
	int [][] chessborad=new int[16][16];//��ʾ���̣�-1������ 0���� 1����
	int [][] chessblack=new int[128][2];//��ʾ���ӵ�ÿһ��
	int [][] chesswhite=new int[128][2];//��ʾ���ӵ�ÿһ��
	int blackcountplay;//�µĺ�����
	int whitecountplay;//�µİ�����
	int x=-1;//��������ӵ�X���꣬��Χ0-15
	int y=-1;//��������ӵ�Y���꣬��Χ0-15
	int chessnum=0;//��������
	


	public static final int  	NONE = -1,//����
								BLACK = 0, //����
								WHITE = 1,//����
								HENG = 0,//�᷽��
								SHU = 1,//��
								R_RAMP = 2,//��б
								L_RAMP = 3;//��б
	public static final int  FIVE = 10000,//�����ķ���
								L_FOUR = 2000,//����
								D_FOUR = 1200,//����
								L_THREE = 800,//����
								D_THREE = 180,//����
								L_TWO = 80,//���
								D_TWO = 20;//���
	//private int model;//�Ⱥ���
	//private int aiSide,humSide;
	private int[][] tempB;//��������
	private int level;//����
	//private int stepNum;//����
	//private int winner;//Ӯ��
	private int minx,maxx,miny,maxy;//����������Χ
	
	
	Algorithm()//���캯��
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
				chessborad[i][j]=-1;//-1 ������ 0 ���� 1����
		chessnum=0;
		for(int i=0;i<128;i++)
			for(int j=0;j<2;j++)
			{
				chessblack[i][j]=-1;//�������� 0-15
				chesswhite[i][j]=-1;//������ 0-15
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
	{//falg=0Ϊ���� 1Ϊ���� ������µ�����
		if(chessborad[x][y]!=-1)//��ʾ���̣�-1������ 0���� 1����
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
	int BackStep(int flag,int flag21)// ����һ�� falg=1Ϊ���� 2Ϊ����
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
				return 0;//������ɻڣ��ú�������
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
				return 0;//������ɻڣ��ú�������
			else return 1-flag;
		}
		
			return 0;//������ɻڣ��ú�������
		
	}
	void UpdateChessBoard()//�����������������������
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
	int Judge(int x,int y,int flag)//�ж���Ӯ flag ���� 0��Ϊ���� 1
	{
		//return 0;
		//��ʤ��־ 0 ����ʤ1����ʤ 2 ƽ�� 3 ���δ���
		//���ж����һ�����ӵ�8�������ܲ�����������
		//���ж�chessnum==256
		int x1=x;
		int y1=y;
		int[] direction=new int [4];
		for(int i=0;i<4;i++)
			direction[i]=1;//��������Ϊ x,y������
		if(chessnum<=256)
		{
			/////////////////////////����1//////////////////////////////
			x1=x+1;
			y1=y;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad��ʾ���̣�-1������ 0���� 1����
			{
				x1=x1+1;
				direction[0]=direction[0]+1;
			}
			x1=x-1;
			y1=y;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad��ʾ���̣�-1������ 0���� 1����
			{
				x1=x1-1;
				direction[0]=direction[0]+1;
			}
			if(direction[0]>=5)
				return flag;
			
			//////////////////////////////////////////����2/////////////////////////////////
			x1=x+1;
			y1=y+1;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad��ʾ���̣�-1������ 0���� 1����
			{
				x1=x1+1;
				y1=y1+1;
				direction[1]=direction[1]+1;
			}
			x1=x-1;
			y1=y-1;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad��ʾ���̣�-1������ 0���� 1����
			{
				x1=x1-1;
				y1=y1-1;
				direction[1]=direction[1]+1;
			}
			if(direction[1]>=5)
				return flag;
			
			//////////////////////////////����3////////////////////////////////////
			x1=x;
			y1=y+1;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad��ʾ���̣�-1������ 0���� 1����
			{
				y1=y1+1;
				direction[2]=direction[2]+1;
			}
			x1=x;
			y1=y-1;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad��ʾ���̣�-1������ 0���� 1����
			{
				y1=y1-1;
				direction[2]=direction[2]+1;
			}
			if(direction[2]>=5)
				return flag;
			
			//////////////////////����4///////////////////////////////
			x1=x-1;
			y1=y+1;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad��ʾ���̣�-1������ 0���� 1����
			{
				x1=x1-1;
				y1=y1+1;
				direction[3]=direction[3]+1;
			}
			x1=x+1;
			y1=y-1;
			while(Xylimit(x1,y1)==1&&chessborad[x1][y1]==flag)//chessborad��ʾ���̣�-1������ 0���� 1����
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
	int Xylimit(int x,int y)//�ж�XYΪ����ĵ��Ƿ���������  1 �����̷�Χ�� 0 �������̷�Χ��
	
	{
		if(x>=0&&x<16&&y>=0&&y<16)
			return 1;
		return 0;
	}
	
	void Alg(int depth,int flag4)//1���㷨 2 �м��㷨 3 �߼��㷨  flag4���� 0��Ϊ���� 1
	{
		x=-1;
		y=-1;
		if(chessnum==0)
			{
			Add(8,8,0);
			return;
			}
		
//		for(int i=0;i<16;i++)///////////������
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
	

	final class AI //ai��
	{		
		private int aix,aiy;//��¼�������µ�λ��
		public final int INFINITY = 10000000;
		int aiflag;
		
		private void play(int d,int flag)//flagΪ��ǰ����Ҫ�������ɫ 0 �� 1 ��
		{
			aiflag=flag;
			aix=0;
			aiy=0;
			for(int r = 0; r<16; r++)
				for(int c = 0; c<16; c++)
					tempB[r][c] = chessborad[r][c];
			if(chessnum ==1)
			{//��һ����������Χ���ѡ��
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
			else if(!must()){//�ж���û�б���Ӧ���ţ���û����������
					search(d,flag,-INFINITY,INFINITY);
					step(aix,aiy);
//					add(aix,aiy,flag);
			}
			//oneRoundisEnd = true;
		}
		private int search(int deep,int side,int alpha, int beta)
		{//alpha beta����
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
		{//�ж���û�б���Ӧ���ţ�������˫��֮��
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
				
				
		private int evlation()//���ۺ���
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
		
		private int score(int side,int r,int c,int direction)//����һ�����������
		{
			int num = 1,//����
					m = 1,n = 1;
				final int ltag = 10,rtag = 100,//����tagΪ���������־��Ϊ����switchʹ�ã����Ч�ʣ�Ϊ����
							a,b;
				switch(direction)
				{
					case HENG   : a = 0; b = 1; break;
					case SHU  : a = 1; b = 0; break;
					case R_RAMP : a = 1; b = 1; break;
					case L_RAMP : a = 1; b = -1; break;
					default	: a = 0;  b = 0;
				}
				for(m=1;m<5;m++)//������ǰ���������
				{
					int row=r-a*m,col=c-b*m;
					if(row<0||col>=16||col<0){
						num = num+ltag;//��Ϊ��num���ϱ�־������ͬ
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

	private boolean step(int r,int c)//���㷶Χ
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
				if(chessnum == 0)//���㷶Χ
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
