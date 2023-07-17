package com.quynhdv.some;

import com.lmax.disruptor.EventHandler;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Log
public class SingleEventPrintConsumer {
        public EventHandler<ValueEvent>[] getEventHandler() {
            EventHandler<ValueEvent> eventHandler
                    = (event, sequence, endOfBatch)
                    -> print(event.getValue(), sequence);
            return new EventHandler[] { eventHandler };
        }

        private void print(int id, long sequenceId) {
            log.info("Id is " + id
                    + " sequence id that was used is " + sequenceId);
        }
}

