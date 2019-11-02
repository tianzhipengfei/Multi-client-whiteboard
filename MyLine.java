

import java.awt.*;

public class MyLine extends MyShape {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7593509072190524909L;

	public MyLine(int x1, int y1, int x2, int y2, Color color, int width){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        this.color=color;
        this.width=width;
    }

    //Make two vertex move (x,y)
    public void moveWhere(int x,int y){
        this.x1 += x;
        this.y1 += y;
        this.x2 += x;
        this.y2 += y;
    }

    //To judge if (x,y) is upon the segment between (x1,y1) and (x2,y2)
    public boolean containsPix(int x, int y){
        if(x>=x1 && x<=x2 && y>=y1 && y<=y2 && (x-x1)*(y2-y)== (y-y1)*(x2-x)){
            return true;
        } else{
            return false;
        }
    }

    //Override Draw
    public void Draw(Graphics g) {
        Graphics2D g1 = (Graphics2D)g;
        g1.setColor(this.color);
        g1.setStroke(new BasicStroke(width));
        g1.drawLine(x1, y1, x2, y2);
    }
}