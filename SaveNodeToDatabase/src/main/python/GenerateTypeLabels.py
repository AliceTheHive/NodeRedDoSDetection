classFolder = 'typeLabels'

typeString = """ERROR = -1,

      RETURN = 4,
      BITOR = 9,
      BITXOR = 10,
      BITAND = 11,
      EQ = 12,
      NE = 13,
      LT = 14,
      LE = 15,
      GT = 16,
      GE = 17,
      LSH = 18,
      RSH = 19,
      URSH = 20,
      ADD = 21,
      SUB = 22,
      MUL = 23,
      DIV = 24,
      MOD = 25,
      NOT = 26,
      BITNOT = 27,
      POS = 28,
      NEG = 29,
      NEW = 30,
      DELPROP = 31,
      TYPEOF = 32,
      GETPROP = 33,
      GETELEM = 35,
      CALL = 37,
      NAME = 38,
      NUMBER = 39,
      STRING = 40,
      NULL = 41,
      THIS = 42,
      FALSE = 43,
      TRUE = 44,
      SHEQ = 45, // shallow equality (===)
      SHNE = 46, // shallow inequality (!==)
      REGEXP = 47,
      THROW = 49,
      IN = 51,
      INSTANCEOF = 52,
      ARRAYLIT = 63, // array literal
      OBJECTLIT = 64, // object literal

      TRY = 77,
      PARAM_LIST = 83,
      COMMA = 85, // comma operator

      ASSIGN = 86, // simple assignment  (=)
      ASSIGN_BITOR = 87, // |=
      ASSIGN_BITXOR = 88, // ^=
      ASSIGN_BITAND = 89, // &=
      ASSIGN_LSH = 90, // <<=
      ASSIGN_RSH = 91, // >>=
      ASSIGN_URSH = 92, // >>>=
      ASSIGN_ADD = 93, // +=
      ASSIGN_SUB = 94, // -=
      ASSIGN_MUL = 95, // *=
      ASSIGN_DIV = 96, // /=
      ASSIGN_MOD = 97, // %=

      HOOK = 98, // conditional (?:)
      OR = 100, // logical or (||)
      AND = 101, // logical and (&&)
      INC = 102, // increment (++)
      DEC = 103, // decrement (--)
      FUNCTION = 105, // function keyword
      IF = 108, // if keyword
      SWITCH = 110, // switch keyword
      CASE = 111, // case keyword
      DEFAULT_CASE = 112, // default keyword
      WHILE = 113, // while keyword
      DO = 114, // do keyword
      FOR = 115, // for keyword
      BREAK = 116, // break keyword
      CONTINUE = 117, // continue keyword
      VAR = 118, // var keyword
      WITH = 119, // with keyword
      CATCH = 120, // catch keyword
      VOID = 122, // void keyword

      EMPTY = 124,

      BLOCK = 125, // statement block
      LABEL = 126, // label
      EXPR_RESULT = 130, // expression statement in scripts
      SCRIPT = 132, // top-level node for entire script

      GETTER_DEF = 147,
      SETTER_DEF = 148,

      CONST = 149, // JS 1.5 const keyword
      DEBUGGER = 152,

      // JSCompiler introduced tokens
      LABEL_NAME = 153,
      STRING_KEY = 154, // object literal key
      CAST = 155,

      // ES6
      ARRAY_PATTERN = 156, // destructuring patterns
      OBJECT_PATTERN = 157,

      CLASS = 158, // classes
      CLASS_MEMBERS = 159, // class member container
      MEMBER_FUNCTION_DEF = 160,
      SUPER = 161,

      LET = 162, // block scoped vars

      FOR_OF = 163, // for-of

      YIELD = 164, // generators

      IMPORT = 165, // modules
      IMPORT_SPECS = 166,
      IMPORT_SPEC = 167,
      IMPORT_STAR = 168, // "* as name", called NameSpaceImport in the spec.
      EXPORT = 169,
      EXPORT_SPECS = 170,
      EXPORT_SPEC = 171,
      MODULE = 172,

      REST = 173, // "..." in formal parameters, or an array pattern.
      SPREAD = 174, // "..." in a call expression, or an array literal.

      COMPUTED_PROP = 175,

      TEMPLATELIT = 176, // template literal
      TEMPLATELIT_SUB = 177, // template literal substitution

      DEFAULT_VALUE = 178, // Formal parameter or destructuring element
      // with a default value

      // ECMAScript 6 Typed AST Nodes.

      MEMBER_VARIABLE_DEF = 179,

      // Used by type declaration ASTs
      STRING_TYPE = 200,
      BOOLEAN_TYPE = 201,
      NUMBER_TYPE = 202,
      FUNCTION_TYPE = 203,
      PARAMETERIZED_TYPE = 204,
      UNION_TYPE = 205,
      ANY_TYPE = 206,
      NULLABLE_TYPE = 208,
      VOID_TYPE = 209,
      REST_PARAMETER_TYPE = 210,
      NAMED_TYPE = 211,
      OPTIONAL_PARAMETER = 212,
      RECORD_TYPE = 213,
      UNDEFINED_TYPE = 214,
      ARRAY_TYPE = 215,

      // JSDoc-only tokens
      ANNOTATION = 300,
      PIPE = 301,
      STAR = 302,
      EOC = 303,
      QMARK = 304,
      ELLIPSIS = 305,
      BANG = 306,
      EQUALS = 307,
      LB = 308, // left brackets
      LC = 309, // left curly braces
      COLON = 310,

      // TypeScript
      INTERFACE = 311,
      INTERFACE_EXTENDS = 312,
      INTERFACE_MEMBERS = 313,
      ENUM = 314,
      ENUM_MEMBERS = 315,

      // Token Types to use for internal bookkeeping,
      // an AST is invalid while these are present.
      PLACEHOLDER1 = 1001,
      PLACEHOLDER2 = 1002
"""

lines = typeString.split('\n')

lines = map(lambda l: l.strip(), lines)

lines = map(lambda l: l.replace(',', ''), lines)

lines = filter(lambda l: l != '', lines)

lines = filter(lambda l: not l.startswith('//'), lines)

lines = map(lambda l: l.split('//'), lines)
lines = map(lambda l: l[0], lines)

lines = map(lambda l: l.replace(' =', ''), lines)
lines = map(lambda l: l.strip(), lines)

typeAndId = map(lambda l: l.split(' '), lines)
for t in typeAndId:
    if len(t) != 2:
        print len(t)

classNames = map(lambda t: t[0].lower().capitalize(), typeAndId)
classNames = map(lambda cn: cn + 'Label', classNames)

for (i, (t, ID)) in enumerate(typeAndId):
    f = file(classFolder + '/' + classNames[i] + '.java', 'w')
    f.write("""package db.types;
import org.neo4j.graphdb.Label;
""")
    f.write("public class " + classNames[i] + " implements Label {\n")
    f.write('@Override\n')
    f.write('public String name() {\n')
    f.write('return "AST_TYPE_' + t + '";\n')
    f.write('}\n')
    f.write("}")
    f.close()

f = file('AstTypeLabelsGenerated.java', 'w')

f.write('package db;\n')
f.write('import db.typeLabels.*;\n')
f.write('import org.neo4j.graphdb.Label;\n')
f.write('class AstTypeLabelsGenerated {\n')
f.write('public static Label typeToLabel(int type) {\n')
f.write('switch (type) {\n')
for (i, (t, ID)) in enumerate(typeAndId):
    f.write('case ' + ID + ':\n')
    f.write('return new ' + classNames[i] + '();\n')
f.write('default: throw new IllegalStateException("Unknown Labeltype");\n')
f.write('}\n')
f.write('}\n')

f.write('public static int labelToType(Label label) {\n')
for (i, (t, ID)) in enumerate(typeAndId):
      f.write('if (label instanceof ' + classNames[i] + ') {\n')
      f.write('return ' + ID + ';\n')
      f.write('}\n')
f.write('throw new IllegalStateException("Unknown label class");\n')
f.write('}\n')
f.write('}\n')
