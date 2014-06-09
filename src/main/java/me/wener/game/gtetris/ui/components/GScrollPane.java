package me.wener.game.gtetris.ui.components;

import java.awt.Component;

import javax.swing.JScrollPane;
import me.wener.game.gtetris.ui.UISetting;

public class GScrollPane extends JScrollPane
{
	private static final long serialVersionUID = 1L;
	public GScrollPane()
	{
		UISetting.ApplySetting(this);
	}
	public GScrollPane(Component comp)
	{
		super(comp);
		UISetting.ApplySetting(this);
		UISetting.ApplySetting(verticalScrollBar);
		//getVerticalScrollBar().setValue(0);
	}
}
