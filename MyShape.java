

import javax.swing.*;
import java.awt.*;

//Abstract parent class
public abstract class MyShape extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2541360573046393578L;
	public int x1,y1,x2,y2;
    public Color color;
    public int width;

    //Abstract method Draw
    public abstract void Draw(Graphics g);

    //For moving operation
    public void moveWhere(int x,int y){
        this.x1 += x;
        this.y1 += y;
    }

    //To judge whether be selecte or not
    public boolean containsPix(int x, int y){
        if(x>=x1 && x<=x1+x2 && y>=y1 && y<=y1+y2){
            return true;
        } else{
            return false;
        }
    }

    //Add points for MULTIPOINTS shape
    public void addPoint(int x1, int y1) {

    }

    //Upgrade points position
    public void upgradePoint(int x1, int y1, int x2,int y2){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
    }
} 