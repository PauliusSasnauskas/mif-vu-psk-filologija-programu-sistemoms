package lt.vu.mif.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.entities.Parcel;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named // or @ManagedBean
@SessionScoped
public class LoggedIn implements Serializable {
    private boolean isLoggedIn = false;
    private String loggedInMessage = "Welcome, admin!";
    private String loggedOutMessage = "Login";
    private String username = "";
    private String password = "";

    public String login() {
        if (username.equals("admin") && password.equals("admin")) {
            this.isLoggedIn = true;
            return "index.xhtml";
        }

        return "login.xhtml";
    }

    public void logout() {
        this.isLoggedIn = false;
    }

    public boolean getIsLoggedIn() {
        return this.isLoggedIn;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
