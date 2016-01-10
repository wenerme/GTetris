package me.wener.game.gtetris.framework.event;

import lombok.Getter;
import me.wener.game.gtetris.framework.IScene;

public class EnterSceneEvent {
	@Getter
	private IScene scene;
}
