package me.wener.game.gtetris.ui.components;

import javax.swing.JTextArea;
import me.wener.game.gtetris.ui.UISetting;

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
