
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RadioButtonListener implements ActionListener {
    int type;
    MyDraw md;

    public RadioButtonListener(MyDraw md) {
        this.md = md;
    }

    //Different radio buttons change the shapeType in different modes
    public void actionPerformed(ActionEvent e) {
        JRadioButton rbt = (JRadioButton) e.getSource();
        String command = rbt.getActionCommand();
        if("pic10".equals(command)){
            type=0;
            md.setType(0);
        } else if("pic12".equals(command)){
            type=1;
            md.setType(1);
        } else if("pic15".equals(command)){
            type=2;
            md.setType(2);
        } else if("pic14".equals(command)){
            type=3;
            md.setType(3);
        } else if("pic4".equals(command)){
            type=4;
            md.setType(4);
        } else if("pic7".equals(command)){
            type=5;
            md.setType(5);
        } else if("pic6".equals(command)){
            type=6;
            md.setType(6);
        } else if("pic8".equals(command)){
            type=7;
            md.setType(7);
        } else if("pic2".equals(command)){
            type=8;
            md.setType(8);
        } else if("pic13".equals(command)){
            type = 9;
            md.setType(9);
        } else if("pic3".equals(command)){
            type = 10;
            md.setType(10);
        } else if("pic9".equals(command)){
            type = 11;
            md.setType(11);
            JFontChooser one = new JFontChooser(new Font("Arial", Font.PLAIN, 24), new Color(0,0,0));
            one.showDialog(null,500,200);
            //获取选择的字体
            Font font=one.getSelectedfont();
            //获取选择的颜色
            Color color=one.getSelectedcolor();
            if(font!=null&&color!=null){
                md.setFont(font);
                md.setColor(color);
                md.setSentences(one.getStr());
            }
        } else if("pic5".equals(command)){
            type = 12;
            md.setType(12);
        } else if("pic11".equals(command)){
            type = 13;
            md.setType(13);
        }
    }
}