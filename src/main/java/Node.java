import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class Node {

    private int x, y;
    private Point point;

    public Node(int x , int y) {
        this.x = x;
        this.y = y;
        this.point = new Point(x, y);
    }

    public Point getPoint() {
        return this.point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
