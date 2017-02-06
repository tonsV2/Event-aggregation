package dk.fitfit.event.service;

import dk.fitfit.event.domain.Event;
import dk.fitfit.event.repository.EventRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Stream;

@Service
public class EventService implements EventServiceInterface {
	private final EventRepositoryInterface eventRepository;

	@Autowired
	public EventService(EventRepositoryInterface eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Override
	public Event findOne(long id) {
		return eventRepository.findOne(id);
	}

	@Override
	public Event save(String type, long timestamp, String indexId, Map<String, Object> payload) {
		return save(new Event(type, timestamp, indexId, payload));
	}

	@Override
	public Stream<Event> findByType(String type) {
		return eventRepository.findByType(type);
	}

	@Override
	public Event save(Event event) {
		return eventRepository.save(event);
	}
}
