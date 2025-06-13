package per.nonobeam.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import per.nonobeam.notification.model.MailMessage;

@Repository
public interface MailMessageRepository extends JpaRepository<MailMessage, Long> {}
