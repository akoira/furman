package by.dak.furman.templateimport;

import javax.swing.*;

public interface IValueConverter<V> {
	public Icon getIcon(V value);
	public String getString(V value);
}
