package me.wener.game.gtetris.framework;

import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import me.wener.game.gtetris.framework.event.Event;

@Slf4j
public class Events
{
    private EventBus bus;

    /**
     * 发布事件
     */
    public Events post(Event e)
    {
        log.trace("发布事件 {}", e);
        bus.post(e);
        return this;
    }

    /**
     * 注册监听对象
     */
    public Events register(Object object)
    {
        log.trace("注册监听器 {}", object);
        bus.register(object);
        return this;
    }

    /**
     * 注销监听对象
     */
    public Events unregister(Object object)
    {
        log.trace("注销监听器 {}", object);
        bus.unregister(object);
        return this;
    }
}
