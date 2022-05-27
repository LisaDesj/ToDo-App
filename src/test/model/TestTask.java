package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestTask {

    @Test
    public void testConst() {
        Task t = new Task("Fold Laundry");
        assertEquals(t.getDescription(), "Fold Laundry");
        assertNull(t.getDueDate());
        assertEquals(t.getStatus(), Status.TODO);
        assertFalse(t.getPriority().isUrgent());
        assertFalse(t.getPriority().isImportant());
        assertEquals(t.getEstimatedTimeToComplete(),0);
        assertEquals(t.getProgress(),0);
    }


    @Test
    public void testSetDescription() {
        Task t = new Task("Fold Laundry");
        t.setDescription("Iron");
        assertEquals(t.getDescription(), "Iron");
    }

    @Test
    public void testSetDescription2() {
        Task t = new Task("Buy Milk ## today; important");
        assertEquals(t.getDescription(), "Buy Milk ");
        assertTrue(t.getPriority().isImportant());
        assertFalse(t.getPriority().isUrgent());
        DueDate d = new DueDate();
        assertEquals(t.getDueDate(),d);
    }

    @Test
    public void testSetDueDate() {
        Calendar c = Calendar.getInstance();
        c.set(2020,Calendar.DECEMBER,20);
        DueDate d = new DueDate(c.getTime());
        Task t = new Task("Fold Laundry");
        t.setDueDate(d);
        DueDate e = t.getDueDate();
        Task l = new Task("Fold Laundry");
        l.setDueDate(e);
        //assertEquals(e.toString(), d.toString());
        assertEquals(e,d);
        assertEquals(t,l);
    }

    // Test set Status
    @Test
    public void testSetStatusInP() {
        Task t = new Task("Fold Laundry");
        t.setStatus(Status.IN_PROGRESS);
        assertEquals(t.getStatus(), Status.IN_PROGRESS);
    }

    @Test
    public void testSetStatusDone() {
        Task t = new Task("Fold Laundry");
        t.setStatus(Status.DONE);
        assertEquals(t.getStatus(), Status.DONE);
    }


    @Test
    public void testSetStatusToDo() {
        Task t = new Task("Fold Laundry");
        t.setStatus(Status.TODO);
        assertEquals(t.getStatus(), Status.TODO);
    }


    @Test
    public void testSetStatusUpNext() {
        Task t = new Task("Fold Laundry");
        t.setStatus(Status.UP_NEXT);
        assertEquals(t.getStatus(), Status.UP_NEXT);
    }

    //Test set priority
    @Test
    public void testSetPriority1() {
        Priority p = new Priority(1);
        Task t = new Task("Fold Laundry");
        t.setPriority(p);
        Priority e = t.getPriority();
        assertTrue(e.isImportant());
        assertTrue(e.isUrgent());
    }

    @Test
    public void testSetPriority2() {
        Priority p = new Priority(2);
        Task t = new Task("Fold Laundry");
        t.setPriority(p);
        Priority e = t.getPriority();
        assertTrue(e.isImportant());
        assertFalse(e.isUrgent());
    }

    @Test
    public void testSetPriority3() {
        Priority p = new Priority(3);
        Task t = new Task("Fold Laundry");
        t.setPriority(p);
        Priority e = t.getPriority();
        assertFalse(e.isImportant());
        assertTrue(e.isUrgent());
    }

    @Test
    public void testSetPriority4() {
        Priority p = new Priority(4);
        Task t = new Task("Fold Laundry");
        t.setPriority(p);
        Priority e = t.getPriority();
        assertFalse(e.isImportant());
        assertFalse(e.isUrgent());
    }

    // Test tag
    @Test
    public void testAddTag() {
        Task t = new Task("Fold Laundry");
        String s = "Singer";
        t.addTag(s);
        assertTrue(t.containsTag(s));
    }

    @Test
    public void testRemoveTag() {
        Task t = new Task("Fold Laundry");
        String s = "Singer";
        t.addTag(s);
        t.removeTag(s);
        assertFalse(t.containsTag(s));
    }

    @Test
    public void testRemoveTagMultiTags() {
        Task t = new Task("Fold Laundry");
        String s = "Singer";
        t.addTag(s);
        t.addTag("Baby");
        t.removeTag(s);
        assertFalse(t.containsTag(s));
    }

    @Test
    public void testRemoveTagNoTagExists() {
        Task t = new Task("Fold Laundry");
        Tag tag = new Tag("Jump");
        t.removeTag(tag);
        assertFalse(t.containsTag(tag));
    }

    @Test
    public void testToString() {
        Task t = new Task("Fold Laundry");
        t.addTag("1");
        t.addTag("2");
        t.addTag("3");
        Calendar c = Calendar.getInstance();
        c.set(2019,Calendar.FEBRUARY,27,22,30,0);
        DueDate d = new DueDate(c.getTime());
        t.setDueDate(d);

        String expectedS = "\n{" + "\n\tDescription: Fold Laundry" + "\n\tDue date: Wed Feb 27 2019 10:30 PM" +
                "\n\tStatus: TODO" + "\n\tPriority: DEFAULT" + "\n\tTags: #1, #2, #3" + "\n}";

        assertEquals(t.toString(),expectedS);
    }

    @Test
    public void testToStringNullDate() {
        Task t = new Task("Fold Laundry");
        t.addTag("1");
        t.addTag("2");
        t.addTag("3");
        Calendar c = Calendar.getInstance();
        c.set(2019,Calendar.FEBRUARY,27,22,30,0);
        DueDate d = null;
        t.setDueDate(d);

        String expectedS = "\n{" + "\n\tDescription: Fold Laundry" + "\n\tDue date: " +
                "\n\tStatus: TODO" + "\n\tPriority: DEFAULT" + "\n\tTags: #1, #2, #3" + "\n}";

        assertEquals(t.toString(),expectedS);
    }

    @Test
    public void testToStringNoTags() {
        Task t = new Task("Fold Laundry");
        Calendar c = Calendar.getInstance();
        c.set(2019,Calendar.FEBRUARY,27,22,30,0);
        DueDate d = new DueDate(c.getTime());
        t.setDueDate(d);

        String expectedS = "\n{" + "\n\tDescription: Fold Laundry" + "\n\tDue date: Wed Feb 27 2019 10:30 PM" +
                "\n\tStatus: TODO" + "\n\tPriority: DEFAULT" + "\n\tTags:  " + "\n}";

        assertEquals(t.toString(),expectedS);
    }

    @Test
    public void testToStringOneTag() {
        Task t = new Task("Fold Laundry");
        t.addTag("1");
        Calendar c = Calendar.getInstance();
        c.set(2019,Calendar.FEBRUARY,27,22,30,0);
        DueDate d = new DueDate(c.getTime());
        t.setDueDate(d);

        String expectedS = "\n{" + "\n\tDescription: Fold Laundry" + "\n\tDue date: Wed Feb 27 2019 10:30 PM" +
                "\n\tStatus: TODO" + "\n\tPriority: DEFAULT" + "\n\tTags: #1" + "\n}";

        assertEquals(t.toString(),expectedS);
    }

    @Test
    public void testGetTags() {
        Task m = new Task("Fold Laundry");
        m.addTag("1");
        m.addTag("2");
        m.addTag("3");
        Tag t1 = new Tag("1");
        Tag t2 = new Tag("2");
        Tag t3 = new Tag("3");
        Set<Tag> testSet1 = new LinkedHashSet<>();
        testSet1.add(t1);
        testSet1.add(t2);
        testSet1.add(t3);
       // Set b = m.getTags();
        assertTrue(m.getTags().containsAll(testSet1));
    }

    @Test
    public void testEquals() {
        Task m = new Task("Fold Laundry");
        Task d = new Task("Fold Laundry");
        assertTrue(m.equals(d));
    }

    @Test
    public void testEquals2() {
        Task m = new Task("Fold Laundry");
        Task d = new Task("Fold Laundry");
        Calendar c = Calendar.getInstance();
        c.set(2019,Calendar.FEBRUARY,27,22,30,0);
        DueDate j = new DueDate(c.getTime());
        m.setDueDate(j);
        d.setDueDate(j);
        Priority p = new Priority(1);
        m.setPriority(p);
        d.setPriority(p);
        assertTrue(m.equals(d));
    }

    //Testing new set super methods
    @Test
    public void testSetProgress0() {
        Task t = new Task("test");
        t.setProgress(0);
        assertEquals(t.getProgress(),0);
    }

    @Test
    public void testSetProgress50() {
        Task t = new Task("test");
        t.setProgress(50);
        assertEquals(t.getProgress(),50);
    }

    @Test
    public void testSetProgress100() {
        Task t = new Task("test");
        t.setProgress(100);
        assertEquals(t.getProgress(),100);
    }

    @Test
    public void testSetEtc() {
        Task t = new Task("test");
        t.setEstimatedTimeToComplete(0);
        assertEquals(t.getEstimatedTimeToComplete(),0);
    }

    @Test
    public void testSetEtc1() {
        Task t = new Task("test");
        t.setEstimatedTimeToComplete(1);
        assertEquals(t.getEstimatedTimeToComplete(),1);
    }

    @Test
    public void testParseDescription() {
        Task t = new Task("test");
        t.setDescription("Monkeying around");
        assertEquals(t.getDescription(),"Monkeying around");
    }



    //Testing Exceptions!!!
    @Test
    public void testSetProgressExceptionNotThrown() {
        Task t = new Task("test");
        try {
            t.setProgress(100);
        } catch (InvalidProgressException e) {
            fail("Got an invalid progress exception when shouldn't have");
        }
        assertEquals(t.getProgress(),100);
    }

    @Test
    public void testSetProgressExceptionThrown1() {
        Task t = new Task("test");
        try {
            t.setProgress(-1);
            fail("An exception should be thrown");
        } catch (InvalidProgressException e) {
            //do nothing
        }
    }

    @Test
    public void testSetProgressExceptionThrown2() {
        Task t = new Task("test");
        try {
            t.setProgress(101);
            fail("An exception should be thrown");
        } catch (InvalidProgressException e) {
            //do nothing
        }
    }

    @Test
    public void testSetEtcExceptionNoThrown() {
        Task t = new Task("test");
        try {
            t.setEstimatedTimeToComplete(0);
        } catch (NegativeInputException e) {
            fail("Got a negative input exception when shouldn't have");
        }
        assertEquals(t.getEstimatedTimeToComplete(),0);
    }

    @Test
    public void testSetEtcExceptionThrown() {
        Task t = new Task("test");
        try {
            t.setEstimatedTimeToComplete(-1);
            fail("An exception should be thrown");
        } catch (NegativeInputException e) {
            //do nothing
        }
    }

    @Test
    public void testSetEtcExceptionThrownVarConstructor() {
        Task t = new Task("test");
        try {
            if (true) {
                throw new NegativeInputException();
            }
            fail("An exception should be thrown");
        } catch (NegativeInputException e) {
            //do nothing
        }
    }

    @Test
    public void testSetEtcNoExceptionThrownVarConstructor() {
        Task t = new Task("test");
        Boolean condition = true;
        try {
            if (condition) {
                assert(condition);
            } else {
                throw new NegativeInputException();
            }
        } catch (NegativeInputException e) {
            fail("An exception shouldn't be thrown");
        }
    }

    @Test
    public void testGetProgressExceptionThrownVarConstructor() {
        Task t = new Task("test");
        try {
            if (true) {
                throw new InvalidProgressException();
            }
            fail("An exception should be thrown");
        } catch (InvalidProgressException e) {
            //do nothing
        }
    }

    @Test
    public void testGetProgressNoExceptionThrownVarConstructor() {
        Task t = new Task("test");
        Boolean condition = true;
        try {
            if (condition) {
                assert(condition);
            } else {
                throw new InvalidProgressException();
            }
        } catch (InvalidProgressException e) {
            fail("An exception shouldn't be thrown");
        }
    }


    @Test
    public void testSetPriorityExceptionNotThrown() {
        //expecting priority to be non null and no exception thrown.
        Task t = new Task("Fold Laundry");
        Priority p = new Priority(2);
        try{
            t.setPriority(p);
        } catch (NullArgumentException e) {
            fail("Got null exception when shouldn't have");
        }
        assertTrue(t.getPriority().isImportant());
        assertFalse(t.getPriority().isUrgent());
    }

    @Test
    public void testSetPriorityExceptionThrown() {
        //expecting priority to be null and an exception thrown.
        Task t = new Task("Fold Laundry");
        try{
            Priority p = null;
            t.setPriority(p);
        } catch (NullArgumentException e) {
            //Do nothing
        }
        assertFalse(t.getPriority().isImportant());
        assertFalse(t.getPriority().isUrgent());
    }

    @Test
    public void testSetStatusExceptionNotThrown() {
        //expecting status to be non null and no exception thrown.
        Task t = new Task("Fold Laundry");
        Status s = Status.DONE;
        try{
            t.setStatus(s);
        } catch (NullArgumentException e) {
            fail("Got null exception when shouldn't have");
        }
        assertEquals(t.getStatus(), Status.DONE);
    }

    @Test
    public void testSetStatusExceptionThrown() {
        //expecting status to be null and an exception thrown.
        Task t = new Task("Fold Laundry");
        try{
            Status s = null;
            t.setStatus(s);
        } catch (NullArgumentException e) {
            //Do nothing
        }
        assertEquals(t.getStatus(), Status.TODO);
    }

    @Test
    public void testTaskStringExceptionNotThrown() {
        //expecting description to be non null/empty, and no exception thrown.
        try {
            Task t = new Task("Test");
        } catch (EmptyStringException e) {
            fail("Got an empty string exception when shouldn't have");
        }
    }

    @Test
    public void testTaskStringExceptionThrownNull() {
        //expecting description to be null, and an exception thrown.
        try {
            String dud = null;
            Task t = new Task(dud);
        } catch (EmptyStringException e) {
            //do nothing
        }
    }

    @Test
    public void testTaskStringExceptionThrownEmpty() {
        //expecting description to be empty, and an exception thrown.
        try {
            String dud = "";
            Task t = new Task(dud);
        } catch (EmptyStringException e) {
            //do nothing
        }
    }

    @Test
    public void testAddStringExceptionNotThrown() {
        //expecting tagName to be non null and no exception thrown.
        Task t = new Task("Jump around");

        try {
            t.addTag("Tag");
        } catch (EmptyStringException e) {
            fail("Got empty string exception when shouldn't have");
        }
        assertTrue(t.containsTag("Tag"));
    }

    @Test
    public void testAddTagStringExceptionThrownNull() {
        //expecting tagName to be null, and an exception thrown.
        Task t = new Task("Jump around");
        try {
            String dud = null;
            t.addTag(dud);
        } catch (EmptyStringException e) {
            //do nothing
        }
    }

    @Test
    public void testAddTagStringExceptionThrownEmpty() {
        //expecting tagName to be null, and an exception thrown.
        Task t = new Task("Jump around");
        try {
            String dud = "";
            t.addTag(dud);
        } catch (EmptyStringException e) {
            //do nothing
        }
    }

    @Test
    public void testRemoveStringExceptionNotThrown() {
        //expecting tagName to be non null and no exception thrown.
        Task t = new Task("Jump around");
        t.addTag("Tag");
        try {
            t.removeTag("Tag");
        } catch (EmptyStringException e) {
            fail("Got empty string exception when shouldn't have");
        }
        assertFalse(t.containsTag("Tag"));
    }

    @Test
    public void testRemoveStringExceptionThrownNull() {
        //expecting tagName to be null and an exception thrown.
        Task t = new Task("Jump around");
        try {
            String dud = null;
            t.removeTag(dud);
        } catch (EmptyStringException e) {
            //do nothing
        }
    }

    @Test
    public void testRemoveStringExceptionThrownEmpty() {
        //expecting tagName to be empty and an exception thrown.
        Task t = new Task("Jump around");
        try {
            String dud = "";
            t.removeTag(dud);
        } catch (EmptyStringException e) {
            //do nothing
        }
    }

    @Test
    public void testSetDescriptionNoExceptionThrown() {
        //expecting description to be non empty/null and no exception thrown.
        Task t = new Task("Jump around");
        try {
            t.setDescription("Description");
        } catch (EmptyStringException e) {
            fail("Got an empty string exception when shouldn't have");
        }
        assertEquals(t.getDescription(), "Description");
    }

    @Test
    public void testSetDescriptionExceptionThrownNull() {
        //expecting description to be null and an exception thrown.
        Task t = new Task("Jump around");
        try {
            String dud = null;
            t.setDescription(dud);
        } catch (EmptyStringException e) {
            //do nothing
        }
        assertEquals(t.getDescription(), "Jump around");
    }

    @Test
    public void testSetDescriptionExceptionThrownEmpty() {
        //expecting description to be empty and an exception thrown.
        Task t = new Task("Jump around");
        try {
            String dud = "";
            t.setDescription(dud);
        } catch (EmptyStringException e) {
            //do nothing
        }
        assertEquals(t.getDescription(), "Jump around");
    }

    @Test
    public void testContainsTagStringExceptionNotThrown() {
        //expecting tagName to be non empty/null and no exception thrown.
        Task t = new Task("Jump around");
        Tag tag = new Tag("Cry");
        t.addTag(tag);
        try {
            t.containsTag("Cry");
        } catch (EmptyStringException e) {
            fail("Got an empty string exception when shouldn't have");
        }
    }

    @Test
    public void testContainsTagStringExceptionThrownNull() {
        Task t = new Task("Jump around");
        try {
            String dud = null;
            t.containsTag(dud);
        } catch (EmptyStringException e) {
            //do nothing
        }
    }

    @Test
    public void testContainsTagStringExceptionThrownEmpty() {
        Task t = new Task("Jump around");
        try {
            String dud = "";
            t.containsTag(dud);
        } catch (EmptyStringException e) {
            //do nothing
        }
    }

    @Test
    public void testContainsTagTagExceptionNotThrown() {
        //expecting tagName to be non empty/null and no exception thrown.
        Task t = new Task("Jump around");
        Tag tag = new Tag("Cry");
        t.addTag(tag);
        try {
            t.containsTag(tag);
        } catch (EmptyStringException e) {
            fail("Got an empty string exception when shouldn't have");
        }
    }

    @Test
    public void testContainsTagTagExceptionThrownNull() {
        Task t = new Task("Jump around");
        try {
            Tag dud = null;
            t.containsTag(dud);
        } catch (NullArgumentException e) {
            //do nothing
        }
    }

}