
import java.awt.*;

public class MyRoundRectangle extends MyShape {
    /**
	 * 
	 */
	private static final long serialVersionUID = -832604805053978832L;
	public int arcWidth,arcHeight;              //arcWidth and arcHeight are properties for arc

    public MyRoundRectangle(int x1, int y1, int x2, int y2, int arcWidth, int arcHeight, Color color , int width){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        this.arcWidth=arcWidth;
        this.arcHeight=arcHeight;
        this.color=color;
        this.width=width;
    }

    //Override Draw
    public void Draw(Graphics g) {
        Graphics2D g1 = (Graphics2D) g;
        g1.setColor(this.color);
        g1.setStroke(new BasicStroke(width));
        g1.drawRoundRect(x1, y1, x2, y2, this.arcWidth, this.arcHeight);
    }

}