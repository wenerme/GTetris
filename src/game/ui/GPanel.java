package game.ui;

import javax.swing.JPanel;

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
