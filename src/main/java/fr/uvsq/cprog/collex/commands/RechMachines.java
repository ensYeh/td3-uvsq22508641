package fr.uvsq.cprog.collex.commands;
import fr.uvsq.cprog.collex.Commande;
import fr.uvsq.cprog.collex.Dns;
import fr.uvsq.cprog.collex.DnsItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RechMachines implements Commande {
    private final Dns dns;
    private final String domaine;
    private final boolean byIp;

    public RechMachines(final Dns dns, final String domaine, final boolean byIp) {
        this.dns = dns;
        this.domaine = domaine;
        this.byIp = byIp;
    }

    @Override
    public String execute() {
        List<DnsItem> items = dns.getItems(domaine);
        if (items.isEmpty()) {
            return "";
        }
        if (byIp) {
            Collections.sort(items, Comparator.comparing(i -> i.getIp()));
        } else {
            Collections.sort(items, Comparator.comparing(i -> i.getNom().getMachine().toLowerCase()));
        }
        return items.stream()
                .map(i -> i.getIp().asString() + " " + i.getNom().asString())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}