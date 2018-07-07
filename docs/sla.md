# SLA design

_"What should I test in a load test?"_

A test case with assertions on the load test `Result` is an executable Service Level Agreement (SLA). It is therefore important to craft your SLA around the measurements that are relevant to your stakeholders.

Your SLA should include one or more of the following key measurements.

## Knockout Rate

The percentage of requests that are allowed to fail (also called the 'percent KO').

## Requests Per Second

The minimum acceptable throughput for your service.

## Response Time

The maximum time that requests are allowed to take.

This is measured in terms of **percentiles**. For example, if the 95th percentile response time was 500ms, this means 95% of requests completed in 500ms or less, while 5% took longer.
