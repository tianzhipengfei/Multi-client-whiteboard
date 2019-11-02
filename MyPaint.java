

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class MyPaint extends JFrame implements ActionListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = -399667390692605853L;
	private JButton btt;                        //Current color  button
    private ButtonListener bl;                  //Color button listener
    private RadioButtonListener rbl;            //Function button listener
    private MyDraw md;                          //Canvas
    private JMenuBar bar;
    private JMenu menuFile;
    private JMenu menuOperate;
    private JMenu menuHelp;
    private JMenuItem newFile;
    private JMenuItem openFile;
    private JMenuItem saveFile;
    private JMenuItem saveAsFile;
    private JMenuItem saveimgFile;
    private JMenuItem revoke;
    private JMenuItem voke;
    private JMenuItem help;
    private JMenuItem about;
    private JPanel panelCentral;
    private JPanel panelLeft;
    private ButtonGroup bg;
    private LineWidthChooser lineWidthChooser;
    private JPanel panelDown;
    private JPanel panelDownLeft;
    private JPanel panelDownRight;
    private JPanel panelDownChild;
    private String currentPath = "";
    private JPanel panelright;//new
    private JTextArea userList;
    private JMenuItem kick;
    private IRemoteBoard remoteBoard;
    private JPanel messagePanel,typePanel;
    private JTextArea messageList;
    private JTextField field;
    private JButton sendButton;
    private JScrollPane scrollPane;
    private String username;
    private int exitFlag = 0;
    private JScrollPane userscrollPane;
    //Init Frame
    public void initFrame(boolean adminFlag, final IRemoteBoard remoteBoard,final String username){
    	this.username = username;
    	this.remoteBoard = remoteBoard;
        JPanel panel = new JPanel();
        btt = new JButton();
        md = new MyDraw(btt,remoteBoard,username);
        bl = new ButtonListener(this.md, btt);
        rbl = new RadioButtonListener(this.md);
        bar= new JMenuBar();
        menuFile = new JMenu("File");
        menuOperate = new JMenu("Edit");
        menuHelp = new JMenu("Help");
        panelCentral = new JPanel();
        panelLeft = new JPanel();
        bg = new ButtonGroup();
        lineWidthChooser = new LineWidthChooser(md);
        panelDown = new JPanel();
        panelDownChild = new JPanel();
        panelDownLeft = new JPanel();
        panelDownRight = new JPanel();
        panelright = new JPanel();

        //Set properties
        this.setSize(850,500);
        this.setDefaultCloseOperation(3);
        this.setTitle(username);
        this.setLocationRelativeTo(null);
        panel.setLayout(new BorderLayout(1,0));
        this.add(panel);

        //Add menu bar
        if (adminFlag == true) {
        	panel.add(bar,BorderLayout.NORTH);
        }
        //Add three menu into menu bar
        bar.add(menuFile);
        bar.add(menuOperate);
        bar.add(menuHelp);

        //Add four menu item into the first menu(File)
        newFile= new JMenuItem("New File");
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK));         //Add shortcutkeys
        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value=JOptionPane.showConfirmDialog(null, "Do you want to save this image", "Tips", 0);
                if(value==0){
                    saveFileAs(0);
                }
                if(value==1){
                    md.reset();
                }
            }
        });
        

        //Add four menu item into the first menu(File)
        JMenuItem closeFile= new JMenuItem("Close File");
        closeFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	md.reset();
            }
        });

        openFile = new JMenuItem("Open Project File");
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,Event.CTRL_MASK));         //Add shortcutkeys
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value=JOptionPane.showConfirmDialog(null, "Do you want to save this image", "Tips", 0);
                if(value==0){
                    saveFileAs(0);
                }
                if(value==1){
                    try {
                        //Show file chooser to let user select file
                        JFileChooser chooser = new JFileChooser();
                        chooser.showOpenDialog(null);
                        File file =chooser.getSelectedFile();
                        //if file doesn't exist, show dialog "NO FILE"
                        if(file==null){
                            JOptionPane.showMessageDialog(null, "No file is selected");
                            md.repaint();
                        }
                        else {
                        	//need edit
                            //delete all shapes in Shapelist
                            remoteBoard.getAllShape().clear();
                            md.repaint();
                            currentPath = file.getAbsolutePath();
                            FileInputStream fis = new FileInputStream(file);
                            ObjectInputStream ois = new ObjectInputStream(fis);
                            //Get objects from file
                            ArrayList<MyShape> list =(ArrayList<MyShape>)ois.readObject();
                            //Add objects into list
                            for (int i = 0; i <list.size(); i++) {
                                MyShape shape=(MyShape)list.get(i);
                                remoteBoard.getAllShape().add(shape);
                                //Repaint to show the painting
                                md.repaint();
                            }
                            ois.close();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        saveFile = new JMenuItem("Save Project File");
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK));         //Add shortcutkeys
        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }           //0 means save project (Objects)
        });

        saveAsFile = new JMenuItem("Save as Project File");
        saveAsFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK+Event.SHIFT_MASK));         //Add shortcutkeys
        saveAsFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFileAs(0);
            }           //0 means save project (Objects)
        });

        saveimgFile = new JMenuItem("Save image file");
//        saveimgFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.SHIFT_MASK+Event.CTRL_MASK));         //Add shortcutkeys
        saveimgFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFileAs(1);
            }           //1 means save a picture
        });

        menuFile.add(newFile);
        menuFile.add(openFile);
        menuFile.add(saveFile);
        menuFile.add(saveAsFile);
        menuFile.add(saveimgFile);
        menuFile.add(closeFile);

        //Add two menu item into the second menu(Edit)
//        revoke = new JMenuItem("revoke");
//        revoke.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,Event.CTRL_MASK));         //Add shortcutkeys
//        revoke.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            	//need edit md.revoke();
//            }       //Call method revoke in MyDraw
//        });

//        voke = new JMenuItem("voke");
//        voke.setAccelerator(KeyStroke .getKeyStroke(KeyEvent.VK_Z,Event.SHIFT_MASK+Event.CTRL_MASK));         //Add shortcutkeys
//        voke.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //need edit md.voke();
//            }       //Call method voke in MyDraw
//        });
        
        kick = new JMenuItem("kick");
        kick.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		String value=JOptionPane.showInputDialog("Which user to kick?");
                try {
					if(remoteBoard.kickUser(value) == 0) {
						JOptionPane.showMessageDialog(null, value + " does not exist.");
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
        	}
        });
        

//        menuOperate.add(revoke);
//        menuOperate.add(voke);
        menuOperate.add(kick);

        //Add two menu item into the third menu(Help)
        help = new JMenuItem("Help");
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,Event.CTRL_MASK));         //Add shortcutkeys
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Show help dialog
                JOptionPane.showMessageDialog(null,
                        "Erasor:  rub out anything you draw\n" +
                        "Pencil: use a pencil to free draw something you want\n" +
                        "Spray: use a spray to free draw something you want\\n\n" +
                        "Brush: use a brush to free draw something you want\\n\n" +
                        "Font: choose a font for text\n" +
                        "Line size: choose differnet width for shapes\n");
            }
        });

        about = new JMenuItem("About");
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK));         //Add shortcutkeys
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Show about dialog
                JOptionPane.showMessageDialog(null, "Developed by The A Team");
            }
        });

        menuHelp.add(help);
        menuHelp.add(about);


        //Add central JPanel
        panelCentral.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        panel.setBackground(Color.gray);
        panel.add(panelCentral);
        //Set MyDraw's properties
        md.setBackground(Color.WHITE);
        md.setPreferredSize(new Dimension(500,330));
        //Add MyDraw into central JPanel
        panelCentral.add(md);

        //Add left JPanel (Function RadioButton JPanel)
        panelLeft.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        panelLeft.setBackground(new Color(235,233,238));
        panelLeft.setPreferredSize(new Dimension(110, 0));
        //Add RatioButtons in the central left JPanel

        for(int i=2;i<16;i++){
            //Add four different resources.images to each RadioBuuton
            String p = "bin/Resources/images/draw"+i+".jpg";
            String p1 = "bin/Resources/images/draw"+i+"-1"+".jpg";
            String p2 = "bin/Resources/images/draw"+i+"-2"+".jpg";
            String p3 = "bin/Resources/images/draw"+i+"-3"+".jpg";
            ImageIcon img1 = new ImageIcon(p);         //Default image
            ImageIcon img2 = new ImageIcon(p1);    //Image when mouse moves upon button
            ImageIcon img3 = new ImageIcon(p2);    //Image when mouse pressed
            ImageIcon img4 = new ImageIcon(p3);    //Image when selected
            JRadioButton jrb = new JRadioButton();
            jrb.setActionCommand("pic"+i);
            //Set default function is draw Line
            if(i==10){
                jrb.setSelected(true);
            }
            jrb.addActionListener(rbl);
            jrb.setIcon(img1);
            jrb.setRolloverIcon(img2);
            jrb.setPressedIcon(img3);
            jrb.setSelectedIcon(img4);
            jrb.setBorder(null);            //Set no Border, since we added the border in image. :)
            if(i == 3 || i == 4 || i == 5 || i == 13) {
            	continue;
            }
            bg.add(jrb);            //add to buttonGroup
            panelLeft.add(jrb);
        };

        //Add line width chooser into left JPanel
        panelLeft.add(new JLabel("line size"));
        panelLeft.add(lineWidthChooser.getBox());

        //Add down JPanel
        panelDown.setBackground(Color.gray);
        panelDown.setLayout(null);
        panelDown.setPreferredSize(new Dimension(0, 80));

        //Add down child JPanel
        panelDownChild.setLayout(null);
        panelDownChild.setBounds(15, 10, 380, 60);
        panelDownChild.setBackground(Color.green);

        //Add down left JPanel (for current color)
        panelDownLeft.setLayout(null);
        panelDownLeft.setBackground(Color.white);
        panelDownLeft.setBounds(0, 0, 60, 60);

        //Add down right JPanel (for choosing color)
        panelDownRight.setBackground(null);
        panelDownRight.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        panelDownRight.setBounds(60, 0, 320, 60);

        //Add child JPanel into parent JPanel and set position
        panel.add(panelLeft,BorderLayout.WEST);
        panel.add(panelDown, BorderLayout.SOUTH);
        panelDown.add(panelDownChild);
        panelDownChild.add(panelDownLeft);
        panelDownChild.add(panelDownRight);

        //Add Button to show current color
        btt.setBackground(Color.black);
        btt.setBounds(10, 10, 30, 30);
        panelDownLeft.add(btt);

        //Add onSet special effect
        BevelBorder  bb = new BevelBorder(0,Color.gray,Color.white);        //Set border for button
        BevelBorder  bb1 = new BevelBorder(1,Color.gray,Color.white);
        panelDownLeft.setBorder(bb);
        btt.setBorder(bb1);

        //Add right side color
        int min = 0;
        int max = 254;
        Color colors[] = {Color.BLUE,Color.red,Color.black,Color.ORANGE,Color.gray, Color.CYAN,Color.GREEN,Color.YELLOW,Color.PINK,Color.magenta,new Color(234,45,78),new Color(67,123,9),new Color(ThreadLocalRandom.current().nextInt(min, max + 1),ThreadLocalRandom.current().nextInt(min, max + 1),ThreadLocalRandom.current().nextInt(min, max + 1)), new Color(ThreadLocalRandom.current().nextInt(min, max + 1),ThreadLocalRandom.current().nextInt(min, max + 1),ThreadLocalRandom.current().nextInt(min, max + 1)), new Color(ThreadLocalRandom.current().nextInt(min, max + 1),ThreadLocalRandom.current().nextInt(min, max + 1),ThreadLocalRandom.current().nextInt(min, max + 1)), new Color(ThreadLocalRandom.current().nextInt(min, max + 1),ThreadLocalRandom.current().nextInt(min, max + 1),ThreadLocalRandom.current().nextInt(min, max + 1))};
        for(int i=0;i<16;i++){
            JButton bt = new JButton();
            bt.setBackground(colors[i]);
            bt.setBorder(bb);
            bt.setPreferredSize(new Dimension(40,30));
            //Add button listener for each button
            bt.addActionListener(bl);
            bt.setOpaque(true);
            panelDownRight.add(bt);
        }
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
        	  public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        	    int confirmed = JOptionPane.showConfirmDialog(null, 
        	        "Are you sure you want to exit the program?", "Exit Program Message Box",
        	        JOptionPane.YES_NO_OPTION);

        	    if (confirmed == JOptionPane.YES_OPTION) {
        	    	try {
						remoteBoard.kickUser(username);
						System.exit(0);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        	    	
        	    }
        	  }
        	});
        panelright = new JPanel();

        //Set properties
        panelright.setLayout(new BorderLayout(0,4));
        userList = new JTextArea(4,10);
        userscrollPane = new JScrollPane(userList);
        DefaultCaret usercaret = (DefaultCaret)userList.getCaret();
        usercaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        userList.setEditable(false);
        panelright.add(userscrollPane,BorderLayout.NORTH);
        
        messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout(0,1));
        messagePanel.setSize(14, 10);
        messageList = new JTextArea(13,10);
        scrollPane = new JScrollPane(messageList);
        DefaultCaret caret = (DefaultCaret)messageList.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        messageList.setEditable(false);
        messageList.append("");
        messagePanel.add(scrollPane,BorderLayout.NORTH);
        
        typePanel = new JPanel();
        typePanel.setLayout(new FlowLayout());
        
        field = new JTextField(10);
        field.setText("");
        typePanel.add(field);
        sendButton = new JButton("send");
        sendButton.addActionListener(this);
        typePanel.add(sendButton);
        messagePanel.add(typePanel,BorderLayout.SOUTH);
        
        panelright.add(messagePanel);
        
        panel.add(panelright,BorderLayout.EAST);
        this.setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == sendButton) {
    		String word = field.getText().trim();
    		if(word == "") {
				return;
			}
    		try {
				remoteBoard.sendMessage(username +": " + word);
				field.setText("");
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
    	}
    }
    
    public void updateMessageList() throws RemoteException{
    	ArrayList<String> tmp = remoteBoard.getAllMessage();
    	messageList.setText("");
    	for(int i=0;i<tmp.size();i++) {
    		messageList.append(tmp.get(i)+"\n");
    	}
    }
    
    public void updateUserList() throws RemoteException {
    	ArrayList<String> tmp = remoteBoard.getUserList();
    	userList.setText("");
    	for(int i=0;i<tmp.size();i++) {
    		userList.append(tmp.get(i)+"\n");
    	}
    	
    }
    
    public void updateShape() {
    	md.repaint();
    }
    //Save file into project file or image file
    public void saveFile(){
    	if (currentPath.equals("")){
    		saveFileAs(0);
    	} else{
    		try{
        		File file = new File(currentPath);
        		FileOutputStream fis = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fis);
                //Add all shapes in list into output stream
                oos.writeObject(remoteBoard.getAllShape());
                oos.close(); 
                currentPath = file.getAbsolutePath();
                JOptionPane.showMessageDialog(null, "Save sucessfully");   			
    		} catch (Exception e1) {
                e1.printStackTrace();
            }
    	}
    }
    
    public void saveFileAs(int type){
        //Get file (including name and path)
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(null);
        File file =chooser.getSelectedFile();

        if(file==null){
            JOptionPane.showMessageDialog(null, "No file is selected");
        }else {

            try {
                if(type==1){
                    //Save as an jpg file
                    File file1 = new File(file.getAbsoluteFile()+".jpg");   //Add ".jpg"
                    md.savePic(file1);
                } else{
                	//need edit
                    FileOutputStream fis = new FileOutputStream(file);
                    ObjectOutputStream oos = new ObjectOutputStream(fis);
                    //Add all shapes in list into output stream
                    oos.writeObject(remoteBoard.getAllShape());
                    oos.close();
                    currentPath = file.getAbsolutePath();
                };
                JOptionPane.showMessageDialog(null, "Save as sucessfully");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
