package com.github.themasterchef.loadtest4j.drivers.wrk2;

import com.github.themasterchef.loadtest4j.LoadTester;
import com.github.themasterchef.loadtest4j.LoadTesterTest;
import com.github.themasterchef.loadtest4j.junit.IntegrationTest;
import org.junit.experimental.categories.Category;

import java.time.Duration;

@Category(IntegrationTest.class)
public class Wrk2Test extends LoadTesterTest {
    @Override
    public LoadTester sut() {
        final String executable = "src/test/resources/bin/wrk2";
        return new Wrk2(100, Duration.ofSeconds(30), executable, 2000, 2);
    }
}
