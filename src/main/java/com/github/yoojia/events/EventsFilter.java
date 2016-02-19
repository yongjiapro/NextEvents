package com.github.yoojia.events;

import com.github.yoojia.events.supports.ClassTypes;

/**
 * Subscriber 事件过滤器
 * @author YOOJIA.CHEN (yoojia.chen@gmail.com)
 * @version 2015-11-07
 */
class EventsFilter implements Filter<Event> {

    private final String mDefineName;
    private final Class<?> mDefineType;

    EventsFilter(String defineName, Class<?> rawDefineType) {
        mDefineName = defineName;
        mDefineType = ClassTypes.wrap(rawDefineType);
    }

    @Override
    public boolean accept(Event evt) {
        // 不接受: 事件名不同
        if (! mDefineName.equals(evt.name)) {
            return false;
        }
        // 不接受: 事件类型不相同
        if (! mDefineType.equals(evt.valueType)) {
            return false;
        }
        return true;
    }

    static EventsFilter with(String defineName, Class<?> rawDefineType) {
        return new EventsFilter(defineName, rawDefineType);
    }
}
