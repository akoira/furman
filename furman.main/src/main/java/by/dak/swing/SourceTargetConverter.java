/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.swing;

/**
 * @author admin
 */
public interface SourceTargetConverter<T, S>
{
    public S source(T target);

    public T target(S source);
}
