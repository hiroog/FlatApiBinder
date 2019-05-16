// Auto generated file
// vim:ts=4 sw=4 et:
package com.example.flbtest02

class TestApiClass {
	var	NativeThis : Long = 0

	fun	ReleaseAPI() : Unit = NdkRoot.NativeInstance.TestApiClassReleaseAPI( NativeThis )
	fun	SetParams( a0 : Int, a1 : Short, a2 : Byte, a3 : Long, a4 : Float, a5 : Double ) : Unit = NdkRoot.NativeInstance.TestApiClassSetParams( NativeThis, a0, a1, a2, a3, a4, a5 )
	fun	GetIntParam() : Int = NdkRoot.NativeInstance.TestApiClassGetIntParam( NativeThis )
	fun	GetShortParam() : Short = NdkRoot.NativeInstance.TestApiClassGetShortParam( NativeThis )
	fun	GetByteParam() : Byte = NdkRoot.NativeInstance.TestApiClassGetByteParam( NativeThis )
	fun	GetLongParam() : Long = NdkRoot.NativeInstance.TestApiClassGetLongParam( NativeThis )
	fun	GetFloatParam() : Float = NdkRoot.NativeInstance.TestApiClassGetFloatParam( NativeThis )
	fun	GetDoubleParam() : Double = NdkRoot.NativeInstance.TestApiClassGetDoubleParam( NativeThis )
	fun	AccessJNIEnv( java_object : Any? ) : Unit = NdkRoot.NativeInstance.TestApiClassAccessJNIEnv( NativeThis, java_object )
	// ------------------------------------------------------------------------
	fun	SetNativeAPI( api : Long )
	{
		NativeThis= api
	}
	fun	GetNativeAPI() : Long = NativeThis
	constructor( api : Long )
	{
		NativeThis= api
	}
	constructor()
	{
		SetNativeAPI( NdkRoot.NativeInstance.TestApiClassCreateAPI() )
	}
	fun	Release()
	{
		ReleaseAPI()
		NativeThis= 0
	}
}

