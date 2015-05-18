package towerDefense;

import java.awt.Image;

public class Cannon extends Archer{
	Cannon(int x, int y, Image img) {
		super(x, y, img);
		super.cooldown_time=1500;
	}

}
