package repasoexamen3;

public class Analisis {

    private String codigoa;
    private int acidez;
    private int grao;
    private int taninos;
    private String tipoUva;
    private int cantidade;
    private String dni;

    public Analisis() {
    }

    public Analisis(String codigoa, int acidez, int grao, int taninos, String tipoUva, int cantidade, String dni) {
        this.codigoa = codigoa;
        this.acidez = acidez;
        this.grao = grao;
        this.taninos = taninos;
        this.tipoUva = tipoUva;
        this.cantidade = cantidade;
        this.dni = dni;
    }

    public String getCodigoa() {
        return codigoa;
    }

    public int getAcidez() {
        return acidez;
    }

    public int getGrao() {
        return grao;
    }

    public int getTaninos() {
        return taninos;
    }

    public String getTipoUva() {
        return tipoUva;
    }

    public int getCantidade() {
        return cantidade;
    }

    public String getDni() {
        return dni;
    }

    public void setCodigoa(String codigoa) {
        this.codigoa = codigoa;
    }

    public void setAcidez(int acidez) {
        this.acidez = acidez;
    }

    public void setGrao(int grao) {
        this.grao = grao;
    }

    public void setTaninos(int taninos) {
        this.taninos = taninos;
    }

    public void setTipoUva(String tipoUva) {
        this.tipoUva = tipoUva;
    }

    public void setCantidade(int cantidade) {
        this.cantidade = cantidade;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Analisis{" + "codigoa=" + codigoa + ", acidez=" + acidez + ", grao=" + grao + ", taninos=" + taninos + ", tipoUva=" + tipoUva + ", cantidade=" + cantidade + ", dni=" + dni + '}';
    }

}
