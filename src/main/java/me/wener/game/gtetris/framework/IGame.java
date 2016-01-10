package me.wener.game.gtetris.framework;

import com.google.inject.Injector;

public interface IGame {
	void initialize();

	void start();

	void quit();

	void pause();

	void resume();

	Events getEventBus();

	Injector getInjector();
}
