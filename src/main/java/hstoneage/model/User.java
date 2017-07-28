package hstoneage.model;

public final class User {
    private String userId;

    public User(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userId != null ? userId.equals(user.userId) : user.userId == null;

    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }
}
