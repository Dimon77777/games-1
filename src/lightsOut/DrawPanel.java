package lightsOut;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawPanel extends JPanel implements MouseListener {
	JLabel[][] card=new JLabel[4][4];
	boolean[][] openFlag=new boolean[4][4];
	ImageIcon open=new ImageIcon("img(lightsOut)/card1.png");
	ImageIcon close=new ImageIcon("img(lightsOut)/card2.png");
	public DrawPanel(){
        setBackground(Color.white);
		addMouseListener(this);
		GridBagLayout layout=new GridBagLayout();
		//各カードの設置と開閉フラグの初期化
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				card[x][y] = new JLabel(close);
				setContent(layout, card[x][y], x, y, 1, 1);
				add(card[x][y]);
				openFlag[x][y]=false;
			}
		}
		setLayout(layout);
	}


	private static void setContent(GridBagLayout layout,Object obj,int x,int y,int width,int height){
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.NONE;
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=width;
		gbc.gridheight=height;
		layout.setConstraints((Component) obj, gbc);

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println(e.getX()+","+e.getY());

		//カードを指しているか
		if (e.getX()>114 && e.getX()<114+256
				&& e.getY()>103 && e.getY()<103+256) {
			int x = (e.getX() - 114) / 64;
			int y=(e.getY() - 103) / 64;
			//System.out.println(x+","+y);
			reverseCard(x,y);	//reverse clicked card
			//right side
			if(x!=3){
				reverseCard(x+1,y);
			}
			//left side
			if(x!=0){
				reverseCard(x-1,y);
			}
			//upper side
			if(y!=0){
				reverseCard(x,y-1);
			}
			//under side
			if(y!=3){
				reverseCard(x,y+1);
			}
		}
	}

	public void reverseCard(int x,int y){
		if (openFlag[x][y]) {//伏せる
			card[x][y].setIcon(close);
			openFlag[x][y] = false;
		} else {//開く
			card[x][y].setIcon(open);
			openFlag[x][y] = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
