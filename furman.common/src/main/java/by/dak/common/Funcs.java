package by.dak.common;

import java.awt.*;
import java.util.Objects;

public class Funcs {
    public final static Runnable init_fonts = () -> {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Funcs.class.getResourceAsStream("arial.ttf"))));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Funcs.class.getResourceAsStream("arialbd.ttf"))));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Funcs.class.getResourceAsStream("arialbi.ttf"))));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Funcs.class.getResourceAsStream("ariali.ttf"))));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Funcs.class.getResourceAsStream("Tahoma.ttf"))));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Funcs.class.getResourceAsStream("Tahoma-Bold.ttf"))));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    };
}
