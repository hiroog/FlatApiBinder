// FlatApiBinder sample
// vim:ts=4 sw=4 noet:
#pragma once

#if defined(FLAPIBINDER)
const char*	FLAPIBINDER_ClassPath= "com.example.simplejni.NdkLib";
const char*	FLAPIBINDER_Includes=  "NativeInterface.h";
#endif

const char*	stringFromJNI();

