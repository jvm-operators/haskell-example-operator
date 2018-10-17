package io.operator;

import io.radanalytics.operator.common.AbstractOperator
import io.radanalytics.operator.common.Operator
import io.operator.types.Example
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Operator(forKind = Example.class, prefix = "io.acme")
class ExampleOperator extends AbstractOperator<Example> {

    private static final Logger log = LoggerFactory.getLogger(AbstractOperator.class.getName())

    ExampleOperator() {
    }

    protected void onAdd(Example example) {
        log.info("new example has been created: ${example}")
        println("\n\nhello from Groovy at ${new Date()}\n\n")
        // todo: implement the logic
        // KubernetesResourceList list = ???
        // client.resourceList(list).createOrReplace();
    }

    protected void onDelete(Example example) {
        log.info("existing example has been deleted: ${example}")
    }

    protected void onModify(Example example) {
        log.info("existing example has been modified: ${example}")
    }
}