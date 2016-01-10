package me.wener.game.gtetris.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 指明该类型需要注入 {@link me.wener.game.gtetris.utils.Prop}
 */
@Target({TYPE})
@Retention(RUNTIME)
@Documented
public @interface WithProp {
}
