package fr.uvsq.cprog.collex;
package fr.uvsq.cprog.collex.Commande;
package fr.uvsq.cprog.collex.Dns;
package fr.uvsq.cprog.collex.DnsItem;
package fr.uvsq.cprog.collex.AdresseIP;

public class RechIP implements Commande {
    private final Dns dns;
    private final String ip;

    public RechIP(final Dns dns, final String ip) {
        this.dns = dns;
        this.ip = ip;
    }

    @Override
    public String execute() {
        AdresseIP a = new AdresseIP(ip);
        DnsItem item = dns.getItem(a);
        if (item == null) {
            return "ERREUR : introuvable";
        }
        return item.getNom().asString();
    }
}

