@echo off
setlocal
cd /d "%~dp0"
for /f "skip=1 tokens=1,2" %%a in ('"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" devices') do if "%%b"=="device" "%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" -s %%a reverse tcp:3000 tcp:3000
node server.js
pause
