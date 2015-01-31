package lightsOut;

import java.awt.Button;
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
	JLabel card1;
	public DrawPanel(){
		//System.out.println("aaaa");
        setBackground(Color.white);
		addMouseListener(this);
		JLabel label1=new JLabel("Mtest");
		add(label1);
		GridBagLayout layout=new GridBagLayout();
		Button[] button=new Button[9];
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.fill=GridBagConstraints.NONE;
//		for(int i=0;i<9;i++){
//			button[i]=new Button(String.valueOf(i));
//			gbc.gridx=i;
//			gbc.gridy=i;
//			gbc.gridwidth=1;
//			gbc.gridheight=1;
//			layout.setConstraints(button[i], gbc);
//			frame.add(button[i]);
//		}
		Button testbutton=new Button("test");
		setContent(layout,testbutton,2,1,1,1);
		add(testbutton);
		card1=new JLabel(new ImageIcon("img(lightsOut)/card1.png"));
		setContent(layout, card1, 1,1, 1, 1);
		add(card1);
//		frame.addMouseListener(new onClick());
		setLayout(layout);
		card1.setIcon(new ImageIcon("img(lightsOut)/card2.png"));
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
		System.out.println(e.getX()+","+e.getY());
		card1.setIcon(new ImageIcon("img(lightsOut)/card1.png"));
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
