/*
 * QuickList.java
 *
 * Created on 30. listopad 2006, 15:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

import java.util.AbstractList;

/**
 * @author Peca
 */
public class QuickList<E> extends AbstractList<E>
{
    private int size;
    private E[] data;

    /**
     * Creates a new instance of QuickList
     */
    public QuickList()
    {
        this(16);
    }

    public QuickList(int capacity)
    {
        data = (E[]) new Object[capacity];
    }

    public void setCapacity(int capacity)
    {
        Object[] a = new Object[capacity];
        if (size > 0) System.arraycopy(data, 0, a, 0, size);
        data = (E[]) a;
    }

    public boolean add(E o)
    {
        if (size >= data.length) setCapacity(size * 2);
        data[size++] = o;
        return true;
    }

    public E get(int index)
    {
        return data[index];
    }

    public E remove(int index)
    {
        size--;
        E o = data[index];
        if (index < size) data[index] = data[size]; //pokud to neni posledni prvek tak na toto misto dame posledni prvek
        return o;
    }

    public int size()
    {
        return size;
    }

    public E set(int index, E element)
    {
        E oldValue = data[index];
        data[index] = element;
        return oldValue;
    }

    public void pack()
    {
        int i1 = 0;
        int i2 = size - 1;
        while (true)
        {
            while ((data[i1] != null) && (i1 <= i2)) i1++;//jedeme dopredu dokud nenarazime na prazdne misto
            while ((data[i2] == null) && (i2 > i1)) i2--;     //jedeme zpet od zadu, dokud nenarazime na neprazdne misto
            if (i1 >= i2) break;
            data[i1] = data[i2];              //do prazdneho mista dame objekt zezadu
            i1++;
            i2--;
        }
        //size = Math.min(i1, i2);
        size = i1;
    }

    public void clear()
    {
        size = 0;
    }
}
