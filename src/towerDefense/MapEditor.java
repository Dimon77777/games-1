package towerDefense;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapEditor extends base{
	Image selectedFrame;
	Image selected;
	BufferedImage map;//マップ情報
	Graphics gv;//画面にmapとマップチップを描写
	Graphics mapEditor;//mapを書き換えるためのもの(画面描写はしない)
	int fx=0;
	int fy=0;
	public void init(){
		super.init();
		try{
			selectedFrame=ImageIO.read(new FileInputStream("..\\img(TowerDefence)\\selected.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		map=new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
		mapEditor=map.getGraphics();
		setSize(new Dimension(width+32*8,height));
		offImage=createImage(width+32*8,height);
		gv=offImage.getGraphics();
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
	}
	public void run() {
		while (gameThread == Thread.currentThread()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				break;
			}
			if(keyEnter){
				try{
					ImageIO.write(map, "png", new File("img(TowerDefence)\\map.png"));
					System.exit(0);
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			gv.clearRect(0, 0, getWidth(), getHeight());
			//マップチップを描写
			gv.drawImage(field, width+1,0 ,this);
			//被選択マップチップを描写
			gv.drawImage(field, width, height-32,width+32, height,fx*32,fy*32,fx*32+32,fy*32+32, this);
			gv.drawImage(selectedFrame, width, height-32, this);
			gv.drawImage(map, 0, 0, this);
			gv.drawString(((int)point.getX()/32+","+(int)point.getY()/32), (int)point.getX(), (int)point.getY());
			//draw_squareBlock(gv);
			repaint();
		}
	}	
	// 描画
	public void paint(Graphics g) {
		g.drawImage(offImage,0,0,this );
	}
	
	//格子線描写
	public void draw_squareBlock(Graphics gv){
		for(int i=0;i<width/32;i++){
			gv.drawLine(32+i*32, 0, 32+i*32, height);
		}
		for(int i=0;i<height/32;i++){
			gv.drawLine(0, 32+i*32,width , 32+i*32);
		}
		gv.drawLine(0, height-1,width , height-1);
		gv.drawLine(width-1, 0,width-1 , height);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		point=e.getPoint();
		int mx=point.x/32;
		int my=point.y/32;
		if(0<=(point.x-width+2) && point.x<width+32*8 && (point.y)/32<16){
			fx=(point.x-width+2)/32;
			fy=(point.y)/32;
		}else{
			mapEditor.drawImage(field, mx*32, my*32,mx*32+32, my*32+32,fx*32,fy*32,fx*32+32,fy*32+32, this);
		}
	}	
	@Override
	public void mouseClicked(MouseEvent e) {
		point=e.getPoint();
		int mx=point.x/32;
		int my=point.y/32;
		//マップチップを選択していたら真
		if(0<=(point.x-width+2) && point.x<width+32*8 && (point.y)/32<16){
			fx=(point.x-width+2)/32;
			fy=(point.y)/32;
		}else{//左画面クリック場所に選択されているマップチップを配置
			mapEditor.drawImage(field, mx*32, my*32,mx*32+32, my*32+32,fx*32,fy*32,fx*32+32,fy*32+32, this);
		}
	}
	
}
