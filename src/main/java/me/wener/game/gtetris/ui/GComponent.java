package me.wener.game.gtetris.ui;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class GComponent
	extends JComponent
{
	public GComponent()
	{
		super();
		UISetting.ApplySetting(this);
	}
}
