package fr.uvsq.cprog.collex.commands;
import fr.uvsq.cprog.collex.Commande;
import fr.uvsq.cprog.collex.Dns;
import fr.uvsq.cprog.collex.DnsItem;
import fr.uvsq.cprog.collex.NomMachine;

public class RechNom implements Commande {
    private final Dns dns;
    private final String name;

    public RechNom(final Dns dns, final String name) {
        this.dns = dns;
        this.name = name;
    }

    @Override
    public String execute() {
        NomMachine nm = new NomMachine(name);
        DnsItem item = dns.getItem(nm);
        if (item == null) {
            return "ERREUR : introuvable";
        }
        return item.getIp().asString();
    }
}
