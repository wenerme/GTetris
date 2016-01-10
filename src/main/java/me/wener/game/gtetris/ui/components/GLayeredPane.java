package me.wener.game.gtetris.ui.components;

import me.wener.game.gtetris.ui.UISetting;

import javax.swing.*;

public class GLayeredPane extends JLayeredPane {
	private static final long serialVersionUID = 1L;

	public GLayeredPane() {
		UISetting.ApplySetting(this);
	}
}
