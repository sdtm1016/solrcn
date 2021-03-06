package net.moraleboost.flux.eval.expr.func;

import java.util.Date;
import java.util.List;

import org.apache.solr.common.util.DateUtil;

import net.moraleboost.flux.eval.EvalContext;
import net.moraleboost.flux.eval.EvalException;

public class DateFunction extends BaseFunction
{
    public DateFunction()
    {
        super();
    }

    @Override
    public Date call(List<Object> arguments, EvalContext ctx)
            throws EvalException
    {
        if (arguments.size() == 1) {
            Object arg = arguments.get(0);
            if (arg == null) {
                return null;
            } else if (isDate(arg)) {
                return (Date)arg;
            } else if (isNumber(arg)) {
                return new Date(convertToLong((Number)arg));
            } else if (isString(arg)) {
                try {
                    return DateUtil.parseDate((String)arg);
                } catch (Exception e) {
                    throw new EvalException(e);
                }
            } else {
                throw new EvalException(
                        "Can't convert " + arg.getClass().getCanonicalName() +
                        " to a Date.");
            }
        } else {
            throw new EvalException("DATE function must take 1 argument.");
        }
    }
}
