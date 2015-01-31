package towerDefense;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;


public class TowerDefense extends Applet implements Runnable,KeyListener,MouseListener,MouseMotionListener{
	Thread gameThread;		// ゲームスレッド
	int scene=0;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keySpace;
	boolean keyEnter;
	boolean keyA;
	boolean keyB;
	char typekey=0;
	
	int width=608;
	int height=608;
	Graphics gv;
	Image offImage;
	Image star;
	Image zeni;
	Image enemy[]=new Image[3];
	Image map;
	Image Image_archer;
	Image Image_barricade;
	Image bullet;
	Image underbar;
	Image selected_archer;
	Image image;
	Image frame;
	int size=32;
	int x,y;
	int move=1;
	int Ssize=80;
	boolean xFlag,yFlag=true;
	Point point;
	double degree=45;
	int degreeWay=1;
	double radians;
	double rx,ry;
	int radius=100;
	int increaseRad=1;
	int centerPointX=300;
	int centerPointY=300;
	LinkedList<Archer> archer = new LinkedList<Archer>();
	LinkedList<Barricade> barricade = new LinkedList<Barricade>();
	int dragFlag=0;
	int Mpoint=10;	//敵進軍方向変更回数+2
    MarchRoute[] route=new MarchRoute[Mpoint];
    int NumberOfEnemys=60;
    Enemy ene[]=new Enemy[NumberOfEnemys];
    int RespawnCount=0;
    int[] SpawnInterval=new int[NumberOfEnemys];
    Bullet[] bul=new Bullet[100];
    long time;
    ArrayList<Integer> code=new ArrayList<Integer>();
    String TowerType="";
    boolean BlinkTitle=false;
	int AsizeX=44;
	int AsizeY=75;

	public void init(){
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setSize(new Dimension(width,height));
		offImage=createImage(getWidth(),getHeight());
		setBackground(Color.BLACK);
	    try(FileInputStream in1=new FileInputStream("images\\star.png");//FileInputStreamを作る
	    		FileInputStream in2=new FileInputStream("images\\zeni.png");
	    		FileInputStream in3=new FileInputStream("images\\map_1.png");
	    		FileInputStream in4=new FileInputStream("images\\enemy.png");
	    		FileInputStream in5=new FileInputStream("images\\enemy2.png");
	    		FileInputStream in6=new FileInputStream("images\\enemy3.png");
	    		FileInputStream in7=new FileInputStream("images\\archer.png");
	    		FileInputStream in8=new FileInputStream("images\\tama.png");
	    		FileInputStream in9=new FileInputStream("images\\underbar.png");
	    		FileInputStream in10=new FileInputStream("images\\selected_archer.png");
	    		FileInputStream in11=new FileInputStream("images\\frame.png");
	    		FileInputStream in12=new FileInputStream("images\\barricade.png")

	    		){
	    	star=ImageIO.read(in1);//イメージを取り込む
	    	zeni=ImageIO.read(in2);
	    	map=ImageIO.read(in3);
	    	enemy[0]=ImageIO.read(in4);
	    	enemy[1]=ImageIO.read(in5);
	    	enemy[2]=ImageIO.read(in6);
	    	Image_archer=ImageIO.read(in7);
	    	bullet=ImageIO.read(in8);
	    	underbar=ImageIO.read(in9);
	    	selected_archer=ImageIO.read(in10);
	    	frame=ImageIO.read(in11);
	    	Image_barricade=ImageIO.read(in12);
	    	image=star;
	    	gv=offImage.getGraphics();
			// 自機画像読み込み
	    }catch(IOException e){
		        //エラー時の処理（エラーを表示）しnullを返す
	    	System.out.println("Err e="+e);//エラーを表示
	    }
	    for(int i=0;i<Mpoint;i++){
	    	route[i]=new MarchRoute();
	    }
	    SetRoute(route);
	    for(int i=0;i<NumberOfEnemys;i++){
	    	ene[i]=new Enemy(route);
	    }
	    x=200;
	    y=300;
	    radians=Math.toRadians(degree);
	    rx=Math.cos(radians);
	    ry=Math.sin(radians);

	    time=System.currentTimeMillis();
	    for(int i=0;i<NumberOfEnemys;i++){
	    	code.add(i);
	    }
	    SetSpawnInterval();

	}

	public void SetSpawnInterval() {
		SpawnInterval[0]=0;
		SpawnInterval[1]=5000;
		SpawnInterval[2]=500;
		SpawnInterval[3]=2000;
		SpawnInterval[4]=500;
		SpawnInterval[5]=500;
		SpawnInterval[6]=3000;
		SpawnInterval[7]=200;
		SpawnInterval[8]=300;
		SpawnInterval[9]=100;
		SpawnInterval[10]=500;
		SpawnInterval[11]=500;
		SpawnInterval[12]=3000;
		SpawnInterval[13]=500;
		SpawnInterval[14]=500;
		SpawnInterval[15]=2000;
		SpawnInterval[16]=500;
		SpawnInterval[17]=500;
		SpawnInterval[18]=3000;
		SpawnInterval[19]=500;
		SpawnInterval[20]=500;
		SpawnInterval[21]=3000;
		SpawnInterval[22]=500;
		SpawnInterval[23]=500;
		SpawnInterval[24]=4000;
		SpawnInterval[25]=400;
		SpawnInterval[26]=500;
		SpawnInterval[27]=3000;
		SpawnInterval[28]=500;
		SpawnInterval[29]=500;
		
	}

	public void SetRoute(MarchRoute[] route2) {
		route[0].setXY(19, 5);
		route[1].setXY(5,5);
		route[2].setXY(5,9);
		route[3].setXY(13,9);
		route[4].setXY(13,13);
		route[5].setXY(12,13);
		route[6].setXY(12,14);
		route[7].setXY(10,14);
		route[8].setXY(10,13);
		route[9].setXY(-1,13);
	}

	// ゲームスレッドの開始
	public void start() {
		if(gameThread == null) {
			gameThread = new Thread(this);
			gameThread.start();
		}
	}

	// ゲームスレッドの停止
	public void stop() {
		gameThread = null;
	}

	@Override
	// ゲームスレッドのメイン
	public void run() {
		while (gameThread == Thread.currentThread()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				break;
			}
			switch(scene){
			case 0:gameTitle();break;
			case 1:gameMain();break;
			//case 2:gameOver();break;
			}
		}
	}

	private void gameTitle() {
		gv.setColor(Color.ORANGE);
		gv.setFont(new Font("Serif",Font.BOLD,64));
		gv.clearRect(0, 0, width, height);
		gv.drawString("Tower Deffence!", 100,height/2);
		gv.setFont(new Font("Dialog",Font.BOLD,32));
		if(System.currentTimeMillis()-time>1000){
			if(BlinkTitle){
				BlinkTitle=false;
			}else{
				BlinkTitle=true;
			}
			time=System.currentTimeMillis();
		}
		if(BlinkTitle){
			gv.drawString("Click to start", 250,height/2+64);
		}
		repaint();
	}

	private void gameMain() {
		moveStar();
		gv.clearRect(0, 0, getWidth(), getHeight());
		gv.drawImage(map, 0, 0, this);

		switch(dragFlag){
		case 0:break;
		case 1:
			centerPointX=point.x-Ssize/2;
			centerPointY=point.y-Ssize/2;
			move=0;		//物体の動きをとめる変数
			break;
		case 2:
			gv.drawImage(selected_archer, point.x-size/2, point.y-size/2,this);
			break;
		case 3:
			gv.drawImage(Image_barricade, point.x-size/2, point.y-size/2,this);
			break;
		default:
			if(TowerType=="Archer"){
				archer.get(dragFlag-20).setXY(point.x-size/2,point.y-size/2);
				break;
			}
			if(TowerType=="Barricade"){
				barricade.get(dragFlag-20).setXY(point.x-size/2,point.y-size/2);
				break;
			}
		}
		if(keyA){
			gv.drawImage(selected_archer, point.x-size/2, point.y-size/2,this);
		}
		if(keyB){
			gv.drawImage(Image_barricade, point.x-size/2, point.y-size/2,this);
		}
		
		//弓兵
		for (int TowerNumber = 0; TowerNumber < archer.size(); TowerNumber++) {
			gv.drawImage(archer.get(TowerNumber).img, archer.get(TowerNumber).x, archer.get(TowerNumber).y, this);
			for(int i=0;i<NumberOfEnemys;i++){
				if(ShootingEnemy(code.get(i),TowerNumber)){
					int temp=code.get(0);
					code.remove(0);
					code.add(temp);
					break;
				}
			}
		}
		//バリケード
		for(int TowerNumber = 0; TowerNumber < barricade.size(); TowerNumber++){
			gv.drawImage(barricade.get(TowerNumber).img, barricade.get(TowerNumber).x, barricade.get(TowerNumber).y, this);
			for(int i=0;i<NumberOfEnemys;i++){
				if(barricade.get(TowerNumber).JudgeWithin(ene[i].x, ene[i].y)){
					//ene[i].x-=ene[i].direX*10;
					//ene[i].y-=ene[i].direY*10;
					ene[i].setXY(ene[i].x-ene[i].direX*10, ene[i].y-ene[i].direY*10);
					barricade.get(TowerNumber).HP--;
					break;
				}
			}
			if(barricade.get(TowerNumber).HP<=0){
				barricade.remove(TowerNumber);
				TowerNumber--;
			}
		}
		
		gv.setColor(Color.BLACK);
		gv.setFont(new Font("Serif",Font.BOLD,32));
		for(int i=0;i<NumberOfEnemys;i++){
			if (ene[i].flag) {
				ene[i].forward();
				gv.drawImage(enemy[ene[i].G_num], ene[i].x, ene[i].y, this);
				gv.drawString(i+":"+String.valueOf(ene[i].damage), 20+i*64, 20);
			}else{
				if(RespawnCount==SpawnInterval.length){
					continue;
				}
				if(System.currentTimeMillis()-time>SpawnInterval[RespawnCount]){
					ene[i].flag=true;
					time=System.currentTimeMillis();
					RespawnCount++;
				}
			}
		}
		gv.drawImage(image, x, y, this);
		gv.drawImage(underbar,0,size*17,this);
		gv.drawImage(Image_archer, size*1, size*16, this);
		gv.drawImage(Image_barricade, size*2, size*17+size/2, this);

		try {	//塔をマウスオーバー時にフレームを描写
			for(int i=1;i<=2;i++){
				if(point.x>size*i && size*(i+1)>point.x && point.y>size*17+size/2 && size*18+size/2>point.y){
					gv.drawImage(frame, size*i, size*17+size/2, this);
				}
			}
		} catch (Exception e) {
		}
		repaint();
	}

	// キーが押されたときの処理
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			// 左キーが押されたとき
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			// 右キーが押されたとき
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_UP:	keyUp=true;break;
			case KeyEvent.VK_DOWN:	keyDown=true;break;
			case KeyEvent.VK_SPACE: keySpace = true;break;
			case KeyEvent.VK_ENTER: keyEnter =true;break;
		}
	}

	// キーが離されたときの処理
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_UP:	keyUp= false; break;
			case KeyEvent.VK_DOWN:	keyDown= false; break;
			case KeyEvent.VK_SPACE: keySpace = false; break;
			case KeyEvent.VK_ENTER: keyEnter =false;break;
		}
	}

	// キーがタイプされたときの処理
	public void keyTyped(KeyEvent e) {
		typekey=e.getKeyChar();
		switch(typekey){
		case 'a':
			if(keyA){
				keyA=false;
			}else{
				keyA=true;
			}
			keyB=false;
			break;
		case 'b':
			if(keyB){
				keyB=false;
			}else{
				keyB=true;
			}
			keyA=false;
			break;
		}
	}

	// 描画
	public void paint(Graphics g) {
		g.drawImage(offImage,0,0,this );
	}

	public void update(Graphics g){
		paint(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(keyA && e.getPoint().y<size*17-size/2){
			archer.add(new Archer(e.getPoint().x-size/2,e.getPoint().y-size/2,Image_archer));
		}
		if(keyB && e.getPoint().y<size*17-size/2){
			barricade.add(new Barricade(e.getPoint().x-size/2,e.getPoint().y-size/2,Image_barricade));
		}
		keyA=keyB=false;
		if(scene==0){
			scene=1;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		move=1;
		if(dragFlag==2 && e.getPoint().y<size*17-size/2){
			archer.add(new Archer(e.getPoint().x-size/2,e.getPoint().y-size/2,Image_archer));
		}
		if(dragFlag==3 && e.getPoint().y<size*17-size/2){
			barricade.add(new Barricade(e.getPoint().x-size/2,e.getPoint().y-size/2,Image_barricade));
		}
		dragFlag=0;
		TowerType="";
	}


	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		point=e.getPoint();
	    int select_TowerX=size;	//n番目の塔はn-1個分sizeを加算
	    int select_TowerY=size*17+size/2;
	    
		//星
		if(e.getPoint().x>x && x+Ssize>e.getPoint().x &&
				e.getPoint().y>y && y+Ssize>e.getPoint().y && dragFlag==0){
			dragFlag=1;
		}

		//下窓の塔を選択しているか
		//弓兵
		if (e.getPoint().x > select_TowerX
				&& select_TowerX + size > e.getPoint().x
				&& e.getPoint().y > select_TowerY
				&& select_TowerY + size > e.getPoint().y
				&& dragFlag == 0) {
			dragFlag = 2;
		}
		//バリケード
		if (e.getPoint().x > select_TowerX+ size
				&& select_TowerX + size+ size > e.getPoint().x
				&& e.getPoint().y > select_TowerY
				&& select_TowerY + size > e.getPoint().y
				&& dragFlag == 0) {
			dragFlag = 3;
		}

		//場に存在する弓兵
		for (int i = 0; i < archer.size(); i++) {
			if (e.getPoint().x > archer.get(i).x
					&& archer.get(i).x + AsizeX > e.getPoint().x
					&& e.getPoint().y > archer.get(i).y
					&& archer.get(i).y + AsizeY > e.getPoint().y
					&& dragFlag == 0) {
				dragFlag = i+20;
				TowerType="Archer";
			}
		}
		//場に存在するバリケード
		for (int i = 0; i < barricade.size(); i++) {
			if (e.getPoint().x > barricade.get(i).x
					&& barricade.get(i).x + size > e.getPoint().x
					&& e.getPoint().y > barricade.get(i).y
					&& barricade.get(i).y + size > e.getPoint().y
					&& dragFlag == 0) {
				dragFlag = i+20;
				TowerType="Barricade";
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		point=e.getPoint();
		if(e.getPoint().x>x && x+Ssize>e.getPoint().x &&
				e.getPoint().y>y && y+Ssize>e.getPoint().y){
			image=zeni;
		}else{
			image=star;
		}
	}
	public void radian(double degree){
	    radians=Math.toRadians(degree);
	    rx=Math.cos(radians);
	    ry=Math.sin(radians);
	    x=(int)(rx*radius)*move+centerPointX;
	    y=(int)(ry*radius)*move+centerPointY;
	}

	public void moveStar(){

		radian(degree);
		if(degree<0){
			degreeWay=1;
		}
		if(degree>360){
			degreeWay=-1;
		}
		degree+=5*degreeWay;

		if(radius>150)increaseRad=-1;
		if(radius<50)increaseRad=1;
		radius+=5*increaseRad;

		if(keyLeft)centerPointX-=10;
		if(keyRight)centerPointX+=10;
	}

	public boolean ShootingEnemy(int code,int TowerNumber){
		if (archer.get(TowerNumber).bulFlag) {
			if (bul[TowerNumber].moveBullet()) {	//未着弾
				gv.drawImage(bullet, bul[TowerNumber].x, bul[TowerNumber].y, this);

			} else {	//着弾
				archer.get(TowerNumber).bulFlag = false;
				ene[bul[TowerNumber].code].damage++;
			}
			return true;
		} else {
			if (archer.get(TowerNumber).JudgeWithin(ene[code].x, ene[code].y)) {
				bul[TowerNumber] = new Bullet(code, ene[code].x, ene[code].y, archer.get(TowerNumber).x, archer.get(TowerNumber).y);
				try {
					bul[TowerNumber].moveBullet();
					archer.get(TowerNumber).bulFlag = true;
				} catch (NullPointerException e) {
					System.out.println("ぬるぽ");
				} catch (Exception e) {
					System.out.println("エラー発生");
				}
				return true;
			}
		}
		return false;
	}

}
