package Code;

public interface Login {

    public Student login(String username, String password);

    void logout();

    String getPassWord(String username);

    public void addUser(String name, String password, String confirm);


}
