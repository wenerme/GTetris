package game.logic;

import game.GameSetting;
import game.ui.GComponent;
import game.ui.UISetting;

import java.awt.Graphics;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public @Data
class CubeGraphics extends GComponent
{
	CubeInfo[][] cubeMatrix;
	CubeInfo[][] nextCubeMartix;
	static int nextCubeSize = GameSetting.cubeSize / 4;
	static int nextCubeX = (GameSetting.windowWidth - nextCubeSize*4)/2;
	static int nextCubeY = 2;
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponents(g);

		g.setColor(getBackground());
		g.fillRect(10, 10, getWidth(), getHeight());

		if (cubeMatrix != null)
		for (int i = 0; i < cubeMatrix.length; i++)
			for (int j = 0; j < cubeMatrix[i].length; j++)
			{
				CubeInfo info = cubeMatrix[i][j];

				if (info != null && info.isVisible())
				{
					g.setColor(info.getColor());

					int x = j * GameSetting.cubeSize;
					int y = i * GameSetting.cubeSize;

					g.fillRect(x, y, GameSetting.cubeSize,
							GameSetting.cubeSize);
				}
			}
		
		paintNextShape(g);
	}

	public void paintNextShape(Graphics g)
	{
		int nextCubeSize = GameSetting.cubeSize / 4;
		
		if (nextCubeMartix != null)
		for (int i = 0; i < nextCubeMartix.length; i++)
			for (int j = 0; j < nextCubeMartix[i].length; j++)
			{
				CubeInfo info = nextCubeMartix[i][j];

				if (info != null && info.isVisible())
				{
					g.setColor(info.getColor());

					int x = nextCubeX + j * nextCubeSize;
					int y = nextCubeY + i * nextCubeSize;

					g.fillRect(x, y, nextCubeSize,
							nextCubeSize);
				}
			}
		
		// 绘制边框
//		g.setColor(UISetting.noticeColor);
//		g.drawRect(nextCubeX, nextCubeY, nextCubeSize*4, nextCubeSize*4);
		
	}
}
