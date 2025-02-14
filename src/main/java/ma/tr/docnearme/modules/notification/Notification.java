package ma.tr.docnearme.modules.notification;

import jakarta.persistence.*;
import ma.tr.docnearme.modules.user.User;

import java.util.UUID;

@Entity
public class Notification {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private java.time.LocalDateTime createdAt;
}
