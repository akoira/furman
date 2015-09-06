package by.dak.autocad.com;

import by.dak.autocad.com.entity.AEntity;
import by.dak.autocad.com.entity.EntityName;
import com.jacob.com.Dispatch;

/**
 * User: akoyro
 * Date: 28.03.11
 * Time: 13:31
 */
public class Block extends ASet<AEntity> {
	public Block(Dispatch dispatch) {
		super(dispatch);
	}

	@Override
	protected AEntity create(Dispatch dispatch) {
		String name = Dispatch.call(dispatch, "ObjectName").getString();
		EntityName entityName = EntityName.valueOf(name);
		return entityName.createInstanceBy(dispatch);
	}
}
