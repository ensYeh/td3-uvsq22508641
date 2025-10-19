package fr.uvsq.cprog.collex.commands;
import fr.uvsq.cprog.collex.Commande;
import fr.uvsq.cprog.collex.Dns;
import fr.uvsq.cprog.collex.AdresseIP;
import fr.uvsq.cprog.collex.NomMachine;

public class AjoutItem implements Commande {
    private final Dns dns;
    private final String ip;
    private final String name;

    public AjoutItem(final Dns dns, final String ip, final String name) {
        this.dns = dns;
        this.ip = ip;
        this.name = name;
    }

    @Override
    public String execute() {
        try {
            dns.addItem(new AdresseIP(ip), new NomMachine(name));
            return ""; 
        } catch (IllegalArgumentException e) {
            return "ERREUR : " + e.getMessage();
        } catch (Exception e) {
            return "ERREUR : " + e.getMessage();
        }
    }
}