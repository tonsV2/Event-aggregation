package dk.fitfit.event.service;

import dk.fitfit.event.domain.Event;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

public interface EventServiceInterface {
	Event save(Event event);
	Event findOne(long id);
	Event save(String type, long timestamp, String indexId, Map<String, Object> payload) throws IOException;
	Stream<Event> findByType(String type);
}
