package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.LoadTester;
import com.github.loadtest4j.loadtest4j.Request;
import com.github.loadtest4j.loadtest4j.Result;
import com.github.loadtest4j.loadtest4j.SLA;
import com.github.loadtest4j.loadtest4j.driver.Driver;
import com.github.loadtest4j.loadtest4j.driver.DriverRequest;
import com.github.loadtest4j.loadtest4j.driver.DriverReport;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class DriverAdapter implements LoadTester {
    private final Driver driver;

    DriverAdapter(Driver driver) {
        this.driver = driver;
    }

    @Override
    public Result run(List<Request> requests, Consumer<SLA> slaConsumer) {
        final List<DriverRequest> driverRequests = preprocessRequests(requests);

        final ValidatingSLA sla = new ValidatingSLA();
        slaConsumer.accept(sla);

        final DriverReport driverReport = driver.run(driverRequests);

        return sla.validate(driverReport);
    }

    private static List<DriverRequest> preprocessRequests(List<Request> requests) {
        return requests.stream()
                .map(request -> new DriverRequest(request.getBody(), request.getHeaders(), request.getMethod(), request.getPath(), request.getQueryParams()))
                .collect(Collectors.toList());
    }
}
