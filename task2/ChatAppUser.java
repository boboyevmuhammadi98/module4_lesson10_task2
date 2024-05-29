package tasks.task2;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ChatAppUser implements Serializable {
    private String email;
    private LocalDateTime registeredAt;

    public ChatAppUser(String email) {
        this.email = email;
        this.registeredAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ChatAppUser{" +
                "email='" + email + '\'' +
                ", registeredAt=" + registeredAt +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }
}
