package com.syt.listener;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Think on 2017/8/15.
 */



class Person extends EventManager {
    public String name;

    public Person(String name) {
        this.name = name;
    }

    public void talk() {
        emit("talk");
    }
}

class EventManager {
    // 监听器列表，监听器的注册则加入此列表
    private Map<String, EventListener> listeners;

    // 定义监听器接口
    interface EventListener {
        void handleEvent(Object... args);
    }

    public EventManager() {
        this(new HashMap<String, EventListener>());
    }

    public EventManager(Map<String, EventListener> listenerList) {
        listeners = listenerList;
    }

    //注册监听器
    public void on(String id, EventListener eventListener) {
        listeners.put(id, eventListener);
    }

    //接受外部事件
    public void emit(String id, Object... args) {
        EventListener eventListener = listeners.get(id);
        assert eventListener != null;

        eventListener.handleEvent(id, args);
    }
}

public class ListenerDemo {

    public static void main(String[] args) {

        Person person = new Person("popvlovs");

        person.on("talk", new EventManager.EventListener() {
            @Override
            public void handleEvent(Object... args) {
                System.out.println("do talk");
            }
        });

        person.talk();
    }
}
