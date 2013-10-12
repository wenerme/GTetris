package game.res;

import lombok.Getter;
import game.U5;

/**
 * 枚举文本资源
 */
public enum TextRes
{
	About(Res.Text("about.txt")),
	;
	// {{
	@Getter Res res;
	TextRes(Res res)
	{
		this.res = res;
	}
	public String getText()
	{
		return U5.FileGetContents(res.getResourceFile());
	}
	//}}
}