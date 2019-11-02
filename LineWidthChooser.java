

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;



public class LineWidthChooser extends JComboBox{
    /**
	 * 
	 */
	private static final long serialVersionUID = 696263631144008887L;
	private String[] s = {"Thin","Medium","Thick"};             //Three lengths of line
    private ImageIcon[] icons = new ImageIcon[3];       //Three icons
    private JComboBox combo;
    private MyDraw md;
    //Two useful classes in this line width chooser
    class ItemObj
    {
        //it contains a name and an icon
        String name;
        ImageIcon icon;
        public ItemObj(String name, ImageIcon icon){
            this.name = name;
            this.icon = icon;
        }
    }

    class ACellRenderer extends JLabel implements ListCellRenderer
    {
        ACellRenderer()
        {
            setOpaque(true);
        }
        public Component getListCellRendererComponent(JList list,
                                                      Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus)
        {
            if (value != null)
            {
                setText(((ItemObj)value).name);
                setIcon(((ItemObj)value).icon);
            }

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }
            else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            return this;
        }
    }

    public LineWidthChooser(MyDraw md) {
        this.md = md;
        ItemObj[] obj = new ItemObj[3];
        //Get three icons
        int j = 0;
        for(int i=1; i <= 5; i=i+2)
        {
            icons[j] = new ImageIcon("bin/Resources/images/lineSize_"+i+".gif");
            obj[j] = new ItemObj(s[j],icons[j]);
            j += 1;
        }
        combo = new JComboBox(obj);     //use the array of ItemObj to fill the new JComboBox
        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ItemObj selectedObj = (ItemObj) e.getItem();
                String width = selectedObj.name;            //use selected Item's name to change the width in MyDraw
                if(width.equals("Thin")){
                    md.setWidth(1);
                } else if(width.equals("Medium")){
                    md.setWidth(3);
                } else if(width.equals("Thick")){
                    md.setWidth(5);
                }
            }
        });
        combo.setRenderer(new ACellRenderer());
        combo.setSize(50, 10);
        combo.setMaximumRowCount(3);
        combo.setVisible(true);
    }

    //get the JComboBox
    public JComboBox getBox(){
        return combo;
    }

}

