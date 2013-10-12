package game.logic;

import game.Game;
import game.GameSetting;
import game.ui.GComponent;

import java.awt.Color;
import java.awt.Graphics;

import lombok.Getter;
import lombok.Setter;

public class CubeGraphics extends GComponent
{
	@Getter @Setter CubeInfo[][] cubeMatrix;
	
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
					
					g.fillRect(x, y, GameSetting.cubeSize, GameSetting.cubeSize);
				}
			}

    }
}
