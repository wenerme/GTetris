package game.ui;

import game.U5;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain=true)
public class GDialogPanel extends JPanel
	implements ActionListener
{
	public static EnumMap<DialogButton, String> DialogButtonText;
	public enum DialogButton
	{
		Yes,No,Cancel,OK,Retry
	}

	
	static
	{
		DialogButtonText = new EnumMap<>(DialogButton.class);
		DialogButtonText.put(DialogButton.Yes, "Yes");
		DialogButtonText.put(DialogButton.No, "No"); 
		DialogButtonText.put(DialogButton.Cancel, "Cancel");
		DialogButtonText.put(DialogButton.OK, "OK");
		DialogButtonText.put(DialogButton.Retry, "Retry");
	}
	
	GLabel titlelLabel;
	GTextArea textArea;
	GPanel buttonPanel;
	DialogButton[] buttonOption;
	@Setter @Getter DialogResultListener resultListener;
	public GDialogPanel()
	{
		UISetting.ApplySetting(this);
		
		setLayout(new BorderLayout());
		
		// 标题
		{
			titlelLabel = new GLabel();
			titlelLabel.setFont(UISetting.LabelFont.deriveFont(Font.PLAIN, 30));
			titlelLabel.setVerticalAlignment(GLabel.CENTER);
			titlelLabel.setBorder(new EmptyBorder(new Insets(10, 10, 5, 5)));
			add(titlelLabel, BorderLayout.NORTH);
		}
		
		// 文字
		{
			textArea = new GTextArea();
			textArea.setFont(UISetting.ChineseFont);
			textArea.setEditable(false);
			textArea.setMargin(new Insets(15, 10, 15, 10));
			//textArea.setContentType("text/html");
			
			
			// 滚动条
			GScrollPane scrollPane = new GScrollPane(textArea);
			scrollPane.setBorder(BorderFactory.createMatteBorder(2,0,2,0,getForeground()));
			add(scrollPane, BorderLayout.CENTER);
		}
		
		
		// 按钮
		{
			buttonPanel = new GPanel();
			add(buttonPanel, BorderLayout.SOUTH);
		}
		
		
	}
	
	protected void initButton()
	{
		if(buttonOption == null)
			return;
		
		buttonPanel.removeAll();
		buttonPanel.setLayout(new GridLayout(0, buttonOption.length, 0, 0));
		
		GButton button;
		for(int i = 0; i < buttonOption.length; i ++)
		{
			button = new GButton();
			String text = DialogButtonText.get(buttonOption[i]);
			
			button.setText(text);
			button.setActionCommand(text);
			button.addActionListener(this);
			buttonPanel.add(button);
		}
		buttonPanel.setSize(buttonPanel.getPreferredSize());
	}
	
	public GDialogPanel setButtons(DialogButton ... buttons )
	{
		buttonOption = buttons;
		initButton();
		return this;
	}
	
	public DialogButton[] getButtons()
	{
		return buttonOption;
	}
	
	public GDialogPanel setTitle(String text)
	{
		titlelLabel.setText(text);
		return this;
	}

	public GDialogPanel setContent(String text)
	{
		textArea.setText(text);
		return this;
	}
	public String getTitle()
	{
		return titlelLabel.getText();
	}

	public String setContent()
	{
		return textArea.getText();
	}

	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(resultListener != null)
		{
			DialogButton button;
			button = U5.getKeyByValue(DialogButtonText, e.getActionCommand());
			
			resultListener.OnDialogButtonClick(this, button);
		}
	}
	
}
