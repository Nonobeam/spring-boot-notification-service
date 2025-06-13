package per.nonobeam.notification.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import per.nonobeam.notification.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
  Optional<Message> findByCorrelationId(String correlationId);
}
