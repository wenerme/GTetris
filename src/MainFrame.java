import game.Game;
import game.U5;
import game.ui.DialogResultListener;
import game.ui.GDialogPanel;
import game.ui.GLayeredPane;
import game.ui.MessageBox;
import game.ui.GDialogPanel.DialogButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.JButton;


public class MainFrame extends JFrame
{

	private JLayeredPane contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JLayeredPane();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setPreferredSize(new Dimension(500, 300));
		pack();
		
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(54, 149, 54, 15);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(92, 193, 66, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLayeredPane layeredPane = new JLayeredPane();
		
		contentPane.setLayer(layeredPane, 20);
		layeredPane.setBackground(Color.GRAY);
		layeredPane.setBounds(293, 216, 92, -100);
		layeredPane.setPreferredSize(new Dimension(200,200));
		contentPane.add(layeredPane);
		
		JPanel panel = new JPanel();
		layeredPane.setLayer(panel, 10);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(0, 0, 54, 15);
		layeredPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(0, 0, 93, 23);
		layeredPane.add(btnNewButton);
		panel.setBackground(Color.BLACK);
		panel.setBounds(10, 2, 38, 52);
		layeredPane.add(panel,1,0);
		
		GDialogPanel dialogPanel = new GDialogPanel();
		dialogPanel
				.setButtons(DialogButton.OK)
				.setButtons(DialogButton.OK, DialogButton.No)
				.setTitle("Wener Greate")
				.setContent("bababababwnwnn我嫩恩萨斯没那么 三萨满所那么\r\n萨斯那么你所啊所那么松那 萨满萨满司马 三藐三那么你所a\r\n三毛所那么松那没 三毛桑拿那么每年萨满你所嘛撒撒\r\n三四嘛三名萨满撒")
				;
		dialogPanel.setSize(100,200);
		dialogPanel.setLocation(10, 10);
		//layeredPane.add(dialogPanel, 400);
		
		layeredPane.setSize(200, 200);
		layeredPane.setVisible(false);
		System.out.println(layeredPane.getSize());
		System.out.println(layeredPane.getLocation());
		
		{
			GLayeredPane layer = MessageBox.getDialogLayeredPane();
			layer.setSize(contentPane.getSize());
			layer.setLocation(0, 0);
			contentPane.setLayer(layer, 100);
			contentPane.add(layer);
			
			layer.add(btnNewButton);
			layer.add(panel,1,0);
			
			MessageBox.Show("Wener", "So this is content",new DialogResultListener()
			{
				
				public void OnDialogButtonClick(GDialogPanel dialogPanel,
						DialogButton clicked)
				{
					// TODO Auto-generated method stub
					
				}
			});
			
			System.out.println(layer.getSize());
			System.out.println(layer.getLocation());
		}
		
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		System.out.println(U5.FileGetContents(classLoader.getResource("game/res/about.txt").getFile()));
		System.out.println(classLoader.getResource("game/res/mini.ttf"));
		
		Object data = new MyData();
		byte[] bytes = U5.SerializeObject(data);
		U5.FilePutContents("save.dat", bytes);
		System.out.println(new String(bytes));
		U5.FilePutContents("wen.dat", "Wener is good!");
	}
}

class MyData 
	implements Serializable
{
	String name;
	int age;
	int[][] matrix;
	
	MyData()
	{
		matrix = new int[5][5];
		matrix[3][3] = 1;
		name = "wener";
		age = 21;
	}
}
class Employee implements java.io.Serializable
{
   public String name;
   public String address;
   public transient int SSN;
   public int number;
   public void mailCheck()
   {
      System.out.println("Mailing a check to " + name
                           + " " + address);
   }
}
