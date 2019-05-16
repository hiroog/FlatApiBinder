#pragma once

#if defined(FLAPIBINDER)
const char*	FLAPIBINDER_ClassPath= "com.example.flbtest02.NdkRoot";
struct JNIEnv;
struct jobject;
#endif


const char*	stringFromJNI();
void	Assert( bool expr, const char* message );
int		SetParams( int a0, short a1, signed char a2, long long a3, float a4, double a5, const char* a6 );
long long	AddLong( int a0, long long a1 );
float		AddFloat( float a0, int a1 );
double		AddDouble( double a0, float a1 );





class TestApiClass {
public:
	static TestApiClass*	CreateAPI();

	virtual	~TestApiClass();
	virtual void	ReleaseAPI()= 0;
	virtual void	SetParams( int a0, short a1, signed char a2, long long a3, float a4, double a5 )= 0;
	virtual int			GetIntParam()= 0;
	virtual short		GetShortParam()= 0;
	virtual signed char	GetByteParam()= 0;
	virtual long long	GetLongParam()= 0;
	virtual float		GetFloatParam()= 0;
	virtual double		GetDoubleParam()= 0;
	virtual void		AccessJNIEnv( JNIEnv*, jobject, jobject java_object )= 0;
};



