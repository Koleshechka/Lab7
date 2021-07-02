package product;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.HashSet;

/**
 * Класс из задачи.
 * Класс продукта.
 * @author Koleshechka
 */
public class Product implements Comparable<Product> {
    private static final HashSet<Long> IDs = new HashSet<>();
    private static long currentID = 1;

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double price; //Поле не может быть null, Значение поля должно быть больше 0
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    private Organization manufacturer; //Поле не может быть null
    private String username; // TODO изменить конструкторы

    public Product(String name, Coordinates coordinates, Double price, UnitOfMeasure unitOfMeasure, Organization manufacturer) {
        this.id = -1L;
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturer = manufacturer;
        this.creationDate = ZonedDateTime.now();
    }

    public Product(String name, int x, int y, Double price, UnitOfMeasure unitOfMeasure, String nameOfOrg, Double annualTurnover, OrganizationType type, String username) {
        this.id = -1L;
        this.name = name;
        this.coordinates = new Coordinates(x,y);
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturer = new Organization(nameOfOrg, annualTurnover, type);
        this.creationDate = ZonedDateTime.now();
        this.username = username;
    }
    public Product(String name, int x, int y, Double price, UnitOfMeasure unitOfMeasure, String nameOfOrg, Double annualTurnover, OrganizationType type) {
        this.id = -1L;
        this.name = name;
        this.coordinates = new Coordinates(x,y);
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturer = new Organization(nameOfOrg, annualTurnover, type);
        this.creationDate = ZonedDateTime.now();
    }

    public Product(Long id, String name, int x, int y, ZonedDateTime creationDate, Double price, UnitOfMeasure unitOfMeasure,Long idOfOrg, String nameOfOrg, Double annualTurnover, OrganizationType type, String username) {
        this.id = id;
        this.name = name;
        this.coordinates = new Coordinates(x, y);
        this.creationDate = creationDate;
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturer = new Organization(idOfOrg, nameOfOrg, annualTurnover, type);
        this.creationDate = ZonedDateTime.now();
        this.username = username;
    }



    public Long getId() {
        if (id == -1) {
            while (IDs.contains(currentID)) {
                currentID++;
            }
            id = currentID;
            IDs.add(id);
        }
        return id;
    }

    public void setId(long id) {
        if (IDs.contains(id)) {
            //ignore new id
            return;
        }
        IDs.add(id);
        this.id = id;
    }

    public void removeByID() {
        IDs.remove(id);
    }


    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }


    @Override
    public String toString() {
        return " id: " + getId() +
                ",\n name: " + name +
                ",\n coordinates: \n" + coordinates +
                ",\n creationDate: " + creationDate +
                ",\n price: " + price +
                ",\n unitOfMeasure: " + unitOfMeasure +
                ",\n manufacturer: \n" + manufacturer +
                "\n____________\n";
    }

    @Override
    public int compareTo(Product product) {
        /*
        if (this.getPrice()<product.getPrice()) {
            return -1;
        } else if (product.getPrice()<this.getPrice()) {
            return 1;
        } else return 0;
         */
        return Double.compare(this.getPrice(), product.getPrice());
    }
    public ZonedDateTime getCreationDate() {return creationDate;}

    public String getName() {return name;}
    public int getX() {return coordinates.getX();}
    public int getY() {return coordinates.getY();}
    //public Timestamp getDate() {return Timestamp.from(creationDate.toInstant());}
    public Double getPrice() {return price;}
    public UnitOfMeasure getUnitOfMeasure() {return unitOfMeasure;}
    public Long getIdOfOrg() {return manufacturer.getId();}
    public String getNameOfOrg() {return manufacturer.getName();}
    public Double getAnnualTurnover() {return manufacturer.getAnnualTurnover();}
    public OrganizationType getType() {return manufacturer.getType();}
    public String getUsername() {return username;}

}

