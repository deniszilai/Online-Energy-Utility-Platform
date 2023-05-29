package Domain;

import java.io.Serializable;

public class Admin extends Client implements Serializable {

    public Admin(){super();}

    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public String toString() {
        return super.toString() + "is admin";
    }
}

