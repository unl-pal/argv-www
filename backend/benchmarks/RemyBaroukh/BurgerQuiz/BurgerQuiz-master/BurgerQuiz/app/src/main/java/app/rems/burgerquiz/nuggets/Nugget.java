package app.rems.burgerquiz.nuggets;

/**
 * Created by remsd_000 on 04/04/2015.
 */
public class Nugget {
    private int idNugget;
    private String question;
    private String reponse1;
    private String reponse2;
    private String reponse3;
    private String reponse4;

    Nugget(int idNugget)
    {
        this.idNugget = idNugget;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse1() {
        return reponse1;
    }

    public void setReponse1(String reponse1) {
        this.reponse1 = reponse1;
    }

    public String getReponse2() {
        return reponse2;
    }

    public void setReponse2(String reponse2) {
        this.reponse2 = reponse2;
    }

    public String getReponse3() {
        return reponse3;
    }

    public void setReponse3(String reponse3) {
        this.reponse3 = reponse3;
    }

    public String getReponse4() {
        return reponse4;
    }

    public void setReponse4(String reponse4) {
        this.reponse4 = reponse4;
    }

    @Override
    public String toString() {
        return "Nugget{" +
                "idNugget=" + idNugget +
                ", question='" + question + '\'' +
                ", reponse1='" + reponse1 + '\'' +
                ", reponse2='" + reponse2 + '\'' +
                ", reponse3='" + reponse3 + '\'' +
                ", reponse4='" + reponse4 + '\'' +
                '}';
    }
}
