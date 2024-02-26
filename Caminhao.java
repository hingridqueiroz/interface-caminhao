import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Caminhao {

    private String placa;
    private String combustivel;
    private LocalDate dataChegada;
    private double volume;

    Caminhao(String placa, String combustivel, LocalDate dataChegada, double volume) {
        this.placa = placa;
        this.combustivel = combustivel;
        this.dataChegada = dataChegada;
        this.volume = volume;
    }

    public String getPlaca() {
        return placa;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public String getDataChegada() {
        if (dataChegada != null) {
            return dataChegada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            return "Data não informada";
        }
    }

    public double getVolume() {
        return volume;
    }
}