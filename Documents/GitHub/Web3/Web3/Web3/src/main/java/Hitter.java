import java.time.LocalDateTime;

public class Hitter {
    public double x;
    public double y;
    public double r;
    public boolean hit;
    public LocalDateTime time;

    public Hitter(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.time = LocalDateTime.now();
        this.hit = checkHit();
    }

    public boolean checkHit() {
        return checkCircle() || checkTriangle() || checkRectangle();
    }

    public boolean checkCircle() {
        return x >= 0 && y >= 0 && x * x + y * y <= r * r;
    }

    public boolean checkRectangle() {
        return x >= 0 && y <= 0 && x <= r/2 && y <= r;
    }

    public boolean checkTriangle() {
        return x <= 0 && y <= 0 && 2 * x + y + r >= 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public boolean getHit() {
        return hit;
    }
}
