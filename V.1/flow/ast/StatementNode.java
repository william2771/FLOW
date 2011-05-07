package flow.ast;

public abstract class StatementNode extends ASTNode {

        public Type type;
    public Type retType;

	@Override
	abstract public String toString();

}
