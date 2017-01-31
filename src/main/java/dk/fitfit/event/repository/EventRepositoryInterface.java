package dk.fitfit.event.repository;

import dk.fitfit.event.domain.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepositoryInterface extends CrudRepository<Event, Long> {
}
