package ir.rezvandeveloper.bookonline;

public class ItemTitrLV {
    private int id;
    private String name,lesson,number,link;

    public ItemTitrLV(int id,String name,String lesson,String number,String link) {
        this.id = id;
        this.name = name;
        this.lesson = lesson;
        this.number = number;
        this.link = link;
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

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
