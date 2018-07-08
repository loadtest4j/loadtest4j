# Service Level Agreement (SLA)

The performance guarantees that you provide to clients of your service.

## Measurements

An effective SLA should include one or more of the following measurements. Collaborate with your project stakeholders to choose the appropriate measurements for your service. 

### Knockout Rate

The percentage of requests that are allowed to fail (also called the 'percent KO').

### Requests Per Second

The minimum acceptable throughput for your service.

### Response Time

The maximum amount of time that requests are allowed to take.

This is measured in terms of **percentiles**. For example, if the SLA defines a 95th percentile response time of 500ms, this means 95% of requests must complete in 500ms or less, while 5% may take longer.

The percentiles measured in the SLA depend on the purpose of the service. Gil Tene provides an excellent introduction to this in his presentatation ['How not to measure latency'](https://www.infoq.com/presentations/latency-pitfalls). To illustrate the difference, here are Gil's load test archetypes:

#### Athlete

**Goal: Win some gold medals.**

Key percentiles:

- TODO add

#### Pacemaker (hard real-time)

**Goal: Keep the heart beating.**

Key percentiles:

- Max (p100)

#### Trader (soft real-time)

**Goals: Be fast enough to make some good trades. Contain risk while you do it.**

Key percentiles:

- Median (p50)
- Max (p100)

#### Interactive App (squishy real-time)

**Goal: Keep users happy (don’t make them leave).**

Key percentiles:

- p90
- p99
- Max (p100)

## Sidenote: The executable SLA

Loadtest4j makes your Service Level Agreement (SLA) **executable**.

When you write a test case with assertions on the load test `Result`, you reify a part of that SLA in an executable and verifiable form.
