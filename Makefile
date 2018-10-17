IMAGE?=io.acme/groovy-example-operator

.PHONY: build
build: package image-build

.PHONY: package
package:
	./gradlew clean shadowJar

.PHONY: image-build
image-build:
	docker build -t $(IMAGE):latest -f Dockerfile .

.PHONY: devel
devel: build
	-docker kill `docker ps -q` || true
	oc cluster up
	oc create -f manifest/operator.yaml
	until [ "true" = "`oc get pod -l app.kubernetes.io/name=groovy-example-operator -o json 2> /dev/null | grep \"\\\"ready\\\": \" | sed -e 's;.*\(true\|false\),;\1;'`" ]; do printf "."; sleep 1; done
	oc logs -f `oc get pods --no-headers -l app.kubernetes.io/name=groovy-example-operator | cut -f1 -d' '`

.PHONY: devel-kubernetes
devel-kubernetes:
	-minikube delete
	minikube start --vm-driver kvm2
	eval `minikube docker-env` && $(MAKE) build
	kubectl create -f manifest/operator.yaml
	until [ "true" = "`kubectl get pod -l app.kubernetes.io/name=groovy-example-operator -o json 2> /dev/null | grep \"\\\"ready\\\": \" | sed -e 's;.*\(true\|false\),;\1;'`" ]; do printf "."; sleep 1; done
	kubectl logs -f `kubectl get pods --no-headers -l app.kubernetes.io/name=groovy-example-operator | cut -f1 -d' '`
