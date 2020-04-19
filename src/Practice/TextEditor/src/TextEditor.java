import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class TextEditor extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4980965590591140805L;
	private JMenuItem menuOpen;
    private JMenuItem menuSave;
    private JMenuItem menuSaveAs;
    private JMenuItem menuClose;

    private JMenu editMenu;
    private JMenuItem menuCut;
    private JMenuItem menuCopy;
    private JMenuItem menuPaste;

    private JMenuItem menuAbout;
    
    static JTextArea textArea;
    private JLabel stateBar;
    private JFileChooser fileChooser;
    
    private JPopupMenu popUpMenu;
    private JMenuItem menuItem;

    public TextEditor() {
        super("�½��ı��ļ�");
        setTitle("\u6587\u672C\u7F16\u8F91\u5668");
        setUpUIComponent();
        setUpEventListener();
        setVisible(true);
    }
    
    private void setUpUIComponent() {
        setSize(566, 401);
         
        JMenuBar menuBar = new JMenuBar();
        
        //�ļ�
        JMenu fileMenu = new JMenu("\u6587\u4EF6");
        
        menuOpen = new JMenuItem("\u6253\u5F00");
        menuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
        fileMenu.add(menuOpen);
        
        menuSave = new JMenuItem("\u4FDD\u5B58");
        menuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
        fileMenu.add(menuSave);
        
        menuSaveAs = new JMenuItem("\u53E6\u5B58\u4E3A");
        fileMenu.add(menuSaveAs); 

        menuClose = new JMenuItem("\u9000\u51FA");
        menuClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.CTRL_MASK));
        fileMenu.add(menuClose);
        
     
        //�༭        
        JMenu editMenu = new JMenu("\u7F16\u8F91");
        
        menuCut = new JMenuItem("\u526A\u5207");
        menuCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
        editMenu.add(menuCut);
        
        menuCopy = new JMenuItem("\u590D\u5236");
        menuCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
        editMenu.add(menuCopy);
        
        menuPaste = new JMenuItem("\u7C98\u8D34");
        menuPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK));
        editMenu.add(menuPaste);
        

        //����        
        JMenu aboutMenu = new JMenu("\u5E2E\u52A9");
        menuAbout = new JMenuItem("\u5B58\u5728\u7684\u95EE\u9898");
        aboutMenu.add(menuAbout);
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        
        menuItem = new JMenuItem("\u5B57\u6570\u7EDF\u8BA1");
        menuItem.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
        		TextCount textcount=new TextCount();
        		textcount.qu();
        	}
        });
        editMenu.add(menuItem);
        menuBar.add(aboutMenu);

       
        setJMenuBar(menuBar);
        
        //���ֱ༭����
        textArea = new JTextArea();
        textArea.setFont(new Font("����", Font.PLAIN, 20));
        textArea.setLineWrap(true);
        JScrollPane panel = new JScrollPane(textArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        Container contentPane = getContentPane();
        contentPane.add(panel, BorderLayout.CENTER);  
        
        //�Ƿ��޸�
        stateBar = new JLabel("δ�޸�");
        stateBar.setHorizontalAlignment(SwingConstants.LEFT); 
        stateBar.setBorder(BorderFactory.createEtchedBorder());      
        
        popUpMenu = editMenu.getPopupMenu();
        fileChooser = new JFileChooser();
    }
    
    
    private void setUpEventListener() {
        
    	//���´��ڹر�ť�¼�����
        addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) { 
                    closeFile();
                }
            }
        );
        
        //��
        menuOpen.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    openFile();
                }
            }
        );

        //����
        menuSave.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveFile();
                }
            }
        );

        //����Ϊ
        menuSaveAs.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveFileAs();
                }
            }
        );


        //�˳�
        menuClose.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    closeFile();
                }
            }
        );

        //����
        menuCut.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cut();
                }
            }
        );

        //����
        menuCopy.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    copy();
                }
            }
        );

        //ճ��
        menuPaste.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    paste();
                }
            }
        );
        
        //����
        menuAbout.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // ��ʾ�Ի���
                    JOptionPane.showOptionDialog(null,"���ڵ�Bug��\n�����ı����Ҽ�֮�󣬱༭�˵���������","����TextEditor",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null, null, null);
                }
            }
        );      
        
        //�༭�������¼�
        textArea.addKeyListener(
            new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    processTextArea();
                }
            }
        );

        //�༭������¼�
        textArea.addMouseListener(
            new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    if(e.getButton() == MouseEvent.BUTTON3)
                        popUpMenu.show(editMenu, e.getX(), e.getY());
                }
                
                public void mouseClicked(MouseEvent e) {
                    if(e.getButton() == MouseEvent.BUTTON1)
                        popUpMenu.setVisible(false);
                }
            }
        );        
    }

    private void openFile() {
        if(isCurrentFileSaved()) { //�ļ��Ƿ�Ϊ����״̬
            open(); //��
        }
        else {
            //��ʾ�Ի���
            int option = JOptionPane.showConfirmDialog(
                    null, "�ļ����޸ģ��Ƿ񱣴棿","�����ļ���", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE, null);
            switch(option) { 
                //ȷ���ļ�����
                case JOptionPane.YES_OPTION:
                    saveFile(); //�����ļ�
                     break;
               //�����ļ�����
                case JOptionPane.NO_OPTION:
                    open();
                    break;
            }
        }
    }
    private boolean isCurrentFileSaved() {
        if(stateBar.getText().equals("���޸�")) {
            return false;
        }
        else {
            return true;
        }
    }
    private void open() {
        
        //��ʾ�ļ�ѡȡ�ĶԻ���
        int option = fileChooser.showDialog(null, null);
            
        //ʹ���߰���ȷ�ϼ�
        if(option == JFileChooser.APPROVE_OPTION) {
            try {
                //����ѡȡ���ļ�
                BufferedReader buf = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));

                //�趨�ļ�����
                setTitle(fileChooser.getSelectedFile().toString());
                //���ǰһ���ļ�
                textArea.setText("");
                //�趨״̬��
                stateBar.setText("δ�޸�");
                //ȡ��ϵͳ�����Ļ����ַ�
                String lineSeparator = System.getProperty("line.separator");
                //��ȡ�ļ������������ֱ༭��
                String text;
                while((text = buf.readLine()) != null) {
                    textArea.append(text);
                    textArea.append(lineSeparator);
                }

                buf.close();
            }   
            catch(IOException e) {
                JOptionPane.showMessageDialog(null, e.toString(),"��ʧ��", JOptionPane.ERROR_MESSAGE);
            }
        }        
    }
    private void saveFile() {

        File file = new File(getTitle());

        //��ָ�����ļ�������
        if(!file.exists()) {
            //ִ������Ϊ
            saveFileAs();
        }
        else {
            try {
                //����ָ�����ļ�
                BufferedWriter buf = new BufferedWriter(new FileWriter(file));
                //�����ֱ༭��������д���ļ�
                buf.write(textArea.getText());
                buf.close();
                //�趨״̬��Ϊδ�޸�
                stateBar.setText("δ�޸�");
            }
            catch(IOException e) {
                JOptionPane.showMessageDialog(null, e.toString(),"���治�ɹ�", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFileAs() {
        //��ʾ�ļ��Ի���
        int option = fileChooser.showSaveDialog(null);

        //���ȷ��ѡȡ�ļ�
        if(option == JFileChooser.APPROVE_OPTION) {
            //ȡ��ѡ����ļ�
            File file = fileChooser.getSelectedFile();
            
            //�ڱ��������趨�ļ�����
            setTitle(file.toString());
                
            try {
                //�����ļ�
                file.createNewFile();
                //�����ļ�����
                saveFile();
            }
            catch(IOException e) {
            JOptionPane.showMessageDialog(null, e.toString(),"�޷��������ļ�", JOptionPane.ERROR_MESSAGE);
            }
        }   
    }

    private void closeFile() {
        //�Ƿ��ѱ����ļ�
        if(isCurrentFileSaved()) {
            //�ͷŴ�����Դ������رճ���
            dispose();
        }
        else {
            int option = JOptionPane.showConfirmDialog(null, "�ļ����޸ģ��Ƿ񱣴棿","�����ļ���", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE, null);

            switch(option) {
                case JOptionPane.YES_OPTION:
                    saveFile();
                    break;
                case JOptionPane.NO_OPTION:
                   dispose();
            }
        }
    }

    private void cut() {
        textArea.cut();
        stateBar.setText("���޸�");
        popUpMenu.setVisible(false);
    }

    private void copy() {
        textArea.copy();
        popUpMenu.setVisible(false);    
    }

    private void paste() {
        textArea.paste();
        stateBar.setText("���޸�");
        popUpMenu.setVisible(false);
    }

    private void processTextArea() {
            stateBar.setText("���޸�");
    }

 
    public static void main(String[] args) {
        new TextEditor();
    }    
   }