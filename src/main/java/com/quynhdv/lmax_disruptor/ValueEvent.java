package com.quynhdv.some;

import com.lmax.disruptor.EventFactory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValueEvent {
    private int value;
    public final static EventFactory EVENT_FACTORY
            = () -> new ValueEvent();

    // standard getters and setters
}
