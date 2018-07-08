# The Executable SLA

Loadtest4j allows you to make your Service Level Agreement (SLA) **executable**.

When you write a test case with assertions on the load test `Result`, you reify a part of that SLA in an executable and verifiable form.

An effective SLA should include one or more of the following measurements. Collaborate with your project stakeholders to choose the appropriate measurements for your service. 

## Knockout Rate

The percentage of requests that are allowed to fail (also called the 'percent KO').

## Requests Per Second

The minimum acceptable throughput for your service.

## Response Time

The maximum amount of time that requests are allowed to take.

This is measured in terms of **percentiles**. For example, if the SLA defines a 95th percentile response time of 500ms, this means 95% of requests must complete in 500ms or less, while 5% may take longer.

The percentiles measured in the SLA depend on the purpose of the service. Gil Tene provides an excellent introduction to this in his presentatation ['How not to measure latency'](https://www.infoq.com/presentations/latency-pitfalls).
