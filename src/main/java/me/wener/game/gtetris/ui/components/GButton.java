package me.wener.game.gtetris.ui.components;
import javax.swing.JButton;
import me.wener.game.gtetris.ui.UISetting;


public class GButton extends JButton
{
	private static final long serialVersionUID = 1L;

	public GButton()
	{
		UISetting.ApplySetting(this);
		setFont(UISetting.ButtonFont);
		setFocusPainted(false);
	}
}
