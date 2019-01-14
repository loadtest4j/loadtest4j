package org.loadtest4j.factory;

import org.loadtest4j.LoadTester;
import org.loadtest4j.driver.Driver;

/**
 * A mechanism to construct a LoadTester directly in code.
 */
public abstract class LoadTesterBuilder {

    protected abstract Driver buildDriver();

    public LoadTester build() {
        final Driver driver = buildDriver();
        return new DriverAdapter(new ValidatingDriver(driver));
    }
}
