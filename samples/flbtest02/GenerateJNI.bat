@echo off

python ..\..\FlatApiBinder.py --cpp app/src/main/cpp --java app/src/main/java --dll native-lib app/src/main/cpp/NativeInterface.h --kotlin

