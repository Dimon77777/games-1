package block_kuzusi;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Block_kuzusi extends Applet implements Runnable, KeyListener,
		MouseListener, MouseMotionListener {
	Thread gameThread;

	// 画面 /////////////////////
	int width = 640;
	int height = 500;
	// //////////////////////////////

	// Image ////////////////////
	Graphics gv;
	Image offimage;
	HashMap<String, Image> images = new HashMap<String, Image>();
	// //////////////////////////////

	// オブジェクト ////////////////////
	Block[][] blocks = new Block[10][5];
//	ArrayList<Block> Blocks = new ArrayList<Block>();
	int block_size_x = 64;
	int block_size_y = 16;
	int block_row=10;
	int block_col=5;
	// //////////////////////////////

	// プレイヤー ///////////////
	int bar_x = width / 2 - block_size_x;
	int bar_y = height - 64;
	int bar_size_x=64;
	int bar_size_y=16;
	int ball_x=width/2;
	int ball_y=100;
	int ball_speed=5;
	int dx=ball_speed;
	int dy=-ball_speed;
	int ball_size=16;
	// //////////////////////////////

	//キー		/////////////////////
	private boolean keyLeft;
	private boolean keyRight;
	private boolean keyUp;
	private boolean keyDown;
	private boolean keySpace;
	private boolean keyEnter;
	//////////////////////////////////

	public void init() {
		// リスナー ///////////
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		// ////////////////////////

		// 画面設定 ///////////
		setSize(new Dimension(width, height));
		offimage = createImage(width, height);
		gv = offimage.getGraphics();
		setBackground(Color.BLACK);
		ReadImages();
		// ////////////////////////


		initBlocks();

		// スレッド //////////
		gameThread = new Thread(this);
		gameThread.start();
		// /////////////////////////
	}

	// 最初のブロック設置/////////////////////////
	private void initBlocks() {
		for (int y = 0; y < blocks[y].length; y++) {
			for (int x = 0, length = blocks.length; x < length; x++) {
				// とりあえず全部設置
				blocks[x][y]=new Block(x * 64, y * 16, true, images.get("block"));
			}
		}
	}


	//画像読み込み////////////////////////////
	private void ReadImages() {
		String imgAdd = "..\\img(block_kuzusi)\\";
		try (FileInputStream in1 = new FileInputStream(imgAdd + "block.png");
				FileInputStream in2 = new FileInputStream(imgAdd + "bar.png");
				FileInputStream in3 = new FileInputStream(imgAdd + "ball.png")) {
			images.put("block", ImageIO.read(in1));
			images.put("bar", ImageIO.read(in2));
			images.put("ball",ImageIO.read(in3));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
			gameMain();
		}
	}

	// メインのゲーム内容 ////////////////////////////
	private void gameMain() {
		gv.clearRect(0, 0, width, height);
		gv.setFont(new Font("Serif", Font.BOLD, 64));
		gv.setColor(Color.ORANGE);
		gv.drawString("test", 100, 100);

		// ブロック///////////////
		for (Block block[] : blocks) {
			for (Block b:block) {
				if (b.isExist() && b.isBreakable()) {
					gv.drawImage(b.image, b.getX(), b.getY(), this);
				}
			}
		}
		//////////////////////

		//ボール	/////////////////
		ball_x+=dx;
		ball_y+=dy;
		//端に当たったか
		if(ball_x+dx<0 || ball_x+dx+ball_size>width){
			dx*=-1;
		}
		if(ball_y+dy<0 || ball_y+dy+-ball_size>height){
			dy*=-1;
		}
		//バーに当たったか
		if(ball_y+ball_size>bar_y && ball_y<bar_y+bar_size_y &&
				ball_x+ball_size>bar_x && ball_x<bar_x+bar_size_x){
			dy=-ball_speed;
		//ブロックに当たったか
		}else if(ball_x/64<block_row && ball_y/16<block_col){
			if(blocks[ball_x/64][ball_y/16].isExist()){
				blocks[ball_x/64][ball_y/16].setExist(false);
				//ボールのぶつかる方向で進行方向を変える
				if(ball_y/16==(ball_y-dy)/16){
					dx*=-1;
				}else{
					dy*=-1;
				}

			}
		}


		gv.drawImage(images.get("ball"), ball_x, ball_y, this);
		/////////////////////////

		//バー	/////////////////
		if(keyLeft){
			bar_x-=5;
		}
		if(keyRight){
			bar_x+=5;
		}
		gv.drawImage(images.get("bar"), bar_x, bar_y, this);
		/////////////////////////
		repaint();
	}

	// 描画//////////////////////////////////////
	public void paint(Graphics g) {
		g.drawImage(offimage, 0, 0, this);
	}

	public void update(Graphics g) {
		paint(g);
	}

	// MouseMotionListener //////////////////
	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		bar_x=e.getX()-block_size_x/2;
	}


	// MouseListener ///////////////////////
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


	// KeyListener ////////////////////////
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
p("typed");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			keyLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			keyRight = true;
			break;
		case KeyEvent.VK_UP:
			keyUp = true;
			break;
		case KeyEvent.VK_DOWN:
			keyDown = true;
			break;
		case KeyEvent.VK_SPACE:
			keySpace = true;
			break;
		case KeyEvent.VK_ENTER:
			keyEnter = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			keyLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			keyRight = false;
			break;
		case KeyEvent.VK_UP:
			keyUp = false;
			break;
		case KeyEvent.VK_DOWN:
			keyDown = false;
			break;
		case KeyEvent.VK_SPACE:
			keySpace = false;
			break;
		case KeyEvent.VK_ENTER:
			keyEnter = false;
			break;
		}

	}


	private void p(Object message) {
		System.out.println(message);
	}
}
