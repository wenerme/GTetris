package me.wener.game.gtetris.logic;

public interface GameAreaEventListener
{
	
	void OnCubeChanged(GameArea area);
	void OnGameOver(GameArea area);
	void OnGotPoint(GameArea area, GamePlayer player,int point);
	void OnNextShapeChanged(GameArea area, CubeShape nextShape);
}
