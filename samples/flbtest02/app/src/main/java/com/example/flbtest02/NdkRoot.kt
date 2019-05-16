// Auto generated file
// vim:ts=4 sw=4 et:
package com.example.flbtest02

class NdkRoot {

	// NdkApiDefault

	// NdkRoot
	external fun	stringFromJNI() : String
	external fun	Assert( expr : Boolean, message : String ) : Unit
	external fun	SetParams( a0 : Int, a1 : Short, a2 : Byte, a3 : Long, a4 : Float, a5 : Double, a6 : String ) : Int
	external fun	AddLong( a0 : Int, a1 : Long ) : Long
	external fun	AddFloat( a0 : Float, a1 : Int ) : Float
	external fun	AddDouble( a0 : Double, a1 : Float ) : Double

	// TestApiClass
	external fun	TestApiClassCreateAPI() : Long
	external fun	TestApiClassReleaseAPI( jj_this : Long ) : Unit
	external fun	TestApiClassSetParams( jj_this : Long, a0 : Int, a1 : Short, a2 : Byte, a3 : Long, a4 : Float, a5 : Double ) : Unit
	external fun	TestApiClassGetIntParam( jj_this : Long ) : Int
	external fun	TestApiClassGetShortParam( jj_this : Long ) : Short
	external fun	TestApiClassGetByteParam( jj_this : Long ) : Byte
	external fun	TestApiClassGetLongParam( jj_this : Long ) : Long
	external fun	TestApiClassGetFloatParam( jj_this : Long ) : Float
	external fun	TestApiClassGetDoubleParam( jj_this : Long ) : Double
	external fun	TestApiClassAccessJNIEnv( jj_this : Long, java_object : Any? ) : Unit

	// ------------------------------------------------------------------------
	companion object {
		init {
			System.loadLibrary("native-lib")
		}
		var	NativeInstance : NdkRoot = NdkRoot()
		fun Init() : NdkRoot
		{
			return	NativeInstance
		}
	}
}

