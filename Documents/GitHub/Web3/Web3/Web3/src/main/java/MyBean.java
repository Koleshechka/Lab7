import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@Named ("myBean")
@ApplicationScoped
public class MyBean implements Serializable {
    private String y = "0";
    private String x ="0";
    private String r = "0";
    private List<Hitter> collection = new LinkedList<>();

    public List<Hitter> getCollection() {
        return collection;
    }

    public void setCollection(List<Hitter> collection) {
        this.collection = collection;
    }



    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }



    private Boolean x1 = true;
    private Boolean x2 = false;
    private Boolean x3 = false;
    private Boolean x4 = false;
    private Boolean x5 = false;
    private Boolean x6 = false;
    private Boolean x7 = false;

    public Boolean getX1() {
        return x1;
    }

    public void setX1(Boolean x1) {
        this.x1 = x1;
        this.x = "-5";
        this.x2 = false;
        this.x3 = false;
        this.x4 = false;
        this.x5 = false;
        this.x6 = false;
        this.x7 = false;
    }

    public Boolean getX2() {
        return x2;
    }

    public void setX2(Boolean x2) {
        this.x2 = x2;
        this.x = "-4";
        this.x1 = false;
        this.x3 = false;
        this.x4 = false;
        this.x5 = false;
        this.x6 = false;
        this.x7 = false;
    }

    public Boolean getX3() {
        return x3;
    }

    public void setX3(Boolean x3) {
        this.x3 = x3;
        this.x = "-3";
        this.x1 = false;
        this.x2 = false;
        this.x4 = false;
        this.x5 = false;
        this.x6 = false;
        this.x7 = false;
    }

    public Boolean getX4() {
        return x4;
    }

    public void setX4(Boolean x4) {
        this.x4 = x4;
        this.x = "-2";
        this.x1 = false;
        this.x2 = false;
        this.x3 = false;
        this.x5 = false;
        this.x6 = false;
        this.x7 = false;
    }

    public Boolean getX5() {
        return x5;
    }

    public void setX5(Boolean x5) {
        this.x5 = x5;
        this.x = "-1";
        this.x1 = false;
        this.x2 = false;
        this.x3 = false;
        this.x4 = false;
        this.x6 = false;
        this.x7 = false;
    }

    public Boolean getX6() {
        return x6;
    }

    public void setX6(Boolean x6) {
        this.x6 = x6;
        this.x = "0";
        this.x2 = false;
        this.x3 = false;
        this.x4 = false;
        this.x5 = false;
        this.x1 = false;
        this.x7 = false;
    }

    public Boolean getX7() {
        return x7;
    }

    public void setX7(Boolean x7) {
        this.x7 = x7;
        this.x = "1";
        this.x2 = false;
        this.x3 = false;
        this.x4 = false;
        this.x5 = false;
        this.x6 = false;
        this.x1 = false;
    }



    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }


    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setR1() {
        this.r = "1";
    }
    public void setR2() {
        this.r = "2";
    }
    public void setR3() {
        this.r = "3";
    }
    public void setR4() {
        this.r = "4";
    }
    public void setR5() {
        this.r = "5";
    }

    public void addPoint(){
        Double newX = null;
        Double newY = null;
        Double newR = null;

        try {
            newX = Double.parseDouble(x);
        }catch (Exception e) {
            System.out.println(x + e.toString());
        }

        try {
            newY = Double.parseDouble(y);
        }catch (Exception e) {
            System.out.println(y + e.toString());
        }

        try {
            newR = Double.parseDouble(r);
        }catch (Exception e) {
            System.out.println(r + e.toString());
        }
        try {
            Hitter point = new Hitter(newX, newY, newR);
            collection.add(point);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
