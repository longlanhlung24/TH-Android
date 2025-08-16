package com.example.dongabank;

public class Tygia {
    private final String code;
    private final String muaCK;
    private final String banCK;
    private final String muaTM;
    private final String banTM;

    public Tygia(String code, String muaCK, String banCK, String muaTM, String banTM) {
        this.code = code;
        this.muaCK = muaCK;
        this.banCK = banCK;
        this.muaTM = muaTM;
        this.banTM = banTM;
    }

    public String getCode() { return code; }
    public String getMuaCK() { return muaCK; }
    public String getBanCK() { return banCK; }
    public String getMuaTM() { return muaTM; }
    public String getBanTM() { return banTM; }
}
