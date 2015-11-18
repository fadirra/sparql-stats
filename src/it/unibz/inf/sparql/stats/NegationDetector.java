package it.unibz.inf.sparql.stats;

import org.openrdf.query.algebra.*;

/**
 * Detecting whether a query is with negation or not
 * 
 * @author Fariz Darari (fadirra@gmail.com)
 *
 */
public class NegationDetector implements QueryModelVisitor<NegationException> {
	
	/**
	 * If a node is not a negation node, then pass by
	 * 
	 * @param arg0
	 * @throws NegationException
	 */
	public void passBy(QueryModelNode arg0) throws NegationException {
		// DEBUGGING: Print what kind of query node this is
//		System.out.println("meet " + arg0.getSignature());
		arg0.visitChildren(this);
	}
	
	// Negation signature: NOT BOUND
	@Override
	public void meet(Bound arg0) throws NegationException {
		String parentSignature = arg0.getParentNode().getSignature();
		if (parentSignature.compareTo("Not") == 0)
			throw new NegationException();
	}
	
	// Negation signature: MINUS
	@Override
	public void meet(Difference arg0) throws NegationException {
		throw new NegationException();
	}
	
	// Negation signature: NOT EXISTS
	@Override
	public void meet(Exists arg0) throws NegationException {
		String parentSignature = arg0.getParentNode().getSignature();
		if (parentSignature.compareTo("Not") == 0)
			throw new NegationException();
	}
	
	@Override
	public void meet(QueryRoot arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Add arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(And arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(ArbitraryLengthPath arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Avg arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(BindingSetAssignment arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(BNodeGenerator arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Clear arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Coalesce arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Compare arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(CompareAll arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(CompareAny arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(DescribeOperator arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Copy arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Count arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Create arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Datatype arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(DeleteData arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Distinct arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(EmptySet arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Extension arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(ExtensionElem arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Filter arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(FunctionCall arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Group arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(GroupConcat arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(GroupElem arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(If arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(In arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(InsertData arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Intersection arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(IRIFunction arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(IsBNode arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(IsLiteral arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(IsNumeric arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(IsResource arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(IsURI arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Join arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Label arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Lang arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(LangMatches arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(LeftJoin arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Like arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Load arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(LocalName arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(MathExpr arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Max arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Min arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Modify arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Move arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(MultiProjection arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Namespace arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Not arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Or arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Order arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(OrderElem arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Projection arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(ProjectionElem arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(ProjectionElemList arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Reduced arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Regex arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(SameTerm arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Sample arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Service arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(SingletonSet arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Slice arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(StatementPattern arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Str arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Sum arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Union arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(ValueConstant arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(ListMemberOperator arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(Var arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meet(ZeroLengthPath arg0) throws NegationException {
		passBy(arg0);
	}

	@Override
	public void meetOther(QueryModelNode arg0) throws NegationException {
		passBy(arg0);
	}

}