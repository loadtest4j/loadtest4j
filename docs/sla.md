# The Executable SLA

Loadtest4j enables you to write an **Executable Service Level Agreement (SLA)**.

Every time that you write a test case with assertions on the load test `Result`, you take a part of the SLA defined with your stakeholders, and reify it in an executable and verifiable form.

Your SLA should include one or more of the following measurements. Collaborate with your project stakeholders to choose the right measurements for your service. 

## Knockout Rate

The percentage of requests that are allowed to fail (also called the 'percent KO').

## Requests Per Second

The minimum acceptable throughput for your service.

## Response Time

The maximum time that requests are allowed to take.

This is measured in terms of **percentiles**. For example, if the 95th percentile response time was 500ms, this means 95% of requests completed in 500ms or less, while 5% took longer.
