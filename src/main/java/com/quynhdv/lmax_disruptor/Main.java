package com.quynhdv.some;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.extern.java.Log;

import java.util.concurrent.ThreadFactory;

@Log
public class Main {
    public static EventHandler<ValueEvent>[] getEventHandler() {
        EventHandler<ValueEvent> eventHandler
                = (event, sequence, endOfBatch)
                -> print(event.getValue(), sequence);
        return new EventHandler[] { eventHandler };
    }

    private static void print(int id, long sequenceId) {
        log.info("Id is " + id
                + " sequence id that was used is " + sequenceId);
    }
    public static void main(String[] args) {
        ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;
        WaitStrategy waitStrategy = new BusySpinWaitStrategy();
        Disruptor<ValueEvent> disruptor
                = new Disruptor<>(
                ValueEvent.EVENT_FACTORY,
                16,
                threadFactory,
                ProducerType.SINGLE,
                waitStrategy);
        disruptor.handleEventsWith(getEventHandler());
        RingBuffer<ValueEvent> ringBuffer = disruptor.start();
        for (int eventCount = 0; eventCount < 32; eventCount++) {
            long sequenceId = ringBuffer.next();
            ValueEvent valueEvent = ringBuffer.get(sequenceId);
            valueEvent.setValue(eventCount);
            ringBuffer.publish(sequenceId);
        }
    }
}

