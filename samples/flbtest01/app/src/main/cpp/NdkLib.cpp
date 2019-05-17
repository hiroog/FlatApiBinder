// Auto generated file
// vim:ts=4 sw=4 et:
#include <jni.h>
#include "NativeInterface.h"

extern "C" {
//-----------------------------------------------------------------------------


// NdkDefault

// NdkLib
JNIEXPORT jstring JNICALL	Java_com_example_flbtest01_NdkLib_stringFromJNI( JNIEnv* env, jobject tobj )
{
	auto	cc_result_= stringFromJNI();
	auto	jj_result_= env->NewStringUTF( cc_result_ );
	return	jj_result_;
}

JNIEXPORT void JNICALL	Java_com_example_flbtest01_NdkLib_Assert( JNIEnv* env, jobject tobj, jboolean jj_expr, jstring jj_message )
{
	auto	cc_expr= static_cast<bool>( jj_expr );
	const char*	cc_message= env->GetStringUTFChars( jj_message, nullptr );
	Assert( cc_expr, cc_message );
	env->ReleaseStringUTFChars( jj_message, cc_message );
}

JNIEXPORT jint JNICALL	Java_com_example_flbtest01_NdkLib_SetParams( JNIEnv* env, jobject tobj, jint jj_a0, jshort jj_a1, jbyte jj_a2, jlong jj_a3, jfloat jj_a4, jdouble jj_a5, jstring jj_a6 )
{
	auto	cc_a0= static_cast<int>( jj_a0 );
	auto	cc_a1= static_cast<short>( jj_a1 );
	auto	cc_a2= static_cast<signed char>( jj_a2 );
	auto	cc_a3= static_cast<long long>( jj_a3 );
	auto	cc_a4= static_cast<float>( jj_a4 );
	auto	cc_a5= static_cast<double>( jj_a5 );
	const char*	cc_a6= env->GetStringUTFChars( jj_a6, nullptr );
	auto	cc_result_= SetParams( cc_a0, cc_a1, cc_a2, cc_a3, cc_a4, cc_a5, cc_a6 );
	env->ReleaseStringUTFChars( jj_a6, cc_a6 );
	auto	jj_result_= static_cast<jint>( cc_result_ );
	return	jj_result_;
}

JNIEXPORT jlong JNICALL	Java_com_example_flbtest01_NdkLib_AddLong( JNIEnv* env, jobject tobj, jint jj_a0, jlong jj_a1 )
{
	auto	cc_a0= static_cast<int>( jj_a0 );
	auto	cc_a1= static_cast<long long>( jj_a1 );
	auto	cc_result_= AddLong( cc_a0, cc_a1 );
	auto	jj_result_= static_cast<jlong>( cc_result_ );
	return	jj_result_;
}

JNIEXPORT jfloat JNICALL	Java_com_example_flbtest01_NdkLib_AddFloat( JNIEnv* env, jobject tobj, jfloat jj_a0, jint jj_a1 )
{
	auto	cc_a0= static_cast<float>( jj_a0 );
	auto	cc_a1= static_cast<int>( jj_a1 );
	auto	cc_result_= AddFloat( cc_a0, cc_a1 );
	auto	jj_result_= static_cast<jfloat>( cc_result_ );
	return	jj_result_;
}

JNIEXPORT jdouble JNICALL	Java_com_example_flbtest01_NdkLib_AddDouble( JNIEnv* env, jobject tobj, jdouble jj_a0, jfloat jj_a1 )
{
	auto	cc_a0= static_cast<double>( jj_a0 );
	auto	cc_a1= static_cast<float>( jj_a1 );
	auto	cc_result_= AddDouble( cc_a0, cc_a1 );
	auto	jj_result_= static_cast<jdouble>( cc_result_ );
	return	jj_result_;
}


// NativeClass
JNIEXPORT jlong JNICALL	Java_com_example_flbtest01_NdkLib_NativeClassCreateInstance( JNIEnv* env, jobject tobj )
{
	auto	cc_result_= NativeClass::CreateInstance();
	auto	jj_result_= static_cast<jlong>( reinterpret_cast<intptr_t>(cc_result_) );
	return	jj_result_;
}

JNIEXPORT void JNICALL	Java_com_example_flbtest01_NdkLib_NativeClassReleaseInstance( JNIEnv* env, jobject tobj, jlong jj_This_ )
{
	auto*	cc_This_= reinterpret_cast<NativeClass*>(static_cast<intptr_t>(jj_This_));
	cc_This_->ReleaseInstance();
}

JNIEXPORT void JNICALL	Java_com_example_flbtest01_NdkLib_NativeClassSetParams( JNIEnv* env, jobject tobj, jlong jj_This_, jint jj_a0, jshort jj_a1, jbyte jj_a2, jlong jj_a3, jfloat jj_a4, jdouble jj_a5 )
{
	auto	cc_a0= static_cast<int>( jj_a0 );
	auto	cc_a1= static_cast<short>( jj_a1 );
	auto	cc_a2= static_cast<signed char>( jj_a2 );
	auto	cc_a3= static_cast<long long>( jj_a3 );
	auto	cc_a4= static_cast<float>( jj_a4 );
	auto	cc_a5= static_cast<double>( jj_a5 );
	auto*	cc_This_= reinterpret_cast<NativeClass*>(static_cast<intptr_t>(jj_This_));
	cc_This_->SetParams( cc_a0, cc_a1, cc_a2, cc_a3, cc_a4, cc_a5 );
}

JNIEXPORT jint JNICALL	Java_com_example_flbtest01_NdkLib_NativeClassGetIntParam( JNIEnv* env, jobject tobj, jlong jj_This_ )
{
	auto*	cc_This_= reinterpret_cast<NativeClass*>(static_cast<intptr_t>(jj_This_));
	auto	cc_result_= cc_This_->GetIntParam();
	auto	jj_result_= static_cast<jint>( cc_result_ );
	return	jj_result_;
}

JNIEXPORT jshort JNICALL	Java_com_example_flbtest01_NdkLib_NativeClassGetShortParam( JNIEnv* env, jobject tobj, jlong jj_This_ )
{
	auto*	cc_This_= reinterpret_cast<NativeClass*>(static_cast<intptr_t>(jj_This_));
	auto	cc_result_= cc_This_->GetShortParam();
	auto	jj_result_= static_cast<jshort>( cc_result_ );
	return	jj_result_;
}

JNIEXPORT jbyte JNICALL	Java_com_example_flbtest01_NdkLib_NativeClassGetByteParam( JNIEnv* env, jobject tobj, jlong jj_This_ )
{
	auto*	cc_This_= reinterpret_cast<NativeClass*>(static_cast<intptr_t>(jj_This_));
	auto	cc_result_= cc_This_->GetByteParam();
	auto	jj_result_= static_cast<jbyte>( cc_result_ );
	return	jj_result_;
}

JNIEXPORT jlong JNICALL	Java_com_example_flbtest01_NdkLib_NativeClassGetLongParam( JNIEnv* env, jobject tobj, jlong jj_This_ )
{
	auto*	cc_This_= reinterpret_cast<NativeClass*>(static_cast<intptr_t>(jj_This_));
	auto	cc_result_= cc_This_->GetLongParam();
	auto	jj_result_= static_cast<jlong>( cc_result_ );
	return	jj_result_;
}

JNIEXPORT jfloat JNICALL	Java_com_example_flbtest01_NdkLib_NativeClassGetFloatParam( JNIEnv* env, jobject tobj, jlong jj_This_ )
{
	auto*	cc_This_= reinterpret_cast<NativeClass*>(static_cast<intptr_t>(jj_This_));
	auto	cc_result_= cc_This_->GetFloatParam();
	auto	jj_result_= static_cast<jfloat>( cc_result_ );
	return	jj_result_;
}

JNIEXPORT jdouble JNICALL	Java_com_example_flbtest01_NdkLib_NativeClassGetDoubleParam( JNIEnv* env, jobject tobj, jlong jj_This_ )
{
	auto*	cc_This_= reinterpret_cast<NativeClass*>(static_cast<intptr_t>(jj_This_));
	auto	cc_result_= cc_This_->GetDoubleParam();
	auto	jj_result_= static_cast<jdouble>( cc_result_ );
	return	jj_result_;
}

JNIEXPORT void JNICALL	Java_com_example_flbtest01_NdkLib_NativeClassAccessJNIEnv( JNIEnv* env, jobject tobj, jlong jj_This_, jobject jj_java_object )
{
	const auto&	cc_java_object= jj_java_object;
	auto*	cc_This_= reinterpret_cast<NativeClass*>(static_cast<intptr_t>(jj_This_));
	cc_This_->AccessJNIEnv( env, tobj, cc_java_object );
}


//-----------------------------------------------------------------------------
}

