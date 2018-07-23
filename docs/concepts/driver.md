# Driver

A [Scenario](scenario.md) is written in an abstract DSL for load testing. A Driver translates this into a concrete executable form.

## Typical Structure

1. **Translate** the Scenario definition into instructions for a load generator tool (e.g. Gatling) to execute.
2. **Invoke** the load generator, and wait for it to finish.
3. **Marshal** the load generator's raw report into a form that can be handed back to the library user.
