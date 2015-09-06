/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.common.Common;
import by.dak.cutting.cut.guillotine.GuillotineTreeCutter;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.SegmentNode;

/**
 * @author Peca
 */
public class GTreeChromosome extends Chromosome
{

    private SegmentNode segmentNode;
    private Segment segment;
    private Element[] elements;
    private int maxLevel;

    public GTreeChromosome(Segment segment, Element[] elements, int maxLevel)
    {
        super();
        segmentNode = new SegmentNode();
        this.segment = segment;
        this.elements = elements;
        this.maxLevel = maxLevel;
    }

    /*public GTreeChromosome(Segment segment){
        this();
        segmentNode.createFromSegment(segment);
    }*/

    @Override
    public void initRandom()
    {
        GuillotineTreeCutter cutter = new GuillotineTreeCutter();
        SChromosome sch = new SChromosome(this.elements.length);
        sch.initRandom();
        int[] indexes = sch.getElements();
        //GChromosome gch = ind.getGChromosome();
        segment.clear();
        boolean success = true;
        int maxItems = Common.nextInt(4) + 1;
        for (int i = 0; i < indexes.length; i++)
        {
            boolean rotate = Common.nextBoolean() && false;
            if (!cutter.cut(segment, elements[indexes[i]], maxLevel, maxItems, rotate))
            {
                success = false;
                break;
            }
        }
        if (success)
        {
            this.segmentNode.createFromSegment(segment);
            //System.out.println(this.segmentNode.toString());
        }
        else
        {
            //System.out.println("nepovedlo se vytvorit chromozom");
            //throw new Exception("hj");
            assert false : "nepovedlo se vytvorit nahodny chromozom";
        }
    }

    @Override
    public Chromosome crossWith(Chromosome ch)
    {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.clone();
    }

    @Override
    public Chromosome clone()
    {
        //throw new UnsupportedOperationException("Not supported yet.");
        GTreeChromosome ch = new GTreeChromosome(this.segment, this.elements, this.maxLevel);
        ch.segmentNode = this.getSegmentNode().clone();
        return ch;
    }

    @Override
    public void mutate()
    {
        //throw new UnsupportedOperationException("Not supported yet.");
        SegmentNode parent1, parent2;
        SegmentNode child1, child2;
        int index1, index2;
        parent1 = this.segmentNode;
        parent2 = this.segmentNode;
        int level = Common.nextInt(this.maxLevel) + 1;
        level = this.maxLevel;
        while (true)
        {
            if (parent1.size() == 0) break;
            if (parent2.size() == 0) break;
            index1 = Common.nextInt(parent1.size());
            index2 = Common.nextInt(parent2.size());
            if (parent1.getLevel() == level)
            {
                if (parent1 == parent2) break;
                if (Common.luck(0.5f))
                {
                    //prohozeni
                    child1 = parent1.removeNode(index1);
                    child2 = parent2.removeNode(index2);
                    parent1.addNode(child2);
                    parent2.addNode(child1);
                }
                else
                {
                    //presunuti
                    child1 = parent1.removeNode(index1);
                    parent2.addNode(child1);
                }
                break;
            }
            if (Common.luck(0.1f))
            {
                //parent1.addNode(new SegmentNode());
            }
            parent1 = parent1.getNode(index1);
            parent2 = parent2.getNode(index2);
        }
    }

    @Override
    public String toString()
    {
        //throw new UnsupportedOperationException("Not supported yet.");
        return segmentNode.toString();
    }

    @Override
    public void fromString(String s)
    {
        ////throw new UnsupportedOperationException("Not supported yet.");
    }

    public SegmentNode getSegmentNode()
    {
        return segmentNode;
    }

}
