# Service Level Agreement (SLA)

The performance guarantees that you provide to clients of your service.

Side note: loadtest4j makes your SLA **executable**. When you write a test case with assertions on the load test `Result`, you reify a part of that SLA in an executable and verifiable form.

## Measurements

An effective SLA should include one or more of the following measurements. Collaborate with your project stakeholders to choose the appropriate measurements for your service. 

### Knockout Rate

The percentage of requests that are allowed to fail (also called the 'percent KO').

### Requests Per Second

The minimum acceptable throughput for your service.

### Response Time

The maximum amount of time that requests are allowed to take.

This is measured in terms of **percentiles**. For example, if the SLA defines a 95th percentile response time of 500ms, this means 95% of requests must complete in 500ms or less, while 5% may take longer.

The percentiles measured in the SLA depend on the purpose of the service. There is no 'one size fits all' set of percentiles to measure. That said, most services resemble one of the following archetypes, which you can use as a starting point. For more detail, see Gil Tene's excellent presentation ['How not to measure latency'](https://www.infoq.com/presentations/latency-pitfalls).

#### Archetype: Athlete

*Goal: Win some gold medals.*

The Athlete needs to be faster than everyone else in N% of races. Where N = desired percentage of races to win.

Example: There are 5 races. The Athlete wants to win 3 gold medals. So they must be faster than all their competitors in 60% of races. N = 60.

Key percentiles:

- pN

#### Archetype: Pacemaker (hard real-time)

*Goal: Keep the heart beating.*

The worst case is all that matters.

Key percentiles:

- Max (p100)

#### Archetype: Trader (soft real-time)

*Goals: Be fast enough to make some good trades. Contain risk while you do it.*

The Trader must 'typically' react quickly to open a position. But their 'max' must be reasonable too, to avoid getting stuck with a bad open position.

Key percentiles:

- Median (p50)
- Max (p100)

#### Archetype: Interactive App (squishy real-time)

*Goal: Keep users happy (don’t make them leave).*

The App must be 'typically' snappy. It's OK to take longer sometimes - but not too long, and not too often.

Key percentiles:

- p90
- p99
- Max (p100)
