# FlatApiBinder

Android NDK の jni interface code を生成します。


## setup 手順 (Windows)

1. download and install prebuilt package (LLVM-X.X.X-win64.exe) from llvm.org
2. add "LLVM/bin" directory to PATH
3. pip3 install clang

## setup 手順 (Linux)

1. apt install clang
2. pip3 install clang
3. export LD\_LIBRARY\_PATH=`llvm-config-X.X --libdir`


## 使い方

### (1) Interface Header の作成

Java/Kotlin から呼び出したい C++ 関数のヘッダを作成。

```cpp
// NativeInterface.h
#pragma once

const char* stringFromJNI();
```

### (2) jni コードの生成

```
python3 FlatApiBinder.py --cpp app/src/main/cpp --java app/src/main/java --include NativeInterface.h --dll native-lib --classpath com.example.testapp.NdkRoot app/src/main/cpp/NativeInterface.h
```

下記のファイルが作られる。

- app/src/main/java/com/example/testapp/NdkRoot.java
- app/src/main/cpp/NdkRoot.cpp



```java
// NdkRoot.java

package com.example.testapp;

public class NdkRoot {
    static {
        System.loadLibrary("native-lib");
    }

    // NdkRoot
    public native String    stringFromJNI();

    //-------------------------------------------------------------------------
    public static NdkRoot   NativeInstance;
    public static NdkRoot   Init()
    {
        NativeInstance= new NdkRoot();
        return  NativeInstance;
    }
}
```

```cpp
// NdkRoot.cpp

#include <jni.h>
#include "NativeInterface.h"

extern "C" {
//-----------------------------------------------------------------------------

// NdkRoot
JNIEXPORT jstring JNICALL   Java_com_example_testapp_NdkRoot_stringFromJNI( JNIEnv* env, jobject tobj )
{
    auto    cc_result_= stringFromJNI();
    auto    jj_result_= env->NewStringUTF( cc_result_ );
    return  jj_result_;
}

//-----------------------------------------------------------------------------
}
```


## 使い方詳細







