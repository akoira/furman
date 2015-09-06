package by.dak.design.draw.components.annotation;

import java.lang.annotation.Annotation;

/**
 * User: akoyro
 * Date: 13.09.11
 * Time: 15:30
 */
public class ConvertedAnnotationProcessor
{
    private final static ConvertedAnnotationProcessor PROCESSOR = new ConvertedAnnotationProcessor();


    public static ConvertedAnnotationProcessor getProcessor()
    {
        return PROCESSOR;
    }


    public boolean isConverted(Class annotatedClass)
    {
        Annotation annotation = annotatedClass.getAnnotation(Converted.class);
        return annotation != null;
    }
}
