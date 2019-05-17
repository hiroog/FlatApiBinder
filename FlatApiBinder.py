# FlatApiBinder 2019/05/11 Hiroyuki Ogasawara
# vim:ts=4 sw=4 et:

# for windows
#  1. download and install prebuilt package (LLVM-X.X.X-win64.exe) from llvm.org
#  2. add "LLVM/bin" directory to PATH
#  3. pip3 install clang
#
# for Debian/Ubuntu
#  1. apt install clang
#  2. pip3 install clang
#  3. export LD_LIBRARY_PATH=`llvm-config-X.X --libdir`

import  sys
import  os
from clang import cindex


g_indent= ''
def iprint( *args ):
    print( g_indent, *args )


class BArg:
    def __init__( self, name, btype ):
        self.name= name
        self.btype= btype


class BFunction:
    def __init__( self, name, btype, arg_list, is_static, is_method, jni_arg_list ):
        self.name= name
        self.btype= btype
        self.arg_list= arg_list
        self.jni_arg_list= jni_arg_list
        self.is_static= is_static
        self.is_method= is_method


class BClass:
    def __init__( self, name, is_root, package ):
        self.name= name
        self.is_root= is_root
        self.func_list= []
        self.package= package

    def add_func( self, func ):
        self.func_list.append( func )




class HeaderDecoder:

    def __init__( self ):
        self.func_table= {
                cindex.CursorKind.FUNCTION_DECL         :   self.p_function,
                cindex.CursorKind.CXX_METHOD            :   self.p_function,
                cindex.CursorKind.CLASS_DECL            :   self.p_class,
                cindex.CursorKind.CXX_ACCESS_SPEC_DECL  :   self.p_access_spec,
                cindex.CursorKind.VAR_DECL              :   self.p_var,
            }
        self.endfunc_table= {
                cindex.CursorKind.CLASS_DECL            :   self.p_class_end,
            }
        self.class_name= 'NdkDefault'
        self.class_stack= []
        self.class_list= {}
        self.class_access= False
        self.set_classpath( 'com.example.NdkDefault' )
        self.dll_name= 'native-lib'
        self.java_root= '.'
        self.cpp_root= '.'
        self.include_list= []

    def add_class( self, class_name, is_root= False ):
        self.class_name= class_name
        self.class_list[ class_name ]= BClass( class_name, is_root, self.package )
        if is_root:
            self.root_class= class_name
            self.root_package= self.package

    def set_classpath( self, class_path ):
        node_list= class_path.split('.')
        self.package= '.'.join( node_list[:-1] )
        self.add_class( node_list[-1], True )

    def find_string( self, cursor ):
        if cursor.kind == cindex.CursorKind.STRING_LITERAL:
            return  cursor.spelling.strip( '"' )
        for ch in cursor.get_children():
            result= self.find_string( ch )
            if result is not None:
                return  result
        return  None

    def p_var( self, cursor ):
        if cursor.spelling == 'FLAPIBINDER_ClassPath':
            path= self.find_string( cursor )
            self.set_classpath( path )
            print( 'Set PACKAGE   =', self.package )
        elif cursor.spelling == 'FLAPIBINDER_Package':
            path= self.find_string( cursor )
            self.package= path
            print( 'Set PACKAGE   =', self.package )
        elif cursor.spelling == 'FLAPIBINDER_DllName':
            name= self.find_string( cursor )
            self.dll_name= name
        elif cursor.spelling == 'FLAPIBINDER_JavaRoot':
            root= self.find_string( cursor )
            self.java_root= root
        elif cursor.spelling == 'FLAPIBINDER_CppRoot':
            root= self.find_string( cursor )
            self.cpp_root= root
        elif cursor.spelling == 'FLAPIBINDER_Includes':
            path= self.find_string( cursor )
            self.include_list.extend( path.split( ';' ) )

    def p_function( self, cursor ):
        class_name= self.class_name
        if cursor.kind == cindex.CursorKind.CXX_METHOD:
            if not self.class_access:
                return
            is_static= cursor.is_static_method()
            is_method= True
        else:
            is_static= True
            is_method= False
        arg_list= []
        jni_arg_list= []
        index= 0
        for arg in cursor.get_arguments():
            if arg.kind == cindex.CursorKind.PARM_DECL:
                arg_name= arg.spelling
                if arg_name == '':
                    arg_name= 'aRg%02d_' % index
                print( 'TYPE',arg.type.spelling )
                if index == 0 and arg.type.spelling == 'JNIEnv *':
                    jni_arg_list.append( BArg( arg_name, arg.type.spelling ) )
                elif index == 1 and arg.type.spelling == 'jobject' and jni_arg_list != []:
                    jni_arg_list.append( BArg( arg_name, arg.type.spelling ) )
                else:
                    arg_list.append( BArg( arg_name, arg.type.spelling ) )
                index+= 1
        self.class_list[ class_name ].add_func( BFunction( cursor.spelling, cursor.result_type.spelling, arg_list, is_static, is_method, jni_arg_list ) )

    def p_class( self, cursor ):
        self.class_stack.append( self.class_name )
        self.add_class( cursor.spelling )

    def p_class_end( self, cursor ):
        self.class_name= self.class_stack.pop()

    def p_access_spec( self, cursor ):
        if cursor.access_specifier == cindex.AccessSpecifier.PUBLIC:
            self.class_access= True
        else:
            self.class_access= False


    def dump_cursor( self, cursor, nest= 0 ):
        global g_indent
        g_indent= ' ' * nest
        iprint( '((%s : %s))' % (cursor.kind.name, cursor.displayname) )
        #iprint( '((COMMENT=%s))' % cursor.brief_comment )
        if cursor.kind == cindex.CursorKind.FUNCTION_DECL:
            iprint( '  *NAME     = ', cursor.spelling )
            iprint( '  *RET TYPE = ', cursor.result_type.spelling )
            iprint( '  *STORAGE  = ', cursor.storage_class )
            iprint( '  *ARGS     = ', cursor.get_arguments() )
        elif cursor.kind == cindex.CursorKind.PARM_DECL:
            iprint( '  *NAME     = ', cursor.spelling )
            iprint( '  *TYPE     = ', cursor.type.spelling )
        elif cursor.kind == cindex.CursorKind.CLASS_DECL:
            iprint( '  *NAME     = ', cursor.spelling )
            iprint( '  *TYPE     = ', cursor.type.spelling )
        elif cursor.kind == cindex.CursorKind.CXX_ACCESS_SPEC_DECL:
            iprint( '  *ACC-SPEC = ', cursor.access_specifier )
        elif cursor.kind == cindex.CursorKind.CXX_METHOD:
            iprint( '  *NAME     = ', cursor.spelling )
            iprint( '  *RET TYPE = ', cursor.result_type.spelling )
            iprint( '  *STORAGE  = ', cursor.storage_class )
            iprint( '  *CLASS    = ', cursor.type.spelling )
        elif cursor.kind == cindex.CursorKind.VAR_DECL:
            iprint( '  *NAME     = ', cursor.spelling )
            iprint( '  *TYPE     = ', cursor.type.spelling )
        elif cursor.kind == cindex.CursorKind.UNEXPOSED_EXPR:
            iprint( '  *NAME     = ', cursor.spelling )
            iprint( '  *TYPE     = ', cursor.type.spelling )
        elif cursor.kind == cindex.CursorKind.STRING_LITERAL:
            iprint( '  *NAME     = ', cursor.spelling )
            iprint( '  *TYPE     = ', cursor.type.spelling )
        for ch in cursor.get_children():
            self.dump_cursor( ch, nest + 1 )


    def decodeHeader( self, cursor ):
        if cursor.kind in self.func_table:
            self.func_table[ cursor.kind ]( cursor )

        for ch in cursor.get_children():
            self.decodeHeader( ch )

        if cursor.kind in self.endfunc_table:
            self.endfunc_table[ cursor.kind ]( cursor )


    def dump_api( self ):
        print( 'Package:', self.package )
        for class_name in self.class_list:
            api= self.class_list[ class_name ]
            print( 'Class', api.name )
            for func in api.func_list:
                print( '  Func:', func.name )
                print( '    Type: [%s]' % func.btype )
                for arg in func.arg_list:
                    print( '    Arg: [%s] %s' % (arg.btype, arg.name) )




class CodeGenerator:

    def __init__( self ):
        pass

    def getType( self, btype ):
        return  btype

    def getArgList( self, arg_list ):
        comma_flag= False
        param= ''
        for arg in arg_list:
            if comma_flag:
                param+= ','
            param+= ' %s %s' % (self.getType( arg.btype ), arg.name)
            comma_flag= True
        if comma_flag:
            param+= ' '
        return  param

    def getDeclFunction( self, func, func_prefix= '', arg_prefix= '', pre_arg= None ):
        comma_flag= False
        param= func_prefix + func.name + '('
        if pre_arg:
            param+= pre_arg
            comma_flag= True
        for arg in func.arg_list:
            if comma_flag:
                param+= ','
            param+= ' ' + self.getType( arg.btype )
            param+= ' ' + arg_prefix + arg.name
            comma_flag= True
        if comma_flag:
            param+= ' '
        param+= ')'
        return  param

    def getCallFunction( self, func, func_prefix= '', arg_prefix= '', pre_arg= None ):
        comma_flag= False
        param= func_prefix + func.name + '('
        if pre_arg:
            param+= pre_arg
            comma_flag= True
        for arg in func.arg_list:
            if comma_flag:
                param+= ','
            param+= ' ' + arg_prefix + arg.name
            comma_flag= True
        if comma_flag:
            param+= ' '
        param+= ')'
        return  param

    def packageToPath( self, package ):
        return  package.replace( '.', '/' )

    def getPackageFolder( self, output_dir, package ):
        path= os.path.join( output_dir, self.packageToPath( package ) )
        if not os.path.exists( path ):
            os.makedirs( path )
        return  path

    def hasFactory( self, api ):
        has_create= None
        has_release= None
        for func in api.func_list:
            if func.name == 'CreateInstance':
                has_create= func
            elif func.name == 'ReleaseInstance':
                has_release= func
        return  has_create,has_release


class JavaGenerator( CodeGenerator ):

    BTYPE_TABLE= {
            'void'              :       'void',
            'int'               :       'int',
            'unsigned int'      :       'int',
            'short'             :       'short',
            'unsigned short'    :       'short',
            'char'              :       'byte',
            'signed char'       :       'byte',
            'unsigned char'     :       'byte',
            'long long'         :       'long',
            'unsigned long long':       'long',
            'float'             :       'float',
            'double'            :       'double',
            'bool'              :       'boolean',
            'const char *'      :       'String',
            'jobject'           :       'Object',
            'void *'            :       'long',
            'int8_t'            :       'byte',
            'uint8_t'           :       'byte',
            'int16_t'           :       'short',
            'uint16_t'          :       'short',
            'int32_t'           :       'int',
            'uint32_t'          :       'int',
            'int64_t'           :       'long',
            'uint64_t'          :       'long',
            'char16_t'          :       'char',
        }

    def __init__( self ):
        pass

    def getType( self, btype ):
        if btype in self.BTYPE_TABLE:
            return  self.BTYPE_TABLE[btype]
        if btype[-1] == '*':
            return  'long'
        return  'ERROR_TYPE_'+btype


    def generateNativeClass( self, output_dir, class_list, package, root_class, dll_name ):
        output_file= os.path.join( self.getPackageFolder( output_dir, package ), root_class + '.java' )
        print( 'output=', output_file )
        with open( output_file, 'w', encoding= 'UTF-8' ) as fo:
            fo.write( '// Auto generated file\n' )
            fo.write( '// vim:ts=4 sw=4 et:\n' )
            fo.write( 'package %s;\n' % package )
            fo.write( '\n' )
            fo.write( 'public class %s {\n' % root_class )
            for class_name in class_list:
                api= class_list[class_name]
                fo.write( '\n' )
                fo.write( '\t// %s\n' % api.name )
                for func in api.func_list:
                    func_prefix= ''
                    this_arg= None
                    if not api.is_root:
                        func_prefix= api.name
                    if not func.is_static:
                        this_arg= ' long jj_this'
                    fo.write( '\tpublic native %s\t%s;\n' % (self.getType( func.btype ), self.getDeclFunction( func, func_prefix, '', this_arg )) )
            fo.write( '\n' )
            fo.write( '\t//-------------------------------------------------------------------------\n' )
            fo.write( '\tstatic {\n' )
            fo.write( '\t\tSystem.loadLibrary("%s");\n' % dll_name )
            fo.write( '\t}\n' )
            fo.write( '\tpublic static %s\tNativeAPI= new %s();\n' % (root_class, root_class) )
            fo.write( '\tpublic static %s\tgetAPI()\n' % root_class )
            fo.write( '\t{\n' )
            fo.write( '\t\treturn\tNativeAPI;\n' )
            fo.write( '\t}\n' )
            fo.write( '}\n\n' )


    def generateApiClass( self, output_dir, class_list, root_package, root_class ):
        for class_name in class_list:
            api= class_list[class_name]
            if api.is_root or api.func_list == []:
                continue
            output_file= os.path.join( self.getPackageFolder( output_dir, api.package ), api.name + '.java' )
            print( 'output=', output_file )
            with open( output_file, 'w', encoding= 'UTF-8' ) as fo:
                fo.write( '// Auto generated file\n' )
                fo.write( '// vim:ts=4 sw=4 et:\n' )
                fo.write( 'package\t%s;\n' % api.package )
                if root_package != api.package:
                    fo.write( 'import\t%s.%s;\n' % (root_package, root_class) )
                fo.write( '\n' )
                fo.write( 'public class %s {\n' % api.name )
                fo.write( '\tlong\tNativeThis;\n' )
                fo.write( '\n' )
                has_create,has_release= self.hasFactory( api )
                for func in api.func_list:
                    func_static= ''
                    if func.is_static:
                        func_static= 'static '
                    fo.write( '\tpublic %s%s\t%s(%s)\n' % (func_static, self.getType( func.btype ), func.name, self.getArgList( func.arg_list )) )
                    fo.write( '\t{\n' )
                    this_arg= None
                    if not func.is_static:
                        this_arg= ' NativeThis'
                    func_result= ''
                    if func.btype != 'void':
                        func_result= 'return\t'
                    fo.write( '\t\t%s%s.NativeAPI.%s;\n' % (func_result, root_class, self.getCallFunction( func, api.name, '', this_arg )) )
                    fo.write( '\t}\n' )
                fo.write( '\t//-------------------------------------------------------------------------\n' )
                fo.write( '\tpublic void\tsetNativeInstance( long api )\n' )
                fo.write( '\t{\n' )
                fo.write( '\t\tNativeThis= api;\n' )
                fo.write( '\t}\n' )
                fo.write( '\tpublic long\tgetNativeInstance()\n' )
                fo.write( '\t{\n' )
                fo.write( '\t\treturn\tNativeThis;\n' )
                fo.write( '\t}\n' )
                fo.write( '\tpublic %s( long api )\n' % api.name )
                fo.write( '\t{\n' )
                fo.write( '\t\tsetNativeInstance( api );\n' )
                fo.write( '\t}\n' )
                if has_create:
                    fo.write( '\tpublic %s(%s)\n' % (api.name, self.getArgList( has_create.arg_list )) )
                    fo.write( '\t{\n' )
                    fo.write( '\t\tsetNativeInstance( %s );\n' % self.getCallFunction( has_create, '', '' ) )
                    fo.write( '\t}\n' )
                if has_release:
                    fo.write( '\tpublic void\trelease(%s)\n' % self.getArgList( has_release.arg_list ) )
                    fo.write( '\t{\n' )
                    fo.write( '\t\t%s;\n' % self.getCallFunction( has_release ) )
                    fo.write( '\t\tNativeThis= 0;\n' )
                    fo.write( '\t}\n' )
                fo.write( '}\n\n' )


class KotlinGenerator( CodeGenerator ):

    BTYPE_TABLE= {
            'void'              :       'Unit',
            'int'               :       'Int',
            'unsigned int'      :       'Int',
            'short'             :       'Short',
            'unsigned short'    :       'Short',
            'char'              :       'Byte',
            'signed char'       :       'Byte',
            'unsigned char'     :       'Byte',
            'long long'         :       'Long',
            'unsigned long long':       'Long',
            'float'             :       'Float',
            'double'            :       'Double',
            'bool'              :       'Boolean',
            'const char *'      :       'String',
            'jobject'           :       'Any?',
            'void *'            :       'Long',
            'int8_t'            :       'Byte',
            'uint8_t'           :       'Byte',
            'int16_t'           :       'Short',
            'uint16_t'          :       'Short',
            'int32_t'           :       'Int',
            'uint32_t'          :       'Int',
            'int64_t'           :       'Long',
            'uint64_t'          :       'Long',
            'char16_t'          :       'Char',
        }

    def __init__( self ):
        pass

    def getArgList( self, arg_list ):
        comma_flag= False
        param= ''
        for arg in arg_list:
            if comma_flag:
                param+= ','
            param+= ' %s : %s' % (arg.name, self.getType( arg.btype ))
            comma_flag= True
        if comma_flag:
            param+= ' '
        return  param

    def getDeclFunction( self, func, func_prefix= '', arg_prefix= '', pre_arg= None ):
        comma_flag= False
        param= func_prefix + func.name + '('
        if pre_arg:
            param+= pre_arg
            comma_flag= True
        for arg in func.arg_list:
            if comma_flag:
                param+= ','
            param+= ' ' + arg_prefix + arg.name
            param+= ' : ' + self.getType( arg.btype )
            comma_flag= True
        if comma_flag:
            param+= ' '
        param+= ')'
        return  param

    def getType( self, btype ):
        if btype in self.BTYPE_TABLE:
            return  self.BTYPE_TABLE[btype]
        if btype[-1] == '*':
            return  'Long'
        return  'ERROR_TYPE_'+btype


    def generateNativeClass( self, output_dir, class_list, package, root_class, dll_name ):
        output_file= os.path.join( self.getPackageFolder( output_dir, package ), root_class + '.kt' )
        print( 'output=', output_file )
        with open( output_file, 'w', encoding= 'UTF-8' ) as fo:
            fo.write( '// Auto generated file\n' )
            fo.write( '// vim:ts=4 sw=4 et:\n' )
            fo.write( 'package %s\n' % package )
            fo.write( '\n' )
            fo.write( 'class %s {\n' % root_class )
            for class_name in class_list:
                api= class_list[class_name]
                fo.write( '\n' )
                fo.write( '\t// %s\n' % api.name )
                for func in api.func_list:
                    func_prefix= ''
                    this_arg= None
                    if not api.is_root:
                        func_prefix= api.name
                    if not func.is_static:
                        this_arg= ' jj_this : Long'
                    fo.write( '\texternal fun\t%s : %s\n' % (self.getDeclFunction( func, func_prefix, '', this_arg ), self.getType( func.btype )) )
            fo.write( '\n' )
            fo.write( '\t//-------------------------------------------------------------------------\n' )
            fo.write( '\tcompanion object {\n' )
            fo.write( '\t\tinit {\n' )
            fo.write( '\t\t\tSystem.loadLibrary("%s")\n' % dll_name )
            fo.write( '\t\t}\n' )
            fo.write( '\t\tvar\tNativeAPI : %s = %s()\n' % (root_class, root_class) )
            fo.write( '\t\tfun getAPI() : %s = NativeAPI\n' % root_class )
            fo.write( '\t}\n' )
            fo.write( '}\n\n' )


    def generateApiClass( self, output_dir, class_list, root_package, root_class ):
        for class_name in class_list:
            api= class_list[class_name]
            if api.is_root or api.func_list == []:
                continue
            output_file= os.path.join( self.getPackageFolder( output_dir, api.package ), api.name + '.kt' )
            print( 'output=', output_file )
            with open( output_file, 'w', encoding= 'UTF-8' ) as fo:
                fo.write( '// Auto generated file\n' )
                fo.write( '// vim:ts=4 sw=4 et:\n' )
                fo.write( 'package\t%s\n' % api.package )
                if root_package != api.package:
                    fo.write( 'import\t%s.%s\n' % (root_package, root_class) )
                fo.write( '\n' )
                fo.write( 'class %s {\n' % api.name )
                fo.write( '\tvar\tNativeThis : Long = 0\n' )
                fo.write( '\n' )
                has_create,has_release= self.hasFactory( api )
                has_static= False
                for func in api.func_list:
                    if func.name == 'CreateInstance':
                        continue
                    if func.is_static:
                        has_static= True
                        continue
                    fo.write( '\tfun\t%s(%s) : %s = ' % (func.name, self.getArgList( func.arg_list ), self.getType( func.btype )) )
                    this_arg= ' NativeThis'
                    fo.write( '%s.NativeAPI.%s\n' % (root_class, self.getCallFunction( func, api.name, '', this_arg )) )
                fo.write( '\t//-------------------------------------------------------------------------\n' )
                fo.write( '\tfun\tsetNativeInstance( api : Long )\n' )
                fo.write( '\t{\n' )
                fo.write( '\t\tNativeThis= api\n' )
                fo.write( '\t}\n' )
                fo.write( '\tfun\tgetNativeInstance() : Long = NativeThis\n' )
                fo.write( '\tconstructor( api : Long )\n' )
                fo.write( '\t{\n' )
                fo.write( '\t\tsetNativeInstance( api )\n' )
                fo.write( '\t}\n' )
                if has_create:
                    fo.write( '\tconstructor(%s)\n' % self.getArgList( has_create.arg_list ) )
                    fo.write( '\t{\n' )
                    fo.write( '\t\tsetNativeInstance( %s.NativeAPI.%s )\n' % (root_class, self.getCallFunction( has_create, api.name )) )
                    fo.write( '\t}\n' )
                if has_release:
                    fo.write( '\tfun\trelease(%s)\n' % self.getArgList( has_release.arg_list ) )
                    fo.write( '\t{\n' )
                    fo.write( '\t\t%s\n' % self.getCallFunction( has_release ) )
                    fo.write( '\t\tNativeThis= 0\n' )
                    fo.write( '\t}\n' )
                if has_static:
                    fo.write( '\tcompanion object {\n' )
                    fo.write( '\t//-------------------------------------------------------------------------\n' )
                    for func in api.func_list:
                        if not func.is_static:
                            continue
                        fo.write( '\tfun\t%s(%s) : %s = ' % (func.name, self.getArgList( func.arg_list ), self.getType( func.btype )) )
                        this_arg= None
                        fo.write( '%s.NativeAPI.%s\n' % (root_class, self.getCallFunction( func, api.name, '', this_arg )) )
                    fo.write( '\t//-------------------------------------------------------------------------\n' )
                    fo.write( '\t}\n' )
                fo.write( '}\n\n' )


class CppGenerator( CodeGenerator ):

    BTYPE_TABLE= {
            'void'              :       'void',
            'int'               :       'jint',
            'unsigned int'      :       'jint',
            'short'             :       'jshort',
            'unsigned short'    :       'jshort',
            'char'              :       'jbyte',
            'signed char'       :       'jbyte',
            'unsigned char'     :       'jbyte',
            'long long'         :       'jlong',
            'unsigned long long':       'jlong',
            'float'             :       'jfloat',
            'double'            :       'jdouble',
            'bool'              :       'jboolean',
            'const char *'      :       'jstring',
            'jobject'           :       'jobject',
            'void *'            :       'jlong',
            'int8_t'            :       'jbyte',
            'uint8_t'           :       'jbyte',
            'int16_t'           :       'jshort',
            'uint16_t'          :       'jshort',
            'int32_t'           :       'jint',
            'uint32_t'          :       'jint',
            'int64_t'           :       'jlong',
            'uint64_t'          :       'jlong',
            'char16_t'          :       'jchar',
        }

    def __init__( self ):
        pass

    def getType( self, btype ):
        if btype in self.BTYPE_TABLE:
            return  self.BTYPE_TABLE[btype]
        if btype[-1] == '*':
            return  'jlong'
        return  'ERROR_TYPE_'+btype

    def getJniName( self, package ):
        path_list= package.split( '.' )
        result= 'Java_'
        for path in path_list:
            if '_' in path:
                path= path.replace( '_', '_1' )
            result+= path + '_'
        return  result

    def generateCode( self, output_dir, class_list, package, root_class, include_list ):
        output_file= os.path.join( output_dir, root_class + '.cpp' )
        print( 'output=', output_file )
        with open( output_file, 'w', encoding= 'UTF-8' ) as fo:
            fo.write( '// Auto generated file\n' )
            fo.write( '// vim:ts=4 sw=4 et:\n' )
            fo.write( '#include <jni.h>\n' )
            for include_file in include_list:
                fo.write( '#include "%s"\n' % include_file )
            fo.write( '\n' )
            fo.write( 'extern "C" {\n' )
            fo.write( '//-----------------------------------------------------------------------------\n' )
            fo.write( '\n' )
            for class_name in class_list:
                api= class_list[class_name]
                fo.write( '\n' )
                fo.write( '// %s\n' % api.name )
                for func in api.func_list:
                    func_prefix= self.getJniName( package + '.' + root_class )
                    this_arg= ' JNIEnv* env, jobject tobj'
                    if not api.is_root:
                        func_prefix+= api.name
                    if not func.is_static:
                        this_arg+= ', jlong jj_This_'
                    fo.write( 'JNIEXPORT %s JNICALL\t%s\n' % (self.getType( func.btype ), self.getDeclFunction( func, func_prefix, 'jj_', this_arg )) )
                    fo.write( '{\n' )
                    string_release_list= []
                    for arg in func.arg_list:
                        if arg.btype == 'const char *':
                            fo.write( '\tconst char*\tcc_%s= env->GetStringUTFChars( jj_%s, nullptr );\n' % (arg.name, arg.name) )
                            string_release_list.append( arg.name )
                        elif arg.btype[-1] == '*':
                            fo.write( '\tauto\tcc_%s= reinterpret_cast<%s>( static_cast<intptr_t>( jj_%s ) );\n' % (arg.name, arg.btype, arg.name) )
                        elif arg.btype == self.getType( arg.btype ):
                            fo.write( '\tconst auto&\tcc_%s= jj_%s;\n' % (arg.name, arg.name) )
                        else:
                            fo.write( '\tauto\tcc_%s= static_cast<%s>( jj_%s );\n' % (arg.name, arg.btype, arg.name) )
                    func_result= ''
                    func_method= ''
                    jni_arg= ''
                    if not func.is_static:
                        fo.write( '\tauto*\tcc_This_= reinterpret_cast<%s*>(static_cast<intptr_t>(jj_This_));\n' % api.name )
                        func_method= 'cc_This_->'
                    elif func.is_method:
                        func_method= api.name + '::'
                    if func.btype != 'void':
                        func_result= 'auto\tcc_result_= '
                    if func.jni_arg_list != []:
                        if len(func.jni_arg_list) >= 2:
                            jni_arg= ' env, tobj'
                        else:
                            jni_arg= ' env'
                    fo.write( '\t%s%s%s;\n' % (func_result, func_method, self.getCallFunction( func, '', 'cc_', jni_arg )) )
                    for rel_str in string_release_list:
                        fo.write( '\tenv->ReleaseStringUTFChars( jj_%s, cc_%s );\n' % (rel_str, rel_str) )
                    if func.btype != 'void':
                        if func.btype == 'const char *':
                            fo.write( '\tauto\tjj_result_= env->NewStringUTF( cc_result_ );\n' )
                        elif func.btype[-1] == '*':
                            fo.write( '\tauto\tjj_result_= static_cast<%s>( reinterpret_cast<intptr_t>(cc_result_) );\n' % self.getType( func.btype ) )
                        elif func.btype == self.getType( func.btype ):
                            fo.write( '\tconst auto&\tjj_result_= cc_result_;\n' )
                        else:
                            fo.write( '\tauto\tjj_result_= static_cast<%s>( cc_result_ );\n' % self.getType( func.btype ) )
                        fo.write( '\treturn\tjj_result_;\n' )
                    fo.write( '}\n' )
                    fo.write( '\n' )
            fo.write( '\n' )
            fo.write( '//-----------------------------------------------------------------------------\n' )
            fo.write( '}\n\n' )




class ApiBinder:

    def __init__( self ):
        self.index= cindex.Index.create()

    def generateCode( self, header_list, dll_name, java_root, cpp_root, include_list, class_path, use_kotlin ):
        decoder= HeaderDecoder()

        if class_path is not None:
            decoder.set_classpath( class_path )

        for header_file in header_list:
            if not os.path.exists( header_file ):
                raise FileNotFoundError( 0, 'File not found', header_file )
            tu= self.index.parse(
                        header_file,
                        ['-std=c++17', '-DFLAPIBINDER=1'],
                        None,
                        cindex.TranslationUnit.PARSE_SKIP_FUNCTION_BODIES
                    )

            #decoder.dump_cursor( tu.cursor )

            decoder.decodeHeader( tu.cursor )

        if dll_name is None:
            dll_name= decoder.dll_name
        if java_root is None:
            java_root= decoder.java_root
        if cpp_root is None:
            cpp_root= decoder.cpp_root
        include_list.extend( decoder.include_list )

        decoder.dump_api()

        if use_kotlin:
            java_generator= KotlinGenerator()
        else:
            java_generator= JavaGenerator()
        java_generator.generateNativeClass( java_root, decoder.class_list, decoder.root_package, decoder.root_class, dll_name )
        java_generator.generateApiClass( java_root, decoder.class_list, decoder.root_package, decoder.root_class )

        cpp_generator= CppGenerator()
        cpp_generator.generateCode( cpp_root, decoder.class_list, decoder.root_package, decoder.root_class, include_list )




def usage():
    print( 'API Binder v1.00 2019 Hiroyuki Ogasawara' )
    print( 'usage: FlatAPIBinder.py [options] <header_file.h> ...' )
    print( 'option' )
    print( '   --dll <dll_name>             default \'native-lib\'' )
    print( '   --java <java output root>    default \'.\'' )
    print( '   --cpp <cpp output path>      default \'.\'' )
    print( '   --kotlin' )
    print( '   --include <include file>' )
    print( '   --classpath <jni_class_path> default \'com.example.NdkDefault\'' )
    print( '\nex. python3 FlatApiBinder.py --cpp app/src/main/cpp --java app/src/main/java --include NativeInterface.h --dll native-lib --classpath com.example.testapp.NdkRoot app/src/main/cpp/NativeInterface.h' )
    sys.exit( 0 )


def main( argv ):
    header_list= []
    dll_name= None
    java_root= None
    cpp_root= None
    class_path= None
    include_list= []
    use_kotlin= False
    acount= len(argv)
    ai= 1
    while ai < acount:
        arg= argv[ai]
        if arg[0] == '-':
            if arg == '--dll':
                if ai+1 < acount:
                    ai+= 1
                    dll_name= argv[ai]
            elif arg == '--java':
                if ai+1 < acount:
                    ai+= 1
                    java_root= argv[ai]
            elif arg == '--cpp':
                if ai+1 < acount:
                    ai+= 1
                    cpp_root= argv[ai]
            elif arg == '--include':
                if ai+1 < acount:
                    ai+= 1
                    include_list.append( argv[ai] )
            elif arg == '--classpath':
                if ai+1 < acount:
                    ai+= 1
                    class_path= argv[ai]
            elif arg == '--kotlin':
                use_kotlin= True
            else:
                usage()
        else:
            header_list.append( arg )
        ai+= 1

    if header_list == []:
        usage()

    binder= ApiBinder()
    binder.generateCode( header_list, dll_name, java_root, cpp_root, include_list, class_path, use_kotlin )
    return  0


if __name__=='__main__':
    sys.exit( main( sys.argv ) )



#python3 FlatApiBinder.py --cpp app/src/main/cpp --java app/src/main/java --include NativeInterface.h --dll native-lib app/src/main/cpp/NativeInterface.h
#python3 FlatApiBinder.py --cpp app/src/main/cpp --java app/src/main/java --include NativeInterface.h --dll native-lib app/src/main/cpp/NativeInterface.h --kotlin


