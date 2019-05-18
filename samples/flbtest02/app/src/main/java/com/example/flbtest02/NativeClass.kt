// Auto generated file
// vim:ts=4 sw=4 et:
package	com.example.flbtest02

class NativeClass {
	var	NativeThis : Long = 0

	fun	ReleaseInstance() : Unit = NdkLib.NativeAPI.NativeClassReleaseInstance( NativeThis )
	fun	SetParams( a0 : Int, a1 : Short, a2 : Byte, a3 : Long, a4 : Float, a5 : Double ) : Unit = NdkLib.NativeAPI.NativeClassSetParams( NativeThis, a0, a1, a2, a3, a4, a5 )
	fun	GetIntParam() : Int = NdkLib.NativeAPI.NativeClassGetIntParam( NativeThis )
	fun	GetShortParam() : Short = NdkLib.NativeAPI.NativeClassGetShortParam( NativeThis )
	fun	GetByteParam() : Byte = NdkLib.NativeAPI.NativeClassGetByteParam( NativeThis )
	fun	GetLongParam() : Long = NdkLib.NativeAPI.NativeClassGetLongParam( NativeThis )
	fun	GetFloatParam() : Float = NdkLib.NativeAPI.NativeClassGetFloatParam( NativeThis )
	fun	GetDoubleParam() : Double = NdkLib.NativeAPI.NativeClassGetDoubleParam( NativeThis )
	fun	AccessJNIEnv( java_object : Any? ) : Unit = NdkLib.NativeAPI.NativeClassAccessJNIEnv( NativeThis, java_object )
	//-------------------------------------------------------------------------
	fun	setNativeInstance( api : Long )
	{
		NativeThis= api
	}
	fun	getNativeInstance() : Long = NativeThis
	constructor( api : Long = 0 )
	{
		setNativeInstance( api )
	}
	fun	release()
	{
		ReleaseInstance()
		NativeThis= 0
	}
	companion object {
	//-------------------------------------------------------------------------
	fun	create() : NativeClass
	{
		return	NativeClass( NdkLib.NativeAPI.NativeClassCreateInstance() )
	}
	//-------------------------------------------------------------------------
	}
}

