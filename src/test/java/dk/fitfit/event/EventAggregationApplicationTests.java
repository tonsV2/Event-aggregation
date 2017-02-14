package dk.fitfit.event;

import dk.fitfit.event.domain.Event;
import dk.fitfit.event.service.EventServiceInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventAggregationApplicationTests {
	private final EventServiceInterface eventService;

	public EventAggregationApplicationTests(EventServiceInterface eventService) {
		this.eventService = eventService;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void generateHeartbeatEvents() throws InterruptedException {

	}

	@Test
	public void generateClickEvents() throws InterruptedException {
		int events = 1_000_000;
		int numOids = 100;

		String[] oids = new String[numOids];
		for (int i = 0; i < numOids; i++) {
			String uuid = UUID.randomUUID().toString();
			oids[i] = uuid;
		}

		Random r = new Random();
		for (int i = 0; i < events; i++) {
			String type = "CLICK_EVENT";

			long timestamp = Instant.now().toEpochMilli();

			String oid = oids[r.nextInt(numOids)];

			Map<String, Object> payload = new HashMap<>();
			payload.put("lat", r.nextInt(100));
			payload.put("long", r.nextInt(100));

			Event event = eventService.save(type, timestamp, oid, payload);
			Thread.sleep(r.nextInt(200));
			if (i % 10 == 0) {
				System.out.println(i);
			}
		}
	}

}
