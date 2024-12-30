package gabriel.investmentportfolio.model;

import java.io.Serializable;
import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private UUID userId;

    @NotBlank(message = "Username is required.")
    private String userName;

    @Email(message = "Email should be valid.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, message = "Password should be at least 6 characters long.")
    private String password;

  
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public User(UUID userId, String userName, String email, String password) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", email=" + email + ", password=" + password
                + "]";
    }  
}
