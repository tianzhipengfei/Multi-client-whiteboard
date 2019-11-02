

import java.awt.*;

public class MyWord extends MyShape {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1294309577537888533L;
	private Font font;          //Word's font
    private String s;           //Word
    private int size;           //Word's size

    public MyWord(int x1, int y1,String s, Font f, Color c) {
        this.x1 = x1;
        this.y1 = y1;
        this.s = s;
        this.font = f;
        this.color = c;
        this.size = f.getSize();
    }

    //To judge if (x,y) is in the limited zone
    public boolean containsPix(int x, int y){
        if(x>=x1 && x<=x1+s.length()*size && y>=y1 && y<=y1-size){
            return true;
        } else{
            return false;
        }
    }

    //Override Draw
    public void Draw(Graphics g){
        Graphics2D g1 = (Graphics2D) g;
        g1.setPaint(color);
        g1.setFont(font);
        g1.drawString(s,x1,y1);
    }
}
