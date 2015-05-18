package towerDefense;

import java.awt.Image;

public class Archer {
	int x,y;
	Image img;
	int bulx=0;
	int buly=0;
	int range=4*32;
	boolean bulFlag=false;	//弾が画面に存在しているか
	Bullet bullet;
	long time=0;
	long cooldown_time=300;
	Archer(int x,int y,Image img){
		this.x=x;
		this.y=y;
		this.img=img;
	}
	public void setXY(int x,int y ){
		this.x=x;
		this.y=y;
	}

	//敵が範囲内にいるか判定
	public boolean JudgeWithin(int ex,int ey){
		//range<敵との距離であるときtrue
		if(range>Math.sqrt((double)(Math.pow(Math.abs(x-ex),2)+Math.pow(Math.abs(y-ey),2)))){
			if(!bulFlag && System.currentTimeMillis()-time>cooldown_time){//弾が存在していない&クールダウンを終えているとtrue
				time= System.currentTimeMillis();
				return true;
			}
		}
		return false;
	}
}
