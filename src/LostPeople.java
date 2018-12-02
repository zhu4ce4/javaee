public class LostPeople {
    private String name;
    private String haoma;
    private String messages;

    public LostPeople(String name, String haoma, String messages) {
        this.name = name;
        this.haoma = haoma;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHaoma() {
        return haoma;
    }

    public void setHaoma(String haoma) {
        this.haoma = haoma;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

}
