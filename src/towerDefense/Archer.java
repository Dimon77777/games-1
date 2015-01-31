package towerDefense;

import java.awt.Image;

public class Archer {
	int x,y;
	Image img;
	int bulx=0;
	int buly=0;
	int range=4*32;
	boolean bulFlag=false;
	Bullet bullet;
	long time=0;

	Archer(int x,int y,Image img){
		this.x=x;
		this.y=y;
		this.img=img;
	}
	public void setXY(int x,int y ){
		this.x=x;
		this.y=y;
	}

	public boolean JudgeWithin(int ex,int ey){
		if(range>Math.sqrt((double)(Math.pow(Math.abs(x-ex),2)+Math.pow(Math.abs(y-ey),2)))){
			if(!bulFlag && System.currentTimeMillis()-time>300){
				time= System.currentTimeMillis();
				return true;
			}
		}
		return false;
	}
}
