

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MySprayGun extends MyShape {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7505291615358351441L;
	private Random r;                   //To create random integer
    public ArrayList<Point> points;     //The set of points should be painted

    public MySprayGun(int x1, int y1, Color c, int width) {
        this.color = c;
        if(width <3){
            this.width=5;
        } else if(width <5){
            this.width=10;
        } else{
            this.width=15;
        }
        points = new ArrayList<Point>();
        Point temppoint = new Point(x1,y1);
        points.add(temppoint);
        r = new Random();
    }

    //Make every point in set move （x,y)
    public void moveWhere(int mx,int my){
        for(int i=0; i<points.size(); i++){
            points.get(i).move(points.get(i).x+mx,points.get(i).y+my);
        }
    }

    //add point should be painted into set
    public void addPoint(int x2, int y2){
        for (int i = 0; i < 30; i++) {
            int xp=r.nextInt(2*width)-width;
            int yp=r.nextInt(2*width)-width;
            //create random point around the mouse position
            Point temppoint = new Point(x2+xp,y2+yp);
            points.add(temppoint);
        }
    }

    //Override Draw
    public void Draw(Graphics g){
        Graphics2D g1 = (Graphics2D)g;
        g1.setColor(this.color);
        g1.setStroke(new BasicStroke(1));
        int tempx, tempy;
        for(int i=0; i<points.size();i++){
            tempx = points.get(i).x;
            tempy = points.get(i).y;
            g1.drawLine(tempx, tempy, tempx, tempy);
        }
    }
}
