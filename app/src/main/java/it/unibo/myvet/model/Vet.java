package it.unibo.myvet.model;

public class Vet extends Account {
    private int idVeterinario;

    // Costruttore
    public Vet(String cf, String password, String nome, String cognome, String telefono, int idVeterinario) {
        super(cf, password, nome, cognome, telefono);
        this.idVeterinario = idVeterinario;
    }

    // Getter e Setter
    public int getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    @Override
    public String toString() {
        return "Veterinario{" +
                "idVeterinario=" + idVeterinario +
                ", cf='" + getCf() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", cognome='" + getCognome() + '\'' +
                ", telefono='" + getTelefono() + '\'' +
                '}';
    }
}
