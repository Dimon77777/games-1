package lightsOut;

import javax.swing.JFrame;

public class LightsOut extends JFrame{

	public static void main(String[] args) {
		JFrame frame=new LightsOut();
		Game(frame);
		
	}
	public LightsOut(){
		add(new DrawPanel());
	}

	private static void Game(JFrame frame) {
		int width=500;
		int height=500;
		frame.setSize(width, height);
		
		frame.setVisible(true);
	}
	

}

////マウスイベント処理用クラス
//class onClick extends MouseAdapter{
//	public void mouseClicked(MouseEvent e){
//		System.out.println(e.getX()+","+e.getY());
//	}
//}
