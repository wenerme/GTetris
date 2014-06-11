package me.wener.game.gtetris.ui;

import me.wener.game.gtetris.framework.GameSetting;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import me.wener.game.gtetris.ui.components.GLayeredPane;
import me.wener.game.gtetris.ui.components.GScene;

/**
 * 场景管理器
 * @author Wener
 */
@Accessors(chain=true)
public class SceneManager
{

	@Getter
    GLayeredPane stage;
	@Getter
    GScene currentScene;
	String lastSceneName;
	@Getter Map<String, GScene> sceneMap;
	/**
	 * 由场景设置的信息
	 */
	@Getter @Setter String sceneInfo = "";
	
	public SceneManager()
	{
		stage = new GLayeredPane();
		stage.setBounds(0, 0, GameSetting.windowWidth, GameSetting.windowHeight);
		// add message dialog layer
		stage.setLayer(MessageBox.getDialogLayeredPane(), GLayeredPane.POPUP_LAYER);
		MessageBox.getDialogLayeredPane().setLocation(0, 0);
		MessageBox.getDialogLayeredPane().setSize(stage.getSize());
		stage.add(MessageBox.getDialogLayeredPane());
		
//        stage.addKeyListener(this);
//        stage.setFocusable(true);
//		stage.requestFocusInWindow();
		
        //
		sceneMap = new HashMap<String, GScene>();
	}
	
	/**
	 * 显示场景
	 * @param name
	 */
	public void showScene(@NonNull String name)
	{
		if(currentScene != null)
		{
			currentScene.leaveStage(this);
			stage.remove(currentScene);
			currentScene.setVisible(false);
			//
			lastSceneName = currentScene.getName();
		}
		
		GScene scene = getScene(name);
		stage.add(scene);
		scene.setVisible(true);
		scene.enterStage(this);
		
		currentScene = scene;
	}
	
	/**
	 * 显示上一个场景
	 */
	public void showLastScene()
	{
		if(lastSceneName != null)
			showScene(lastSceneName);
		else {
			System.err.println("No last scene");
		}
	}
	
	/**
	 * 替换已存在的场景
	 * @param scene
	 */
	@SneakyThrows
	public void replaceScene(@NonNull GScene scene)
	{
		if (sceneMap.containsKey(scene.getName()))
		{
			sceneMap.put(scene.getName(), scene);
		}else {
			throw new Exception("scene '"+scene.getName()+"' not exists");
		}
	}
	
	/**
	 * 放入场景
	 * @param scene
	 */
	@SneakyThrows
	public void putScene(@NonNull GScene scene)
	{
		if (sceneMap.containsKey(scene.getName()) == false)
		{
			sceneMap.put(scene.getName(), scene);
			scene.setVisible(false);
			scene.setSceneManager(this);
		}else {
			throw new Exception("scene '"+scene.getName()+"' already exists");
		}
	}

	/**
	 * 获取命名的场景
	 * @param name
	 * @return
	 */
	@SneakyThrows
	public GScene getScene(String name)
	{
		if(false == haveScene(name))
			throw new Exception("Scene "+name+" not found.");
		
		return sceneMap.get(name);
	}

	public boolean haveScene(String name)
	{
		return sceneMap.containsKey(name);
	}

}
