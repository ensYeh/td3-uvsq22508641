package fr.uvsq.cprog.collex;
import java.util.Objects;

public final class AdresseIP implements Comparable<AdresseIP> {
    private final int[] octets = new int[4];
    
    public AdresseIP(final String ip) {
        Objects.requireNonNull(ip, "ip ne peut pas etre null");
        String[] parts = ip.trim().split("\\.");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Adresse IP invalide: " + ip);
        }
        for (int i = 0; i < 4; i++) {
            try {
                int v = Integer.parseInt(parts[i]);
                if (v < 0 || v > 255) {
                    throw new IllegalArgumentException("Octet hors plage: " + parts[i]);
                }
                octets[i] = v;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Octet non numerique: " + parts[i], e);
            }
        }
    }

    public String asString() {
        return octets[0] + "." + octets[1] + "." + octets[2] + "." + octets[3];
    }

    @Override
    public int compareTo(final AdresseIP other) {
        for (int i = 0; i < 4; i++) {
            int diff = Integer.compare(this.octets[i], other.octets[i]);
            if (diff != 0) {
                return diff;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdresseIP)) {
            return false;
        }
        AdresseIP that = (AdresseIP) o;
        for (int i = 0; i < 4; i++) {
            if (this.octets[i] != that.octets[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (int v : octets) {
            result = 31 * result + v;
        }
        return result;
    }

    @Override
    public String toString() {
        return asString();
    }
}