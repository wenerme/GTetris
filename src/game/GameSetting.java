package game;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;


/**
 * 游戏设置
 *  
 * @author Wener
 *
 */
public final class GameSetting 
{
	//
	public final static String Version = "1.0";
	public static final String title = "俄罗斯方块";
	public static final String logo= "Tetris";
	public static final List<String> authorList;
	public static final int cubeSize = 32;
	public static final int cubePreLine = 12;
	public static final int cubePreColumn = 18;
	public static final int windowWidth;
	public static final int windowHeight;
	public static final List<Color> cubeColorList;

	// constructor
	static
	{
		authorList = new ArrayList<String>();
		authorList.add("wener<wenermail@gmail.com>");
		windowWidth = cubeSize * cubePreLine;
		windowHeight = cubeSize * cubePreColumn;
		
		// 方块颜色列表
		cubeColorList = new ArrayList<Color>();
		cubeColorList.add(new Color(0xE6DB74));
		cubeColorList.add(new Color(0xA6E22E));
		cubeColorList.add(new Color(0xF92672));
		cubeColorList.add(new Color(0xAE81FF));
		cubeColorList.add(new Color(0x79ABFF));
		cubeColorList.add(new Color(0xFF0000));
		cubeColorList.add(new Color(0xFFFFFF));
	}
	private GameSetting()
	{
	}
	public static Dimension getWindowSize()
	{
		return new Dimension(windowWidth,windowHeight);
	}
	public static Color getRandomColor()
	{
		return cubeColorList.get((int)Math.random() % cubeColorList.size());
	}
}
