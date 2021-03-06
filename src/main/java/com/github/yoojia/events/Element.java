package com.github.yoojia.events;

/**
 * @author Yoojia Chen (yoojiachen@gmail.com)
 * @since 2.0
 */
class Element {

    public final Object event;
    public final EventSubscriber handler;
    public final On scheduleOn;

    public Element(Object event, EventSubscriber handler, On scheduleOn) {
        this.event = event;
        this.handler = handler;
        this.scheduleOn = scheduleOn;
    }

}
