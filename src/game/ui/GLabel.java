package game.ui;
import javax.swing.JLabel;


public class GLabel extends JLabel {
	
	
	public GLabel() 
	{
		UISetting.ApplySetting(this);
		setFont(UISetting.LabelFont);
	}

}
