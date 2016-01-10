package me.wener.game.gtetris.logic;


public enum GamePlayerAction {
	RightRotation(1),
	LeftRotation(5),
	FallDown(2),
	LeftMove(3),
	RightMove(4),;
	int val;

	GamePlayerAction(int i) {
		val = i;
	}


}
