package game.ui;

import game.ui.GDialogPanel.DialogButton;

public interface DialogResultListener
{
	void OnDialogButtonClick(GDialogPanel dialogPanel, DialogButton clicked);
}
