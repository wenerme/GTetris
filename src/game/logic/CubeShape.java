package game.logic;
import game.U5;

import java.awt.Color;
import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * @author Wener
 *
 */
@Accessors(chain=true)
@Data
public class CubeShape
	implements Serializable
{

	private static final long serialVersionUID = 1L;
	/**
	 * 恒定宽
	 */
	static final int WIDTH = 4;
	/**
	 * 恒定高
	 */
	static final int HEIGHT = 4;
	
	// field
	Color color;
	@Setter(value=AccessLevel.NONE) int [][][] shapeArray;
	int rotate = 0;
	int x = 0;
	int y = 0;
	/*
	 * 缓存的CubeInfo
	 */
	@Setter(value=AccessLevel.NONE)
	@Getter(value=AccessLevel.NONE)
	private CubeInfo[][] cubeInfo;
	// ctor
	protected CubeShape(int [][][] shapeArray)
	{
		this.shapeArray = shapeArray;
		cubeInfo = new CubeInfo[HEIGHT][WIDTH];
		// 初始化 设置好颜色,全不可见
		for (int y = 0; y < HEIGHT; y++)
			for (int x = 0; x < WIDTH; x++)
				cubeInfo[y][x] = new CubeInfo(color, false);
	}

	public int[][] getCurrentShapeArray()
	{
		return shapeArray[rotate];
	}
	public CubeInfo[][] getCurrentInfoeArray()
	{
		int[][] shape = getCurrentShapeArray();
		for (int y = 0; y < shape.length; y++)
			for (int x = 0; x < shape[y].length; x++)
				cubeInfo[y][x].setVisible(shape[y][x] == 1).setColor(color);
		
		return cubeInfo;
	}
	public int getWidth()
	{
		return WIDTH;
	}
	public int getHeight()
	{
		return HEIGHT;
	}
	public CubeShape setRotate(int r)
	{
		if (r < 0)
			return setRotate(shapeArray.length + r);
		
		rotate = r % shapeArray.length;
		return this;
	}
	
	public CubeShape randomRotate()
	{
		setRotate(U5.randomInt() % shapeArray.length);
		return this;
	}
	
	
	public CubeShape leftRotation()
	{
		setRotate(rotate - 1);
		return this;
	}
	public CubeShape rightRotation()
	{
		setRotate(rotate + 1);
		return this;
	}
	public CubeShape leftMove()
	{
		x -= 1;
		return this;
	}
	public CubeShape rightMove()
	{
		x += 1;
		return this;
	}
	
	public CubeShape fallDown(int n)
	{
		y += n;
		return this;
	}
}

