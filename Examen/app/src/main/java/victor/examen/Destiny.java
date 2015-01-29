package victor.examen;

import java.io.Serializable;

/**
 * Created by victor on 29/01/15.
 */
public class Destiny implements Serializable {
    private char area;
    private String continent;
    private int price;

    public Destiny(char area, String continent, int price){
        this.area = area;
        this.continent = continent;
        this.price = price;
    }

    public String toString(){
        return String.format("Zona %s\n%s\n%d€\n", area, continent, price);
    }

    public char getArea() {
        return area;
    }

    public String getContinent() {
        return continent;
    }

    public int getPrice() {
        return price;
    }
}