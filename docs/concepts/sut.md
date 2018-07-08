# Service Under Test

The HTTP service that your load test exercises.

The Service Under Test (SUT) is attached via its URL in the loadtest4j configuration file. This decouples your load test definition from the SUT's location. As a result you can re-target your load tests to exercise the SUT in any environment (e.g. development, staging, production) just by plugging in a different URL behind the scenes.
