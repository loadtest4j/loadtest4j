package org.loadtest4j;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.junit.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class RequestCountTest {

    private final RequestCount requestCount = new RequestCount(1, 2);

    @Test
    public void testOk() {
        assertThat(requestCount.getOk()).isEqualTo(1);
    }

    @Test
    public void testKo() {
        assertThat(requestCount.getKo()).isEqualTo(2);
    }

    @Test
    public void testTotal() {
        assertThat(requestCount.getTotal()).isEqualTo(3);
    }
}
