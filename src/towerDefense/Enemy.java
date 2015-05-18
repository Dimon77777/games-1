package towerDefense;

public class Enemy {
	int x,y,direX,direY,inX,inY;
	double speed=0.5;
	int size=32;
	int point=0;	//現在位置している区間
	int G_num=0;	//キャラ画像の番号
	int count=0;	//キャラ画像の変更頻度
	boolean flag=false;
	MarchRoute[] route;
	int damage=0;
	int HP=20;

	//コンストラクタ
	Enemy(MarchRoute[] route){
		this.route=route;
		x=route[0].x*size;
		y=route[0].y*size;
		inX=x*100;
		inY=y*100;
		setNextRoute(0);
	}

	public void setXY(int x,int y){
		this.x=x;
		this.y=y;
		inX=x*100;
		inY=y*100;
	}

	//現在位置の番号が引数(0～)
	public void setNextRoute(int no){
		int gapX=route[no+1].x-route[no].x;
		int gapY=route[no+1].y-route[no].y;
		if(0<gapX)direX=1;
		if(gapX==0)direX=0;
		if(gapX<0)direX=-1;
		if(0<gapY)direY=1;
		if(gapY==0)direY=0;
		if(gapY<0)direY=-1;
	}
	public void forward(){
		inX+=speed*direX*100;
		inY+=speed*direY*100;
		x=inX/100;
		y=inY/100;
		count++;
		if(count==6){
			G_num++;
			count=0;
		}
		if(G_num==3)G_num=0;
		if(JudgeDied())return;

		//目標ポイントに着いたら次のポイントへ進行方向を変える
		if(route[point+1].x*size<x && 0<direX ){
			point++;
			judgeGoal();
			setNextRoute(point);
			return;
		}
		if(route[point+1].x*size>x && 0>direX ){
			point++;
			judgeGoal();
			setNextRoute(point);
			return;
		}
		if(route[point+1].y*size<y && 0<direY ){
			point++;
			judgeGoal();
			setNextRoute(point);
			return;
		}
		if(route[point+1].y*size>y && 0>direY ){
			point++;
			judgeGoal();
			setNextRoute(point);
			return;
		}
	}

	public void judgeGoal(){
		if(point==route.length-1){
			flag=false;
			point=0;
			//damage=0;
			setXY(route[0].x*size,route[0].y*size);
			setNextRoute(point);

		}
	}
	public boolean JudgeDied(){
		if(damage>HP){
			flag=false;
			point=0;
			damage=0;
			setXY(route[0].x*size,route[0].y*size);
			setNextRoute(point);
			return true;
		}
		return false;
	}
	public void giveDamage(int damage){
		this.damage+=damage;
	}
}
