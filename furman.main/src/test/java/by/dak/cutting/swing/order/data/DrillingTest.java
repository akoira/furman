package by.dak.cutting.swing.order.data;

public class DrillingTest {

    public static void main(String[] args) {

        testSetGet();
        testClone();

    }
    public static void testSetGet(){
        Drilling drilling = new Drilling();
        drilling.setNumber(1);
        drilling.setNumberForLoop(2);
        drilling.setNumberForHandle(3);
        drilling.setNote("note");
        System.out.println("number = " + drilling.getNumber());
        System.out.println("NumberForLoop = " + drilling.getNumberForLoop() );
        System.out.println("NumberForHandle = " + drilling.getNumberForHandle() );
        System.out.println("Note = " + drilling.getNote() );
    }

    public static void testClone()
    {
        Drilling drilling = new Drilling();
        drilling.setNumber(1);
        drilling.setNumberForLoop(2);
        drilling.setNumberForHandle(3);
        drilling.setNote("note");

        Drilling clone = (Drilling) drilling.clone();
        System.out.println("clone class = " + clone.getClass());
         if (drilling.getNumber().equals(clone.getNumber()))
             System.out.println("drilling.number == clone.number");
        if (drilling.getNumberForLoop().equals(clone.getNumberForLoop()))
            System.out.println("drilling.numberForLoop == clone.numberForLoop");
        if (drilling.getNumberForHandle().equals(clone.getNumberForHandle()))
            System.out.println("drilling.numberForHandle == clone.numberForHandle");
        if (drilling.getNote().equals(clone.getNote()))
            System.out.println("drilling.Note == clone.Note");
    }



}

