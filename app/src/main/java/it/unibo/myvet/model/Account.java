package it.unibo.myvet.model;

public class Account {
    private String cf; // Codice Fiscale, utilizzato come ID
    private String password;
    private String nome;
    private String cognome;
    private String telefono;

    // Costruttore
    public Account(String cf, String password, String nome, String cognome, String telefono) {
        this.cf = cf;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
    }

    // Getter e Setter
    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Account{" +
                "cf='" + cf + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
