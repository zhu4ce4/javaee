public class LostPeople {
    //    private int id;
    private String lpname;
    private String lphaoma;
    private String lpmessages;

    public LostPeople() {
    }

    public LostPeople(String name, String messages, String haoma) {
//        id = aid;
        lpname = name;
        lpmessages = messages;
        lphaoma = haoma;
    }

//    public int getId() {
//        return id;
//    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public String getName() {
        return lpname;
    }

    public void setName(String name) {
        lpname = name;
    }

    public String getHaoma() {
        return lphaoma;
    }

    public void setHaoma(String haoma) {
        lphaoma = haoma;
    }

    public String getMessages() {
        return lpmessages;
    }

    public void setMessages(String messages) {
        lpmessages = messages;
    }

}
