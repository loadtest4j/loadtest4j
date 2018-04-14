package com.github.themasterchef.loadtest4j.drivers.wrk;

import com.github.themasterchef.loadtest4j.LoadTester;
import com.github.themasterchef.loadtest4j.LoadTesterTest;
import com.github.themasterchef.loadtest4j.junit.UnitTest;
import org.junit.experimental.categories.Category;

import java.time.Duration;

@Category(UnitTest.class)
public class WrkTest extends LoadTesterTest {
    @Override
    public LoadTester sut() {
        final String executable = "src/test/resources/bin/wrk";
        return new Wrk(1, Duration.ofSeconds(1), executable, 1);
    }
}
