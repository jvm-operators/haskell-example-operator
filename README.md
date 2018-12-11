# haskell-example-operator

[![Build status](https://travis-ci.org/jvm-operators/haskell-example-operator.svg?branch=master)](https://travis-ci.org/jvm-operators/haskell-example-operator)
[![License](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

`{ConfigMap|CRD}`-based approach for managing the X in Kubernetes and OpenShift.

This operator uses [abstract-operator](https://github.com/jvm-operators/abstract-operator) library.

# Info
Very simple operator that calls Haskell code (from Groovy), prints the Nth Fibonacci number (counting from zero) to console and also creates
 a config map with the result. So it demonstrates the async workload in Kubernetes.
 
The skeleton of the application is written in Groovy, because Frege currently doesn't support extending existing Java classes.
However, the operator calls the haskell pure function in the `onAdd` method. For demonstration purposes we created very simple
haskell code that calculates the Nth fib number, it also demonstrates the lazy evaluation on infinite data structures called from
a language that doesn't have such features:

```haskell
module acme.Fibonacci where

fibs a b = a : fibs b (a + b)
fibonacci = fibs 0L 1L

fib :: Long -> Long
fib n = head $ drop (Long.int n) $ fibonacci

```

([source](./src/main/frege/Fibonacci.fr))
 
## Example

```bash
cat <<EOF | kubectl apply -f -
apiVersion: io.acme/v1
kind: fibonacciinput
metadata:
  name: fourty-second-fib
spec:
  input: 42
EOF
```

meanwhile in the K8s log:

```bash
...
2018-12-03 15:53:05 INFO  AbstractOperator:? - new input has been created: io.operator.types.FibonacciInput@59c12e28[name=fourty-second-fib,input=42,additionalProperties={}]
the 42th fibonacci number is: 267914296
...
```

Also the corresponding config map with the result (called the same way as the input CM with `-result` sufix) was created:


```bash
kubectl describe cm fourty-second-fib-result

Name:		fourty-second-fib-result
Namespace:	myproject
Labels:		<none>
Annotations:	<none>

Data
====
result:
----
267914296

```

# Build

```bash
make build
```

# Quick Start

Run the `haskell-example-operator` deployment:
```bash
kubectl apply -f manifest/operator.yaml
```

Create new ConfigMap from the prepared example:

```bash
kubectl apply -f examples/cm.yaml
```


For deployment on OpenShift use the same commands as above, but with `oc` instead of `kubectl`.


This operator can also work with CRDs. Assuming the admin user is logged in, you can install the operator with:

```bash
kubectl apply -f manifest/operator-crd.yaml
```

and then create the Spark clusters by creating the custom resources (CR).

```bash
kubectl apply -f examples/cr.yaml
```

# Development

```bash
make devel
```

This will build the image and deploys the operator into OpenShift. It assumes the `oc` on `PATH`.
