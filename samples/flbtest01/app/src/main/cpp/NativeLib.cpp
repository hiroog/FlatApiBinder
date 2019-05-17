// FlatApiBinder sample
// vim:ts=4 sw=4 noet:

#include <jni.h>
#include <string.h>
#include "TestAssert.h"
#include "NativeLib.h"


//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------


const char*	stringFromJNI()
{
	return	"Hello from C++ (" FLB_APPNAME ")";
}


void	Assert( bool expr, const char* message )
{
	TestAssert( expr, message, "Java", 0 );
}


int	SetParams( int a0, short a1, signed char a2, long long a3, float a4, double a5, const char* a6 )
{
	TEST_ASSERT( a0 == 123456789 );
	TEST_ASSERT( a1 == 32767 );
	TEST_ASSERT( a2 == 127 );
	TEST_ASSERT( a3 == 987654321098765432ll );
	TEST_ASSERT( a4 == 200.5f );
	TEST_ASSERT( a5 == 30000.456 );
	TEST_ASSERT( strcmp( a6, "NATIVE-STRING-TEST" ) == 0 );

	return	1122334455;
}


long long	AddLong( int a0, long long a1 )
{
	return	a0 + a1;
}


float		AddFloat( float a0, int a1 )
{
	return	a0 + a1;
}


double		AddDouble( double a0, float a1 )
{
	return	a0 + a1;
}


//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------


class NativeClassImplementation : public NativeClass {

	int			Arg0= 0;
	short		Arg1= 0;
	signed char	Arg2= 0;
	long long	Arg3= 0;
	float		Arg4= 0;
	double		Arg5= 0;

public:
	NativeClassImplementation()
	{
	}

	~NativeClassImplementation() override
	{
	}

	void	ReleaseInstance() override
	{
		delete this;
	}

	void	SetParams( int a0, short a1, signed char a2, long long a3, float a4, double a5 ) override
	{
		Arg0= a0;
		Arg1= a1;
		Arg2= a2;
		Arg3= a3;
		Arg4= a4;
		Arg5= a5;
	}

	int		GetIntParam() override
	{
		return	Arg0;
	}

	short	GetShortParam() override
	{
		return	Arg1;
	}

	signed char	GetByteParam() override
	{
		return	Arg2;
	}

	long long	GetLongParam() override
	{
		return	Arg3;
	}

	float	GetFloatParam() override
	{
		return	Arg4;
	}

	double	GetDoubleParam() override
	{
		return	Arg5;
	}

	void	AccessJNIEnv( JNIEnv* env, jobject tobj, jobject java_object ) override
	{
		auto	jarray= reinterpret_cast<jintArray>( java_object );
		int		array_size= env->GetArrayLength( jarray );
		jint*	data= env->GetIntArrayElements( jarray, nullptr );
		TEST_ASSERT( array_size == 10 );
		TEST_ASSERT( data[0] == 1 );
		TEST_ASSERT( data[9] == 10 );
		env->ReleaseIntArrayElements( jarray, data, 0 );
	}
};




NativeClass::~NativeClass()
{
}


NativeClass*	NativeClass::CreateInstance()
{
	return	new NativeClassImplementation();
}


//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------

