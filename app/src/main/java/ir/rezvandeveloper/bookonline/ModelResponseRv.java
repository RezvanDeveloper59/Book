package ir.rezvandeveloper.bookonline;

public class ModelResponseRv {
    int id,id_question;
    String name,response,date;

    public ModelResponseRv(int id,int id_question, String name, String response,String date){
        this.id = id;
        this.id_question = id_question;
        this.name = name;
        this.response = response;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String question) {
        this.response = question;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
