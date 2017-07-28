package hstoneage.model;

import java.time.ZonedDateTime;

public final class Message implements Comparable<Message> {
    private User user;
    private String message;
    private ZonedDateTime created;

    public Message(User user, String message) {
        this.user = user;
        this.message = message;
        created = ZonedDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (user != null ? !user.equals(message1.user) : message1.user != null) return false;
        if (message != null ? !message.equals(message1.message) : message1.message != null) return false;
        return created != null ? created.equals(message1.created) : message1.created == null;

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Message o) {
        return -created.compareTo(o.getCreated());
    }
}
