// FlatApiBinder sample
// vim:ts=4 sw=4 noet:

#pragma once

#if defined(FLAPIBINDER)
const char*	FLAPIBINDER_Package=  "com.example.common";
const char*	FLAPIBINDER_Includes= "CommonLib.h";
struct JNIEnv;
struct jobject;
#endif


class CommonLib {
public:
	static CommonLib*	CreateInstance( int arg0, int arg1 );
	virtual ~CommonLib();
	virtual void	ReleaseInstance( float param )= 0;
};


CommonLib*	getCommonLibInstance();


