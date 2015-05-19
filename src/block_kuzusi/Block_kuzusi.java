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
import java.util.ArrayList;
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
	ArrayList<Block> Blocks = new ArrayList<Block>();
	int block_size_x = 64;
	int block_size_y = 16;
	// //////////////////////////////

	// プレイヤー ///////////////
	int bar_x = width / 2 - block_size_x;
	int bar_y = height - 32;
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
		boolean[][] address = new boolean[10][5];
		for (int y = 0; y < address[y].length; y++) {
			for (int x = 0, length = address.length; x < length; x++) {
				// とりあえず全部設置
				address[x][y] = true;
				if (address[x][y]) {
					setBlocks(x, y);
				}
			}
		}
	}

	// 1個のブロック設置//////////////////////////
	private void setBlocks(int x, int y) {
		Blocks.add(new Block(x * 64, y * 16, true, images.get("block")));
	}

	//画像読み込み////////////////////////////
	private void ReadImages() {
		String imgAdd = "..\\img(block_kuzusi)\\";
		try (FileInputStream in1 = new FileInputStream(imgAdd + "block.png");
				FileInputStream in2 = new FileInputStream(imgAdd + "bar.png")) {
			images.put("block", ImageIO.read(in1));
			images.put("bar", ImageIO.read(in2));

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
		for (Block b : Blocks) {
			gv.drawImage(b.image, b.getX(), b.getY(), this);
		}
		//////////////////////

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
