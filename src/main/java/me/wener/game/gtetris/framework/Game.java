package me.wener.game.gtetris.framework;

import lombok.Getter;
import me.wener.game.gtetris.ui.SceneManager;


public class Game {

	@Getter
	private SceneManager sceneManager;


	public Game() {
		sceneManager = new SceneManager();
	}

	public static void Quit() {
		System.exit(0);
	}

	public static void Start() {
	}

}
