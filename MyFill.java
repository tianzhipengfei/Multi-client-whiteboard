

import java.awt.*;
import java.util.ArrayList;

public class MyFill extends MyShape {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1040213285229339060L;
	public ArrayList<Point> points;                 //The set of points should be painted

    public MyFill (int x1, int y1, Color c, int width){
        this.x1=x1;
        this.y1=y1;
        this.color=c;
        this.width=width;
        this.points = new ArrayList<Point>();
        Point temppoint = new Point(x1,y1);
        this.points.add(temppoint);
    }

    //Make every point in set move £¨x,y£©
    public void moveWhere(int mx,int my){
        for(int i=0; i<points.size(); i++){
            points.get(i).move(points.get(i).x+mx,points.get(i).y+my);
        }
    }

    //add point should be painted into set
    public void addPoint(int x2, int y2){
        Point temppoint = new Point(x2,y2);
        points.add(temppoint);
    }

    //Override Draw
    public void Draw(Graphics g){
        Graphics2D g1 = (Graphics2D)g;
        g1.setColor(this.color);
        g1.setStroke(new BasicStroke(width));
        //draw a line between two jacent points
        int tempx=points.get(0).x,tempy=points.get(0).y,curx,cury;
        for(int i=0;i < points.size(); i++){
            curx = points.get(i).x;
            cury = points.get(i).y;
            g1.drawLine(tempx,tempy,curx,cury);
            tempx=curx;
            tempy=cury;
        }
    }
}
