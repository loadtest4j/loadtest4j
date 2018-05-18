package com.github.themasterchef.loadtest4j.drivers;

import com.github.themasterchef.loadtest4j.Requests;
import com.github.themasterchef.loadtest4j.drivers.Driver;
import com.github.themasterchef.loadtest4j.drivers.DriverResult;
import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Condition.get;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class DriverTest {

    private StubServer httpServer;

    static {
        // Silence Restito logging.
        Logger.getLogger("org.glassfish.grizzly").setLevel(Level.OFF);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void startServer() {
        httpServer = new StubServer().run();
    }

    @After
    public void stopServer() {
        httpServer.stop();
    }

    private String getServiceUrl() {
        return String.format("http://localhost:%d", httpServer.getPort());
    }

    protected abstract Driver sut(String serviceUrl);

    @Test
    public void testRun() throws Exception {
        // Given
        final Driver driver = sut(getServiceUrl());
        // And
        whenHttp(httpServer).match(get("/")).then(status(HttpStatus.OK_200));

        // When
        final DriverResult result = driver.run(Requests.get("/")).get();

        // Then
        assertTrue(result.getRequests() >= 0);
        assertEquals(0, result.getErrors());
    }

    @Test
    public void testRunWithMultipleRequests() throws Exception {
        // Given
        final Driver driver = sut(getServiceUrl());
        // And
        whenHttp(httpServer).match(get("/")).then(status(HttpStatus.OK_200));
        // And
        whenHttp(httpServer).match(get("/pets")).then(status(HttpStatus.OK_200));

        // When
        final DriverResult result = driver.run(Requests.get("/"), Requests.get("/pets")).get();

        // Then
        assertTrue(result.getRequests() >= 0);
        assertEquals(0, result.getErrors());
    }
}
