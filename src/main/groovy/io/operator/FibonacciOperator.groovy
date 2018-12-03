package io.operator

import io.fabric8.kubernetes.api.model.ConfigMapBuilder
import io.radanalytics.operator.common.AbstractOperator
import io.radanalytics.operator.common.Operator
import io.operator.types.FibonacciInput
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import static acme.Fibonacci.fib

@Operator(forKind = FibonacciInput.class, prefix = "io.acme")
class ExampleOperator extends AbstractOperator<FibonacciInput> {

    private static final Logger log = LoggerFactory.getLogger(AbstractOperator.class.getName())

    ExampleOperator() {
    }

    protected void onAdd(FibonacciInput input) {
        // example logic: calculates Nth fib number, prints it to log and create the config map with the result
        log.info("new input has been created: ${input}")
        def n = input.getInput()
        def result = fib(n)
        println("the ${n}th fibonacci number is: ${result}")
        def resultCM = ConfigMapBuilder.newInstance().withNewMetadata().withName(input.getName() + "-result").endMetadata()
                .withData(Collections.singletonMap("result", String.valueOf(result)))
                .build()
        client.configMaps().createOrReplace(resultCM)
    }

    protected void onDelete(FibonacciInput input) {
        log.info("existing FibonacciInput has been deleted: ${input}")
    }

    protected void onModify(FibonacciInput input) {
        log.info("existing FibonacciInput has been modified: ${input}")
    }
}