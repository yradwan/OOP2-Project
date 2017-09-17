package sample;

public class Dentist extends Person{
    private String password;

    public Dentist(String name, String address, String pass){
        super (name, address);
        password = pass;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
