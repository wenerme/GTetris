package me.wener.game.gtetris.ui.components;

import javax.swing.SwingConstants;
import me.wener.game.gtetris.ui.UISetting;

public class GNotifyLabel extends GLabel
{
	private static final long serialVersionUID = 1L;
	GNotifyLabel()
	{
		UISetting.ApplySetting(this);

		setHorizontalAlignment(CENTER);

		// 设置圆角和边距
//		LineBorder lineBorder = new LineBorder(UISetting.Foreground, 1, true);
//		//lineBorder
//		setBorder(BorderFactory.createCompoundBorder(lineBorder,
//				BorderFactory.createEmptyBorder(4, 6, 4, 6)));

		setBorder(new BubbleBorder(UISetting.Foreground,1,6,10, SwingConstants.LEFT));

		setFont(UISetting.ChineseFont.deriveFont(12f));
		setForeground(UISetting.noticeColor);
	}
	public GNotifyLabel(String text)
	{
		this();
		setText(text);
	}
}
