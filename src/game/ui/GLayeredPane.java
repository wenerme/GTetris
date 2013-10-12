package game.ui;

import javax.swing.JLayeredPane;

public class GLayeredPane extends JLayeredPane
{
	private static final long serialVersionUID = 1L;

	public GLayeredPane()
	{
		UISetting.ApplySetting(this);
	}
}
