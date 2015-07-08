package db.Labels;

import db.Labels.astTypeLabels.*;
import org.neo4j.graphdb.Label;

class AstTypeLabelsGenerated {
	public static Label typeToLabel(int type) {
		switch (type) {
			case -1:
				return new ErrorLabel();
			case 4:
				return new ReturnLabel();
			case 9:
				return new BitorLabel();
			case 10:
				return new BitxorLabel();
			case 11:
				return new BitandLabel();
			case 12:
				return new EqLabel();
			case 13:
				return new NeLabel();
			case 14:
				return new LtLabel();
			case 15:
				return new LeLabel();
			case 16:
				return new GtLabel();
			case 17:
				return new GeLabel();
			case 18:
				return new LshLabel();
			case 19:
				return new RshLabel();
			case 20:
				return new UrshLabel();
			case 21:
				return new AddLabel();
			case 22:
				return new SubLabel();
			case 23:
				return new MulLabel();
			case 24:
				return new DivLabel();
			case 25:
				return new ModLabel();
			case 26:
				return new NotLabel();
			case 27:
				return new BitnotLabel();
			case 28:
				return new PosLabel();
			case 29:
				return new NegLabel();
			case 30:
				return new NewLabel();
			case 31:
				return new DelpropLabel();
			case 32:
				return new TypeofLabel();
			case 33:
				return new GetpropLabel();
			case 35:
				return new GetelemLabel();
			case 37:
				return new CallLabel();
			case 38:
				return new NameLabel();
			case 39:
				return new NumberLabel();
			case 40:
				return new StringLabel();
			case 41:
				return new NullLabel();
			case 42:
				return new ThisLabel();
			case 43:
				return new FalseLabel();
			case 44:
				return new TrueLabel();
			case 45:
				return new SheqLabel();
			case 46:
				return new ShneLabel();
			case 47:
				return new RegexpLabel();
			case 49:
				return new ThrowLabel();
			case 51:
				return new InLabel();
			case 52:
				return new InstanceofLabel();
			case 63:
				return new ArraylitLabel();
			case 64:
				return new ObjectlitLabel();
			case 77:
				return new TryLabel();
			case 83:
				return new Param_listLabel();
			case 85:
				return new CommaLabel();
			case 86:
				return new AssignLabel();
			case 87:
				return new Assign_bitorLabel();
			case 88:
				return new Assign_bitxorLabel();
			case 89:
				return new Assign_bitandLabel();
			case 90:
				return new Assign_lshLabel();
			case 91:
				return new Assign_rshLabel();
			case 92:
				return new Assign_urshLabel();
			case 93:
				return new Assign_addLabel();
			case 94:
				return new Assign_subLabel();
			case 95:
				return new Assign_mulLabel();
			case 96:
				return new Assign_divLabel();
			case 97:
				return new Assign_modLabel();
			case 98:
				return new HookLabel();
			case 100:
				return new OrLabel();
			case 101:
				return new AndLabel();
			case 102:
				return new IncLabel();
			case 103:
				return new DecLabel();
			case 105:
				return new FunctionLabel();
			case 108:
				return new IfLabel();
			case 110:
				return new SwitchLabel();
			case 111:
				return new CaseLabel();
			case 112:
				return new Default_caseLabel();
			case 113:
				return new WhileLabel();
			case 114:
				return new DoLabel();
			case 115:
				return new ForLabel();
			case 116:
				return new BreakLabel();
			case 117:
				return new ContinueLabel();
			case 118:
				return new VarLabel();
			case 119:
				return new WithLabel();
			case 120:
				return new CatchLabel();
			case 122:
				return new VoidLabel();
			case 124:
				return new EmptyLabel();
			case 125:
				return new BlockLabel();
			case 126:
				return new LabelLabel();
			case 130:
				return new Expr_resultLabel();
			case 132:
				return new ScriptLabel();
			case 147:
				return new Getter_defLabel();
			case 148:
				return new Setter_defLabel();
			case 149:
				return new ConstLabel();
			case 152:
				return new DebuggerLabel();
			case 153:
				return new Label_nameLabel();
			case 154:
				return new String_keyLabel();
			case 155:
				return new CastLabel();
			case 156:
				return new Array_patternLabel();
			case 157:
				return new Object_patternLabel();
			case 158:
				return new ClassLabel();
			case 159:
				return new Class_membersLabel();
			case 160:
				return new Member_function_defLabel();
			case 161:
				return new SuperLabel();
			case 162:
				return new LetLabel();
			case 163:
				return new For_ofLabel();
			case 164:
				return new YieldLabel();
			case 165:
				return new ImportLabel();
			case 166:
				return new Import_specsLabel();
			case 167:
				return new Import_specLabel();
			case 168:
				return new Import_starLabel();
			case 169:
				return new ExportLabel();
			case 170:
				return new Export_specsLabel();
			case 171:
				return new Export_specLabel();
			case 172:
				return new ModuleLabel();
			case 173:
				return new RestLabel();
			case 174:
				return new SpreadLabel();
			case 175:
				return new Computed_propLabel();
			case 176:
				return new TemplatelitLabel();
			case 177:
				return new Templatelit_subLabel();
			case 178:
				return new Default_valueLabel();
			case 179:
				return new Member_variable_defLabel();
			case 200:
				return new String_typeLabel();
			case 201:
				return new Boolean_typeLabel();
			case 202:
				return new Number_typeLabel();
			case 203:
				return new Function_typeLabel();
			case 204:
				return new Parameterized_typeLabel();
			case 205:
				return new Union_typeLabel();
			case 206:
				return new Any_typeLabel();
			case 208:
				return new Nullable_typeLabel();
			case 209:
				return new Void_typeLabel();
			case 210:
				return new Rest_parameter_typeLabel();
			case 211:
				return new Named_typeLabel();
			case 212:
				return new Optional_parameterLabel();
			case 213:
				return new Record_typeLabel();
			case 214:
				return new Undefined_typeLabel();
			case 215:
				return new Array_typeLabel();
			case 300:
				return new AnnotationLabel();
			case 301:
				return new PipeLabel();
			case 302:
				return new StarLabel();
			case 303:
				return new EocLabel();
			case 304:
				return new QmarkLabel();
			case 305:
				return new EllipsisLabel();
			case 306:
				return new BangLabel();
			case 307:
				return new EqualsLabel();
			case 308:
				return new LbLabel();
			case 309:
				return new LcLabel();
			case 310:
				return new ColonLabel();
			case 311:
				return new InterfaceLabel();
			case 312:
				return new Interface_extendsLabel();
			case 313:
				return new Interface_membersLabel();
			case 314:
				return new EnumLabel();
			case 315:
				return new Enum_membersLabel();
			case 1001:
				return new Placeholder1Label();
			case 1002:
				return new Placeholder2Label();
			default:
				throw new IllegalStateException("Unknown Labeltype");
		}
	}

	public static int labelToType(Label label) {
		if (label instanceof ErrorLabel) {
			return -1;
		}
		if (label instanceof ReturnLabel) {
			return 4;
		}
		if (label instanceof BitorLabel) {
			return 9;
		}
		if (label instanceof BitxorLabel) {
			return 10;
		}
		if (label instanceof BitandLabel) {
			return 11;
		}
		if (label instanceof EqLabel) {
			return 12;
		}
		if (label instanceof NeLabel) {
			return 13;
		}
		if (label instanceof LtLabel) {
			return 14;
		}
		if (label instanceof LeLabel) {
			return 15;
		}
		if (label instanceof GtLabel) {
			return 16;
		}
		if (label instanceof GeLabel) {
			return 17;
		}
		if (label instanceof LshLabel) {
			return 18;
		}
		if (label instanceof RshLabel) {
			return 19;
		}
		if (label instanceof UrshLabel) {
			return 20;
		}
		if (label instanceof AddLabel) {
			return 21;
		}
		if (label instanceof SubLabel) {
			return 22;
		}
		if (label instanceof MulLabel) {
			return 23;
		}
		if (label instanceof DivLabel) {
			return 24;
		}
		if (label instanceof ModLabel) {
			return 25;
		}
		if (label instanceof NotLabel) {
			return 26;
		}
		if (label instanceof BitnotLabel) {
			return 27;
		}
		if (label instanceof PosLabel) {
			return 28;
		}
		if (label instanceof NegLabel) {
			return 29;
		}
		if (label instanceof NewLabel) {
			return 30;
		}
		if (label instanceof DelpropLabel) {
			return 31;
		}
		if (label instanceof TypeofLabel) {
			return 32;
		}
		if (label instanceof GetpropLabel) {
			return 33;
		}
		if (label instanceof GetelemLabel) {
			return 35;
		}
		if (label instanceof CallLabel) {
			return 37;
		}
		if (label instanceof NameLabel) {
			return 38;
		}
		if (label instanceof NumberLabel) {
			return 39;
		}
		if (label instanceof StringLabel) {
			return 40;
		}
		if (label instanceof NullLabel) {
			return 41;
		}
		if (label instanceof ThisLabel) {
			return 42;
		}
		if (label instanceof FalseLabel) {
			return 43;
		}
		if (label instanceof TrueLabel) {
			return 44;
		}
		if (label instanceof SheqLabel) {
			return 45;
		}
		if (label instanceof ShneLabel) {
			return 46;
		}
		if (label instanceof RegexpLabel) {
			return 47;
		}
		if (label instanceof ThrowLabel) {
			return 49;
		}
		if (label instanceof InLabel) {
			return 51;
		}
		if (label instanceof InstanceofLabel) {
			return 52;
		}
		if (label instanceof ArraylitLabel) {
			return 63;
		}
		if (label instanceof ObjectlitLabel) {
			return 64;
		}
		if (label instanceof TryLabel) {
			return 77;
		}
		if (label instanceof Param_listLabel) {
			return 83;
		}
		if (label instanceof CommaLabel) {
			return 85;
		}
		if (label instanceof AssignLabel) {
			return 86;
		}
		if (label instanceof Assign_bitorLabel) {
			return 87;
		}
		if (label instanceof Assign_bitxorLabel) {
			return 88;
		}
		if (label instanceof Assign_bitandLabel) {
			return 89;
		}
		if (label instanceof Assign_lshLabel) {
			return 90;
		}
		if (label instanceof Assign_rshLabel) {
			return 91;
		}
		if (label instanceof Assign_urshLabel) {
			return 92;
		}
		if (label instanceof Assign_addLabel) {
			return 93;
		}
		if (label instanceof Assign_subLabel) {
			return 94;
		}
		if (label instanceof Assign_mulLabel) {
			return 95;
		}
		if (label instanceof Assign_divLabel) {
			return 96;
		}
		if (label instanceof Assign_modLabel) {
			return 97;
		}
		if (label instanceof HookLabel) {
			return 98;
		}
		if (label instanceof OrLabel) {
			return 100;
		}
		if (label instanceof AndLabel) {
			return 101;
		}
		if (label instanceof IncLabel) {
			return 102;
		}
		if (label instanceof DecLabel) {
			return 103;
		}
		if (label instanceof FunctionLabel) {
			return 105;
		}
		if (label instanceof IfLabel) {
			return 108;
		}
		if (label instanceof SwitchLabel) {
			return 110;
		}
		if (label instanceof CaseLabel) {
			return 111;
		}
		if (label instanceof Default_caseLabel) {
			return 112;
		}
		if (label instanceof WhileLabel) {
			return 113;
		}
		if (label instanceof DoLabel) {
			return 114;
		}
		if (label instanceof ForLabel) {
			return 115;
		}
		if (label instanceof BreakLabel) {
			return 116;
		}
		if (label instanceof ContinueLabel) {
			return 117;
		}
		if (label instanceof VarLabel) {
			return 118;
		}
		if (label instanceof WithLabel) {
			return 119;
		}
		if (label instanceof CatchLabel) {
			return 120;
		}
		if (label instanceof VoidLabel) {
			return 122;
		}
		if (label instanceof EmptyLabel) {
			return 124;
		}
		if (label instanceof BlockLabel) {
			return 125;
		}
		if (label instanceof LabelLabel) {
			return 126;
		}
		if (label instanceof Expr_resultLabel) {
			return 130;
		}
		if (label instanceof ScriptLabel) {
			return 132;
		}
		if (label instanceof Getter_defLabel) {
			return 147;
		}
		if (label instanceof Setter_defLabel) {
			return 148;
		}
		if (label instanceof ConstLabel) {
			return 149;
		}
		if (label instanceof DebuggerLabel) {
			return 152;
		}
		if (label instanceof Label_nameLabel) {
			return 153;
		}
		if (label instanceof String_keyLabel) {
			return 154;
		}
		if (label instanceof CastLabel) {
			return 155;
		}
		if (label instanceof Array_patternLabel) {
			return 156;
		}
		if (label instanceof Object_patternLabel) {
			return 157;
		}
		if (label instanceof ClassLabel) {
			return 158;
		}
		if (label instanceof Class_membersLabel) {
			return 159;
		}
		if (label instanceof Member_function_defLabel) {
			return 160;
		}
		if (label instanceof SuperLabel) {
			return 161;
		}
		if (label instanceof LetLabel) {
			return 162;
		}
		if (label instanceof For_ofLabel) {
			return 163;
		}
		if (label instanceof YieldLabel) {
			return 164;
		}
		if (label instanceof ImportLabel) {
			return 165;
		}
		if (label instanceof Import_specsLabel) {
			return 166;
		}
		if (label instanceof Import_specLabel) {
			return 167;
		}
		if (label instanceof Import_starLabel) {
			return 168;
		}
		if (label instanceof ExportLabel) {
			return 169;
		}
		if (label instanceof Export_specsLabel) {
			return 170;
		}
		if (label instanceof Export_specLabel) {
			return 171;
		}
		if (label instanceof ModuleLabel) {
			return 172;
		}
		if (label instanceof RestLabel) {
			return 173;
		}
		if (label instanceof SpreadLabel) {
			return 174;
		}
		if (label instanceof Computed_propLabel) {
			return 175;
		}
		if (label instanceof TemplatelitLabel) {
			return 176;
		}
		if (label instanceof Templatelit_subLabel) {
			return 177;
		}
		if (label instanceof Default_valueLabel) {
			return 178;
		}
		if (label instanceof Member_variable_defLabel) {
			return 179;
		}
		if (label instanceof String_typeLabel) {
			return 200;
		}
		if (label instanceof Boolean_typeLabel) {
			return 201;
		}
		if (label instanceof Number_typeLabel) {
			return 202;
		}
		if (label instanceof Function_typeLabel) {
			return 203;
		}
		if (label instanceof Parameterized_typeLabel) {
			return 204;
		}
		if (label instanceof Union_typeLabel) {
			return 205;
		}
		if (label instanceof Any_typeLabel) {
			return 206;
		}
		if (label instanceof Nullable_typeLabel) {
			return 208;
		}
		if (label instanceof Void_typeLabel) {
			return 209;
		}
		if (label instanceof Rest_parameter_typeLabel) {
			return 210;
		}
		if (label instanceof Named_typeLabel) {
			return 211;
		}
		if (label instanceof Optional_parameterLabel) {
			return 212;
		}
		if (label instanceof Record_typeLabel) {
			return 213;
		}
		if (label instanceof Undefined_typeLabel) {
			return 214;
		}
		if (label instanceof Array_typeLabel) {
			return 215;
		}
		if (label instanceof AnnotationLabel) {
			return 300;
		}
		if (label instanceof PipeLabel) {
			return 301;
		}
		if (label instanceof StarLabel) {
			return 302;
		}
		if (label instanceof EocLabel) {
			return 303;
		}
		if (label instanceof QmarkLabel) {
			return 304;
		}
		if (label instanceof EllipsisLabel) {
			return 305;
		}
		if (label instanceof BangLabel) {
			return 306;
		}
		if (label instanceof EqualsLabel) {
			return 307;
		}
		if (label instanceof LbLabel) {
			return 308;
		}
		if (label instanceof LcLabel) {
			return 309;
		}
		if (label instanceof ColonLabel) {
			return 310;
		}
		if (label instanceof InterfaceLabel) {
			return 311;
		}
		if (label instanceof Interface_extendsLabel) {
			return 312;
		}
		if (label instanceof Interface_membersLabel) {
			return 313;
		}
		if (label instanceof EnumLabel) {
			return 314;
		}
		if (label instanceof Enum_membersLabel) {
			return 315;
		}
		if (label instanceof Placeholder1Label) {
			return 1001;
		}
		if (label instanceof Placeholder2Label) {
			return 1002;
		}
		throw new IllegalStateException("Unknown label class");
	}
}
