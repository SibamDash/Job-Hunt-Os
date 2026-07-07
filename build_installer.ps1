# build_installer.ps1
# Requires JDK 21 and WiX Toolset installed for MSI generation.

Write-Host "Building Fat JAR using Maven..."
mvn clean package

if ($LASTEXITCODE -ne 0) {
    Write-Host "Maven build failed." -ForegroundColor Red
    exit 1
}

$JAR_PATH = "target\job-hunting-os-1.0-SNAPSHOT.jar"

Write-Host "Running jpackage to create executable/installer..."
jpackage 
  --type app-image 
  --name "Job Hunting OS" 
  --input target 
  --main-jar job-hunting-os-1.0-SNAPSHOT.jar 
  --main-class com.jobhuntos.Launcher 
  --vendor "SibamDash" 
  --app-version 1.0.0 
  --dest target\installer

Write-Host "Packaging complete. App Image generated in target\installer." -ForegroundColor Green
