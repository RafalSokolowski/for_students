package day20201108.logoNBC;

import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PeacockFeather {

    private final Point p1;
    private final Point p2;
    private final Point p3;

    private final Paint color;

    public Shape draw() {
        Shape triangle = createTriangle();
        Shape arc = createArc();
        Shape peacockFeather = Shape.union(triangle, arc);
        peacockFeather.setFill(color);
        return peacockFeather;
    }

    private Shape createTriangle() {
        Polygon triangle = new Polygon(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
        return triangle;
    }

    public Shape createArc() {
        Point center = calculateArcCenter(p2, p3);
        double radius = calculateRadius(p2, p3);

        double startAngle = calculateAngleForArc(p2, center);
        double endAngle = (p1.getX() < p2.getX()) ? calculateAngleForArc(p3, center) : -calculateAngleForArc(p3, center);

        Arc arc = new Arc();
        arc.setCenterX(center.getX());
        arc.setCenterY(center.getY());
        arc.setRadiusX(radius);
        arc.setRadiusY(radius);
        arc.setStartAngle(endAngle);
        arc.setLength(180);

        return arc;
    }

    private double calculateAngleForArc(Point point, Point center) {
        double angle = 180.0 / Math.PI * Math.atan2(point.getY() - center.getY(), point.getX() - center.getX());
        return angle;
    }

    private double calculateRadius(Point start, Point end) {
        double x = Math.abs(end.getX() - start.getX()) / 2;
        double y = Math.abs(end.getY() - start.getY()) / 2;
        return Math.sqrt(x * x + y * y);
    }

    private Point calculateArcCenter(Point start, Point end) {
        double x = Math.abs(end.getX() - start.getX()) / 2 + start.getX();
        double y = Math.abs(end.getY() - start.getY()) / 2;

        y += (end.getY() > start.getY()) ? start.getY() : end.getY();

        return new Point(x, y);
    }


}
