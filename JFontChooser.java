
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;



public class JFontChooser extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2274452658541746127L;

	// Set the appearance style
    {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //[start] Define variables
    private String current_fontName = "Arial";
    private String Str = "";
    private int current_fontStyle = Font.PLAIN;  //Italic, Bold, ...
    private int current_fontSize = 9;
    private Color current_color = Color.BLACK;
    private JDialog dialog;
    private JLabel lblString;
    private JLabel lblFont;
    private JLabel lblStyle;
    private JLabel lblSize;
    private JLabel lblColor;
    private JLabel otherColor;
    private JTextField txtFont;                                   // Show selected text's font
    private JTextField txtStyle;
    private JTextField txtSize;
    private JTextField showTF;                                    // The frame to show the fonts user selects
    private JList lstFont;                                        // Font list for users to select
    private JList lstStyle;
    private JList lstSize;
    private JComboBox cbColor;
    private JButton ok, cancel;
    private JScrollPane spFont;
    private JScrollPane spSize;
    private JPanel showPan;
    private Map sizeMap;
    private Map colorMap;
    private Font selectedfont;
    private Color selectedcolor;
    //[end] Define variables

    public JFontChooser() {
        this.selectedfont = null;
        this.selectedcolor = null;
            /* Init UI */
        init(null, null);
    }

    public JFontChooser(Font font, Color color) {
        if (font != null) {
            this.selectedfont = font;
            this.selectedcolor = color;
            this.current_fontName = font.getName();
            this.current_fontSize = font.getSize();
            this.current_fontStyle = font.getStyle();
            this.current_color = color;
            /* Init UI */
            init(font, color);
        } else {
            JOptionPane.showMessageDialog(this, "No control is selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //public methods
    public Font getSelectedfont() {
        return selectedfont;
    }

    public void setSelectedfont(Font selectedfont) {
        this.selectedfont = selectedfont;
    }

    public Color getSelectedcolor() {
        return selectedcolor;
    }

    public String getStr() {
        return Str;
    }

    public void setSelectedcolor(Color selectedcolor) {
        this.selectedcolor = selectedcolor;
    }

    /* Init UI */
    private void init(Font font, Color color) {
        lblString = new JLabel("String:");
        lblFont = new JLabel("Font:");
        lblStyle = new JLabel("Style:");
        lblSize = new JLabel("Size:");
        lblColor = new JLabel("Color");
        otherColor = new JLabel("Others");
        txtFont = new JTextField("Arial");
        txtStyle = new JTextField("Plain");
        txtSize = new JTextField("9");

        //get fonts avaibale in this enviroment
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();

        lstFont = new JList(fontNames);

        //Style.
        lstStyle = new JList(new String[]{"Plain", "Bold", "Italic", "Bold Italic"});


        String[] sizeStr = new String[]{
                "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72"
        };
        int sizeVal[] = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72};
        sizeMap = new HashMap();
        for (int i = 0; i < sizeStr.length; ++i) {
            sizeMap.put(sizeStr[i], sizeVal[i]);
        }
        lstSize = new JList(sizeStr);
        spFont = new JScrollPane(lstFont);
        spSize = new JScrollPane(lstSize);

        String[] colorStr = new String[]{
                "Black", "Blue", "Cyan", "Dark Gray", "Gray", "Green", "Light Gray", "Magenta", "Orange", "Pink", "Red", "White", "Yellow"
        };
        Color[] colorVal = new Color[]{
                Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW
        };
        colorMap = new HashMap();
        for (int i = 0; i < colorStr.length; i++) {
            colorMap.put(colorStr[i], colorVal[i]);
        }
        cbColor = new JComboBox(colorStr);
        showPan = new JPanel();
        ok = new JButton("Confirm");
        cancel = new JButton("Cancel");


        this.setLayout(null);
        add(lblFont);
        lblFont.setBounds(12, 10, 50, 20);
        txtFont.setEditable(false);
        add(txtFont);
        txtFont.setBounds(10, 30, 155, 20);
        txtFont.setText("Arial");
        lstFont.setSelectedValue("Arial", true);
        if (font != null) {
            txtFont.setText(font.getName());
            lstFont.setSelectedValue(font.getName(), true);
        }

        add(spFont);
        spFont.setBounds(10, 50, 155, 100);

        add(lblStyle);
        lblStyle.setBounds(175, 10, 50, 20);
        txtStyle.setEditable(false);
        add(txtStyle);
        txtStyle.setBounds(175, 30, 130, 20);
        lstStyle.setBorder(javax.swing.BorderFactory.createLineBorder(Color.gray));
        add(lstStyle);
        lstStyle.setBounds(175, 50, 130, 100);
        txtStyle.setText("Plain"); 
        lstStyle.setSelectedValue("Plain", true);   
        if (font != null) {
            lstStyle.setSelectedIndex(font.getStyle());
            if (font.getStyle() == 0) {
                txtStyle.setText("Plain");
            } else if (font.getStyle() == 1) {
                txtStyle.setText("Bold");
            } else if (font.getStyle() == 2) {
                txtStyle.setText("Italic");
            } else if (font.getStyle() == 3) {
                txtStyle.setText("Bold Italic");
            }
        }


        add(lblSize);
        lblSize.setBounds(320, 10, 30, 20);
        txtSize.setEditable(false);
        add(txtSize);
        txtSize.setBounds(320, 30, 60, 20);
        add(spSize);
        spSize.setBounds(320, 50, 60, 100);
        lstSize.setSelectedValue("9", false);
        txtSize.setText("9");
        if (font != null) {
            lstSize.setSelectedValue(Integer.toString(font.getSize()), false);
            txtSize.setText(Integer.toString(font.getSize()));
        }

        add(lblColor);
        lblColor.setBounds(18, 220, 50, 20);
        cbColor.setBounds(18, 245, 100, 22);
        cbColor.setMaximumRowCount(5);
        add(cbColor);
        otherColor.setForeground(Color.blue);
        otherColor.setBounds(130, 245, 100, 22);
        otherColor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(otherColor);

        showTF = new JTextField();
        showTF.setEnabled(true);
        showTF.setFont(new Font(current_fontName, current_fontStyle, current_fontSize));
        showTF.setBounds(10, 10, 300, 50);
        showTF.setHorizontalAlignment(JTextField.CENTER);
        showTF.setText("");
        showTF.setBackground(Color.white);
        showTF.setEditable(true);
        showPan.setBorder(javax.swing.BorderFactory.createTitledBorder("Example"));
        add(showPan);
        showPan.setBounds(13, 150, 370, 80);
        showPan.setLayout(new BorderLayout());
        showPan.add(showTF);
        if (font != null) {
            showTF.setFont(font); 
        }
        if (font != null) {
            showTF.setForeground(color);
        }

        add(ok);
        ok.setBounds(200, 245, 100, 20);
        add(cancel);
        cancel.setBounds(300, 245, 100, 20);

        lstFont.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                current_fontName = (String) lstFont.getSelectedValue();
                txtFont.setText(current_fontName);
                showTF.setFont(new Font(current_fontName, current_fontStyle, current_fontSize));
            }
        });

        lstStyle.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                String value = (String) ((JList) e.getSource()).getSelectedValue();
                if (value.equals("Plain")) {
                    current_fontStyle = Font.PLAIN;
                }
                if (value.equals("Italic")) {
                    current_fontStyle = Font.ITALIC;
                }
                if (value.equals("Bold")) {
                    current_fontStyle = Font.BOLD;
                }
                if (value.equals("Bold Italic")) {
                    current_fontStyle = Font.BOLD | Font.ITALIC;
                }
                txtStyle.setText(value);
                showTF.setFont(new Font(current_fontName, current_fontStyle, current_fontSize));
            }
        });

        lstSize.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                current_fontSize = (Integer) sizeMap.get(lstSize.getSelectedValue());
                txtSize.setText(String.valueOf(current_fontSize));
                showTF.setFont(new Font(current_fontName, current_fontStyle, current_fontSize));
            }
        });

        cbColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                current_color = (Color) colorMap.get(cbColor.getSelectedItem());
                showTF.setForeground(current_color);
            }
        });
        otherColor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color col_temp = new JColorChooser().showDialog(null, null, Color.pink);
                if (col_temp != null) {
                    current_color = col_temp;
                    showTF.setForeground(current_color);
                    super.mouseClicked(e);
                }
            }
        });
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setSelectedfont(new Font(current_fontName, current_fontStyle, current_fontSize));
                setSelectedcolor(current_color);
                Str = showTF.getText();
                dialog.dispose();
                dialog = null;
            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                dialog = null;
            }
        });
    }

    public void showDialog(Frame parent, int x, int y) {
        String title = "Font";
        dialog = new JDialog(parent, title, true);
        dialog.add(this);
        dialog.setResizable(false);
        dialog.setSize(400, 310);
        dialog.setLocation(x, y);
        dialog.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                dialog.removeAll();
                dialog.dispose();
                dialog = null;
            }
        });
        dialog.setVisible(true);
    }

}