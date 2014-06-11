package me.wener.game.gtetris.framework.event;

import lombok.Getter;

public abstract class PlayerActionEvent<T>
{
    @Getter
    private T action;
}
