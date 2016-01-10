package me.wener.game.gtetris.ui.components;

import lombok.Setter;
import me.wener.game.gtetris.framework.GameSetting;
import me.wener.game.gtetris.ui.SceneManager;

/**
 * 游戏场景
 *
 * @author Wener
 */
public abstract class GScene
		extends GLayeredPane {

	private static final long serialVersionUID = 1L;
	@Setter
	protected SceneManager sceneManager;

	public GScene() {
		super();
		setPreferredSize(GameSetting.getWindowSize());
		setBounds(0, 0, GameSetting.windowWidth, GameSetting.windowHeight);
		setOpaque(true);

	}

	/**
	 * 进入舞台事件
	 */
	public void enterStage(SceneManager sceneManager) {
		System.out.println("Enter Scene " + getName());
		setFocusable(true);
		requestFocusInWindow();
	}

	/**
	 * 离开舞台事件
	 */
	public void leaveStage(SceneManager sceneManager) {
	}
}
