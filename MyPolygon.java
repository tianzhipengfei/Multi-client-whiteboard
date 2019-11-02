
import java.awt.*;
import java.util.ArrayList;

public class MyPolygon extends MyShape {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2780058886239243484L;
	private ArrayList<Point> points;        //The set of vertex points
    int minx,miny,maxx,maxy;

    public MyPolygon (int x1, int y1, Color c, int width){
        this.x1=x1;
        this.y1=y1;
        this.color=c;
        this.width=width;
        this.points = new ArrayList<Point>();
        Point temppoint = new Point(x1,y1);
        this.points.add(temppoint);
        minx=500;
        maxx=0;
        miny=330;
        maxy=0;
    }

    //To judge if (x,y) is in the limited zone
    public boolean containsPix(int x, int y){
        if(x>=minx && x<=maxx && y>=miny && y<=maxy){
            return true;
        } else{
            return false;
        }
    }

    //add point should be painted into set
    public void addPoint(int x2, int y2){
        //Upgrade
        if(x2>maxx){
            maxx=x2;
        }
        if(x2<minx){
            minx=x2;
        }
        if(y2>maxy){
            maxy=y2;
        }
        if(y2<miny){
            miny=y2;
        }
        Point temppoint = new Point(x2,y2);
        points.add(temppoint);
    }

    //Make every point in set move £¨x,y£©
    public void moveWhere(int mx,int my){
        for(int i=0; i<points.size(); i++){
            points.get(i).move(points.get(i).x+mx,points.get(i).y+my);
            //Upgrade limited points
            if(points.get(i).x+mx>maxx){
                maxx=points.get(i).x+mx;
            }
            if(points.get(i).x+mx<minx){
                minx=points.get(i).x+mx;
            }
            if(points.get(i).y+my>maxy){
                maxy=points.get(i).y+my;
            }
            if(points.get(i).y+my<miny){
                miny=points.get(i).y+my;
            }
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
        for(int i=0;i < points.size(); i++){
            tempx2 = points.get(i).x;
            tempy2 = points.get(i).y;
            g1.drawLine(tempx1, tempy1, tempx2, tempy2);
            tempx1 = tempx2;
            tempy1 = tempy2;
        }
    }
}
