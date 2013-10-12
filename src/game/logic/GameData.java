package game.logic;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 游戏数据类,用于保存和加载
 */
@Accessors(chain=true)
public @Data class GameData
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	int point;
	int time;
	GameArea gameArea;
}
