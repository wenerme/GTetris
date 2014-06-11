package me.wener.game.gtetris.framework.event;

import lombok.Getter;
import lombok.ToString;

@ToString
public class StartEvent extends AbstractEvent
{
    @Getter
    private Event event;

    public StartEvent(Event event)
    {
        this.event = event;
    }
}
