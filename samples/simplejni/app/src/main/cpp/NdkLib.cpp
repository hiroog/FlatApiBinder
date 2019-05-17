// Auto generated file
// vim:ts=4 sw=4 et:
#include <jni.h>
#include "NativeInterface.h"

extern "C" {
//-----------------------------------------------------------------------------


// NdkDefault

// NdkLib
JNIEXPORT jstring JNICALL	Java_com_example_simplejni_NdkLib_stringFromJNI( JNIEnv* env, jobject tobj )
{
	auto	cc_result_= stringFromJNI();
	auto	jj_result_= env->NewStringUTF( cc_result_ );
	return	jj_result_;
}


//-----------------------------------------------------------------------------
}

