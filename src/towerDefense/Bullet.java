package towerDefense;

public class Bullet {
	int x,y,ex,ey,tx,ty,inx,iny;
	double radian,sin,cos;
	double speed=6;
	int count=0;
	boolean flag;
	int range=4*32;
	double progress=0;
	int code;
	long time=0;
	long time2=0;
	Bullet(){}
	Bullet(int code,int enemyX,int enemyY,int TowerX,int TowerY){
		this.code=code;
		ex=enemyX;
		ey=enemyY;
		tx=TowerX;
		ty=TowerY;
		x=tx;
		y=ty;
		inx=x*100;
		iny=y*100;
		radian=Math.atan2(ey-ty, ex-tx);
		sin=Math.sin(radian);
		cos=Math.cos(radian);
	}
	public boolean moveBullet(){
		inx+=speed*cos*100;
		iny+=speed*sin*100;
		x=inx/100;
		y=iny/100;
		progress+=Math.sqrt(Math.pow(speed*sin, 2)+Math.pow(speed*cos, 2));
		if(progress>Math.sqrt(Math.pow(ex-tx, 2)+Math.pow(ey-ty, 2))){
			progress=0;
			flag=false;
			
		}else{
			flag=true;
		}
		return flag;
	}

}
