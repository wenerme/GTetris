package me.wener.game.gtetris.ui.components;

import javax.swing.JLayeredPane;
import me.wener.game.gtetris.ui.UISetting;

public class GLayeredPane extends JLayeredPane
{
	private static final long serialVersionUID = 1L;

	public GLayeredPane()
	{
		UISetting.ApplySetting(this);
	}
}
