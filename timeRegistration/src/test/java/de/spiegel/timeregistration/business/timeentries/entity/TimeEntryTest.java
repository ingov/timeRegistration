package de.spiegel.timeregistration.business.timeentries.entity;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author ingoveith
 */
public class TimeEntryTest {

    @Test
    public void validTimeEntry() {
        TimeEntry valid = new TimeEntry("", "available", 11);
        assertTrue(valid.isValide());
    }

    @Test
    public void invalidTimeEntry() {
        TimeEntry invalid = new TimeEntry("", null, 11);
        assertFalse(invalid.isValide());
    }

}
