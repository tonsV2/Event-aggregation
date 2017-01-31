package dk.fitfit.event.service;

import dk.fitfit.event.domain.Event;

import java.io.IOException;
import java.util.Map;

public interface EventServiceInterface {
	Event save(Event event);
	Event findOne(long id);
	Event save(String type, long timestamp, Map<String, Object> payload) throws IOException;
}
