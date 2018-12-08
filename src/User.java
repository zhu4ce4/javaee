public class User {

    //故意设置默认id为-1
    private int id = -1;
    private String name;
    private String password;
    private String picpath;

    public User() {
    }

    public User(int id, String name, String picpath) {
        this.id = id;
        this.name = name;
        this.picpath = picpath;
    }

    public User(String name, String password, String picpath) {
        this.name = name;
        this.password = password;
        this.picpath = picpath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    protected String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "id=" + id + "name=" + name;
    }

}
