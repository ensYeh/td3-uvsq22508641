package fr.uvsq.cprog.collex;
import java.util.Objects;

public final class NomMachine {
    private final String machine;
    private final String domaine;

    public NomMachine(final String fqdn) {
        Objects.requireNonNull(fqdn, "fqdn ne peut pas etre null");
        String s = fqdn.trim();
        int idx = s.indexOf('.');
        if (idx <= 0 || idx == s.length() - 1) {
            throw new IllegalArgumentException("Nom qualifie invalide: " + fqdn);
        }
        this.machine = s.substring(0, idx);
        this.domaine = s.substring(idx + 1);
    }

    public String getMachine() {
        return machine;
    }

    public String getDomaine() {
        return domaine;
    }

    public String asString() {
        return machine + "." + domaine;
    }

    @Override
    public String toString() {
        return asString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NomMachine)) {
            return false;
        }
        NomMachine that = (NomMachine) o;
        return this.asString().equalsIgnoreCase(that.asString());
    }

    @Override
    public int hashCode() {
        return asString().toLowerCase().hashCode();
    }
}
