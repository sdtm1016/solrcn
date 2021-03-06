package net.moraleboost.flux.eval.expr.func;

import java.util.List;

import net.moraleboost.flux.eval.EvalContext;
import net.moraleboost.flux.eval.EvalException;
import net.moraleboost.flux.eval.expr.BaseExpression;

public abstract class BaseFunction extends BaseExpression implements Function
{
    public BaseFunction()
    {
        super();
    }

    public Object evaluate(EvalContext ctx) throws EvalException
    {
        return this;
    }
    
    public String toSolrQuery(EvalContext ctx) throws EvalException
    {
        throw new EvalException(
                "Function calls are not supported in WHERE expressions.");
    }

    public abstract Object call(List<Object> arguments, EvalContext ctx) throws EvalException;
}
