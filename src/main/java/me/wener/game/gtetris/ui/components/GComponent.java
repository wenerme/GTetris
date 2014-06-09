package me.wener.game.gtetris.ui.components;

import javax.swing.JComponent;
import me.wener.game.gtetris.ui.UISetting;

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
