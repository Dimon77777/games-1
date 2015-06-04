package block_kuzusi;

import java.awt.Image;

public class Block {
	int x=0;
	int y=0;
	boolean isBreakable=false;
	Image image=null;
	boolean isExist=false;

	//コンストラクタ
	//(x座標,y座標,破壊可能ブロックか)
	Block(int x,int y,boolean isBreakable,Image image){
		this.x=x;
		this.y=y;
		this.isBreakable=isBreakable;
		this.image=image;
		this.isExist=true;
	}

	/**
	 * xを取得します。
	 * @return x
	 */
	public int getX() {
	    return x;
	}

	/**
	 * xを設定します。
	 * @param x x
	 */
	public void setX(int x) {
	    this.x = x;
	}

	/**
	 * yを取得します。
	 * @return y
	 */
	public int getY() {
	    return y;
	}

	/**
	 * yを設定します。
	 * @param y y
	 */
	public void setY(int y) {
	    this.y = y;
	}

	/**
	 * isBreakableを取得します。
	 * @return isBreakable
	 */
	public boolean isBreakable() {
	    return isBreakable;
	}

	/**
	 * isBreakableを設定します。
	 * @param isBreakable isBreakable
	 */
	public void setBreakable(boolean isBreakable) {
	    this.isBreakable = isBreakable;
	}

	/**
	 * isExistを取得します。
	 * @return isExist
	 */
	public boolean isExist() {
	    return isExist;
	}

	/**
	 * isExistを設定します。
	 * @param isExist isExist
	 */
	public void setExist(boolean isExist) {
	    this.isExist = isExist;
	}

}
