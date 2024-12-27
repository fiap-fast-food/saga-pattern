@echo off

REM Diretório raiz onde os manifests estão localizados
cd /d "C:\caminho\para\pasta\kubernetes"

REM Aplicar Namespaces
echo Aplicando Namespaces...
kubectl apply -f namespaces\orchestrator-saga-namespace.yaml

REM Aplicar ConfigMaps
echo Aplicando ConfigMaps...
kubectl apply -f configs-maps\inventory-config-map.yaml
kubectl apply -f configs-maps\inventory-service-config-map.yaml
kubectl apply -f configs-maps\kafka-config-map.yaml
kubectl apply -f configs-maps\kitchen-db-config-map.yaml
kubectl apply -f configs-maps\kitchen-service-config-map.yaml
kubectl apply -f configs-maps\orchestrator-config-map.yaml
kubectl apply -f configs-maps\order-db-config-map.yaml
kubectl apply -f configs-maps\order-service-config-map.yaml
kubectl apply -f configs-maps\payment-db-config-map.yaml
kubectl apply -f configs-maps\payment-service-config-map.yaml
kubectl apply -f configs-maps\product-db-config-map.yaml
kubectl apply -f configs-maps\product-validation-service-config-map.yaml
kubectl apply -f configs-maps\redpanda-console-config-map.yaml

REM Aplicar Persistent Volume Claims (PVCs)
echo Aplicando PVCs...
kubectl apply -f pvc\kafkapvc.yaml
kubectl apply -f pvc\mongopvc.yaml
kubectl apply -f pvc\PersistentVolumeClaim.yaml
kubectl apply -f pvc\postgrespvc.yaml

REM Aplicar Services
echo Aplicando Services...
kubectl apply -f services\inventory-service.yaml
kubectl apply -f services\inventory-db-service.yaml
kubectl apply -f services\kafka-service.yaml
kubectl apply -f services\kitchen-db-service.yaml
kubectl apply -f services\kitchen-service.yaml
kubectl apply -f services\orchestrator-service.yaml
kubectl apply -f services\order-db-service.yaml
kubectl apply -f services\order-service.yaml
kubectl apply -f services\payment-db-service.yaml
kubectl apply -f services\payment-service.yaml
kubectl apply -f services\product-db-service.yaml
kubectl apply -f services\product-validation-service.yaml
kubectl apply -f services\redpanda-console-load-balancer-service.yaml
kubectl apply -f services\redpanda-console-service.yaml

REM Aplicar Deployments
echo Aplicando Deployments...
kubectl apply -f deployments\inventory-db-deployment.yaml
kubectl apply -f deployments\inventory-service-deployment.yaml
kubectl apply -f deployments\kafka-deployment.yaml
kubectl apply -f deployments\kitchen-db-deployment.yaml
kubectl apply -f deployments\kitchen-service-deployment.yaml
kubectl apply -f deployments\orchestrator-service-deployment.yaml
kubectl apply -f deployments\order-db-deployment.yaml
kubectl apply -f deployments\order-service-deployment.yaml
kubectl apply -f deployments\payment-db-deployment.yaml
kubectl apply -f deployments\payment-service-deployment.yaml
kubectl apply -f deployments\product-db-deployment.yaml
kubectl apply -f deployments\product-validation-service-deployment.yaml
kubectl apply -f deployments\redpanda-console-deployment.yaml

echo Todos os manifests foram aplicados com sucesso!
pause