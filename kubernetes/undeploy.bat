@echo off

REM Remover Deployments
echo Removendo Deployments...
for %%f in (deployments\*.yaml) do kubectl delete -f %%f

REM Remover Services
echo Removendo Services...
for %%f in (services\*.yaml) do kubectl delete -f %%f

REM Remover Persistent Volume Claims (PVCs)
echo Removendo PVCs...
for %%f in (pvc\*.yaml) do kubectl delete -f %%f

REM Remover ConfigMaps
echo Removendo ConfigMaps...
for %%f in (configs-maps\*.yaml) do kubectl delete -f %%f

REM Remover Namespaces
echo Removendo Namespaces...
kubectl delete -f namespaces\orchestrator-saga-namespace.yaml

echo Todos os recursos foram removidos com sucesso!
pause
