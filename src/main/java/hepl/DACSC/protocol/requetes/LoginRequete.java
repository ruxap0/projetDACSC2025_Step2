package hepl.DACSC.protocol.requetes;

import hepl.DACSC.protocol.Requete;

public class LoginRequete implements Requete {
    private String login;
    private String password;

    public LoginRequete(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
