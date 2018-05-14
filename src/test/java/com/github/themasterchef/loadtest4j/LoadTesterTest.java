package com.github.themasterchef.loadtest4j;

import com.xebialabs.restito.server.StubServer;
import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;

import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Condition.get;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class LoadTesterTest {

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

    protected abstract LoadTester sut(String serviceUrl);

    @Test
    public void testRun() throws Exception {
        // Given
        final LoadTester loadTester = sut(getServiceUrl());
        // And
        whenHttp(httpServer).match(get("/")).then(status(HttpStatus.OK_200));

        // When
        final Result result = loadTester.run(Request.get("/")).get();

        // Then
        assertTrue(result.getRequests() >= 0);
        assertEquals(0, result.getErrors());
    }

    @Test
    public void testRunWithNoRequests() throws Exception {
        // Given
        final LoadTester loadTester = sut(getServiceUrl());

        // Then
        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("No requests were specified for the load test.");

        // When
        loadTester.run().get();
    }

    @Test
    public void testRunWithMultipleRequests() throws Exception {
        // Given
        final LoadTester loadTester = sut(getServiceUrl());
        // And
        whenHttp(httpServer).match(get("/")).then(status(HttpStatus.OK_200));
        // And
        whenHttp(httpServer).match(get("/pets")).then(status(HttpStatus.OK_200));

        // When
        final Result result = loadTester.run(Request.get("/"), Request.get("/pets")).get();

        // Then
        assertTrue(result.getRequests() >= 0);
        assertEquals(0, result.getErrors());
    }
}
