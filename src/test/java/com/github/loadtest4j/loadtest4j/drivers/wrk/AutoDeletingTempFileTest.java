package com.github.loadtest4j.loadtest4j.drivers.wrk;

import com.github.loadtest4j.loadtest4j.junit.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Category(IntegrationTest.class)
public class AutoDeletingTempFileTest {
    @Test
    public void testCreateAndClose() {
        final AutoDeletingTempFile sut = AutoDeletingTempFile.create("foo");

        assertTrue(sut.exists());

        sut.close();

        assertFalse(sut.exists());
    }
}
