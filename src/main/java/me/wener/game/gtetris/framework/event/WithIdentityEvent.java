package me.wener.game.gtetris.framework.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public abstract class WithIdentityEvent<ID> implements Event
{
    @Setter(AccessLevel.PROTECTED)
    @Getter
    ID id;

}
