package ir.rezvandeveloper.bookonline.model;

public class ModelTalkQuestionRv {
    int id;
    String name,subject,question,date;

    public ModelTalkQuestionRv(int id, String name,String subject,String question,String date){
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.question = question;
        this.date = date;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
