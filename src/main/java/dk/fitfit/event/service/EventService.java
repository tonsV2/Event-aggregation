package dk.fitfit.event.service;

import dk.fitfit.event.domain.Event;
import org.springframework.stereotype.Service;

@Service
public class EventService implements EventServiceInterface {
	@Override
	public Event save(Event event) {
		System.out.println(event.getType());
		System.out.println(event.getTimestamp());
		System.out.println(event.getPayload());
		return event;
	}
}
