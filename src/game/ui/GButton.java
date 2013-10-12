package game.ui;
import javax.swing.JButton;



public class GButton extends JButton 
{
	public GButton()
	{
		UISetting.ApplySetting(this);
		setFont(UISetting.ButtonFont);
		setFocusPainted(false);
	}
}
