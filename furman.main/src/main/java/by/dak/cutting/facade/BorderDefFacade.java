/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.facade;

import by.dak.persistence.entities.BorderDefEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Admin
 */
@Transactional
public interface BorderDefFacade extends BaseFacade<BorderDefEntity>
{


    public BorderDefEntity findDefaultBorderDef();
}
