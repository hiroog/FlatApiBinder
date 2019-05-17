// FlatApiBinder sample
// vim:ts=4 sw=4 noet:

#include	"CommonLib.h"


CommonLib*	getCommonLibInstance()
{
	return	nullptr;
}


class CommonLib_Implement : public CommonLib {
	int	Arg0;
	int	Arg1;
public:
	CommonLib_Implement( int arg0, int arg1 ) : Arg0( arg0 ), Arg1( arg1 )
	{
	}
	~CommonLib_Implement() override
	{
	}
	void	ReleaseInstance( float ) override
	{
		delete this;
	}
};


CommonLib::~CommonLib()
{
}

CommonLib*	CommonLib::CreateInstance( int arg0, int arg1 )
{
	return	new CommonLib_Implement( arg0, arg1 );
}



