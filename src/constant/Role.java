package constant;

public enum Role {
    ADMIN("admin"),USER("user");
    private String roleName;


    private Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
