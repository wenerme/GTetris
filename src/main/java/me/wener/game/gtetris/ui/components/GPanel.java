package me.wener.game.gtetris.ui.components;

import me.wener.game.gtetris.ui.UISetting;

import javax.swing.*;

public class GPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public GPanel() {
		super();

		UISetting.ApplySetting(this);
		setLayout(null);
	}
}
