package dk.fitfit.event.service;

import dk.fitfit.event.domain.Event;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface EventServiceInterface {
	Event save(Event event);
	Event findOne(long id);
	Event save(String type, long timestamp, String indexId, Map<String, Object> payload);
	Stream<Event> findByType(String type);
	List<String> findObjectIdByType(String type);
	Stream<Event> findByObjectId(String objectId);
}
