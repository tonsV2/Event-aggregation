package dk.fitfit.event.controller;

import dk.fitfit.event.domain.Event;
import dk.fitfit.event.service.EventServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
	private final EventServiceInterface eventService;

	@Autowired
	public EventController(EventServiceInterface eventService) {
		this.eventService = eventService;
	}

	@PostMapping("/collect")
	public Event collect(@RequestBody Event event) {
		Event save = eventService.save(event);
		return save;
	}
}
