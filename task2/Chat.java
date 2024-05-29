package tasks.task2;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Chat implements Serializable {
    private ChatAppUser chatAppUser;
    private String message;
    private LocalDateTime sentAt;

    public Chat(ChatAppUser chatAppUser, String message) {
        this.chatAppUser = chatAppUser;
        this.message = message;
        this.sentAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Chat{" +
                "chatAppUser=" + chatAppUser +
                ", message='" + message + '\'' +
                ", sentAt=" + sentAt +
                '}';
    }

    public ChatAppUser getChatAppUser() {
        return chatAppUser;
    }

    public void setChatAppUser(ChatAppUser chatAppUser) {
        this.chatAppUser = chatAppUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
