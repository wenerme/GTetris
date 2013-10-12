package game.ui;
import game.GameSetting;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.util.jar.Attributes.Name;

import javax.swing.JPanel;

/**
 * 游戏场景
 * @author Wener
 *
 */
public abstract class GScene
	extends GLayeredPane
{
	protected SceneManager sceneManager;

	public GScene()
	{
		super();
		setPreferredSize(GameSetting.getWindowSize());
		setBounds(0, 0, GameSetting.windowWidth, GameSetting.windowHeight);
		setOpaque(true);

	}

	/**
	 * 进入舞台事件
	 */
	protected void enterStage(SceneManager sceneManager)
	{
		System.out.println("Enter Scene "+getName());
		setFocusable(true);
		requestFocusInWindow();
	}

	/**
	 * 离开舞台事件
	 */
	protected void leaveStage(SceneManager sceneManager)
	{}
}
