// Auto generated file
// vim:ts=4 sw=4 et:
#include <jni.h>
#include "NativeLib.h"
#include "CommonLib.h"

extern "C" {
//-----------------------------------------------------------------------------


// NdkDefault

// NdkLib
JNIEXPORT jstring JNICALL	Java_com_example_flbtest03_NdkLib_stringFromJNI( JNIEnv* env, jobject tobj )
{
	auto	cc_result_= stringFromJNI();
	auto	jj_result_= env->NewStringUTF( cc_result_ );
	return	jj_result_;
}

JNIEXPORT jlong JNICALL	Java_com_example_flbtest03_NdkLib_getCommonLibInstance( JNIEnv* env, jobject tobj )
{
	auto	cc_result_= getCommonLibInstance();
	auto	jj_result_= static_cast<jlong>( reinterpret_cast<intptr_t>(cc_result_) );
	return	jj_result_;
}


// CommonLib
JNIEXPORT jlong JNICALL	Java_com_example_flbtest03_NdkLib_CommonLibCreateInstance( JNIEnv* env, jobject tobj, jint jj_arg0, jint jj_arg1 )
{
	auto	cc_arg0= static_cast<int>( jj_arg0 );
	auto	cc_arg1= static_cast<int>( jj_arg1 );
	auto	cc_result_= CommonLib::CreateInstance( cc_arg0, cc_arg1 );
	auto	jj_result_= static_cast<jlong>( reinterpret_cast<intptr_t>(cc_result_) );
	return	jj_result_;
}

JNIEXPORT void JNICALL	Java_com_example_flbtest03_NdkLib_CommonLibReleaseInstance( JNIEnv* env, jobject tobj, jlong jj_This_, jfloat jj_param )
{
	auto	cc_param= static_cast<float>( jj_param );
	auto*	cc_This_= reinterpret_cast<CommonLib*>(static_cast<intptr_t>(jj_This_));
	cc_This_->ReleaseInstance( cc_param );
}


//-----------------------------------------------------------------------------
}

