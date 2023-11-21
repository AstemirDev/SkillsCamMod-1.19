package org.astemir.cammod.client.screen.ui.event;

import java.util.function.Consumer;

public abstract class NodeEvent {


    public static NodeEvent.Run run(Runnable runnable){
        return new Run() {
            @Override
            public void onHandle() {
                runnable.run();
            }
        };
    }

    public static <T> NodeEvent.Function<T> function(Consumer<T> consumer){
        return new Function<T>() {
            @Override
            public void onHandle(T value) {
                consumer.accept(value);
            }
        };
    }


    public static NodeEvent.Run emptyRun(){
        return new Run() {
            @Override
            public void onHandle() {}
        };
    }


    public static <T> NodeEvent.Function<T> emptyFunction(){
        return new Function<T>() {
            @Override
            public void onHandle(T value) {}
        };
    }

    public static <K,V> NodeEvent.BinaryFunction<K,V> emptyBinaryFunction(){
        return new BinaryFunction<K,V>() {
            @Override
            public void onHandle(K key, V value) {}
        };
    }



    public static abstract class Run extends NodeEvent{

        public abstract void onHandle();
    }

    public static abstract class Function<T> extends NodeEvent{

        public abstract void onHandle(T value);
    }

    public static abstract class BinaryFunction<K,V> extends NodeEvent{

        public abstract void onHandle(K key,V value);
    }
}
