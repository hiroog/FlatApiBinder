// FlatApiBinder sample
// vim:ts=4 sw=4 noet:

#pragma once

#if defined(FLAPIBINDER)
const char*	FLAPIBINDER_Package=  "com.example.common";
const char*	FLAPIBINDER_Includes= "CommonLib2.h";
#endif


// com.example.common.ItemClass

class ItemClass {
	int	ItemID= 0;
public:
	void	SetItemID( int item_id )
	{
		ItemID= item_id;
	}
	int		GetItemID() const
	{
		return	ItemID;
	}
};


// com.example.common.ManagerClass

class ManagerClass {
public:
	static ManagerClass*	CreateInstance( int talbe_size );
	virtual	~ManagerClass();
	virtual void	ReleaseInstance()= 0;
	virtual ItemClass*	GetItem( int index )= 0;
};



