package game.ui;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JTextArea;

public class GTextArea extends JTextArea
{
	public GTextArea()
	{
		UISetting.ApplySetting(this);
		setFont(UISetting.TextAreaFont.deriveFont(20.0f));
		setLineWrap(true);
		
	}
}
