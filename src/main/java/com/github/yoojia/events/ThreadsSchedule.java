package com.github.yoojia.events;

import com.github.yoojia.events.internal.*;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author Yoojia Chen (yoojiachen@gmail.com)
 * @since 2.0
 */
public class ThreadsSchedule implements Scheduler {

    private final Queue<Element> mLoopTasks = new ConcurrentLinkedQueue<>();

    private final ExecutorService mWorkerThreads;
    private final ExecutorService mLoopThread;

    private final ScheduleLooper mLooper = new ScheduleLooper() {
        @Override
        protected void step() {
            final Element el = mLoopTasks.poll();
            if (el == null) {
                await();
            }else{
                invoke(el.scheduleType, el.event, el.handler);
            }
        }
    };

    public ThreadsSchedule(ExecutorService workerThreads, ExecutorService loopThread) {
        mWorkerThreads = workerThreads;
        mLoopThread = loopThread;
        mLoopThread.submit(mLooper);
    }

    @Override
    public void submit(Object event, List<? extends Handler> handlers) {
        // 如果是 CALLER 方式, 直接在此处执行.
        // 其它方式在线程池处理再做处理
        for (Handler item : handlers) {
            final EventHandler handler = (EventHandler) item;
            final int type = handler.scheduleType();
            if (ScheduleType.ON_CALLER_THREAD == type) {
                new EventRunner(event, handler).run();
            }else{
                mLoopTasks.offer(new Element(event, handler, type));
                synchronized (mLooper) {
                    mLooper.notify();
                }
            }
        }
    }

    public final ExecutorService getWorkerThreads(){
        return mWorkerThreads;
    }

    public final ExecutorService getLoopThread() {
        return mLoopThread;
    }

    protected void invoke(int type, Object event, Handler handler) {
        switch (type) {
            case ScheduleType.ON_THREADS:
                mWorkerThreads.submit(new EventRunner(event, handler));
                break;
            case ScheduleType.ON_MAIN_THREAD:
                handler.onErrors(new IllegalArgumentException("Unsupported <ON_MAIN_THREAD> schedule type! " ));
                break;
            default:
                handler.onErrors(new IllegalArgumentException("Unsupported schedule type: " + type));
                break;
        }
    }

}
