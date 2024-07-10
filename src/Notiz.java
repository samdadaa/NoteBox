import java.util.ArrayList;

public class Notiz
{
    public Notiz(String titel, String inhalt) {
        this.titel = titel;
        this.inhalt = inhalt;
        notizListe.add(this);
    }

    private ArrayList<Notiz> notizListe = new ArrayList<>();
    private String titel;

    public String getInhalt() {
        return inhalt;
    }

    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    private String inhalt;

}
