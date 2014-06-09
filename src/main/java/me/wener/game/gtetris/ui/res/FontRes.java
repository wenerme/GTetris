package me.wener.game.gtetris.ui.res;

import java.awt.Font;

import javax.swing.plaf.FontUIResource;

import lombok.Getter;
import lombok.SneakyThrows;

/**
 * 枚举字体资源
 */
public enum FontRes
{
	Mini(Res.Font("mini.ttf")), 
	Logo(Res.Font("logo.ttf")),
	;
// {{
	@Getter Res res;
	FontRes(Res res)
	{
		this.res = res;
	}
	
	@SneakyThrows
	public Font getFont()
	{
		return FontUIResource.createFont(FontUIResource.TRUETYPE_FONT, res.getResourceAsStream());

	}
//}}
}