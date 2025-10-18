package fr.uvsq.cprog.collex;
package fr.uvsq.cprog.collex.Commande;
package fr.uvsq.cprog.collex.Dns;
package fr.uvsq.cprog.collex.DnsItem;
package fr.uvsq.cprog.collex.NomMachine;

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
