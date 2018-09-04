# Scala guide

A load test style guide for Scala (using ScalaTest).

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
- One [SLI](concepts/sli.md) Requirement per test in that class

```scala
class FindPetsLoadSpec extends FlatSpec with Matchers { 
  
  private val requests = Seq(Request.get("/pet/findByStatus")
                                    .withHeader("Accept", "application/json")
                                    .withQueryParam("status", "available"))
  
  // NOTE: Lazy evaluation ensures this executes at test time - not at class initialization.
  private lazy val result = LoadTester.run(requests)        
  
  behavior of "Response Time SLO"
  
  it should "handle 90% of requests in 500ms" in {
    result.getResponseTime.getPercentile(90) should be <= Duration.ofMillis(500)
  }

  it should "handle 99% of requests in 3s" in {
    result.getResponseTime.getPercentile(99) should be <= Duration.ofSeconds(3)
  }
  
  behavior of "Survival Rate SLO"
  
  it should "successfully handle 99.99% of requests" in {
    result.getPercentOk should be >= 99.99
  }
}
```
