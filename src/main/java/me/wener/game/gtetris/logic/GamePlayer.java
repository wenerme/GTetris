package me.wener.game.gtetris.logic;

import me.wener.game.gtetris.logic.GameArea.CollisionType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain=true)
@Data
public class GamePlayer
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	int point;
	String name;
	@Setter(value=AccessLevel.PACKAGE) GameArea  gameArea;

	CubeShape cubeShape;
	Map<Integer, GamePlayerAction> actionMap;
	/**
	 * 检测是否做了任何动作
	 */
	boolean doneAction = false;
	boolean stop = false;
	public GamePlayer()
	{
		actionMap = new HashMap<Integer, GamePlayerAction>();
	}
	
	public GamePlayer increasePoint(int n)
	{
		point += n;
		return this;
	}
	
	public GamePlayerAction getActionByKeyCode(int keyCode)
	{
		GamePlayerAction action;
		action = actionMap.get(keyCode);
		return action;
	}
	
	/**
	 * 自动下落,方块下一格. 如果下落有碰撞会撤销
	 */
	public CollisionType autoFallDown()
	{
		cubeShape.fallDown(1);
		CollisionType type;
		type = gameArea.testCubeShape(this);
		if (false == type.isNone())
		{
			cubeShape.fallDown(-1);
		}
		return type;
	}
	
	public boolean doAction(GamePlayerAction action)
	{
		boolean result = false;
		
		
		doneAction = true;
		switch (action)
		{
			case RightRotation:
				cubeShape.rightRotation();
				if (CollisionType.None != gameArea.testCubeShape(this))
					cubeShape.leftRotation();
				else
					result = true;
				break;
				
			case FallDown:
				while(autoFallDown().isNone())
					;
				break;
				
			case LeftMove:
				cubeShape.leftMove();
				if (CollisionType.None != gameArea.testCubeShape(this))
					cubeShape.rightMove();
				else
					result = true;
				break;
				
			case RightMove:
				cubeShape.rightMove();
				if (CollisionType.None != gameArea.testCubeShape(this))
					cubeShape.leftMove();
				else
					result = true;
			break;

			default:
				doneAction = false;
			break;
		}

		
		return result;
	}
}
