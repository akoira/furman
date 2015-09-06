package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import java.awt.*;

/**
 * Этот интерфейс предоставляет к 2D shape данный фигуры
 * User: vishutinov
 */
public interface ShapeProvider
{
    public Shape getShape();

    public Shape getInvertedStartEndShape();
}
