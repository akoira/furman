/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.swing.store.modules;

import by.dak.cutting.swing.store.tabs.BoardTab;
import by.dak.persistence.entities.Board;

/**
 * @author savage
 */
public class BoardPanel extends AEntityEditorPanel<Board>
{
    private BoardTab fdTab;

    @Override
    protected void addTabs()
    {
        fdTab = new BoardTab();
        setTab(fdTab);
        addTab(getResourceMap().getString("tab.board.title"), fdTab);
        //addServiceTabs();
    }
}
