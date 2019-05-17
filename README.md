# FlatApiBinder

- Android NDK の jni interface code を生成します。
- C++ の関数を Java や Kotlin から簡単に呼び出せるようになります。
- class 形式の API にも対応しています。


## setup 手順

python 3.x と libclang が必要です。

### Windows

1. download and install prebuilt package (LLVM-X.X.X-win64.exe) from llvm.org
2. add LLVM/bin directory to PATH
3. pip3 install clang

### Linux

```
$ sudo apt install clang libclang-dev python3-pip
$ pip3 install clang
$ export LD_LIBRARY_PATH=`llvm-config-X.X --libdir`
```


## 使い方の例


実行する C++ 関数は下記の通り。

```cpp
// app/src/main/cpp/native-lib.cpp
#include "NativeInterface.h"

const char* stringFromJNI()
{
    return  "Hello from C++ (testapp)";
}
```


### (1) Interface Header の作成

呼び出したい C++ 関数のヘッダファイルを作ります。

```cpp
// app/src/main/cpp/NativeInterface.h
#pragma once

const char* stringFromJNI();
```

### (2) jni interface コードの生成


FlatApiBinder を実行すると (1) を読み込んで jni interface コードを作成します。

```
$ python3 FlatApiBinder.py --cpp app/src/main/cpp --java app/src/main/java --include NativeInterface.h --dll native-lib --classpath com.example.testapp.NdkLib app/src/main/cpp/NativeInterface.h
```

- 出力
    - app/src/main/java/com/example/testapp/NdkLib.java
    - app/src/main/cpp/NdkLib.cpp



- option `--kotlin` をつけた場合
    - app/src/main/java/com/example/testapp/NdkLib.kt
    - app/src/main/cpp/NdkLib.cpp


### (3) 呼び出し方


```java
// Java
NdkLib api= NdkLib.getAPI();
String str= api.stringFromJNI();
```


```kotlin
// kotlin
var api= NdkLib.getAPI()
var str= api.stringFromJNI()
```




### 生成されたコード

```cpp
// app/src/main/cpp/NdkLib.cpp
#include <jni.h>
#include "NativeInterface.h"

extern "C" {

JNIEXPORT jstring JNICALL	Java_com_example_testapp_NdkLib_stringFromJNI( JNIEnv* env, jobject tobj )
{
	auto	cc_result_= stringFromJNI();
	auto	jj_result_= env->NewStringUTF( cc_result_ );
	return	jj_result_;
}

}
```


```java
// app/src/main/java/com/example/testapp/NdkLib.java
package com.example.testapp;

public class NdkLib {
	public native String	stringFromJNI();

	static {
		System.loadLibrary("native-lib");
	}
	public static NdkLib	NativeAPI= new NdkLib();
	public static NdkLib	getAPI()
	{
		return	NativeAPI;
	}
}
```

Command option `--kotlin` を指定した場合

```kotlin
// app/src/main/java/com/example/testapp/NdkLib.kt
package com.example.testapp

class NdkLib {
	external fun	stringFromJNI() : String

	companion object {
		init {
			System.loadLibrary("native-lib")
		}
		var	NativeAPI : NdkLib = NdkLib()
		fun getAPI() : NdkLib = NativeAPI
	}
}

```






## class API の例

class 形式の API 呼び出しができます。


### (1) NativeInterface.h と実行する C++ code


```cpp
// app/src/main/cpp/NativeInterface.h
#pragma once

#if defined(FLAPIBINDER)
const char* FLAPIBINDER_ClassPath= "com.example.testapp.NdkLib";
const char* FLAPIBINDER_Includes= "NativeInterface.h";
#endif

class NativeClass {
public:
    static NativeClass* CreateInstance();
    void        ReleaseInstance();

    void        setStoragePath( const char* path );
};
```


```cpp
// app/src/main/cpp/native-lib.cpp
#include  "NativeInterface.h"

NativeClass*  NativeClass::CreateInstance()
{
    return  new NativeClass();
}

void  NativeClass::ReleaseInstance()
{
    delete this;
}

void  NativeClass::setStoragePath( const char* path )
{
    ...
}
```



### (2) jni code の生成


```
$ python3 FlatApiBinder.py --cpp app/src/main/cpp --java app/src/main/java --dll native-lib app/src/main/cpp/NativeInterface.h
```

or

```
$ python3 FlatApiBinder.py --cpp app/src/main/cpp --java app/src/main/java --dll native-lib app/src/main/cpp/NativeInterface.h --kotlin
```


### (3) 呼び出し

```java
// Java
NativeClass  api= new NativeClass();
api.setStoragePath( "internal storage path" );
api.release();
```

```kotlin
// kotlin
var  api= NativeClass()
api.setStoragePath( "internal storage path" )
api.release()
```




## 使い方詳細

### Command Option

FlatApiBinder.py に呼び出したい C++ API を記述したヘッダファイルを与えます。


```
FlatApiBinder.py [<options>] <HeaderFile.h> ...
```


| Command option                    |                                | default                  |
|:----------------------------------|:-------------------------------|:-------------------------|
| --dll `<dll-name>`                | dll 名                         | native-lib               |
| --cpp `<output_folder>`           | jni cpp code 出力先の指定      | .                        |
| --java `<output_folder_root>`     | java/kotlin 出力先の指定       | .                        |
| --kotlin                          | java の代わりに kotlin を出力  |                          |
| --classpath `<class>`             | package と API class 名        | com.example.NdkDefault   |
| --include `<header_file>`         | cpp で include するファイル名。複数回指定可能。  |          |



### ヘッダ内宣言

Command option の代わりに読み込ませる HeaderFile に記述しておくこともできます。

| Variable name                     |                                |
|:----------------------------------|:-------------------------------|
| FLAPIBINDER\_DllName              | dll 名                         |
| FLAPIBINDER\_CppRoot              | jni cpp code 出力先の指定      |
| FLAPIBINDER\_JavaRoot             | java/kotlin 出力先の指定       |
| FLAPIBINDER\_Includes             | cpp で include するファイル名。';' 区切りで複数指定可能。  |
| FLAPIBINDER\_ClassPath            | package と API class 名        |
| FLAPIBINDER\_Package              | package のみ宣言               |



```cpp
// NativeInterface.h
#pragma once

#if defined(FLAPIBINDER)
const char* FLAPIBINDER_ClassPath= "com.example.testapp.NdkLib";
const char* FLAPIBINDER_DllName=   "native-lib";
const char* FLAPIBINDER_JavaRoot=  "app/src/main/java";
const char* FLAPIBINDER_CppRoot=   "app/src/main/cpp";
const char* FLAPIBINDER_Includes=  "NativeInterface.h";
#endif

const char* stringFromJNI();
```

FlatApiBinder.py は C++ preprocessor macro `FLAPIBINDER` を定義します。(-DFLAPIBINDER=1)


class 形式 API では `FLAPIBINDER_ClassPath` の代わりに `FLAPIBINDER_Package` を使用できます。

```cpp
// NativeInterface.h
#pragma once

#if defined(FLAPIBINDER)
const char* FLAPIBINDER_ClassPath= "com.example.testapp.NdkLib";
const char* FLAPIBINDER_Includes=  "NativeInterface.h";
#endif

const char* stringFromJNI();    //  app/src/main/java/com/example/testapp/NdkLib.java


#if defined(FLAPIBINDER)
const char* FLAPIBINDER_Package= "com.example.common";
#endif

class CommonLib {     //  app/src/main/java/com/example/common/CommonLib.java
public:
   ...
};

```


`FLAPIBINDER_Package` と `FLAPIBINDER_Includes` は同一ヘッダ内で複数回宣言できます。
ただし FlatApiBinder の parser を通すために namespace で分離しておいてください。

```cpp
#if defined(FLAPIBINDER)
namespace A {
const char* FLAPIBINDER_Package=  "com.example.testapp";
const char* FLAPIBINDER_Includes= "NativeInterface.h";
}
#endif

...

#if defined(FLAPIBINDER)
namespace B {
const char* FLAPIBINDER_Package=  "com.example.common";
const char* FLAPIBINDER_Includes= "CommonLibrary.h";
}
#endif

```


### class 形式 API の詳細

動的に C++ class の instance を生成する場合は、CreateInstance() と ReleaseInstance() を特別扱いします。
これらのメソッドが宣言されている場合は java/kotlin に専用の補助コードを生成します。
動的に生成した場合、メモリの解放を忘れないようにしてください。

```cpp
// app/src/main/cpp/NativeInterface.h
#pragma once

#if defined(FLAPIBINDER)
const char* FLAPIBINDER_ClassPath= "com.example.testapp.NdkLib";
#endif

class NativeClass {
public:
    static NativeClass* CreateInstance();
    virtual ~NativeClass();
    virtual void    ReleaseInstance()= 0;

    ...
};
```

```cpp
// app/src/main/cpp/NativeClass.cpp
#include  "NativeInterface.h"

class NativeClassImplementation : public NativeClass {
public:

    ...

    void    ReleaseInstance() override
    {
        delete this;
    }
};

...

NativeClass*  NativeClass::CreateInstance()
{
    return  new NativeClassImplementation();
}
```

呼び出し例 (java)。 明示的な release() が必要。

```java
// java
NativeClass  napi= new NativeClass();  // call CreateInstance()

...

napi.release();  // call ReleaseInstance()
```

CreateInstance() や ReleaseInstance() がなくても構いません。
動的に生成しない class の場合は、C++ から instance を受け取って自分で設定してください。


```cpp
// app/src/main/cpp/NativeInterface.h
#pragma once

#if defined(FLAPIBINDER)
const char* FLAPIBINDER_ClassPath= "com.example.testapp.NdkLib";
#endif

class NativeClass;
NativeClass*  getNativeClassReference();


class NativeClass {
public:
    ...
};

```

```java
// java
NdkLib api= NdkLib.getAPI();
NativeClass napi= new NativeClass( api.getNativeClassReference() );

...
```

CreateInstance() 及び ReleaseInstance() は名前だけで判定しています。
それぞれ任意の引数を持たせることが可能です。

```cpp
// app/src/main/cpp/NativeInterface.h
#pragma once

#if defined(FLAPIBINDER)
const char* FLAPIBINDER_ClassPath= "com.example.testapp.NdkLib";
#endif

class NativeClass {
public:
    static NativeClass* CreateInstance( const char* name, int count );
    virtual void    ReleaseInstance()= 0;
};
```

native API は必ず FLAPIBINDER\_ClassPath (--classpath) で指定した class に作られます。

class 形式 API は FLAPIBINDER\_Package 指定によって任意の場所に作成することができます。
また読み込ませるヘッダファイルを複数のファイルに分割しても構いません。




### cpp で JNIEnv や jobject (this) を受け取る方法


cpp API 側で引数の先頭に JNIEnv\*, jobject (this) を宣言すると cpp で受け取ることができます。

```cpp
// app/src/main/cpp/NativeInterface.h
#pragma once

#if defined(FLAPIBINDER)
const char* FLAPIBINDER_ClassPath= "com.example.testapp.NdkLib";
struct JNIEnv;
struct jobject;
#endif

const char* stringFromJNI( JNIEnv*, jobject );
```

parser を通すために JNIEnv や jobject のダミー宣言が必要です。
struct にしていますがこの宣言は FlatApiBinder のためのもので cpp からは見えません。


生成コードは下記のとおりです。stringFromJNI() に env と tobj を渡しています。


```cpp
// app/src/main/cpp/NdkLib.cpp

JNIEXPORT jstring JNICALL   Java_com_example_testapp_NdkLib_stringFromJNI( JNIEnv* env, jobject tobj )
{
    auto    cc_result_= stringFromJNI( env, tobj );
    auto    jj_result_= env->NewStringUTF( cc_result_ );
    return  jj_result_;
}
```

JNIEnv\* だけ受け取ることもできます。


### cpp に任意の Java Object を渡す方法

this (instance) だけでなく、任意の Object を引数として渡すこともできます。


```cpp
// app/src/main/cpp/NativeInterface.h
#pragma once

#if defined(FLAPIBINDER)
const char* FLAPIBINDER_ClassPath= "com.example.testapp.NdkLib";
struct JNIEnv;
struct jobject;
#endif

void  SetDataArray( JNIEnv* env, int param0, float param1, jobject array_data );
```


### 制限

- overload 非対応


## 参考にしたページ

- https://eli.thegreenplace.net/2011/07/03/parsing-c-in-python-with-clang
- https://pypi.org/project/clang/3.8/


