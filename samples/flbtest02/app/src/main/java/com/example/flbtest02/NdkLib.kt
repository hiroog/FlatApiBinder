// Auto generated file
// vim:ts=4 sw=4 et:
package com.example.flbtest02

class NdkLib {

	// NdkDefault

	// NdkLib
	external fun	stringFromJNI() : String
	external fun	Assert( expr : Boolean, message : String ) : Unit
	external fun	SetParams( a0 : Int, a1 : Short, a2 : Byte, a3 : Long, a4 : Float, a5 : Double, a6 : String ) : Int
	external fun	AddLong( a0 : Int, a1 : Long ) : Long
	external fun	AddFloat( a0 : Float, a1 : Int ) : Float
	external fun	AddDouble( a0 : Double, a1 : Float ) : Double

	// NativeClass
	external fun	NativeClassCreateInstance() : Long
	external fun	NativeClassReleaseInstance( jj_this : Long ) : Unit
	external fun	NativeClassSetParams( jj_this : Long, a0 : Int, a1 : Short, a2 : Byte, a3 : Long, a4 : Float, a5 : Double ) : Unit
	external fun	NativeClassGetIntParam( jj_this : Long ) : Int
	external fun	NativeClassGetShortParam( jj_this : Long ) : Short
	external fun	NativeClassGetByteParam( jj_this : Long ) : Byte
	external fun	NativeClassGetLongParam( jj_this : Long ) : Long
	external fun	NativeClassGetFloatParam( jj_this : Long ) : Float
	external fun	NativeClassGetDoubleParam( jj_this : Long ) : Double
	external fun	NativeClassAccessJNIEnv( jj_this : Long, java_object : Any? ) : Unit

	//-------------------------------------------------------------------------
	companion object {
		init {
			System.loadLibrary("native-lib")
		}
		var	NativeAPI : NdkLib = NdkLib()
		fun getAPI() : NdkLib = NativeAPI
	}
}

