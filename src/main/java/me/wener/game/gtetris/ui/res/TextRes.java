package me.wener.game.gtetris.ui.res;

import lombok.Getter;
import me.wener.game.gtetris.utils.U5;

/**
 * 枚举文本资源
 */
public enum TextRes
{
	About(Res.Text("about.txt")),
    Tips(Res.Text("tips.txt"))
	;
	// {{
	@Getter Res res;
	TextRes(Res res)
	{
		this.res = res;
	}
	public String getText()
	{
		return U5.FileGetContents(res.getResourceAsStream());
	}
	//}}
}