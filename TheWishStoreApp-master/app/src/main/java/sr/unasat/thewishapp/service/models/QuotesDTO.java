package sr.unasat.thewishapp.models;

public class QuotesDTO {

    private String Quote;
    private String Author;

    public QuotesDTO(String Quote, String Author) {
        this.Quote = Quote;
        this.Author = Author;
    }

    public String getQuote() {
        return Quote;
    }

    public void setQuote(String quote) {
        this.Quote = Quote;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    @Override
    public String toString() {
        return "- " + Quote + " -" + "\n" + "~" + Author + "~";
    }
}
