# Scala example

An example in ScalaTest.

First write a companion object which will encapsulate loadtest4j and the Java conversions:

```scala
object LoadTester {
  private val loadTester = LoadTesterFactory.getLoadTester
  
  def run(requests: Seq[Request]): Result = {
    loadTester.run(requests.asJava)
  }
}
```

Then write your load test.

We recommend the following pattern of:

- One [Scenario](concepts/scenario.md) per Spec class
- One [SLA](concepts/sla.md) assertion per test in that class

```scala
class FindPetsLoadSpec extends FlatSpec with Matchers {
  behavior of "Find pets by status"
  
  private val request = Request.get("/pet/findByStatus")
                               .withHeader("Accept", "application/json")
                               .withQueryParam("status", "available"))
  
  // NOTE: Lazy evaluation ensures this executes at test time.
  // You do not want this to block class initialization.
  private lazy val result = LoadTester.run(Seq(request))        
  
  it should "meet the Percent KO threshold" in {
    result.getPercentKo should be <= 0.01
  }
  
  it should "meet the Requests Per Second threshold" in {
    result.getRequestsPerSecond should be >= 150
  }
  
  it should "meet the p90 Response Time threshold" in {
    result.getResponseTime.getPercentile(90) should be <= Duration.ofMillis(500)
  }
}
```
