package game.ui;
import javax.swing.JButton;



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
