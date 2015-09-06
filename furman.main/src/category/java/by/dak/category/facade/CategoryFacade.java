package by.dak.category.facade;

import by.dak.category.Category;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CategoryFacade extends BaseFacade<Category>
{
    public List<Category> loadBy(Category parent);
}
