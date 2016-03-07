package com.github.yoojia.events.internal;

/**
 * @author Yoojia Chen (yoojiachen@gmail.com)
 * @since 1.2
 */
public final class EventRunner implements Runnable{

    private final Object mEvent;
    private final EventHandler mHandler;

    public EventRunner(Object event, EventHandler handler) {
        this.mEvent = event;
        this.mHandler = handler;
    }

    @Override
    public void run() {
        try{
            mHandler.onEvent(mEvent);
        }catch (Exception errors) {
            try{
                mHandler.onErrors(errors);
            }catch (Throwable throwable) {
                // 在处理错误时还抛出异常,那就只打印异常栈信息了
                throwable.printStackTrace();
            }
        }
    }

}
