package me.wener.game.gtetris.logic;
import java.awt.Color;
import java.io.Serializable;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Accessors(chain=true)
@Data
public class CubeInfo
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	// constant
	public static Color DefaultColor = Color.BLUE;
	// field
	@NonNull Color color = DefaultColor;
	boolean visible = true;
	
	public CubeInfo()
	{}
	public CubeInfo(CubeInfo info)
	{
		this(info.color, info.visible);
	}
	public CubeInfo(Color color)
	{
		this(color, true);
	}
	public CubeInfo(Color color,boolean visible)
	{
		this.color = color;
		this.visible = visible;
	}
	
	public CubeInfo setTo(CubeInfo info)
	{
		return setTo(info.color, info.visible);
	}
	public CubeInfo setTo(Color _color,boolean visible)
	{
		this.color = _color;
		this.visible = visible;
		return this;
	}
}
