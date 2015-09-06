package by.dak.autocad.ocom;


import com.jacob.com.Dispatch;
import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

public class AcadHatch extends BaseAcadEntity {
	public AcadHatch(Variant createFrom, AcadApplication app) {
		super(createFrom, app);
	}

	public void AppendOuterLoop(AcadObject[] objects) {
		if ((objects[0] instanceof AcadLWPolyline)) {
			appendOuterLoop(objects[0]);
		}
	}

	void appendOuterLoop(AcadObject object) {
		Variant v = Dispatch.call(object.impl.toDispatch(), "Explode");

		Variant loop = Dispatch.call(this.impl.toDispatch(), "AppendOuterLoop", v);

		SafeArray sa = v.toSafeArray(false);
		for (int i = sa.getLBound(); i < sa.getUBound() + 1; i++)
			Dispatch.call(sa.getVariant(i).toDispatch(), "Delete");
	}

	public void setPatternScale(double scale) {
		Dispatch.put(this.impl.toDispatch(), "PatternScale", new Variant(scale));
	}
}




