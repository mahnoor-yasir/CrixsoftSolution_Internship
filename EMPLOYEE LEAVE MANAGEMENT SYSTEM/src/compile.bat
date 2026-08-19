@echo off
echo ========================================
echo Employee Leave Management System
echo Compiling...
echo ========================================

mkdir out 2>nul

echo Compiling model classes...
javac -d out src/main/java/com/elm/model/*.java

echo Compiling AppContext...
javac -d out -cp out src/main/java/com/elm/AppContext.java

echo Compiling service classes...
javac -d out -cp out src/main/java/com/elm/service/*.java

echo Compiling UI classes...
javac -d out -cp out src/main/java/com/elm/ui/*.java src/main/java/com/elm/ui/admin/*.java src/main/java/com/elm/ui/manager/*.java src/main/java/com/elm/ui/employee/*.java

echo Compiling Main...
javac -d out -cp out src/main/java/com/elm/Main.java

if %errorlevel% == 0 (
    echo.
    echo ========================================
    echo COMPILATION SUCCESSFUL!
    echo ========================================
    echo.
    echo To run the application:
    echo java -cp out com.elm.Main
    echo.
) else (
    echo.
    echo ========================================
    echo COMPILATION FAILED!
    echo ========================================
    echo Please check the errors above.
)

pause
