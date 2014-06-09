package me.wener.game.gtetris.ui.components;

import javax.swing.JPanel;
import me.wener.game.gtetris.ui.UISetting;

public class GPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public GPanel()
	{
		super();
		
		UISetting.ApplySetting(this);
		setLayout(null);
	}
}
