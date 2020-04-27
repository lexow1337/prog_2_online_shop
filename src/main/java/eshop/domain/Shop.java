package eshop.domain;

import java.io.IOException;

public class Shop {

    private String datei = "";

    public void Shop(String datei) throws IOException {
        this.datei = datei;

        meineArtikel = new ShopVerwaltung();

    }
}
