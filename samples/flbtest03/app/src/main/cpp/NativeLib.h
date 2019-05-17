// FlatApiBinder sample
// vim:ts=4 sw=4 noet:

#pragma once

#if defined(FLAPIBINDER)
const char*	FLAPIBINDER_ClassPath= "com.example.flbtest03.NdkLib";
const char*	FLAPIBINDER_Includes=  "NativeLib.h";
struct JNIEnv;
struct jobject;
#endif


const char*	stringFromJNI();


