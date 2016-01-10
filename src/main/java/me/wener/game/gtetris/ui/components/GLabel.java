package me.wener.game.gtetris.ui.components;

import me.wener.game.gtetris.ui.UISetting;

import javax.swing.*;


public class GLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	public GLabel() {
		UISetting.ApplySetting(this);
		setFont(UISetting.LabelFont);
	}

}
