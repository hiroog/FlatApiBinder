#pragma once
#define	TEST_ASSERT(expr)	TestAssert((expr),#expr,__FILE__,__LINE__)

#include	<android/log.h>

inline void	TestAssert( bool result, const char* expr_text, const char* file_name, size_t line_number )
{
	if( !result ){
		__android_log_assert( nullptr, "TestAssert", "Assertion failed: %s (%s,%zd)\n", expr_text, file_name, line_number );
	}
}

