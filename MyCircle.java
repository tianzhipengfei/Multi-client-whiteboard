

import java.awt.*;

public class MyCircle extends MyShape {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4945311328481068611L;

	public MyCircle(int x1, int y1, int x2,int y2, Color color , int width){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        this.color=color;
        this.width=width;
    }

    //Upgrade points position
    public void upgradePoint(int x1, int y1, int x2,int y2){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
    }

    //Override Draw
    public void Draw(Graphics g) {
        Graphics2D g1 = (Graphics2D) g;
        g1.setColor(this.color);
        g1.setStroke(new BasicStroke(width));
        g1.drawOval(x1, y1, x2, y2);
    }
}
