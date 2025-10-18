package fr.uvsq.cprog.collex;
import java.util.Objects;

public final class DnsItem {
    private final NomMachine nom;
    private final AdresseIP ip;

    public DnsItem(final NomMachine nom, final AdresseIP ip) {
        this.nom = Objects.requireNonNull(nom);
        this.ip = Objects.requireNonNull(ip);
    }

    public NomMachine getNom() {
        return nom;
    }

    public AdresseIP getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return nom.asString() + " " + ip.asString();
    }
}
