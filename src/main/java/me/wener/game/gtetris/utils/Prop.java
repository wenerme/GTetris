package me.wener.game.gtetris.utils;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.google.inject.BindingAnnotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ FIELD })
@Retention(RUNTIME)
@Documented
public @interface Prop
{
    String value();

    /**
     * 如果注入的值为 null, 则会抛出异常
     */
    boolean optional() default false;

    /**
     * 指明集合类型包含元素的类型  List&lt;type&gt;, Map&lt;string,type&gt;
     */
//    Class<?> type() default String.class;
}
