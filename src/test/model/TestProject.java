package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {

    @Test
    public void testConstDesc() {
        Project p = new Project("Computer Sci Project");
        assertEquals(p.getDescription(), "Computer Sci Project");
    }

    @Test
    public void testConstList() {
        Project p = new Project("Computer Sci Project");
        assertEquals(p.getNumberOfTasks(), 0);
    }

    @Test
    public void testAddOneTask() {
        Project p = new Project("Computer Sci Project");
        Task t = new Task("Fold Laundry");
        p.add(t);
        assertEquals(p.getNumberOfTasks(), 1);
    }

    @Test
    public void testAddTwoTask() {
        Project p = new Project("Computer Sci Project");
        Task t = new Task("Fold Laundry");
        Task m = new Task("Fold Underwear");
        p.add(t);
        p.add(m);
        assertEquals(p.getNumberOfTasks(), 2);
    }

    @Test
    public void testAddTwoTaskSame() {
        Project p = new Project("Computer Sci Project");
        Task t = new Task("Fold Laundry");
        Task m = new Task("Fold Laundry");
        p.add(t);
        p.add(m);
        assertEquals(p.getNumberOfTasks(), 1);
    }

    @Test
    public void testAddTwoTaskSame2() {
        Project p = new Project("Computer Sci Project");
        Task t = new Task("Fold Laundry");
        Task m = new Task("Fold Laundry");
        p.add(t);
        p.add(t);
        assertEquals(p.getNumberOfTasks(), 1);
    }

    @Test
    public void testAddProject() {
        Project p = new Project("Computer Sci Project");
        Project d = new Project("Words 150 Project");
        Project g = new Project("French Project");
        p.add(d);
        p.add(g);
        assertEquals(p.getNumberOfTasks(), 2);
    }

    @Test
    public void testAddProjectToItself() {
        Project p = new Project("Computer Sci Project");
        Project d = new Project("Computer Sci Project");
        p.add(d);
        assertEquals(p.getNumberOfTasks(), 0);
    }

    @Test
    public void testAddProjectToItself2() {
        Project p = new Project("Computer Sci Project");
        Project d = new Project("Computer Sci Project");
        p.add(p);
        assertEquals(p.getNumberOfTasks(), 0);
    }

    @Test
    public void testRemoveOneTask() {
        Project p = new Project("Computer Sci Project");
        Task t = new Task("Fold Laundry");
        Task m = new Task("Fold Underwear");
        Task d = new Task("Sing");
        p.add(t);
        p.add(m);
        p.add(d);
        p.remove(m);
        assertEquals(p.getNumberOfTasks(), 2);
    }

    @Test
    public void testRemoveOneTaskDoesNotContain() {
        Project p = new Project("Computer Sci Project");
        Task t = new Task("Fold Laundry");
        Task m = new Task("Fold Underwear");
        Task d = new Task("Sing");
        p.add(t);
        p.add(d);
        p.remove(m);
        assertEquals(p.getNumberOfTasks(), 2);
    }

    @Test
    public void testRemoveOneTaskName() {
        Project p = new Project("Computer Sci Project");
        Task t = new Task("Fold Laundry");
        Task m = new Task("Fold Underwear");
        Task d = new Task("Sing");
        p.add(t);
        p.add(m);
        p.add(d);
        p.remove(m);
        assertFalse(p.contains(m));
    }

    @Test
    public void testRemoveTwoTask() {
        Project p = new Project("Computer Sci Project");
        Task t = new Task("Fold Laundry");
        Task m = new Task("Fold Underwear");
        Task d = new Task("Sing");
        p.add(t);
        p.add(m);
        p.add(d);
        p.remove(m);
        p.remove(t);
        assertEquals(p.getNumberOfTasks(), 1);
    }

    @Test
    public void testRemoveTwoTaskName() {
        Project p = new Project("Computer Sci Project");
        Task t = new Task("Fold Laundry");
        Task m = new Task("Fold Underwear");
        Task d = new Task("Sing");
        p.add(t);
        p.add(m);
        p.add(d);
        p.remove(m);
        p.remove(t);
        assertFalse(p.contains(m) && p.contains(t));
    }

    @Test
    public void testGetProgressZeroOfFour() {
        Project p = new Project("Computer Sci Project");
        Task a = new Task("Fold Laundry");
        a.setStatus(Status.IN_PROGRESS);
        Task b = new Task("Fold Underwear");
        b.setStatus(Status.IN_PROGRESS);
        Task c = new Task("Sing");
        c.setStatus(Status.UP_NEXT);
        Task d = new Task("Jam Out");
        d.setStatus(Status.IN_PROGRESS);
        p.add(a);
        p.add(b);
        p.add(c);
        p.add(d);
        assertEquals(p.getProgress(), 0);
    }


    @Test
    public void testGetProgressEmpty() {
        Project p = new Project("Computer Sci Project");
        assertEquals(p.getProgress(), 0);
    }

    @Test
    public void testGetNumTasks() {
        Project p = new Project("Computer Sci Project");
        Task a = new Task("Fold Laundry");
        a.setStatus(Status.DONE);
        Task b = new Task("Fold Underwear");
        b.setStatus(Status.DONE);
        Task c = new Task("Sing");
        c.setStatus(Status.DONE);
        Task d = new Task("Jam Out");
        d.setStatus(Status.DONE);
        p.add(a);
        p.add(b);
        p.add(c);
        p.add(d);
        assertEquals(p.getNumberOfTasks(), 4);
    }

    @Test
    public void testGetNumTasksSubProject() {
        Project p = new Project("Computer Sci Project");
        Project e = new Project("Carry Over");
        Task a = new Task("Fold Laundry");
        a.setStatus(Status.DONE);
        Task b = new Task("Fold Underwear");
        b.setStatus(Status.DONE);
        Task c = new Task("Sing");
        c.setStatus(Status.DONE);
        Task d = new Task("Jam Out");
        d.setStatus(Status.DONE);
        p.add(a);
        p.add(b);
        p.add(c);
        p.add(d);
        p.add(e);
        assertEquals(p.getNumberOfTasks(), 5);
    }

    @Test
    public void testGetNumTasksSubProject2() {
        Project p = new Project("Computer Sci Project");
        Project e = new Project("Carry Over");
        Task a = new Task("Fold Laundry");
        a.setStatus(Status.DONE);
        Task b = new Task("Fold Underwear");
        b.setStatus(Status.DONE);
        Task c = new Task("Sing");
        c.setStatus(Status.DONE);
        Task d = new Task("Jam Out");
        d.setStatus(Status.DONE);
        p.add(a);
        p.add(b);
        p.add(c);
        e.add(d);
        e.add(p);
        assertEquals(e.getNumberOfTasks(), 2);
    }

    @Test
    public void testIsCompletedAll() {
        Project p = new Project("Computer Sci Project");
        Task a = new Task("Fold Laundry");
        a.setStatus(Status.DONE);
        a.setProgress(100);
        Task b = new Task("Fold Underwear");
        b.setStatus(Status.DONE);
        b.setProgress(100);
        Task c = new Task("Sing");
        c.setStatus(Status.DONE);
        c.setProgress(100);
        Task d = new Task("Jam Out");
        d.setStatus(Status.DONE);
        d.setProgress(100);
        p.add(a);
        p.add(b);
        p.add(c);
        p.add(d);
        assertEquals(p.getNumberOfTasks(),4);
        assertTrue(p.isCompleted());
    }

    @Test
    public void testIsCompletedAllSubProject() {
        Project p = new Project("Computer Sci Project");
        Project e = new Project("French Project");
        Task a = new Task("Fold Laundry");
        a.setStatus(Status.DONE);
        a.setProgress(100);
        Task b = new Task("Fold Underwear");
        b.setStatus(Status.DONE);
        b.setProgress(100);
        Task c = new Task("Sing");
        c.setStatus(Status.DONE);
        c.setProgress(100);
        Task d = new Task("Jam Out");
        d.setStatus(Status.DONE);
        d.setProgress(100);
        p.add(a);
        p.add(b);
        p.add(c);
        e.add(d);
        e.add(p);
        assertTrue(e.isCompleted());
    }

    @Test
    public void testIsNotCompletedAllSubProject() {
        Project p = new Project("Computer Sci Project");
        Project e = new Project("French Project");
        Task a = new Task("Fold Laundry");
        a.setStatus(Status.DONE);
        a.setProgress(100);
        Task b = new Task("Fold Underwear");
        b.setStatus(Status.DONE);
        b.setProgress(100);
        Task c = new Task("Sing");
        c.setStatus(Status.DONE);
        c.setProgress(100);
        Task d = new Task("Jam Out");
        d.setStatus(Status.DONE);
        d.setProgress(50);
        p.add(a);
        p.add(b);
        p.add(c);
        e.add(d);
        e.add(p);
        assertFalse(e.isCompleted());
    }

    @Test
    public void testIsNotCompletedAllSubProject2() {
        Project p = new Project("Computer Sci Project");
        Project e = new Project("French Project");
        Task a = new Task("Fold Laundry");
        a.setStatus(Status.DONE);
        a.setProgress(100);
        Task b = new Task("Fold Underwear");
        b.setStatus(Status.DONE);
        b.setProgress(100);
        Task c = new Task("Sing");
        c.setStatus(Status.DONE);
        c.setProgress(25);
        Task d = new Task("Jam Out");
        d.setStatus(Status.DONE);
        d.setProgress(100);
        p.add(a);
        p.add(b);
        p.add(c);
        e.add(d);
        e.add(p);
        assertFalse(e.isCompleted());
    }

    @Test
    public void testGetProgressExample1a() {
        Project p = new Project("Computer Sci Project");
        Task a = new Task("Fold Laundry");
        Task b = new Task("Fold Underwear");
        Task c = new Task("Sing");
        p.add(a);
        p.add(b);
        p.add(c);
        assertEquals(p.getProgress(),0);
    }

    @Test
    public void testGetProgressExample1b() {
        Project p = new Project("Computer Sci Project");
        Task a = new Task("Fold Laundry");
        a.setProgress(100);
        Task b = new Task("Fold Underwear");
        Task c = new Task("Sing");
        p.add(a);
        p.add(b);
        p.add(c);
        assertEquals(p.getProgress(),33);
    }

    @Test
    public void testGetProgressExample1c() {
        Project p = new Project("Computer Sci Project");
        Task a = new Task("Fold Laundry");
        a.setProgress(100);
        Task b = new Task("Fold Underwear");
        b.setProgress(50);
        Task c = new Task("Sing");
        c.setProgress(25);
        p.add(a);
        p.add(b);
        p.add(c);
        assertEquals(p.getProgress(),58);
    }

    @Test
    public void testGetProgressExample1d() {
        Project p = new Project("Computer Sci Project");
        Project d = new Project("French Project");
        Task a = new Task("Fold Laundry");
        a.setProgress(100);
        Task b = new Task("Fold Underwear");
        b.setProgress(50);
        Task c = new Task("Sing");
        c.setProgress(25);
        Task e = new Task("Cry");
        p.add(a);
        p.add(b);
        p.add(c);
        d.add(e);
        d.add(p);
        assertEquals(d.getProgress(),29);
    }

    @Test
    public void testIsCompleteNotAll() {
        Project p = new Project("Computer Sci Project");
        Task a = new Task("Fold Laundry");
        a.setStatus(Status.DONE);
        Task b = new Task("Fold Underwear");
        b.setStatus(Status.IN_PROGRESS);
        Task c = new Task("Sing");
        c.setStatus(Status.TODO);
        Task d = new Task("Jam Out");
        d.setStatus(Status.DONE);
        p.add(a);
        p.add(b);
        p.add(c);
        p.add(d);
        assertFalse(p.isCompleted());
    }

    @Test
    public void testIsCompleteEmpty() {
        Project p = new Project("Computer Sci Project");
        assertFalse(p.isCompleted());
    }

    @Test
    public void testContainsTrue() {
        Project p = new Project("Computer Sci Project");
        Task a = new Task("Fold Laundry");
        Task d = new Task("Jam Out");
        p.add(a);
        p.add(d);
        assertTrue(p.contains(a));
    }

    @Test
    public void testContainsFalse() {
        Project p = new Project("Computer Sci Project");
        Task a = new Task("Fold Laundry");
        Task d = new Task("Jam Out");
        p.add(a);
        assertFalse(p.contains(d));
    }

    @Test
    public void testGetEta() {
        Project p = new Project("Computer Sci Project");
        Project d = new Project("French Project");
        Task a = new Task("Fold Laundry");
        a.setEstimatedTimeToComplete(1);
        Task b = new Task("Jam Out");
        b.setEstimatedTimeToComplete(1);
        Task c = new Task("Sleep");
        c.setEstimatedTimeToComplete(2);
        p.add(d);
        p.add(a);
        p.add(b);
        d.add(c);
        assertEquals(p.getEstimatedTimeToComplete(),4);
    }

    @Test
    public void testGetEtaExample2a() {
        Project p = new Project("Computer Sci Project");
        Task a = new Task("Fold Laundry");
        Task b = new Task("Jam Out");
        Task c = new Task("Sleep");
        p.add(a);
        p.add(b);
        p.add(c);
        assertEquals(p.getEstimatedTimeToComplete(),0);
    }

    @Test
    public void testGetEtaExample2b() {
        Project p = new Project("Computer Sci Project");
        Task a = new Task("Fold Laundry");
        a.setEstimatedTimeToComplete(8);
        Task b = new Task("Jam Out");
        Task c = new Task("Sleep");
        p.add(a);
        p.add(b);
        p.add(c);
        assertEquals(p.getEstimatedTimeToComplete(),8);
    }

    @Test
    public void testGetEtaExample2c() {
        Project p = new Project("Computer Sci Project");
        Task a = new Task("Fold Laundry");
        a.setEstimatedTimeToComplete(8);
        Task b = new Task("Jam Out");
        b.setEstimatedTimeToComplete(2);
        Task c = new Task("Sleep");
        c.setEstimatedTimeToComplete(10);
        p.add(a);
        p.add(b);
        p.add(c);
        assertEquals(p.getEstimatedTimeToComplete(),20);
    }

    @Test
    public void testGetEtaExample2d() {
        Project p = new Project("Computer Sci Project");
        Project d = new Project("French Project");
        Task a = new Task("Fold Laundry");
        a.setEstimatedTimeToComplete(8);
        Task b = new Task("Jam Out");
        b.setEstimatedTimeToComplete(2);
        Task c = new Task("Sleep");
        c.setEstimatedTimeToComplete(10);
        Task e = new Task("Feast");
        e.setEstimatedTimeToComplete(4);
        p.add(a);
        p.add(b);
        p.add(c);
        d.add(e);
        d.add(p);
        assertEquals(d.getEstimatedTimeToComplete(),24);
    }

    @Test
    public void testEqualsSameProject() {
        Project p = new Project("Computer Sci Project");
        Project d = new Project("Computer Sci Project");
        assertEquals(p,d);
        assertTrue(p.equals(d));
    }

    @Test
    public void testEqualsSameProject2() {
        Project p = new Project("Computer Sci Project");
        Project d = new Project("Computer Sci Project");
        assertEquals(p,p);
        assertTrue(p.equals(p));
    }


    //Testing Exceptions!!!
    @Test
    public void testAddThrowNullFalse() {
        //expecting task to be non null, and no exceptions thrown.
        Project p = new Project("Computer Sci Project");
        Task t = new Task("Jump around");
        try {
            p.add(t);
        } catch (NullArgumentException e) {
            fail("Thrown null exception when shouldn't have");
        }
        assertTrue(p.contains(t));
    }

    @Test
    public void testAddThrowNullTrue() {
        //expecting task to be null, and an exception thrown.
        Project p = new Project("Computer Sci Project");

        try {
            Task t = null;
            p.add(t);
        } catch (NullArgumentException e) {
            //don't have to do anything here!
        }
    }

    @Test
    public void testRemoveThrowNullFalse() {
        //expecting task to be non null, and no exceptions thrown.
        Project p = new Project("Computer Sci Project");
        Task t = new Task("Jump around");
        p.add(t);
        try {
            p.remove(t);
        } catch (NullArgumentException e) {
            fail("Thrown null exception when shouldn't have");
        }
        assertFalse(p.contains(t));
    }

    @Test
    public void testRemoveThrowNullTrue() {
        //expecting task to be null, and an exception thrown.
        Project p = new Project("Computer Sci Project");

        try {
            Task t = null;
            p.remove(t);
        } catch (NullArgumentException e) {
            //don't have to do anything here!
        }
    }

    @Test
    public void testContainsThrowNullFalse() {
        //expecting task to be non null, and no exceptions thrown.
        Project p = new Project("Computer Sci Project");
        Task t = new Task("Jump around");
        p.add(t);
        try {
            p.contains(t);
        } catch (NullArgumentException e) {
            fail("Thrown null exception when shouldn't have");
        }
        assertTrue(p.contains(t));
    }

    @Test
    public void testContainsThrowNullTrue() {
        //expecting task to be null, and an exception thrown.
        Project p = new Project("Computer Sci Project");

        try {
            Task t = null;
            p.add(t);
            p.contains(t);
        } catch (NullArgumentException e) {
            //don't have to do anything here!
        }
    }

    @Test
    public void testProjectStringExceptionNotThrown() {
        //expecting description to be non null/empty, and no exception thrown.
        try {
            Project p = new Project("Computer Sci Project");
        } catch (EmptyStringException e) {
            fail("Got a empty string exception when shouldn't have");
        }
    }

    @Test
    public void testProjectStringExceptionThrownNull() {
        //expecting description to be null, and an exception thrown.
        try {
            String dud = null;
            Project p = new Project(dud);
        } catch (EmptyStringException e) {
            //do nothing
        }
    }

    @Test
    public void testProjectStringExceptionThrownEmpty() {
        //expecting description to be empty, and an exception thrown.
        try {
            String dud = "";
            Project p = new Project(dud);
        } catch (EmptyStringException e) {
            //do nothing
        }
    }

    @Test
    public void testGetTasksExceptionThrownEmpty() {
        Project p = new Project("Test");
        try {
           p.getTasks();
        } catch (UnsupportedOperationException e) {
            //do nothing
        }
    }

    // Test Iterator
    @Test
    public void testIteratorOrder() {
        Project p = new Project("Computer Sci Project");

        Task t2 = new Task("test 2");
        Priority p2 = new Priority(2);
        t2.setPriority(p2);
        p.add(t2);

        Task t1 = new Task("test 1");
        Priority p1 = new Priority(1);
        t1.setPriority(p1);
        p.add(t1);

        Task t4 = new Task("test 4");
        Priority p4 = new Priority(4);
        t4.setPriority(p4);
        p.add(t4);

        Project t3 = new Project("test 3");
        Priority p3 = new Priority(3);
        t3.setPriority(p3);
        p.add(t3);

        Task t5 = new Task("test 5");
        Priority p5 = new Priority(2);
        t5.setPriority(p5);
        p.add(t5);

        for(Todo t: p) {
            System.out.println(t.getDescription());
        }
    }

}