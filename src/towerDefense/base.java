package towerDefense;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
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

import javax.imageio.ImageIO;
public class base extends Applet implements Runnable,KeyListener,MouseListener,MouseMotionListener{
				
	Thread gameThread;		// ゲームスレッド
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keySpace;
	boolean keyEnter;
	Image offImage;
	Image star;
	Image zeni;
	Image field;
	Image image;
	Point point;
	int centerPointX=300;
	int centerPointY=300;
	int height=608;
	int width=608;
	public void init(){ 
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setSize(new Dimension(height,width));
		offImage=createImage(width,height);
		setBackground(Color.BLACK);
		setForeground(Color.white);
		try(FileInputStream in1=new FileInputStream("towerDefense\\images\\star.png");//FileInputStreamを作る
				FileInputStream in2=new FileInputStream("towerDefense\\images\\zeni.png");
				FileInputStream in3=new FileInputStream("towerDefense\\images\\field.png")
				){//FileInputStreamを作る
			star=ImageIO.read(in1);//イメージを取り込む
			zeni=ImageIO.read(in2);
			field=ImageIO.read(in3);
			image=star;
			// 自機画像読み込み
		}catch(IOException e){
			//エラー時の処理（エラーを表示）しnullを返す
			System.out.println("Err e="+e);//エラーを表示
		}
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
		}
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
		// 左キーが離されたとき
		case KeyEvent.VK_LEFT: keyLeft = false; break;
		// 右キーが離されたとき
		case KeyEvent.VK_RIGHT: keyRight = false; break;
		case KeyEvent.VK_UP:	keyUp= false; break;
		case KeyEvent.VK_DOWN:	keyDown= false; break;
		case KeyEvent.VK_SPACE: keySpace = false; break;
		case KeyEvent.VK_ENTER: keyEnter =false;break;
		}
	}

	// キーがタイプされたときの処理
	public void keyTyped(KeyEvent e) {
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
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		point=e.getPoint();
	}
}


