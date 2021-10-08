package sr.unasat.thewishapp.models;

public class BankDTO {

    private String saldo;

    public BankDTO(String saldo) {
        this.saldo = saldo;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return saldo;
    }
}
