package dk.fitfit.event.repository;

import dk.fitfit.event.domain.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface EventRepositoryInterface extends CrudRepository<Event, Long> {
	Stream<Event> findByType(String type);
}
