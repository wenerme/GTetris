package game.ui;

import game.GameSetting;

import javax.swing.JPanel;

public class GPanel extends JPanel
{
	public GPanel()
	{
		super();
		
		UISetting.ApplySetting(this);
		setLayout(null);
	}
}
