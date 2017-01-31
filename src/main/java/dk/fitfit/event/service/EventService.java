package dk.fitfit.event.service;

import dk.fitfit.event.domain.Event;
import dk.fitfit.event.repository.EventRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Event save(String type, long timestamp, byte[] payloadAsString) {
		return save(new Event(type, timestamp, payloadAsString));
	}

	@Override
	public Event save(Event event) {
		return eventRepository.save(event);
	}
}
