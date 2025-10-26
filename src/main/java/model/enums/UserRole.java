package model.enums;

public enum UserRole {
    ADMIN("Administrator"),
    CLIENT("Client");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
