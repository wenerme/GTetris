package game;
import java.io.InputStream;
import java.net.URL;

import game.ui.SceneManager;
import lombok.Getter;


public class Game 
{

	// field
	@Getter SceneManager sceneManager;
	@Getter GameSetting gameSetting;
	static ClassLoader classLoader;
	static{
		classLoader = Thread.currentThread()
				.getContextClassLoader();
	}
	
	public Game()
	{
		sceneManager = new SceneManager();
	}
	
	public static void QuitGame()
	{
		System.exit(0);
	}
	
	public static URL getResource(String path)
	{
		return classLoader.getResource(path);
	}
	public static String getResourceFile(String path)
	{
		return classLoader.getResource(path).getFile();
	}
	
}
