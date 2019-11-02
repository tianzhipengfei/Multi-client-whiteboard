

import java.awt.*;
import java.util.ArrayList;

public class MyPencil extends MyShape {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3703644757644977538L;
	public ArrayList<Point> points;         //The set of points pencil moved

    public MyPencil (int x1, int y1, Color c, int width){
        this.x1=x1;
        this.y1=y1;
        this.color=c;
        this.width=width;
        this.points = new ArrayList<Point>();
        Point temppoint = new Point(x1,y1);
        this.points.add(temppoint);
    }

    //add point should be painted into set
    public void addPoint(int x2, int y2){
        Point temppoint = new Point(x2,y2);
        points.add(temppoint);
    }

    //Make every point in set move £¨x,y£©
    public void moveWhere(int mx,int my){
        for(int i=0; i<points.size(); i++){
            points.get(i).move(points.get(i).x+mx,points.get(i).y+my);
        }
    }

    //Override Draw
    public void Draw(Graphics g){
        Graphics2D g1 = (Graphics2D)g;
        g1.setColor(this.color);
        g1.setStroke(new BasicStroke(width));
        int tempx1,tempx2,tempy1,tempy2;
        tempx1 = points.get(0).x;
        tempy1 = points.get(0).y;
        //draw a line between two jacent points
        for(int i=1;i < points.size(); i++){
            tempx2 = points.get(i).x;
            tempy2 = points.get(i).y;
            g1.drawLine(tempx1, tempy1, tempx2, tempy2);
            tempx1 = tempx2;
            tempy1 = tempy2;
        }
    }
    
    public int getWidth() {
    	return this.width;
    }
}
