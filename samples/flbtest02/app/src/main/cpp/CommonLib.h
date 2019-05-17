// FlatApiBinder sample
// vim:ts=4 sw=4 noet:

#pragma once

#if defined(FLAPIBINDER)
const char*	FLAPIBINDER_Package=  "com.example.common";
const char*	FLAPIBINDER_Includes= "CommonLib.h";
#endif


// com.example.common.CommonLib

class CommonLib {
	int	Arg0;
	int	Arg1;
public:
	static CommonLib*	CreateInstance( int arg0, int arg1 )
	{
		return	new CommonLib( arg0, arg1 );
	}
	CommonLib( int arg0, int arg1 ) : Arg0( arg0 ), Arg1( arg1 )
	{
	}
	~CommonLib()
	{
	}
	void	ReleaseInstance( int arg )
	{
		delete this;
	}
	int		GetAddParam( int param )
	{
		return	Arg0 + Arg1 + param;
	}
};

