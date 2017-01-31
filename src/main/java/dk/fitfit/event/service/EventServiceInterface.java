package dk.fitfit.event.service;

import dk.fitfit.event.domain.Event;

public interface EventServiceInterface {
	Event save(Event event);
	Event findOne(long id);
	Event save(String type, long timestamp, byte[] payloadAsString);
}
