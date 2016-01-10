package me.wener.game.gtetris.ui;

import me.wener.game.gtetris.ui.res.FontRes;

import javax.swing.*;
import java.awt.*;

/**
 * 关于UI的设置
 *
 * @author Wener
 */
public abstract class UISetting {
	// 色彩
	public static Color Background = new Color(0x1B1D1E);
	public static Color Foreground = new Color(0xDEDEDE);
	public static Color noticeColor = new Color(0xFF007F);

	// Font
	/**
	 * 小字体
	 */
	public static Font MiniFont;
	public static Font NormalFont;
	public static Font ChineseFont;
	public static Font LogoFont;

	public static Font LabelFont;
	public static Font ButtonFont;
	public static Font TextAreaFont;

	static {
		MiniFont = FontRes.Mini.getFont().deriveFont(Font.PLAIN, 20);

		LogoFont = FontRes.Logo.getFont().deriveFont(Font.PLAIN, 24);

		NormalFont = MiniFont;
		ChineseFont = UIManager.getDefaults().getFont("TabbedPane.font").deriveFont(Font.PLAIN, 12);
		TextAreaFont = ChineseFont;
		ButtonFont = LabelFont = NormalFont;
	}

	//
//	public static void ApplySetting(JPanel component)
//	{
//		ApplySetting((JComponent)component);
//	}
	//
	public static void ApplySetting(JComponent component) {

		component.setBackground(UISetting.Background);
		component.setForeground(UISetting.Foreground);
		component.setFont(NormalFont);
	}
}
