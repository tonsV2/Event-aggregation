package dk.fitfit.event.controller;

import dk.fitfit.event.domain.Event;
import dk.fitfit.event.resource.EventResource;
import dk.fitfit.event.service.EventServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class EventController {
	private final EventServiceInterface eventService;

	@Autowired
	public EventController(EventServiceInterface eventService) {
		this.eventService = eventService;
	}

	@PostMapping("/collect")
	public EventResource collect(@RequestBody EventResource eventResource) throws IOException, ClassNotFoundException {
		Event save = eventService.save(eventResource.getType(), eventResource.getTimestamp(), eventResource.getPayload());
		return EventResource.of(save);
	}

	@GetMapping("/collect/{id}")
	public EventResource collect(@PathVariable long id) throws IOException, ClassNotFoundException {
		Event event = eventService.findOne(id);
		return EventResource.of(event);
	}
}
