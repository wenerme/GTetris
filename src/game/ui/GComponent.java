package game.ui;

import game.GameSetting;

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
