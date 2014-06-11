package me.wener.game.gtetris.framework.event;

public abstract class AbstractEvent implements Event
{
    protected <T extends Event> T getInstance(Class<T> clazz)
    {
        try
        {
            return clazz.newInstance();
        } catch (Exception e)
        {
            throw new RuntimeException("实例化事件类失败",e);
        }
    }
}
