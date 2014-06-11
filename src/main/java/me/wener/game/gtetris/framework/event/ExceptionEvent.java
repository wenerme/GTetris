package me.wener.game.gtetris.framework.event;

import com.google.common.eventbus.SubscriberExceptionContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ExceptionEvent extends AbstractEvent
{
    private Throwable exception;
    private SubscriberExceptionContext context;
    private Object extra;

    public ExceptionEvent(Throwable exception)
    {
        this.exception = exception;
    }

    public ExceptionEvent(Throwable exception, Object extra)
    {
        this.exception = exception;
        this.extra = extra;
    }

    public ExceptionEvent(Throwable exception, SubscriberExceptionContext context)
    {
        this.exception = exception;
        this.context = context;
    }

    public ExceptionEvent(Throwable exception, SubscriberExceptionContext context, Object extra)
    {
        this.exception = exception;
        this.context = context;
        this.extra = extra;
    }
}
