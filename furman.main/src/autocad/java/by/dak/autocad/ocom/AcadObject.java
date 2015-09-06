package by.dak.autocad.ocom;


import com.jacob.com.Variant;

public class AcadObject
{
    Variant impl;
    AcadApplication application;

    AcadObject(Variant createFrom, AcadApplication app)
    {
        this.impl = createFrom;
        this.application = app;
    }
}
