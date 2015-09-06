/*
 * @(#)BoxHandleKit.java
 *
 * Copyright (c) 1996-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package by.dak.design.draw.handle;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.BoardFigure;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.event.TransformRestoreEdit;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.handle.HandleAttributeKeys;
import org.jhotdraw.draw.handle.LocatorHandle;
import org.jhotdraw.draw.locator.Locator;
import org.jhotdraw.draw.locator.RelativeLocator;
import org.jhotdraw.util.ResourceBundleUtil;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static org.jhotdraw.draw.AttributeKeys.TRANSFORM;


public class BoardFigureResizeHandler
{

    public BoardFigureResizeHandler()
    {
    }

    static public Handle south(Figure owner)
    {
        return new SouthHandle(owner);
    }

    static public Handle north(Figure owner)
    {
        return new NorthHandle(owner);
    }

    static public Handle east(Figure owner)
    {
        return new EastHandle(owner);
    }

    static public Handle west(Figure owner)
    {
        return new WestHandle(owner);
    }

    private static class ResizeHandle extends LocatorHandle
    {

        /**
         * Mouse coordinates on track start.
         */
        private int sx, sy;
        /**
         * Geometry for undo.
         */
        private Object geometry;
        /**
         * Figure bounds on track start.
         */
        protected Rectangle2D.Double sb;
        /**
         * Aspect ratio on track start.
         */
        double aspectRatio;
        /**
         * Caches the value returned by getOwner().isTransformable():
         */
        private boolean isTransformableCache;

        ResizeHandle(Figure owner, Locator loc)
        {
            super(owner, loc);
        }

        @Override
        public String getToolTipText(Point p)
        {
            ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
            return labels.getString("handle.resize.toolTipText");
        }

        /**
         * Draws this handle.
         * <p/>
         * If the figure is transformable, the handle is drawn as a filled rectangle.
         * If the figure is not transformable, the handle is drawn as an unfilled
         * rectangle.
         */
        @Override
        public void draw(Graphics2D g)
        {
            if (getEditor().getTool().supportsHandleInteraction())
            {
                if (getOwner().isTransformable())
                {
                    drawRectangle(g,
                            (Color) getEditor().getHandleAttribute(HandleAttributeKeys.RESIZE_HANDLE_FILL_COLOR),
                            (Color) getEditor().getHandleAttribute(HandleAttributeKeys.RESIZE_HANDLE_STROKE_COLOR));
                }
                else
                {
                    drawRectangle(g,
                            (Color) getEditor().getHandleAttribute(HandleAttributeKeys.NULL_HANDLE_FILL_COLOR),
                            (Color) getEditor().getHandleAttribute(HandleAttributeKeys.NULL_HANDLE_STROKE_COLOR));
                }
            }
            else
            {
                drawRectangle(g,
                        (Color) getEditor().getHandleAttribute(HandleAttributeKeys.HANDLE_FILL_COLOR_DISABLED),
                        (Color) getEditor().getHandleAttribute(HandleAttributeKeys.HANDLE_STROKE_COLOR_DISABLED));
            }
        }

        @Override
        public void trackStart(Point anchor, int modifiersEx)
        {
            isTransformableCache = getOwner().isTransformable();
            if (!isTransformableCache)
            {
                return;
            }

            geometry = getOwner().getTransformRestoreData();
            Point location = getLocation();
            sx = -anchor.x + location.x;
            sy = -anchor.y + location.y;
            sb = getOwner().getBounds();
            aspectRatio = sb.height / sb.width;
        }

        @Override
        public void trackStep(Point anchor, Point lead, int modifiersEx)
        {
            if (!isTransformableCache)
            {
                return;
            }
            Point2D.Double p = view.viewToDrawing(new Point(lead.x + sx, lead.y + sy));
            view.getConstrainer().constrainPoint(p);

            if (getOwner().get(TRANSFORM) != null)
            {
                try
                {
                    getOwner().get(TRANSFORM).inverseTransform(p, p);
                }
                catch (NoninvertibleTransformException ex)
                {
                    ex.printStackTrace();
                }
            }
            trackStepNormalized(p, (modifiersEx & (InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK)) != 0);
        }

        @Override
        public void trackEnd(Point anchor, Point lead, int modifiersEx)
        {
            if (!isTransformableCache)
            {
                return;
            }
            fireUndoableEditHappened(
                    new TransformRestoreEdit(getOwner(), geometry, getOwner().getTransformRestoreData()));

            BoardSizeConstrainerHandle sizeConstrainerHandle = new BoardSizeConstrainerHandle((BoardFigure) getOwner());
            sizeConstrainerHandle.setDrawing((FrontDesignerDrawing) getView().getDrawing());
            sizeConstrainerHandle.trackStep();
        }

        protected void trackStepNormalized(Point2D.Double p, boolean keepAspect)
        {
        }

        protected void setBounds(Point2D.Double anchor, Point2D.Double lead)
        {
            Figure f = getOwner();
            f.willChange();
            f.setBounds(anchor, lead);
            f.changed();
        }
    }

    private static class EastHandle extends ResizeHandle
    {

        EastHandle(Figure owner)
        {
            super(owner, RelativeLocator.east(true));
        }

        @Override
        protected void trackStepNormalized(Point2D.Double p, boolean keepAspect)
        {
            setBounds(
                    new Point2D.Double(sb.x, sb.y),
                    new Point2D.Double(Math.max(sb.x + 1, p.x), sb.y + sb.height));
        }

        @Override
        public void keyPressed(KeyEvent evt)
        {
            if (!getOwner().isTransformable())
            {
                evt.consume();
                return;
            }

            Rectangle2D.Double r = getOwner().getBounds();

            switch (evt.getKeyCode())
            {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    evt.consume();
                    break;
                case KeyEvent.VK_LEFT:
                    if (r.width > 1)
                    {
                        setBounds(
                                new Point2D.Double(r.x, r.y),
                                new Point2D.Double(r.x + r.width - 1, r.y + r.height));
                    }
                    evt.consume();
                    break;
                case KeyEvent.VK_RIGHT:
                    setBounds(
                            new Point2D.Double(r.x, r.y),
                            new Point2D.Double(r.x + r.width + 1, r.y + r.height));
                    evt.consume();
                    break;
            }
        }

        @Override
        public Cursor getCursor()
        {
            return Cursor.getPredefinedCursor(
                    getOwner().isTransformable() ? Cursor.E_RESIZE_CURSOR : Cursor.DEFAULT_CURSOR);
        }
    }

    private static class NorthHandle extends ResizeHandle
    {

        NorthHandle(Figure owner)
        {
            super(owner, RelativeLocator.north(true));
        }

        @Override
        protected void trackStepNormalized(Point2D.Double p, boolean keepAspect)
        {
            setBounds(
                    new Point2D.Double(sb.x, Math.min(sb.y + sb.height - 1, p.y)),
                    new Point2D.Double(sb.x + sb.width, sb.y + sb.height));
        }

        @Override
        public void keyPressed(KeyEvent evt)
        {
            if (!getOwner().isTransformable())
            {
                evt.consume();
                return;
            }
            Rectangle2D.Double r = getOwner().getBounds();

            switch (evt.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    setBounds(
                            new Point2D.Double(r.x, r.y - 1),
                            new Point2D.Double(r.x + r.width, r.y + r.height));
                    evt.consume();
                    break;
                case KeyEvent.VK_DOWN:
                    if (r.height > 1)
                    {
                        setBounds(
                                new Point2D.Double(r.x, r.y + 1),
                                new Point2D.Double(r.x + r.width, r.y + r.height));
                    }
                    evt.consume();
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                    evt.consume();
                    break;
            }
        }

        @Override
        public Cursor getCursor()
        {
            return Cursor.getPredefinedCursor(
                    getOwner().isTransformable() ? Cursor.N_RESIZE_CURSOR : Cursor.DEFAULT_CURSOR);
        }
    }

    private static class SouthHandle extends ResizeHandle
    {

        SouthHandle(Figure owner)
        {
            super(owner, RelativeLocator.south(true));
        }

        @Override
        protected void trackStepNormalized(Point2D.Double p, boolean keepAspect)
        {
            setBounds(
                    new Point2D.Double(sb.x, sb.y),
                    new Point2D.Double(sb.x + sb.width, Math.max(sb.y + 1, p.y)));
        }

        @Override
        public void keyPressed(KeyEvent evt)
        {
            if (!getOwner().isTransformable())
            {
                evt.consume();
                return;
            }
            Rectangle2D.Double r = getOwner().getBounds();

            switch (evt.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    if (r.height > 1)
                    {
                        setBounds(
                                new Point2D.Double(r.x, r.y),
                                new Point2D.Double(r.x + r.width, r.y + r.height - 1));
                    }
                    evt.consume();
                    break;
                case KeyEvent.VK_DOWN:
                    setBounds(
                            new Point2D.Double(r.x, r.y),
                            new Point2D.Double(r.x + r.width, r.y + r.height + 1));
                    evt.consume();
                    break;
                case KeyEvent.VK_LEFT:
                    evt.consume();
                    break;
                case KeyEvent.VK_RIGHT:
                    evt.consume();
                    break;
            }
        }

        @Override
        public Cursor getCursor()
        {
            return Cursor.getPredefinedCursor(
                    getOwner().isTransformable() ? Cursor.S_RESIZE_CURSOR : Cursor.DEFAULT_CURSOR);
        }
    }

    private static class WestHandle extends ResizeHandle
    {

        WestHandle(Figure owner)
        {
            super(owner, RelativeLocator.west(true));
        }

        @Override
        protected void trackStepNormalized(Point2D.Double p, boolean keepAspect)
        {
            setBounds(
                    new Point2D.Double(Math.min(sb.x + sb.width - 1, p.x), sb.y),
                    new Point2D.Double(sb.x + sb.width, sb.y + sb.height));
        }

        @Override
        public void keyPressed(KeyEvent evt)
        {
            if (!getOwner().isTransformable())
            {
                evt.consume();
                return;
            }
            Rectangle2D.Double r = getOwner().getBounds();

            switch (evt.getKeyCode())
            {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    evt.consume();
                    break;
                case KeyEvent.VK_LEFT:
                    setBounds(
                            new Point2D.Double(r.x - 1, r.y),
                            new Point2D.Double(r.x + r.width, r.y + r.height));
                    evt.consume();
                    break;
                case KeyEvent.VK_RIGHT:
                    if (r.width > 1)
                    {
                        setBounds(
                                new Point2D.Double(r.x + 1, r.y),
                                new Point2D.Double(r.x + r.width, r.y + r.height));
                    }
                    evt.consume();
                    break;
            }
        }

        @Override
        public Cursor getCursor()
        {
            return Cursor.getPredefinedCursor(
                    getOwner().isTransformable() ? Cursor.W_RESIZE_CURSOR : Cursor.DEFAULT_CURSOR);
        }
    }
}
