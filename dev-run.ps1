Write-Host ""
Write-Host "======================================"
Write-Host "JourneyManager Full Stack Dev Launcher"
Write-Host "======================================"
Write-Host ""

# -----------------------------

# PROJECT PATHS

# -----------------------------

$projectRoot = "C:\Users\jayauser\AndroidStudioProjects\JourneyManager"
$backendPath = "$projectRoot\journeybackend"

$androidStudio = "C:\Program Files\Android\Android Studio\bin\studio64.exe"

$adb = "C:\Users\jayauser\AppData\Local\Android\Sdk\platform-tools\adb.exe"

# -----------------------------

# OPEN ANDROID STUDIO

# -----------------------------

Write-Host "Opening Android Studio..."

Start-Process $androidStudio $projectRoot

Start-Sleep -Seconds 5

# -----------------------------

# START SPRING BOOT BACKEND

# -----------------------------

Write-Host "Starting Spring Boot backend..."

Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd $backendPath; .\gradlew bootRun"

Start-Sleep -Seconds 8

# -----------------------------

# CHECK DEVICE CONNECTION

# -----------------------------

Write-Host ""
Write-Host "Checking connected Android device..."

& $adb devices

Start-Sleep -Seconds 3

# -----------------------------

# BUILD AND INSTALL APK

# -----------------------------

Write-Host ""
Write-Host "Building and installing Android APK..."

cd $projectRoot

.\gradlew installDebug

# -----------------------------

# LAUNCH APP ON DEVICE

# -----------------------------

Write-Host ""
Write-Host "Launching JourneyManager App..."

& $adb shell monkey -p com.allan88.journeymanager -c android.intent.category.LAUNCHER 1

Write-Host ""
Write-Host "--------------------------------------"
Write-Host "JourneyManager Development Ready"
Write-Host "--------------------------------------"
