# The Executable SLA

Loadtest4j allows you to make your Service Level Agreement (SLA) **executable**.

When you write a test case with assertions on the load test `Result`, you reify a part of that SLA in an executable and verifiable form.

An effective SLA should include one or more of the following measurements. Collaborate with your project stakeholders to choose the appropriate measurements for your service. 

## Knockout Rate

The percentage of requests that are allowed to fail (also called the 'percent KO').

## Requests Per Second

The minimum acceptable throughput for your service.

## Response Time

The maximum time that requests are allowed to take.

This is measured in terms of **percentiles**. For example, if the 95th percentile response time was 500ms, this means 95% of requests completed in 500ms or less, while 5% took longer.
