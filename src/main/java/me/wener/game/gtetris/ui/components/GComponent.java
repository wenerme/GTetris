package me.wener.game.gtetris.ui.components;

import me.wener.game.gtetris.ui.UISetting;

import javax.swing.*;

@SuppressWarnings("serial")
public class GComponent
		extends JComponent {
	public GComponent() {
		super();
		UISetting.ApplySetting(this);
	}
}
