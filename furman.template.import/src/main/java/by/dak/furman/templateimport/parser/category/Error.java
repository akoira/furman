package by.dak.furman.templateimport.parser.category;

public enum Error {
	dirEmpty,
	noDocFiles;

	public String getKey()
	{
		return "error." + name();
	}
}
