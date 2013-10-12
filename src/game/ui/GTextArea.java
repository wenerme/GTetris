package game.ui;

import javax.swing.JTextArea;

public class GTextArea extends JTextArea
{
	private static final long serialVersionUID = 1L;

	public GTextArea()
	{
		UISetting.ApplySetting(this);
		setFont(UISetting.TextAreaFont.deriveFont(20.0f));
		setLineWrap(true);
		
	}
}
