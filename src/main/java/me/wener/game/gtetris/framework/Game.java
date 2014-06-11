package me.wener.game.gtetris.framework;
import java.net.URL;

import me.wener.game.gtetris.ui.SceneManager;
import lombok.Getter;


public class Game
{

	@Getter private SceneManager sceneManager;


    public Game()
	{
		sceneManager = new SceneManager();
	}

	public static void Quit()
	{
		System.exit(0);
	}

    public static void Start(){}

}
