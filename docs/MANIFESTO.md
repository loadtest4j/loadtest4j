# Manifesto

**DRAFT - work in progress!**

A manifesto for load testing. Inspired by the structure of the [twelve-factor app](https://12factor.net).

## Codebase

**Store load tests in the same VCS repo as the code they test.**

To ensure that application code stays in sync with its load tests, the two are kept together.

_Antipatterns:_ Load tests in separate VCS repositories from the code they test. Load tests that are not checked into a VCS at all.

## Drivers

**Decouple the load test definition from the driver that executes it.**

The code for a load test is defined in terms of an abstract DSL for load testing. It is not defined in terms of the driver(s) that execute it. As a result, the same load test suite can be run using one driver on your laptop, and another driver on a load testing server.

_Antipattern:_ Load test libraries that couple your test definition to an execution driver.

## Language

**Write load tests in the same first-class programming language as your other tests.**

Load tests are first-class code. Write them in the same language that you use for your other tests and your application code.

_Antipatterns:_ Wrk requires load tests to be written in Lua. Jmeter requires load tests to be expressed in a proprietary config file syntax.

## Portability

**Load tests run everywhere that your other tests can run.**

The rate (requests per second) can vary between your laptop, CI, and a load testing server, but the ability to execute them must not.

_Antipatterns:_ Load tests that only work on a particular server. Load tests that only work on one person's laptop.

## Service Under Test

**Treat the Service Under Test as an attached resource.**

The load test does not know whether your Service Under Test is running locally or on a remote server. To the test, it is simply an attached resource, accessed via a URL or other locator stored in the driver config.

_Antipattern:_ Hardcoding the base URL of the Service Under Test in the load test code.

## Test Harness

**Put load tests in the same test harness as your other tests.**

Load tests are first-class tests. Your test harness and IDE should see load tests as 'just another bunch of tests', and be able to execute them just as easily.

Load tests are slow, so keep them out of the auto-test loop that runs your fast tests. Use test group annotations to achieve this.

_Antipattern:_ Load tests that live outside the test harness, which can only be run by a separate invocation.