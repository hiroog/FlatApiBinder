// FlatApiBinder sample
// vim:ts=4 sw=4 noet:

#include	"CommonLib2.h"
#include	<vector>
#include	"TestAssert.h"


class ManagerClass_Implement : public ManagerClass {
	std::vector<ItemClass>	Table;
public:
	ManagerClass_Implement( int table_size ) : Table( table_size )
	{
	}
	~ManagerClass_Implement()
	{
	}
	void	ReleaseInstance() override
	{
		delete this;
	}
	ItemClass*	GetItem( int index ) override
	{
		TEST_ASSERT( index >= 0 && index < Table.size() );
		return	&Table[index];
	}
};


ManagerClass*	ManagerClass::CreateInstance( int table_size )
{
	return	new ManagerClass_Implement( table_size );
}


ManagerClass::~ManagerClass()
{
}





