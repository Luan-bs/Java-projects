import java.time.Year;

public class Car {

    private static final int FIRST_CAR_YEAR = 1886;

    private String brand;
    private String model;
    private String color;
    private int year;

    public Car(String brand, String model, String color, int year) {
        setBrand(brand);
        setModel(model);
        setColor(color);
        setYear(year);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = requireText(brand, "Marca");
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = requireText(model, "Modelo");
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = requireText(color, "Cor");
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        int maxYear = Year.now().getValue() + 1;
        if (year < FIRST_CAR_YEAR || year > maxYear) {
            throw new IllegalArgumentException(
                    "Ano inválido. Informe um ano entre " + FIRST_CAR_YEAR + " e " + maxYear + ".");
        }
        this.year = year;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " não pode ficar em branco.");
        }
        return value.trim();
    }
}
