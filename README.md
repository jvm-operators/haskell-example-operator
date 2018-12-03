# haskell-example-operator

[![Build status](https://travis-ci.org/radanalyticsio/spark-operator.svg?branch=master)](https://travis-ci.org/radanalyticsio/spark-operator)
[![License](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

`{ConfigMap|CRD}`-based approach for managing the X in Kubernetes and OpenShift.

This operator uses [abstract-operator](https://github.com/jvm-operators/abstract-operator) library.

# Build

```bash
make build
```

# Quick Start

Run the `groovy-example-operator` deployment:
```bash
kubectl create -f manifest/operator.yaml
```

Create new ConfigMap from the prepared example:

```bash
kubectl create -f examples/cm.yaml
```


For deployment on OpenShift use the same commands as above, but with `oc` instead of `kubectl`.


This operator can also work with CRDs. Assuming the admin user is logged in, you can install the operator with:

```bash
kubectl create -f manifest/operator-crd.yaml
```

and then create the Spark clusters by creating the custom resources (CR).

```bash
kubectl create sparkcluster -f examples/cr.yaml
```

# Development

```bash
make devel
```

This will build the image and deploys the operator into OpenShift. It assumes the `oc` on `PATH`.