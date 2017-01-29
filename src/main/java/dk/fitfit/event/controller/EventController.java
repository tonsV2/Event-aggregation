package dk.fitfit.event.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
	@PostMapping("/collect")
	public void collect(@RequestParam String event, @RequestParam long timestamp, @RequestParam String payload) {
		System.out.println(event);
		System.out.println(timestamp);
		System.out.println(payload);
	}
}
