package Menu;

import com.mygdx.game.Gameplay.Car;
import com.mygdx.game.Gameplay.Enums.ESkin;

public class Player {
    private Car car;
    private ESkin carSkin;
    private String name;
    private String email;
    private String password;

    public Player(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogInName() {
        return email;
    }

    public void setLogInName(String logInName) {
        this.email = logInName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
