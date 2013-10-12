package game.ui;
import javax.swing.JLabel;


public class GLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;

	public GLabel() 
	{
		UISetting.ApplySetting(this);
		setFont(UISetting.LabelFont);
	}

}
