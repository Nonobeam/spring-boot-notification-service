CREATE TABLE message (
         id BIGSERIAL PRIMARY KEY,
         type VARCHAR(255),
         to_address VARCHAR(255),
         subject VARCHAR(255),
         from_address VARCHAR(255),
         content TEXT,
         status VARCHAR(255),
         retry_count INTEGER,
         error_message VARCHAR(1024),
         correlation_id VARCHAR(255),
         message_id VARCHAR(255),
         source VARCHAR(255),
         created_at TIMESTAMP,
         last_tried_at TIMESTAMP
);

create unique index idx_msg_id_status on message(id, status);

CREATE TABLE mail_message (
          id BIGSERIAL PRIMARY KEY,
          to_address VARCHAR(255),
          subject VARCHAR(255),
          from_address VARCHAR(255),
          content TEXT,
          cc TEXT[],
          bcc TEXT[],
          reply_to VARCHAR(255),
          content_type VARCHAR(255)
);
