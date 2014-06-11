package me.wener.game.gtetris.framework;

import java.util.Collection;
import java.util.List;

public interface ISceneMgr
{
    /**
     * 显示指定的场景
     */
    void transferTo(IScene scene);

    /**
     * 添加一个场景
     * @throws java.lang.IllegalArgumentException 如果场景的名字已经存在,则会抛出异常
     */
    void add(IScene scene) throws IllegalArgumentException;

    /**
     * 通过场景的类来查找场景实例, 可能有多个
     * @return 如果未找到, 会返回空集合
     */
    Collection<IScene> find(Class<IScene> sceneClass);

    /**
     * 通过场景的名字来查找场景
     * @return 如果未找到会返回 null
     */
    IScene find(String name);
}
