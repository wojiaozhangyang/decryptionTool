package demo.window;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;


import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



import demo.function.pfx.PFX;
import demo.function.sm4.SM4Utils;
import demo.function.ziputil.ZipUtil;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JScrollPane;


/**
 * rsaTool 主窗体
 * @author jelly
 *
 */
public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private  SM4Utils sm4 = new SM4Utils();
 
	
	
	/**-------------------PFX decipher-------------------**/	
	private JTextField panel_1_textField_1;
	private JLabel   panel_1_label_1;
	private JLabel   panel_1_label_2;
	private JButton  panel_1_button_1,panel_1_button_2;
	private JScrollPane panel_1_scrollPane_1;
	private JTextArea panel_1_textArea_1;
	//private JTextArea panel_1_textArea_2;
	private JButton  panel1_btn;
	
	/**-------------------SM4 decipher-------------------**/
	private JLabel panel_2_label_1;
	private JTextField panel_2_textField_1;
	private JLabel panel_2_label_2;
	private JScrollPane panel_2_scrollPane_1;
	private JTextArea panel_2_textArea_1;
	private JButton panel_2_button_1;
	private JButton panel_2_button_2;	
	private JButton  panel2_btn;

	/**-------------------ZIP decipher-------------------**/	
//	private JTextField panel_3_textField_1;
//	private JLabel   panel_3_label_1;
	private JLabel   panel_3_label_2;
	private JButton  panel_3_button_1,panel_3_button_2;
	private JScrollPane panel_3_scrollPane_1;
	private JTextArea panel_3_textArea_1;
	//private JTextArea panel_1_textArea_2;
	private JButton  panel3_btn;
	
	
	
	
	private String plantTxt;
	private byte[] plantTxt_BYTE;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					
					frame.setLocationRelativeTo(null);//主窗体居中
					 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/demo/Tool/img/tools_24px.png")));
		setFont(new Font("微软雅黑", Font.PLAIN, 18));
		setBackground(Color.LIGHT_GRAY);
		setTitle("MINI Tool");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 635, 514);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		/**------------------------------draw the picture of PFX decipher------------------------------**/
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("PFX文件解析", new ImageIcon(MainFrame.class.getResource("/demo/Tool/img/encrypt_3_24px.png")), panel_1, null);
		tabbedPane.setForegroundAt(0, Color.BLACK);		
		tabbedPane.setBackgroundAt(0, Color.WHITE);
	
		
		panel_1_label_1 = new JLabel("PFX口令:");
		panel_1_label_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		panel_1_textField_1 = new JTextField();
		panel_1_textField_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		panel_1_textField_1.setColumns(10);
		
		panel_1_label_2 = new JLabel("PFX文件:");
		panel_1_label_2.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_1_label_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		panel1_btn = new JButton("浏览");
		panel1_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser chooser = new JFileChooser();
			     chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			     chooser.showDialog(new JLabel(), "选择");
			     File file = chooser.getSelectedFile();
			     panel_1_textArea_1.setText(file.getAbsoluteFile().toString());	
			     if (file.isDirectory ()){
						JOptionPane.showMessageDialog(null, "文件不能为文件夹，请选择文件！");
						panel_1_textArea_1.setText("");
				 }
			}
		});
		
		panel_1_button_1 = new JButton("解 析");
		panel_1_button_1.setIcon(new ImageIcon(MainFrame.class.getResource("/demo/Tool/img/encrypt_3_24px.png")));
		panel_1_button_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		 	    panel_1_button_1_click(e);
		    }
		});
	 
		panel_1_button_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		panel_1_button_2 = new JButton("清除文件");
		panel_1_button_2.setIcon(new ImageIcon(MainFrame.class.getResource("/demo/Tool/img/editclear_24px.png")));
		panel_1_button_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		 		panel_1_textArea_1.setText("");
		 		//panel_1_textArea_2.setText("");
		 	}
		});
	 
		panel_1_button_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		panel_1_scrollPane_1 = new JScrollPane();
	
		
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(panel_1_label_1)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(panel_1_textField_1))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(panel_1_label_2, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
									.addGap(16)
									.addComponent(panel_1_scrollPane_1, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
									.addGap(16)	
									.addComponent(panel1_btn))))
									.addGap(16)	
//						.addGroup(gl_panel_1.createSequentialGroup()
//							.addGap(20)
//							.addComponent(panel_1_label_3, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
//							.addGap(13)
//							.addComponent(panel_1_scrollPane_2, GroupLayout.PREFERRED_SIZE, 449, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(105)
							.addComponent(panel_1_button_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_1_button_2)))
					.addContainerGap(51, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel_1_label_1)
						.addComponent(panel_1_textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(panel_1_label_2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addGap(102))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(panel_1_scrollPane_1, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addGap(16))
							.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(panel1_btn, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
									.addGap(16)))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel_1_button_1)
						.addComponent(panel_1_button_2, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
//					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
//						.addGroup(gl_panel_1.createSequentialGroup()
//							.addGap(22)
//							.addComponent(panel_1_label_3, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
//						.addGroup(gl_panel_1.createSequentialGroup()
//							.addGap(24)
//							.addComponent(panel_1_scrollPane_2, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		
//		panel_1_textArea_2 = new JTextArea();
//		panel_1_textArea_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
//		panel_1_scrollPane_2.setViewportView(panel_1_textArea_2);
		
		//panel_1_textArea_1 = new JTextArea();
		panel_1_textArea_1 = new DrapArea();
		panel_1_textArea_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		panel_1_textArea_1.setEditable(false);
		panel_1_scrollPane_1.setViewportView(panel_1_textArea_1);
		panel_1.setLayout(gl_panel_1);

		
		/**------------------------------draw the picture of SM4 decipher------------------------------**/
		JPanel panel_2 = new JPanel();
		panel_2.setForeground(Color.WHITE);
		tabbedPane.addTab("SM4解密_ECB模式", new ImageIcon(MainFrame.class.getResource("/demo/Tool/img/decrypted_24px.png")), panel_2, null);
		
		panel_2_label_1 = new JLabel("SM4密钥:");
		panel_2_label_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		panel_2_textField_1 = new JTextField();
		panel_2_textField_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		panel_2_textField_1.setColumns(10);
		
		panel_2_label_2 = new JLabel("密      文:");
		panel_2_label_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));		
		panel_2_scrollPane_1 = new JScrollPane();
		panel2_btn = new JButton("浏览");
		panel2_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser chooser = new JFileChooser();
			     chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			     chooser.showDialog(new JLabel(), "选择");
			     File file = chooser.getSelectedFile();
			     panel_2_textArea_1.setText(file.getAbsoluteFile().toString());	
				 if (file.isDirectory ()){
					JOptionPane.showMessageDialog(null, "文件不能为文件夹，请选择文件！");
					panel_2_textArea_1.setText("");
				 }
			}
		});
		
		
		
		panel_2_button_1 = new JButton("解  密");
		panel_2_button_1.setIcon(new ImageIcon(MainFrame.class.getResource("/demo/Tool/img/decrypted_24px.png")));
		panel_2_button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_2_button_1_click(e);
			}
			
		});
		panel_2_button_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		panel_2_button_2 = new JButton("清除密文");
		panel_2_button_2.setIcon(new ImageIcon(MainFrame.class.getResource("/demo/Tool/img/editclear_24px.png")));
		panel_2_button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_2_textArea_1.setText("");
				//panel_2_textArea_2.setText("");
			}
		});
		panel_2_button_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));	
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
									.addGap(60)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(panel_2_label_1)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(panel_2_textField_1))
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(panel_2_label_2, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
									.addGap(16)								
									.addComponent(panel_2_scrollPane_1, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
									.addGap(16)	
									.addComponent(panel2_btn))))
									.addGap(16)	
									
									
//						.addGroup(gl_panel_2.createSequentialGroup()
//							.addGap(20)
//							.addComponent(panel_2_label_3, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
//							.addGap(13)
//							.addComponent(panel_2_scrollPane_2, GroupLayout.PREFERRED_SIZE, 449, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(105)
							.addComponent(panel_2_button_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_2_button_2)))
					.addContainerGap(51, Short.MAX_VALUE))//51
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(24)//24
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel_2_label_1)
						.addComponent(panel_2_textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(panel_2_label_2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addGap(102))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(panel_2_scrollPane_1, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addGap(16))
							.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(panel2_btn, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addGap(16)))
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel_2_button_1)
						.addComponent(panel_2_button_2, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(28, Short.MAX_VALUE))
		);		
		panel_2_textArea_1 = new DrapArea();
		panel_2_textArea_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		panel_2_textArea_1.setEditable(false);
		panel_2_scrollPane_1.setViewportView(panel_2_textArea_1);
		panel_2.setLayout(gl_panel_2);
		//tabbedPane.setForegroundAt(1, Color.BLACK);
		
		/**------------------------------draw the picture of ZIP decipher------------------------------**/
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("ZIP文件解析", new ImageIcon(MainFrame.class.getResource("/demo/Tool/img/encrypt_3_24px.png")), panel_3, null);
		tabbedPane.setForegroundAt(0, Color.BLACK);		
		tabbedPane.setBackgroundAt(0, Color.WHITE);
	
		
//		panel_3_label_1 = new JLabel("PFX口令:");
//		panel_3_label_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
//		
//		panel_3_textField_1 = new JTextField();
//		panel_3_textField_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
//		panel_3_textField_1.setColumns(10);
		
		panel_3_label_2 = new JLabel("PFX文件:");
		panel_3_label_2.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_3_label_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		panel3_btn = new JButton("浏览");
		panel3_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser chooser = new JFileChooser();
			     chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			     chooser.showDialog(new JLabel(), "选择");
			     File file = chooser.getSelectedFile();
			     panel_3_textArea_1.setText(file.getAbsoluteFile().toString());	
			     if (file.isDirectory ()){
						JOptionPane.showMessageDialog(null, "文件不能为文件夹，请选择文件！");
						panel_3_textArea_1.setText("");
				 }
			}
		});
		
		panel_3_button_1 = new JButton("解 析");
		panel_3_button_1.setIcon(new ImageIcon(MainFrame.class.getResource("/demo/Tool/img/encrypt_3_24px.png")));
		panel_3_button_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		 	    panel_3_button_1_click(e);
		    }
		});
	 
		panel_3_button_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		panel_3_button_2 = new JButton("清除文件");
		panel_3_button_2.setIcon(new ImageIcon(MainFrame.class.getResource("/demo/Tool/img/editclear_24px.png")));
		panel_3_button_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		 		panel_3_textArea_1.setText("");
		 		//panel_1_textArea_2.setText("");
		 	}
		});
	 
		panel_3_button_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		panel_3_scrollPane_1 = new JScrollPane();
	
		
		
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
//								.addGroup(gl_panel_3.createSequentialGroup()
//									.addComponent(panel_3_label_1)
//									.addPreferredGap(ComponentPlacement.UNRELATED)
//									.addComponent(panel_3_textField_1))
								.addGroup(gl_panel_3.createSequentialGroup()
									.addComponent(panel_3_label_2, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
									.addGap(16)
									.addComponent(panel_3_scrollPane_1, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
									.addGap(16)	
									.addComponent(panel3_btn))))
									.addGap(16)	
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(105)
							.addComponent(panel_3_button_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_3_button_2)))
					.addContainerGap(51, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(24)
//					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
//						.addComponent(panel_3_label_1)
//						.addComponent(panel_3_textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(panel_3_label_2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addGap(102))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(panel_3_scrollPane_1, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addGap(16))
							.addGroup(gl_panel_3.createSequentialGroup()
									.addComponent(panel3_btn, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
									.addGap(16)))
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel_3_button_1)
						.addComponent(panel_3_button_2, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		

		panel_3_textArea_1 = new DrapArea();
		panel_3_textArea_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		panel_3_textArea_1.setEditable(false);
		panel_3_scrollPane_1.setViewportView(panel_3_textArea_1);
		panel_3.setLayout(gl_panel_3);

	}
//	/**
//	 * description:choose the path of films
//	 * @param e
//	 */
//	private void chooseFilm(ActionEvent e){
//		 JFileChooser chooser = new JFileChooser();
//	     chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//	     chooser.showDialog(new JLabel(), "选择");
//	     File file = chooser.getSelectedFile();
//	     this.panel_2_textArea_1.setText(file.getAbsoluteFile().toString());	    
//    }
	private void panel_1_button_1_click(ActionEvent e) {
		//String name="123";
		
		try {
			String word= this.panel_1_textField_1.getText();
			String content =   this.panel_1_textArea_1.getText();
			
			if(word==null ||word.trim().equals("")) {
				JOptionPane.showMessageDialog(null, "PFX口令不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
				return ;
			}else if(word.length()>18){
				JOptionPane.showMessageDialog(null, "PFX口令的长度不能超过18！", "错误", JOptionPane.ERROR_MESSAGE);
				return ;
			}
			if(content==null ||content.trim().equals("")) {
				//JOptionPane.showMessageDialog(null, "明文内容不能为空！", "信息", JOptionPane.INFORMATION_MESSAGE);
				  JOptionPane.showMessageDialog(null, "PFX文件不能为空，请选择文件！");//默认的  提示框 title为“信息” type为  INFORMATION_MESSAGE
				return ;
			}
			byte[] data=readFile_BYTE(content);
			String[] tmpName=content.split("\\\\");
			String[] name=tmpName[tmpName.length-1].split("\\.");
			String  result  = PFX.analysePFXInfo(name[0],word, data);
			if(result==null){
				JOptionPane.showMessageDialog(null, "PFX口令错误或者PFX文件有误", "错误", JOptionPane.ERROR_MESSAGE);
			    return ;
			}else{
				JOptionPane.showMessageDialog(null, "分析PFX成功！");
				//byte[] loadContent=readFile_BYTE(result);
				JFileChooser chooser = new JFileChooser();
	            if (chooser.showSaveDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION) {
	            	File file = chooser.getSelectedFile();
	                //loadFile(file.getPath());
	                 //loadFile_BYTE(file.getPath());
	            	copyDir(result,file.getPath());
	                JOptionPane.showMessageDialog(null, "成功保存文件在目录:"+file.getPath());
	                delFolder(result);
	            }
			}
			//this.panel_1_textArea_2.setText(cipherContent);
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
		}	
	}
	/**
	 * SM4 decipher
	 * @param e
	 */
	private void panel_2_button_1_click(ActionEvent e) {
		try {
			String privateKeyStr =this.panel_2_textField_1.getText();
			String cipherContent =   this.panel_2_textArea_1.getText();
			if(privateKeyStr==null ||privateKeyStr.trim().equals("")) {
				JOptionPane.showMessageDialog(null, "SM4密钥不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
				return ;
			}else if(privateKeyStr.length()!=16){
				JOptionPane.showMessageDialog(null, "SM4密钥长度要为16！", "错误", JOptionPane.ERROR_MESSAGE);
				this.panel_2_textField_1.setText("");
				return ;
			}
			if(cipherContent==null ||cipherContent.trim().equals("")) {
				JOptionPane.showMessageDialog(null, "密文内容不能为空！");//默认的  提示框 title为“信息” type为  INFORMATION_MESSAGE
				return ;
			}
			
			sm4.secretKey = privateKeyStr;  //meF8U9wHFOMfs2Y9
			sm4.hexString = false;
			//String data=readFile(cipherContent);
			//String content  = sm4.decryptData_ECB(data);
			//plantTxt=content;
				
			byte[] data=readFile_BYTE(cipherContent);
			byte[] content  = sm4.decryptData_ECB_BYTE(data);
			plantTxt_BYTE=content;
			//this.panel_2_textArea_2.setText(content);
				
			if(content==null){
				JOptionPane.showMessageDialog(null, "密钥与密文不匹配！");
				return;
			}
			JOptionPane.showMessageDialog(null, "解密成功，请选择解密文件放置位置");
			JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                //loadFile(file.getPath());
                loadFile_BYTE(file.getPath());
                JOptionPane.showMessageDialog(null, "成功保存文件在目录:"+file.getPath());
            }
			
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * read the PFX
	 * @param e
	 */
	private void panel_3_button_1_click(ActionEvent e) {
		//String name="123";
		
		try {
			String content =   this.panel_3_textArea_1.getText();

			if(content==null ||content.trim().equals("")) {
				//JOptionPane.showMessageDialog(null, "明文内容不能为空！", "信息", JOptionPane.INFORMATION_MESSAGE);
				  JOptionPane.showMessageDialog(null, "ZIP文件不能为空，请选择文件！");//默认的  提示框 title为“信息” type为  INFORMATION_MESSAGE
				return ;
			}
			byte[] data=readFile_BYTE(content);
			String[] tmpName=content.split("\\\\");
			String[] name=tmpName[tmpName.length-1].split("\\.");
			String  result  = ZipUtil.analyZip(name[0], content);
			if(result==null){
				JOptionPane.showMessageDialog(null, "非zip压缩文件", "错误", JOptionPane.ERROR_MESSAGE);
			    return ;
			}else{
				JOptionPane.showMessageDialog(null, "分析ZIP文件成功！");
				//byte[] loadContent=readFile_BYTE(result);
				JFileChooser chooser = new JFileChooser();
	            if (chooser.showSaveDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION) {
	            	File file = chooser.getSelectedFile();
	                //loadFile(file.getPath());
	                 //loadFile_BYTE(file.getPath());
	            	copyDir(result,file.getPath());
	                JOptionPane.showMessageDialog(null, "成功保存文件在目录:"+file.getPath());
	                delFolder(result);
	            }
			}
			//this.panel_1_textArea_2.setText(cipherContent);
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	
	/**
	 * copy folder from sourcePath to newPath
	 * @param sourcePath
	 * @param newPath
	 * @throws IOException
	 */
	public static void copyDir(String sourcePath, String newPath) throws IOException {
        File file = new File(sourcePath);
        String[] filePath = file.list();
        
        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }
        
        for (int i = 0; i < filePath.length; i++) {
            if ((new File(sourcePath + file.separator + filePath[i])).isDirectory()) {
                copyDir(sourcePath  + file.separator  + filePath[i], newPath  + file.separator + filePath[i]);
            }
            
            if (new File(sourcePath  + file.separator + filePath[i]).isFile()) {
                copyFile(sourcePath + file.separator + filePath[i], newPath  + file.separator + filePath[i]);
            }
            
        }
    }
	
	/**
	 * copy file from oldPath to newPath
	 * @param oldPath
	 * @param newPath
	 * @throws IOException
	 */
	public static void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);;

        byte[] buffer=new byte[2097152];
        int readByte = 0;
        while((readByte = in.read(buffer)) != -1){
            out.write(buffer, 0, readByte);
        }
    
        in.close();
        out.close();
    }
	
	
	
	/**
	 * description:read film 
	 * @param path
	 * @return string
	 */
	private String readFile(String path){ 
		String encoding = "UTF-8"; 
		File file = new File(path);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        try {  
            return new String(filecontent, encoding);  
        } catch (UnsupportedEncodingException e) {  
            System.err.println("The OS does not support " + encoding);  
            e.printStackTrace();  
            return null;  
        }  
	}
	
	/**
	 * description:read film 
	 * @param path
	 * @return byte[]
	 */
	private byte[] readFile_BYTE(String path){ 
		File file = new File(path);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return filecontent;
	}
	
	
	/**
	 * description:load film to savePath(string)
	 * @param savePath(string)
	 * @return 
	 */
	private void loadFile(String savePath){
		FileOutputStream fos= null;
        try {
            fos=new FileOutputStream(savePath);
            fos.write(plantTxt.getBytes());
            fos.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	/**
	 * description:load film to savePath(byte[]) 
	 * @param savePath(byte[])
	 * @return 
	 */
	private void loadFile_BYTE(String savePath){
		FileOutputStream fos= null;
        try {
            fos=new FileOutputStream(savePath);
            fos.write(plantTxt_BYTE);
            fos.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	/**
	 * delete folder from folderPath
	 * @param folderPath
	 */
	public static void delFolder(String folderPath) {  
	     try {  
	        delAllFile(folderPath); //删除完里面所有内容  
	        String filePath = folderPath;  
	        filePath = filePath.toString();  
	        java.io.File myFilePath = new java.io.File(filePath);  
	        myFilePath.delete(); //删除空文件夹  
	     } catch (Exception e) {  
	       e.printStackTrace();   
	     }  
	}  
	/**
	 * delete all files of folder in path 
	 * @param path
	 * @return
	 */
	public static boolean delAllFile(String path) {  
	       
		  boolean flag = false;  
	       File file = new File(path);  
	       if (!file.exists()) {  
	         return flag;  
	       }  
	       if (!file.isDirectory()) {  
	         return flag;  
	       }  
	       String[] tempList = file.list();  
	       File temp = null;  
	       for (int i = 0; i < tempList.length; i++) {  
	          if (path.endsWith(File.separator)) {  
	             temp = new File(path + tempList[i]);  
	          } else {  
	              temp = new File(path + File.separator + tempList[i]);  
	          }  
	          if (temp.isFile()) {  
	             temp.delete();  
	          }  
	          if (temp.isDirectory()) {  
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件  
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹  
	             flag = true;  
	          }  
	       }  
	       return flag;  
	}  

	
	
	
	
	
	
	
	
}
